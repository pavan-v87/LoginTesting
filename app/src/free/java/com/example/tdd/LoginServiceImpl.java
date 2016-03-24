package com.example.tdd;

import com.example.tdd.login.Login;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pavan.VijayaNar on 23-Mar-16.
 */
class LoginServiceImpl implements Login.BackendService, Callback {

    public static final String URL = "www.google.com";
    private final OkHttpClient httpClient;
    private Login.ServiceCallback loginReqCallback;

    private int status;

    public LoginServiceImpl(OkHttpClient client) {
        httpClient = client;
    }

    @Override
    public void doLogin(String userName, String password, Login.ServiceCallback callback) {
        status = LOGIN_STARTED;
        loginReqCallback = callback;
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        httpClient.newCall(request).enqueue(this);
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        loginReqCallback.onLoginFailed();
        status = LOGIN_COMPLETED;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        loginReqCallback.onLoginSuccess();
        status = LOGIN_COMPLETED;
    }
}
