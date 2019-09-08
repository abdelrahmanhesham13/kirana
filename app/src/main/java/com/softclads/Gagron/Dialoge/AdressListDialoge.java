package com.softclads.Gagron.Dialoge;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.softclads.Gagron.Models.AdresssModel.AddressModell;
import com.softclads.Gagron.Models.AdresssModel.AdressExampleModel;
import com.softclads.Gagron.Models.ORDERMODEL;
import com.softclads.Gagron.NavItemsActivities.ShoppingCartActivity;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.OrderListAdapter;
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

public class AdressListDialoge extends Dialog {
    Dialog d;
    ShoppingCartActivity c;
    Spinner Adress_Spinner;
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> list, ids;
    Button Select, Add;
    String Sub_Coast, Total;
    ArrayList<ORDERMODEL> orderListmodels;
    OrderListAdapter orderListAdapter;
    String AddressID="";

    public AdressListDialoge(ShoppingCartActivity a, String Sub_Coast, String Total, ArrayList<ORDERMODEL> orderListmodels, OrderListAdapter orderListAdapter) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.Sub_Coast = Sub_Coast;
        this.Total = Total;
        this.orderListmodels = orderListmodels;
        this.orderListAdapter = orderListAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllAdressesServices();

        setContentView(R.layout.address_list_dialog);
        Adress_Spinner = findViewById(R.id.Adress_Spinner);


        list = new ArrayList<String>();
        list.add(

                "Select Address");
        ids = new ArrayList<String>();
        ids.add("0000");
        //list.add("Select Adress");
        //spinner2.setPrompt(getString(R.string.SelectAdress));
        dataAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Adress_Spinner.setAdapter(dataAdapter);
        Adress_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    AddressID = ids.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Select = findViewById(R.id.Select);
        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AddressID.equals("")) {
                    Hawk.put(Constants.muserAdressID, AddressID);
                    dismiss();
                    OrderConfirmationDialoge cdd = new OrderConfirmationDialoge(c
                            , "" + Sub_Coast, "" + Total, orderListmodels, orderListAdapter);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();


                } else {
                    Toast.makeText(c, "Please Choose one of your Addresses or add one", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Add = findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                AddAdressDialog cdd = new AddAdressDialog(c, "0");
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });

    }

    private void getAllAdressesServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getLoginDataService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getLoginDataService getLoginDataService = retrofit.create(Connectors.getLoginDataService.class);
        getLoginDataService.get_addresses(Hawk.get(Constants.muserEmail) + "").enqueue(new Callback<AdressExampleModel>() {
            @Override
            public void onResponse(Call<AdressExampleModel> call, Response<AdressExampleModel> response) {
                ArrayList<AddressModell> addressModell = response.body().getAddresses();
                if (addressModell != null) {
                    for (int i = 0; i < addressModell.size(); i++) {

                        list.add(addressModell.get(i).getAddress());
                        ids.add(addressModell.get(i).getId());
                    }
                    dataAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<AdressExampleModel> call, Throwable t) {

                Toast.makeText(c, "Please Check your internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
