package com.example.list.assigmnent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.list.assigmnent.model.LoginRequest;
import com.example.list.assigmnent.model.LoginResponse;
import com.example.list.assigmnent.service.ApiManager;
import com.example.list.assigmnent.utility.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    String emailid="",password="";
    EditText textEmail,textPass;
    TextView registrationTV;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (ContextCompat.checkSelfPermission(Login.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(Login.this,new String[] { Manifest.permission.READ_PHONE_STATE }, 1);
            }
        }
            textEmail = (EditText) findViewById(R.id.textEmail);
            textPass = (EditText) findViewById(R.id.textPass);
            loginButton = (Button) findViewById(R.id.loginButton);
            registrationTV=(TextView) findViewById(R.id.registrationTV);
            registrationTV.setOnClickListener(this);
            loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.loginButton:
                emailid=textEmail.getText().toString();
                password=textPass.getText().toString();
                if(emailid.isEmpty()){
                    Utility.showAlert(Login.this,getString(R.string.alrt_email));
                }
                else if(!Utility.isEmail(emailid)){
                    Utility.showAlert(Login.this,getString(R.string.alrt_validEmail));
                }
                else if(password.isEmpty()){
                    Utility.showAlert(Login.this,getString(R.string.alrt_password));
                }
                else{
                    login();
                }
                break;

            case R.id.registrationTV:

                Intent in=new Intent(Login.this,Registration.class);
                startActivity(in);
                finish();
                break;
        }
    }

    public void login (){
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setEmail(emailid);
        loginRequest.setPassword(password);

        ApiManager.getInstance().getApiService().generate_token(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    String token=response.body().getToken();
                    Log.e("token",token);

                    Bundle b=new Bundle();
                    Intent in=new Intent(Login.this,Dashboard.class);
                    b.putString("token", token);
                    b.putString("email",emailid);
                    in.putExtras(b);
                    in.putExtras(b);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Problem To Login",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 1: {

                if (permissions[0]
                        .equalsIgnoreCase(Manifest.permission.READ_PHONE_STATE))
                {
                    if (ContextCompat.checkSelfPermission( Login.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        {
                        }
                        else
                        {

                            ActivityCompat
                                    .requestPermissions(
                                            Login.this,
                                            new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                            1);
                        }
                    }
                } else if (permissions[0]
                        .equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (ContextCompat.checkSelfPermission( Login.this,
                            Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                Login.this, Manifest.permission.READ_CONTACTS)) {
                        } else {

                            ActivityCompat
                                    .requestPermissions(
                                            Login.this,
                                            new String[] { Manifest.permission.READ_CONTACTS },
                                            1);
                        }
                    }
                } else if (permissions[0]
                        .equalsIgnoreCase(Manifest.permission.READ_CONTACTS)) {
                    if (ContextCompat.checkSelfPermission( Login.this,
                            Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                Login.this, Manifest.permission.RECEIVE_SMS)) {
                        } else {

                            ActivityCompat
                                    .requestPermissions(
                                            Login.this,
                                            new String[] { Manifest.permission.RECEIVE_SMS },
                                            1);
                        }
                    }
                } else if (permissions[0]
                        .equalsIgnoreCase(Manifest.permission.RECEIVE_SMS)) {
                    if (ContextCompat.checkSelfPermission( Login.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                Login.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {
                        } else {

                            ActivityCompat
                                    .requestPermissions(
                                            Login.this,
                                            new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                                            1);
                        }
                    }
                }  else if (permissions[0]
                        .equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (ContextCompat.checkSelfPermission( Login.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                Login.this, Manifest.permission.CAMERA)) {
                        } else {

                            ActivityCompat
                                    .requestPermissions(
                                            Login.this,
                                            new String[] { Manifest.permission.CAMERA },
                                            1);
                        }
                    }
                }
                else if (permissions[0]
                        .equalsIgnoreCase(Manifest.permission.CAMERA)) {
                    if (ContextCompat.checkSelfPermission( Login.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                Login.this, Manifest.permission.CAMERA)) {
                        } else {

                            ActivityCompat
                                    .requestPermissions(
                                            Login.this,
                                            new String[] { Manifest.permission.CAMERA },
                                            1);
                        }
                    }
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
