package com.example.tdd.login;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tdd.BuildConfig;
import com.example.tdd.LoginServiceFactory;
import com.example.tdd.R;
import com.example.tdd.TestFactory;

public class MainActivity extends AppCompatActivity implements Login.View {

    private EditText userName;
    private EditText password;

    LoginPresenter loginPresenter;
    private Login.BackendService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(TestFactory.getValue());

        CheckBox check = (CheckBox) findViewById(R.id.checkBox);
        if (BuildConfig.LOGGING) {
            check.setChecked(true);
            check.setText(BuildConfig.RELEASE_TYPE + " Debug release, Logging is enabled");
        }
        else {
            check.setText(BuildConfig.RELEASE_TYPE + " Prod release, Logging is disabled");
        }

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        loginService = LoginServiceFactory.get();
        loginPresenter = new LoginPresenter(this, loginService);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.doLogin();
                setMessage(R.string.logging);
            }
        });
    }

    private void setMessage(int resId) {
        TextView tv = (TextView) findViewById(R.id.textView3);
        tv.setText(resId);
    }


    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    @Override
    public void showUserNameError(int strResId) {
        userName.setError(getString(strResId));
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showPasswordError(int strResId) {
        password.setError(getString(strResId));
    }

    @Override
    public void loginFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setMessage(R.string.login_failed);
            }
        });
    }

    @Override
    public void loginSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setMessage(R.string.login_success);
            }
        });
    }

    @VisibleForTesting
    public boolean isLoginCompleted() {
        return Login.BackendService.LOGIN_COMPLETED == loginService.getStatus();
    }
}
