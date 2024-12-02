package com.example.planetz.LoginandRegister;

import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordPresenterImpl implements ForgetPasswordContract.Presenter {
    private ForgetPasswordContract.View view;
    private FirebaseAuth auth;

    // Constructor with FirebaseAuth injection
    public ForgetPasswordPresenterImpl(FirebaseAuth auth) {
        this.auth = auth;
    }

    // Default constructor uses FirebaseAuth.getInstance()
    public ForgetPasswordPresenterImpl() {
        this(FirebaseAuth.getInstance());
    }

    @Override
    public void attachView(ForgetPasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void sendResetLink(String email) {
        if (view == null) return;

        if (email == null || email.trim().isEmpty()) {
            view.showEmailError("Email field can't be empty");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Please enter a valid email address");
            return;
        }

        view.showLoading();
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> {
                    view.hideLoading();
                    view.showSuccessMessage("A link has been sent to your email!");
                    view.navigateToLogin();
                })
                .addOnFailureListener(e -> {
                    view.hideLoading();
                    view.showErrorMessage("Error: " + e.getMessage());
                });
    }

    @Override
    public void navigateToLogin() {
        if (view != null) {
            view.navigateToLogin();
        }
    }
}