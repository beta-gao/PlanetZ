package com.example.planetz.LoginandRegister;

import com.example.planetz.model.AnnualFootprintData;
import com.example.planetz.model.CarbonFootprintData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private Pattern emailPattern;

    // 无参构造函数
    public LoginPresenterImpl() {
        this(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance());
    }

    // 用于测试的构造函数，支持注入 mock 的 FirebaseAuth 和 FirebaseFirestore
    public LoginPresenterImpl(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.auth = auth;
        this.firestore = firestore != null ? firestore : FirebaseFirestore.getInstance();
        this.emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void login(String email, String password) {
        if (view == null) return;

        boolean isValid = true;

        if (email == null || email.trim().isEmpty()) {
            view.showEmailError("Enter email");
            isValid = false;
        } else if (!emailPattern.matcher(email).matches()) {
            view.showEmailError("Please enter a valid email address");
            isValid = false;
        }

        if (password == null || password.trim().isEmpty()) {
            view.showPasswordError("Enter password");
            isValid = false;
        }

        if (!isValid) return;

        view.showLoading();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    view.hideLoading();
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            loadUserData(userId);
                        }
                    } else {
                        view.showLoginError("Authentication failed: wrong email or password");
                    }
                });
    }

    private void loadUserData(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        AtomicInteger taskCounter = new AtomicInteger(2); // 用于追踪两个任务的完成情况

        db.collection("carbonFootprints")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        CarbonFootprintData carbonData = CarbonFootprintData.getInstance();
                        carbonData.setUserId(userId);
                        carbonData.setUsingVehicle(documentSnapshot.getBoolean("isUsingVehicle"));
                        carbonData.setVehicleType(documentSnapshot.getString("vehicleType"));
                        carbonData.setAnnualMileage(documentSnapshot.getLong("annualMileage").intValue());
                        carbonData.setDietType(documentSnapshot.getString("dietType"));
                    }
                    if (taskCounter.decrementAndGet() == 0) {
                        view.navigateToHome();
                    }
                })
                .addOnFailureListener(e -> view.showLoginError("Failed to load CarbonFootprintData"));

        db.collection("annualFootprints")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        AnnualFootprintData annualData = AnnualFootprintData.getInstance();
                        annualData.setUserId(userId);
                        annualData.setTransportation(documentSnapshot.getDouble("transportation"));
                        annualData.setHousing(documentSnapshot.getDouble("housing"));
                        annualData.setFood(documentSnapshot.getDouble("food"));
                        annualData.setConsumption(documentSnapshot.getDouble("consumption"));
                        annualData.setTotal(documentSnapshot.getDouble("total"));
                    }
                    if (taskCounter.decrementAndGet() == 0) {
                        view.navigateToHome();
                    }
                })
                .addOnFailureListener(e -> view.showLoginError("Failed to load AnnualFootprintData"));
    }

    @Override
    public void navigateToRegister() {
        if (view != null) {
            view.navigateToRegister();
        }
    }

    @Override
    public void navigateToForgetPassword() {
        if (view != null) {
            view.navigateToForgetPassword();
        }
    }
}
