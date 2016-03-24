package com.example.tdd;

import android.test.AndroidTestCase;

import com.example.tdd.login.Login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Pavan.VijayaNar on 23-Mar-16.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest extends AndroidTestCase{
    LoginServiceImpl loginService;


    @Mock
    private Login.ServiceCallback loginServiceCallback;

    @Mock
    private OkHttpClient client;

    @Captor
    private ArgumentCaptor<Callback> callbackArgumentCaptor;

    @Before
    public void initialize() {
        //MockitoAnnotations.initMocks(this);
        loginService = new LoginServiceImpl(client);

    }

    @Test
    public void shouldCallbackLoginFailed() {

        Call call = mock(Call.class);
        when(client.newCall(any(Request.class))).thenReturn(call);

        /*when(mClient.newCall(any(Request.class)).execute())
                .thenReturn(new Response.Builder()
                        .request(new Request.Builder()
                                .url("http://test.com")
                                .build())
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .body(ResponseBody.create(MediaType.parse("text/html"), ""))
                        .build());*/

        loginService.doLogin("John", "Doe", loginServiceCallback);

        verify(call).enqueue(callbackArgumentCaptor.capture());

        callbackArgumentCaptor.getValue().onFailure(call, null);

        verify(loginServiceCallback).onLoginFailed();
    }


    @Test
    public void shouldCallbackLoginSuccess() {

        Call call = mock(Call.class);
        when(client.newCall(any(Request.class))).thenReturn(call);

        /*when(mClient.newCall(any(Request.class)).execute())
                .thenReturn(new Response.Builder()
                        .request(new Request.Builder()
                                .url("http://test.com")
                                .build())
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .body(ResponseBody.create(MediaType.parse("text/html"), ""))
                        .build());*/

        loginService.doLogin("John", "Doe", loginServiceCallback);

        verify(call).enqueue(callbackArgumentCaptor.capture());
        Response.Builder builder =new Response.Builder();
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.url("http://test.com").build();
        Response response = builder.request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.parse("text/html"), ""))
                .build();
        try {
            callbackArgumentCaptor.getValue().onResponse(call, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        verify(loginServiceCallback).onLoginSuccess();

    }

}
