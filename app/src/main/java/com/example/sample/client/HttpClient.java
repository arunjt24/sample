package com.example.sample.client;

import com.example.sample.model.Borrower;
import com.example.sample.model.Login;
import com.example.sample.model.BorrowerResponse;
import com.example.sample.model.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.example.sample.util.Config;

import org.jetbrains.annotations.NotNull;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class HttpClient {
    private static Gson gson = new Gson();
    public static OkHttpClient client = new OkHttpClient.Builder()
            .authenticator((route, response) -> {
                String credential = Credentials.basic(Config.AUTH_USER_NAME, Config.AUTH_PASSWORD);
                return response.request().newBuilder().header("Authorization", credential).build();
            })
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    private static Retrofit retrofit;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.SERVER_BASE_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static FinanceAPI getServerApi() {
        return getClient().create(FinanceAPI.class);
    }

    public static Call<User> login(Login login) {
        System.out.println(login.getUserName());
        System.out.println(login.getUserPassword());
        return getServerApi().doLogin(login);
    }

    public static Call<BorrowerResponse> getBorrowers() {
        return getServerApi().getBorrowers();
    }

    public static Call<Borrower> createBorrower(Borrower borrower) {
        return getServerApi().createBorrower(borrower);
    }

    public static Call<Borrower> editBorrower(Borrower borrower) {
        return getServerApi().editBorrower(borrower);
    }

    public static Call<ResponseBody> downloadApk(String url) {
        return getServerApi().download(url);
    }

    public interface FinanceAPI {
        @GET("content")
        Call<String> getContents();

        @GET
        Call<ResponseBody> download(@Url String url);

        @GET("borrowerslist.php")
        Call<BorrowerResponse> getBorrowers();

        @POST("login.php")
        Call<User> doLogin(@Body Login body);

        @POST("addborrowers.php")
        Call<Borrower> createBorrower(@Body Borrower borrower);

        @POST("editborrowers.php")
        Call<Borrower> editBorrower(@Body Borrower borrower);
    }

}