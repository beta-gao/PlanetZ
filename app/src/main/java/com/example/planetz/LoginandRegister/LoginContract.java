package com.example.planetz.LoginandRegister;

import android.content.Context;

public interface LoginContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showLoginSuccess();
        void showLoginError(String message);
        void navigateToHome();
        void navigateToRegister();
        void navigateToForgetPassword();
        void showEmailError(String message);
        void showPasswordError(String message);
        Context getContext();
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void login(String email, String password);
        void navigateToRegister();
        void navigateToForgetPassword();
    }
}