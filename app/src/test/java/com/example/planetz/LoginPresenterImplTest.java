package com.example.planetz;

import static org.mockito.Mockito.*;

import com.example.planetz.LoginandRegister.LoginContract;
import com.example.planetz.LoginandRegister.LoginPresenterImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginPresenterImplTest {

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseFirestore mockFirestore;

    @Mock
    private LoginContract.View mockView;

    @Mock
    private Task<AuthResult> mockAuthTask;

    @Mock
    private AuthResult mockAuthResult;

    @Mock
    private FirebaseUser mockUser;

    private LoginPresenterImpl loginPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginPresenter = new LoginPresenterImpl(mockAuth, mockFirestore);
        loginPresenter.attachView(mockView);
    }

    @Test
    public void testLogin_withValidCredentials_callsFirebaseAuth() {
        // Mock successful sign-in
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthTask);
        doAnswer(invocation -> {
            OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
            when(mockAuthTask.isSuccessful()).thenReturn(true);
            when(mockAuthTask.getResult()).thenReturn(mockAuthResult);
            when(mockAuthResult.getUser()).thenReturn(mockUser);
            when(mockUser.getUid()).thenReturn("mockUserId");
            listener.onComplete(mockAuthTask);
            return null;
        }).when(mockAuthTask).addOnCompleteListener(any());

        loginPresenter.login("test@example.com", "password123");

        verify(mockView).showLoading();
        verify(mockAuth).signInWithEmailAndPassword("test@example.com", "password123");
        verify(mockView).hideLoading();
    }

    @Test
    public void testLogin_withFailedAuth_showsLoginError() {
        // Mock failed sign-in
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthTask);
        doAnswer(invocation -> {
            OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
            when(mockAuthTask.isSuccessful()).thenReturn(false);
            listener.onComplete(mockAuthTask);
            return null;
        }).when(mockAuthTask).addOnCompleteListener(any());

        loginPresenter.login("test@example.com", "wrongpassword");

        verify(mockView).showLoading();
        verify(mockAuth).signInWithEmailAndPassword("test@example.com", "wrongpassword");
        verify(mockView).hideLoading();
        verify(mockView).showLoginError("Authentication failed: wrong email or password");
    }
}
