package com.example.list.assigmnent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.list.assigmnent.model.SubscriptionRequest;
import com.example.list.assigmnent.model.SubscriptionResponse;
import com.example.list.assigmnent.service.ApiManager;
import com.example.list.assigmnent.utility.Constants;
import com.example.list.assigmnent.utility.LoadProgressBar;
import com.example.list.assigmnent.utility.ProgressBarManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {

    Button subScription,cancleSubscription,resumeSubscriptin;
    Checkout checkout =null;
    String token="",email="",subscriptionStatus="",subscriptionId="";
    LoadProgressBar loadProgressBar;
    TextView txtviewSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        subScription=(Button)findViewById(R.id.subScription);
        cancleSubscription=(Button)findViewById(R.id.cancleSubscription);
        resumeSubscriptin=(Button)findViewById(R.id.resumeSubscriptin);
        txtviewSub=(TextView)findViewById(R.id.txtviewSub);

        subScription.setOnClickListener(this);
        cancleSubscription.setOnClickListener(this);
        resumeSubscriptin.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        try {
            if (b != null) {
                token=b.getString("token");
                email=b.getString("email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadProgressBar= ProgressBarManager.getInstance(Dashboard.this);
        checkout = new Checkout();
        Checkout.preload(getApplicationContext());
        loadProgressBar.show();
        fetchSubscription ();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.subScription:
                loadProgressBar.show();
               if(subscriptionStatus.equalsIgnoreCase("created")) {
                    startPayment();
                }
                else {
                    startSubscription ();
                }
                break;
            case R.id.cancleSubscription:
                subscriptionCancle();
                break;
            case R.id.resumeSubscriptin:
                subscriptionCompleted();
                break;
        }
    }

    public void fetchSubscription (){
        Log.e("sud--",token+email);
        ApiManager.getInstance().getApiService().get_subscription(Constants.tokenStart +token,email).enqueue(new Callback<SubscriptionResponse>() {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {
                loadProgressBar.hide();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    Log.e("successs",response.body().getSubscriptionStatus());
                    subscriptionStatus=response.body().getSubscriptionStatus();
                    showButtons();
                } else {
                    Toast.makeText(getApplicationContext(),"Invalide Credentials",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                loadProgressBar.hide();
                Toast.makeText(getApplicationContext(),"Problem Fetch Subscription",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void startSubscription (){
        SubscriptionRequest request=new SubscriptionRequest();
        request.setEmail(email);
        ApiManager.getInstance().getApiService().create_subscription(Constants.tokenStart +token,request).enqueue(new Callback<SubscriptionResponse>() {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    Log.e("successs",response.body().getSubscriptionId());
                    subscriptionStatus=response.body().getSubscriptionStatus();
                    subscriptionId=response.body().getSubscriptionId();
                    startPayment();
                } else {
                    loadProgressBar.hide();
                    Toast.makeText(getApplicationContext(),"Invalide Credentials",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                loadProgressBar.hide();
                Toast.makeText(getApplicationContext(),"Problem To Login",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showButtons(){
        if(subscriptionStatus.equalsIgnoreCase("NO_SUBSCRIPTION") || subscriptionStatus.equalsIgnoreCase("created") ){
            txtviewSub.setText(getString(R.string.lbl_notSubscribed));
            subScription.setVisibility(View.VISIBLE);
            cancleSubscription.setVisibility(View.GONE);
            resumeSubscriptin.setVisibility(View.GONE);
        }
        else if(subscriptionStatus.equalsIgnoreCase("completed")){
            txtviewSub.setText(getString(R.string.lbl_subscribed));
            cancleSubscription.setVisibility(View.VISIBLE);
            subScription.setVisibility(View.GONE);
            resumeSubscriptin.setVisibility(View.GONE);
        }
        else if(subscriptionStatus.equalsIgnoreCase("cancelled")){
            txtviewSub.setText(getString(R.string.lbl_cancleSubscribed));
            resumeSubscriptin.setVisibility(View.VISIBLE);
            subScription.setVisibility(View.GONE);
            cancleSubscription.setVisibility(View.GONE);
        }
    }



    public void subscriptionCompleted (){

        ApiManager.getInstance().getApiService().complete_subscription(Constants.tokenStart +token,email).enqueue(new Callback<SubscriptionResponse>() {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {
                loadProgressBar.hide();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    subscriptionStatus=response.body().getSubscriptionStatus();
                    subscriptionId=response.body().getSubscriptionId();
                    showButtons();
                } else {
                    Toast.makeText(getApplicationContext(),"No Response",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Problem To Complete",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void subscriptionCancle (){

        ApiManager.getInstance().getApiService().cancle_subscription(Constants.tokenStart +token,email).enqueue(new Callback<SubscriptionResponse>() {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {
                loadProgressBar.hide();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    subscriptionStatus=response.body().getSubscriptionStatus();
                    subscriptionId=response.body().getSubscriptionId();
                    showButtons();
                } else {
                    Toast.makeText(getApplicationContext(),"No Response",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Problem To cancle",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void startPayment() {
        loadProgressBar.hide();
        checkout.setKeyID(Constants.razorpayKeyId);
        checkout.setImage(R.drawable.logo);
        final Activity activity = this;
        try {
            JSONObject request = new JSONObject();
            request.put("subscription_id", subscriptionId);
            checkout.open(activity, request);
        } catch(Exception e) {
            Log.e("sud", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(), "Subscription Successfully Done..", Toast.LENGTH_LONG).show();
        loadProgressBar.show();
        subscriptionCompleted();
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            Toast.makeText(getApplicationContext(), jsonObject.getJSONObject("error").getString("description"), Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_LONG).show();
        }
    }
}