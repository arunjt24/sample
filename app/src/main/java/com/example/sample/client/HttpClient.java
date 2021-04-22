package com.example.sample.client;

import com.example.sample.model.BorrowerResponse;
import com.example.sample.model.BranchResponse;
import com.example.sample.model.EmployeeResponse;
import com.example.sample.model.Login;
import com.example.sample.model.User;
import com.example.sample.util.Config;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class HttpClient {
    public static OkHttpClient client = new OkHttpClient.Builder()
            .authenticator((route, response) -> {
                String credential = Credentials.basic(Config.AUTH_USER_NAME, Config.AUTH_PASSWORD);
                return response.request().newBuilder().header("Authorization", credential).build();
            })
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    private static Gson gson = new Gson();
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

    public static Call<JSONObject> update_pass(JSONObject data) {
        return getServerApi().update_password(data);
    }

    public static Call<JSONObject> update_user(JSONObject data) {
        return getServerApi().update_username(data);
    }

    public static Call<BorrowerResponse> getBorrowers() {
        return getServerApi().getBorrowers();
    }

    public static Call<BorrowerResponse.Borrower> createBorrower(BorrowerResponse.Borrower borrower) {
        return getServerApi().createBorrower(borrower);
    }

    public static Call<String> editBorrower(BorrowerResponse.Borrower borrower) {
        return getServerApi().editBorrower(borrower);
    }

    public static Call<ResponseBody> downloadApk(String url) {
        return getServerApi().download(url);
    }

    public static Call<BranchResponse> getBranches() {
        return getServerApi().getBranches();
    }

    public static Call<EmployeeResponse> getEmployees() {
        return getServerApi().getEmployees();
    }

    public interface FinanceAPI {
        @GET("content")
        Call<String> getContents();

        @GET
        Call<ResponseBody> download(@Url String url);

        @GET("borrowerslist.php")
        Call<BorrowerResponse> getBorrowers();

        @GET("employeelist.php")
        Call<EmployeeResponse> getEmployees();

        @GET("allbranch.php")
        Call<BranchResponse> getBranches();

        @POST("login.php")
        Call<User> doLogin(@Body Login body);

        @POST("addborrowers.php")
        Call<BorrowerResponse.Borrower> createBorrower(@Body BorrowerResponse.Borrower borrower);

        @POST("editborrowers.php")
        Call<String> editBorrower(@Body BorrowerResponse.Borrower borrower);

        @POST("updatepassword.php")
        Call<JSONObject> update_password(@Body JSONObject body);

        @POST("updateusername.php")
        Call<JSONObject> update_username(@Body JSONObject body);

    }

}