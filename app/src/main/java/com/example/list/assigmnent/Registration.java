package com.example.list.assigmnent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.list.assigmnent.model.RegistrationRequest;
import com.example.list.assigmnent.model.RegistrationResponse;
import com.example.list.assigmnent.service.ApiManager;
import com.example.list.assigmnent.utility.Utility;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    Calendar calender;
    Calendar dateandtime;
    Date date;
    SimpleDateFormat simpleDateFormat;
    EditText et_email;
    EditText et_firstName;
    EditText et_lastName;
    EditText et_pass;
    EditText et_address;
    EditText et_dob;
    EditText et_companyName;

    private int year;
    private int month;
    private int day;
    Button regButton;
    String curDate;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    private String birthDate;
    private String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        et_dob=(EditText)findViewById(R.id.dob);
        et_dob.setEnabled(true);
        et_email=(EditText)findViewById(R.id.email);
        et_firstName=(EditText)findViewById(R.id.first_name);
        et_lastName=(EditText)findViewById(R.id.last_name);
        et_pass=(EditText)findViewById(R.id.password);
        et_address=(EditText)findViewById(R.id.address);
        et_companyName=(EditText)findViewById(R.id.company);
        regButton=(Button)findViewById(R.id.regButton);
        regButton.setOnClickListener(this);
        et_dob.setOnClickListener(this);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateandtime = Calendar.getInstance(Locale.US);
        curDate = simpleDateFormat.format(dateandtime.getTime());




    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.dob:
                onDOBClick();
                setDate(v);
                break;

            case R.id.regButton:
                registration();
                break;
            default:

                break;
        }
    }


    public void registration(){

        email=et_email.getText().toString();
        firstName=et_firstName.getText().toString();
        lastName=et_lastName.getText().toString();
        password=et_pass.getText().toString();
        address=et_address.getText().toString();
        birthDate=et_dob.getText().toString();
        companyName=et_companyName.getText().toString();

        if(email.isEmpty()){
            Utility.showAlert(Registration.this,getString(R.string.alrt_email));
        }
        else if(!Utility.isEmail(email)){
            Utility.showAlert(Registration.this,getString(R.string.alrt_validEmail));
        }
        else if(firstName.isEmpty()){
            Utility.showAlert(Registration.this,getString(R.string.alrt_first_name));
        }
        else if(lastName.isEmpty()){
            Utility.showAlert(Registration.this,getString(R.string.alrt_last_name));
        }
        else if(password.isEmpty()){
            Utility.showAlert(Registration.this,getString(R.string.alrt_password));
        }
        else if(address.isEmpty()){
            Utility.showAlert(Registration.this,getString(R.string.alrt_addr));
        }
        else if(birthDate.isEmpty()){
            Utility.showAlert(Registration.this,getString(R.string.alrt_dob));
        }
        else if(companyName.isEmpty()){
            Utility.showAlert(Registration.this,getString(R.string.alrt_cmp_nm));
        }
        else {
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setFirstName(firstName);
            registrationRequest.setLastName(lastName);
            registrationRequest.setEmail(email);
            registrationRequest.setAddress(address);
            registrationRequest.setCompanyName(companyName);
            registrationRequest.setPassword(password);
            registrationRequest.setBirthDate(birthDate);

            ApiManager.getInstance().getApiService().save_user(registrationRequest).enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                        String retval= response.body().getRetval();

                        if(retval.equals("Success")){

                            Utility.showAlert(Registration.this,getString(R.string.alrt_successRegistration));
                            Intent in=new Intent(Registration.this,Login.class);
                            startActivity(in);
                            finish();
                        }
                        else if(retval.equals("Already_Exist")){
                            Utility.showAlert(Registration.this,getString(R.string.alrt_alredyEmail));
                        }
                        else{
                            Utility.showAlert(Registration.this,getString(R.string.alrt_problemRegistration));
                        }

                    } else {
                        Utility.showAlert(Registration.this,getString(R.string.alrt_problemRegistration));
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    Utility.showAlert(Registration.this,getString(R.string.alrt_problemRegistration));
                }
            });
        }


    }

    public void onDOBClick()
    {
        calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);
    }
    public void setDate(View view)
    {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "",
                Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        // TODO Auto-generated method stub
        if (id == 999)
        {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3)
                {
                     showDate(arg1, arg2 + 1, arg3);

                }
            };

    private void showDate(int year, int month, int day)
    {
        StringBuilder seldate =new StringBuilder();
        if(day<10)
            seldate.append("0"+day);
        else
            seldate.append(day);

        seldate.append("-");
        if(month<10)
            seldate.append("0"+month);
        else
            seldate.append(month);
        seldate.append("-");
        seldate.append(year);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = seldate.toString();
        Log.e("strDate--","strDate--"+strDate);
        try{
            date = simpleDateFormat.parse(curDate);
            Date dt2 = formatter.parse(strDate);
            Log.e("dt2--","dt2--"+dt2);
            if (dt2.compareTo(date) > 0)
            {
                Toast.makeText(getApplicationContext(),"Please Select Birthdate Less Than Todays Date",Toast.LENGTH_LONG).show();
                et_dob.setText("");
            }
            else
            {
                et_dob.setText(strDate);
            }

        } catch (Exception e) {
            Log.e("Exception","error messsage"+e.getMessage());
        }
    }
}