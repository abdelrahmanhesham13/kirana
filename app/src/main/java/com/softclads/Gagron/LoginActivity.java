package com.softclads.Gagron;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.softclads.Gagron.Models.UserStatusModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.Utils.Connectors;
import com.softclads.Gagron.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText name, pass;
    TextView forget_pass, creat_accout;
    Button log_in;
    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hawk.init(this).build();
        Hawk.put(Constants.INLOGIN,"1");
        pd= new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        name = findViewById(R.id.input_name);
        pass = findViewById(R.id.input_pass);
        forget_pass = findViewById(R.id.forget_pass);
        creat_accout = findViewById(R.id.sign_up);
        log_in = findViewById(R.id.sign_in_button);
        creat_accout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
        ImageView bar_image = findViewById(R.id.toolbarback);
        bar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Are you sure you want to exit ?", Snackbar.LENGTH_LONG)
                        .setAction("Exit", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();
                if (name.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Please Fill empty fields ", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    pd.dismiss();
                } else {
                    loginConnector(name.getText().toString() + "", pass.getText().toString() + "");
                    //Toast.makeText(LoginActivity.this, "New Intent111", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void loginConnector(String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getLoginDataService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getLoginDataService getLoginDataService = retrofit.create(Connectors.getLoginDataService.class);
        getLoginDataService.login(email, password).enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                pd.dismiss();
                UserStatusModel model = response.body();
                if (model.getStatus()) {
                    Hawk.put(Constants.museruserID, model.getUser().getUserID());
                    Hawk.put(Constants.muserFirstName, model.getUser().getFirstName());
                    Hawk.put(Constants.muserLastName, model.getUser().getLastName());
                    Hawk.put(Constants.muserAddress, model.getUser().getAddress());
                    Hawk.put(Constants.muserDateOfBirth, model.getUser().getDateOfBirth());
                    Hawk.put(Constants.muserEmail, model.getUser().getEmail());
                    Hawk.put(Constants.muserGender, model.getUser().getGender());
                    Hawk.put(Constants.muserMobile, model.getUser().getMobile());
                    Hawk.put(Constants.muserPostalCode, model.getUser().getPostalCode());
                    Hawk.put(Constants.musernewsletter, model.getUser().getNewsletter());
                    Hawk.put(Constants.muserAdressID, model.getUser().getAddressId());
                    Hawk.put(Constants.muserCountryID, model.getUser().getCountryId());
                    Hawk.put(Constants.muserStateID, model.getUser().getStateId());
                    Intent i = new Intent(getApplicationContext(), Home1Activity.class);
                    startActivity(i);
                    finish();
                    Log.i("LoginRes", response.toString() + response.body().toString() + model.getUser().getAddress() + model.getUser().getFirstName() + model.getUser().getDateOfBirth());
                } else {

                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Email or Password don't match , Please try again", Snackbar.LENGTH_LONG)
                            /*.setAction("Registration", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i=new Intent(LoginActivity.this,RegistrationActivity.class);
                                    startActivity(i);

                                }
                            })*/
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }

            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {

                pd.dismiss();
                Log.d("FAil", "onFailure: " + t.getMessage());
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Please Check your internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }
}
