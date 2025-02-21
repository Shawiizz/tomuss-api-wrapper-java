package fr.shawiizz.services;

import fr.shawiizz.config.Environment;
import fr.shawiizz.exceptions.LoginException;
import okhttp3.*;
import okhttp3.java.net.cookiejar.JavaNetCookieJar;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class CASAuthService {
    private String execToken;
    private final CookieJar cookieJar;
    private final OkHttpClient client;

    public CASAuthService() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        this.cookieJar = new JavaNetCookieJar(cookieManager);
        this.client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }

    public String getExecToken() throws IOException {
        Request request = new Request.Builder()
                .url(Environment.CAS_URL + "/cas/login")
                .build();
        Response response = client.newCall(request).execute();
        try (response) {
            String responseBody = response.body().string();
            return responseBody.split("name=\"execution\" value=\"")[1].split("\"")[0];
        }
    }

    public String serviceRedirect(String serviceUrl, boolean unsafe) throws IOException {
        String url = Environment.CAS_URL + "/cas/login?service=" + serviceUrl + (unsafe ? "/?unsafe=1" : "");
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1")
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() >= 400) {
            throw new IOException("Service redirect failed");
        }
        return response.body().string();
    }

    public String getPage(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void login(String username, String password) throws IOException {
        if (execToken == null || execToken.isEmpty()) {
            Request request = new Request.Builder()
                    .url(Environment.getCasLoginUrl())
                    .build();
            try (Response response = client.newCall(request).execute()) {
                execToken = getExecToken();
            }
        }

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("lt", "")
                .add("execution", execToken)
                .add("_eventId", "submit")
                .add("submit", "SE CONNECTER")
                .build();

        Request request = new Request.Builder()
                .url(Environment.CAS_URL + "/cas/login")
                .post(formBody)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() >= 400) {
                throw new LoginException("Login failed");
            }
        }
    }
}