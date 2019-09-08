package com.softclads.Gagron.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.softclads.Gagron.Models.AdressModel;
import com.softclads.Gagron.Models.CountryModel.CountryExampleModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.Utils.Connectors;
import com.softclads.Gagron.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAdressDialog extends Dialog {
    Dialog d;
    Activity c;
    EditText input_Addres,Postal_Addres;
    Button Add;
    Spinner CountrySpinner, StateSpinner;
    ArrayList<String> Country, States;
    ArrayList<String> CountryID, StatesID;
    ArrayAdapter<String> CountryAdapter, SataeAdapter;
    String countryID = "", StateID = "";

    String flag;

    public AddAdressDialog(Activity a, String flag) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.flag = flag;
    }

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_address_dialog);

        pd = new ProgressDialog(c);
        pd.setMessage("Loading..");
        d = this;
        CountrySpinner = findViewById(R.id.CountrySpinner);
        StateSpinner = findViewById(R.id.StateSpinner);
        Country = new ArrayList<>();
        Country.add("Select Country");
        States = new ArrayList<>();
        States.add("Select State");
        getCountries();
        CountryID = new ArrayList<>();
        CountryID.add("case");
        StatesID = new ArrayList<>();
        StatesID.add("case");
        Postal_Addres=findViewById(R.id.Postal_Addres);
        CountryAdapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_spinner_item, Country);
        SataeAdapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_spinner_item, States);
        CountrySpinner.setAdapter(CountryAdapter);
        StateSpinner.setAdapter(SataeAdapter);
        SataeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        CountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    getStates(CountryID.get(position));
                    countryID = CountryID.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        StateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    StateID = StatesID.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        input_Addres = findViewById(R.id.input_Addres);
        input_Addres.setMovementMethod(new ScrollingMovementMethod());
        Add = findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (input_Addres.getText().toString().equals("") || input_Addres.getText().toString() == null) {
                    Toast.makeText(c, "Please Add Address", Toast.LENGTH_SHORT).show();
                }else if (Postal_Addres.getText().toString().equals("")||Postal_Addres.getText().toString()==null){

                    Toast.makeText(c, "Please Add Postal Code", Toast.LENGTH_SHORT).show();

                }

                    else if (StateID.equals("")) {
                    Toast.makeText(c, "Please Add State", Toast.LENGTH_SHORT).show();



                } else if (countryID.equals("")) {
                    Toast.makeText(c, "Please Add Country", Toast.LENGTH_SHORT).show();


                }


                   else {
                    pd.show();

                    add_Address(input_Addres.getText().toString(),StateID,countryID,Postal_Addres.getText().toString());
                }

            }
        });
    }

    private void add_Address(final String Address,String StateID,String CountryID,String postal) {


        Log.d("TTTTTTT", "onResponse: " + "" + Hawk.get(Constants.muserFirstName)
                + Hawk.get(Constants.muserLastName)
                + Hawk.get(Constants.muserEmail)
                + Hawk.get(Constants.muserMobile)
                + Address + "---"
                + Hawk.get(Constants.muserPostalCode) + "--"
                + CountryID + "---"
                + StateID + "---"
                + "Kotok");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getLoginDataService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getLoginDataService getLoginDataService = retrofit.create(Connectors.getLoginDataService.class);
        getLoginDataService.add_address(
                "" + Hawk.get(Constants.muserFirstName)
                , "" + Hawk.get(Constants.muserLastName)
                , "" + Hawk.get(Constants.muserEmail)
                , "" + Hawk.get(Constants.muserMobile)
                , "" + Address
                , "" + postal
                , "" + CountryID
                , "" + StateID
                , "kotok").enqueue(new Callback<AdressModel>() {
            @Override
            public void onResponse(Call<AdressModel> call, Response<AdressModel> response) {
                pd.dismiss();
                Log.d("TTTTT", "onResponse: " + response.toString() + "-----" + response.body());


                AdressModel adressModel = response.body();
                if (adressModel.getStatus()) {
                    Hawk.put(Constants.muserAdressID, adressModel.getAddressId());
                   // Hawk.put(Constants.muserAddress, Address);
                    d.dismiss();
                    Log.d("TTTTT", "onResponse: " + adressModel.getStatus() + "-----" + adressModel.getAddressId());
                    if (flag.equals("1")) {
                        Intent intent = c.getIntent();
                        c.finish();
                        c.startActivity(intent);
                    }
                } else {
                    Toast.makeText(c, "the Address not added", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AdressModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(c, "Check your internet Connection and try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getStates(String country_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.setNewUser.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getLoginDataService setNewUser = retrofit.create(Connectors.getLoginDataService.class);
        setNewUser.States(country_id).enqueue(new Callback<CountryExampleModel>() {
            @Override
            public void onResponse(Call<CountryExampleModel> call, Response<CountryExampleModel> response) {
                CountryExampleModel countryExampleModel = response.body();
                for (int i = 0; i < countryExampleModel.getCountries().size(); i++) {
                    States.add(countryExampleModel.getCountries().get(i).getName());
                    StatesID.add(countryExampleModel.getCountries().get(i).getId() + "");
                }
                SataeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<CountryExampleModel> call, Throwable t) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Please Check your internet Connection", Snackbar.LENGTH_LONG)
                        .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                        .show();


            }
        });
    }

    public void getCountries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.setNewUser.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getLoginDataService setNewUser = retrofit.create(Connectors.getLoginDataService.class);
        setNewUser.Countries().enqueue(new Callback<CountryExampleModel>() {
            @Override
            public void onResponse(Call<CountryExampleModel> call, Response<CountryExampleModel> response) {
                Log.d("TTTT", "onResponse: " + response.toString() + "---");
                Log.d("TTTT", "onResponse: " + response.body().getCountries().get(0).getName());
                CountryExampleModel countryExampleModel = response.body();
                for (int i = 0; i < countryExampleModel.getCountries().size(); i++) {
                    Country.add(countryExampleModel.getCountries().get(i).getName());
                    CountryID.add(countryExampleModel.getCountries().get(i).getId() + "");
                }
                CountryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CountryExampleModel> call, Throwable t) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Please Check your internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        });


    }
}
