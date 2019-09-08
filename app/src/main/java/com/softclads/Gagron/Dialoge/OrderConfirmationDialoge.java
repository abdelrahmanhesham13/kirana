package com.softclads.Gagron.Dialoge;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.softclads.Gagron.Models.ORDERMODEL;
import com.softclads.Gagron.Models.ORDER_TO_SEND_MODEL;
import com.softclads.Gagron.Models.UserStatusModel;
import com.softclads.Gagron.NavItemsActivities.ShoppingCartActivity;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.OrderListAdapter;
import com.softclads.Gagron.Utils.Connectors;
import com.softclads.Gagron.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderConfirmationDialoge extends Dialog {
    public ShoppingCartActivity c;
    public Dialog d;
    String Sub_Coast, Total;
    TextView Totall, Sub_total;
    Button check_out_button;
    ArrayList<ORDERMODEL> ordermodels;
    ORDER_TO_SEND_MODEL order_to_send_model;
    ArrayList<String> product_id, price, quantity;
    Map<String,String> data;
    ProgressDialog pd;
    OrderListAdapter orderListAdapter;
    public OrderConfirmationDialoge(ShoppingCartActivity a, String Sub_Coast, String Total, ArrayList<ORDERMODEL> orderListmodels , OrderListAdapter orderListAdapter) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.Sub_Coast = Sub_Coast;
        this.Total = Total;
        this.ordermodels = orderListmodels;
        this.orderListAdapter = orderListAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        data = new HashMap<>();
        product_id = new ArrayList<>();
        price = new ArrayList<>();
        quantity = new ArrayList<>();
        for (int j = 0; j < ordermodels.size(); j++) {
            product_id.add(ordermodels.get(j).getProductId());
            price.add(ordermodels.get(j).getPrice());
            quantity.add(ordermodels.get(j).getQuantity());
        }


        order_to_send_model = new ORDER_TO_SEND_MODEL(product_id, quantity, price
                ,   ""+Hawk.get(Constants.museruserID), "" + Total
                , ""+ Hawk.get(Constants.muserAdressID), "" + Sub_Coast);
        d = this;
        setContentView(R.layout.orderconfirm_dialoge);

        data.put("total_price",Total+"");
        data.put("subtotal",Sub_Coast+"");
        data.put("customer_id",""+Hawk.get(Constants.museruserID));
        data.put("address_id",""+Hawk.get(Constants.muserAdressID));

        for (int i = 0;i<product_id.size();i++){
            data.put("product_id[" + i + "]",product_id.get(i));
            data.put("quantity[" + i + "]",quantity.get(i));
            data.put("price[" + i + "]",price.get(i));
            Log.d("TTTTT", "onCreate: "+"Quantity : "+quantity.get(i)+"Price : "+price.get(i));
        }


        Totall = findViewById(R.id.Total);
        Sub_total = findViewById(R.id.Sub_total);
        Totall.setText(Total+" ₹");
        Sub_total.setText(Sub_Coast+" ₹");
        check_out_button = findViewById(R.id.check_out_button);
        check_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TTTT", "Clicked: ");
                pd=new ProgressDialog(c);
                pd.setMessage("Sending Order...");
                pd.show();

                SendOrder();

            }
        });

    }

    private void SendOrder() {
        Log.d("TTTT", "IN: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getLoginDataService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getProductsServices productsServices = retrofit.create(Connectors.getProductsServices.class);
        productsServices.getOrder(data).enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                pd.dismiss();
                UserStatusModel userStatusModel=response.body();
                Log.d("TTTT", "onResponse: ");
                Log.d("TTTT", "onResponse: "
                        +order_to_send_model.getCustomer_id()
                        +"---"+order_to_send_model.getAddress_id()
                        +"--"+order_to_send_model.getSubtotal()
                        +"--"+order_to_send_model.getTotal_price()
                        +"--"+order_to_send_model.getProduct_id()
                        +"--"+order_to_send_model.getQuantity()
                        +order_to_send_model.getPrice());
                Log.d("TTTT", "onResponse: "+response.toString()+"---------" + response.body() + "-------" + response.body().getStatus()+"-----"+userStatusModel.getStatus());

                if(userStatusModel.getStatus()){
                    Hawk.delete(Constants.ORDER_LIST);
                    d.dismiss();
                    Hawk.delete(Constants.muserAdressID);

                   orderListAdapter.notifyDataSetChanged();
                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "your Order had been send", Snackbar.LENGTH_LONG)
                            .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    c.orderListmodels.clear();
                    c.orderListAdapter.notifyDataSetChanged();



                }else{
                    pd.dismiss();
                    d.dismiss();
                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "SomeThing went Wrong", Snackbar.LENGTH_LONG)
                            .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }

            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                Log.d("TTTT", "onFail"+t.getMessage());
                View parentLayout = c.findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "SomeThing went Wrong", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                        .show();


            }
        });




                /*new Callback<ArrayList<UserStatusModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserStatusModel>> call, Response<ArrayList<UserStatusModel>> response) {
                ArrayList<UserStatusModel> orderConfirmationDialoges = response.body();
                Log.d("TTTT", "onResponse: ");
                Log.d("TTTT", "onResponse: " + response.body().size() + "-------" + response.body().get(0));
                if (orderConfirmationDialoges.get(0).getStatus()) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserStatusModel>> call, Throwable t) {
                Log.d("TTTT", "onFail");


            }
        });*/
        Log.d("TTTT", "Out: ");

    }
}
