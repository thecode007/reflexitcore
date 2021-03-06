package com.reflex.authentications.login;


import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;

/**
 * Built for handling user interactions with the LoginActivity
 * */

public class LoginPresenter implements  LoginInteractor.OnLoginDoneListener, LoginInteractor.OnLoginFieldChangeListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;

        if (loginView.isLoggedIn()) {
            loginView.navigateToHome();
        }
    }

    void validateCredentials(String email, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(email, password, this, this);
    }

    void validateFields(String email, String password) {
        if (loginView != null) {
            loginInteractor.validateFields(email, password, this);
        }
    }

    void onDestroy() {
        loginView = null;
    }


    @Override
    public void onInvalidEmail() {
        if (loginView != null) {
            loginView.setEmailError();
            loginView.hideProgress();
            loginView.lockLoginButton();
        }
    }

    @Override
    public void onInvalidPassword() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
            loginView.lockLoginButton();
        }
    }

    @Override
    public void onLockLoginButton() {
        loginView.lockLoginButton();
    }

    @Override
    public void onUnlockLoginButton() {
        loginView.unlockLoginButton();
    }

    @Override
    public void onValidEmail() {
        if (loginView != null) {
            loginView.hideEmailError();
        }
    }

    @Override
    public void onValidPassword() {
        if (loginView != null) {
            loginView.hidePasswordError();
        }
    }

    @Override
    public void onSuccess(JSONObject jsonResult) throws JSONException {

        if (loginView != null || jsonResult != null) {
            Objects.requireNonNull(loginView).setSharedPreference("user_config",jsonResult.toString());
            loginView.setSharedPreference("api_token",jsonResult.getJSONObject("data").getString("api_token"));
            loginView.setSharedPreference("id",jsonResult.getJSONObject("data").getString("id"));
            Log.i("Success",jsonResult.getJSONObject("data").getString("api_token"));
            loginView.hideProgress();
            loginView.navigateToHome();
        }
    }

    @Override
    public void onFail() {
        loginView.setFormError();
        loginView.hideProgress();
    }

    void onNavigateToRegistration() {
        loginView.navigateToRegistration();
    }

}
