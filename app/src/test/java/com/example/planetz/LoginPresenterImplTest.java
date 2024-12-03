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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginPresenterImplTest {

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseFirestore mockFirestore;

    @Mock
    private LoginContract.View mockView;

    @Mock
    private FirebaseUser mockUser;

    @Mock
    private Task<AuthResult> mockAuthTask;

    @Mock
    private AuthResult mockAuthResult;


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
    @Test
    public void testLogin_withNullView_doesNothing() {
        loginPresenter.detachView();
        loginPresenter.login("test@example.com", "password123");

        verifyNoInteractions(mockAuth);
    }

    @Test
    public void testDetachView_clearsView() {
        loginPresenter.detachView();
        loginPresenter.login("test@example.com", "password123");

        verify(mockAuth, never()).signInWithEmailAndPassword(anyString(), anyString());
        verifyNoInteractions(mockView);
    }

    @Test
    public void testNavigateToRegister_callsViewNavigateToRegister() {
        loginPresenter.navigateToRegister();
        verify(mockView).navigateToRegister();
    }

    @Test
    public void testNavigateToForgetPassword_callsViewNavigateToForgetPassword() {
        loginPresenter.navigateToForgetPassword();
        verify(mockView).navigateToForgetPassword();
    }






}
