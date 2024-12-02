package com.example.planetz.LoginandRegister;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;
    private FirebaseAuth auth;

    public LoginPresenterImpl() {
        this(FirebaseAuth.getInstance());
    }

    public LoginPresenterImpl(FirebaseAuth auth) {
        this.auth = auth;
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
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
                            UserManager.getInstance(view.getContext()).setUserId(user.getUid());
                            view.showLoginSuccess();
                            view.navigateToHome();
                        }
                    } else {
                        view.showLoginError("Authentication failed: wrong email or password");
                    }
                });
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