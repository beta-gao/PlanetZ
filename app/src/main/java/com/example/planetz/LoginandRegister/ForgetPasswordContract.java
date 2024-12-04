package com.example.planetz.LoginandRegister;

import android.content.Context;

public interface ForgetPasswordContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showEmailError(String message);
        void showSuccessMessage(String message);
        void showErrorMessage(String message);
        void navigateToLogin();
        Context getContext();
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void sendResetLink(String email);
        void navigateToLogin();
    }
}