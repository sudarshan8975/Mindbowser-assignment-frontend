package com.example.list.assigmnent.service;

import com.example.list.assigmnent.model.SubscriptionRequest;
import com.example.list.assigmnent.model.SubscriptionResponse;
import com.example.list.assigmnent.model.LoginRequest;
import com.example.list.assigmnent.model.LoginResponse;
import com.example.list.assigmnent.model.RegistrationRequest;
import com.example.list.assigmnent.model.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Services {
    @POST("saveuser")
    Call<RegistrationResponse> save_user(@Body RegistrationRequest registrationRequest);

    @POST("generate-token")
    Call<LoginResponse> generate_token(@Body LoginRequest loginRequest);

    @GET("get-subscription/{email}")
    Call<SubscriptionResponse> get_subscription(@Header("Authorization") String token,@Path("email")  String email);

    @POST("create-subscription")
    Call<SubscriptionResponse> create_subscription(@Header("Authorization")  String token,@Body SubscriptionRequest request);

    @PUT("complete-subscription/{email}")
    Call<SubscriptionResponse> complete_subscription(@Header("Authorization")  String token,@Path("email")  String email);

    @PUT("cancle-subscription/{email}")
    Call<SubscriptionResponse> cancle_subscription(@Header("Authorization")  String token,@Path("email")  String email);

}
