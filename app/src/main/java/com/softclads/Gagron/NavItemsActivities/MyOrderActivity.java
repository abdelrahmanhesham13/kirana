package com.softclads.Gagron.NavItemsActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.Dialoge.OldOrderDetailsDialoge;
import com.softclads.Gagron.Models.OldOrderListModel;
import com.softclads.Gagron.Models.OldOrderModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.OldOrderAdapter;
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

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView OrderListRV;
    OldOrderAdapter oldOrderAdapter;
    ArrayList<OldOrderModel> oldOrderModels;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        setContentView(R.layout.activity_my_order);

        oldOrderModels = new ArrayList<>();
        TextView bar_title = findViewById(R.id.toolbar_title);
        ImageView bar_image = findViewById(R.id.toolbarback);
        bar_title.setText("My Order");
        bar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getOldOrder(Hawk.get(Constants.museruserID) + "");
        OrderListRV = findViewById(R.id.OrderListRV);

        oldOrderAdapter = new OldOrderAdapter(oldOrderModels, this, new OrderListAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Hawk.put(Constants.Specific_Item,oldOrderModels.get(position).getId());
                Intent intent=new Intent(MyOrderActivity.this,OldOrderDetailsDialoge.class);
                startActivity(intent);
            }
        });
        OrderListRV.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        OrderListRV.setAdapter(oldOrderAdapter);
        oldOrderAdapter.notifyDataSetChanged();

    }

    private void getOldOrder(String ID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getLoginDataService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getProductsServices getProductsServices = retrofit.create(Connectors.getProductsServices.class);
        getProductsServices.get_my_orders(ID).enqueue(new Callback<OldOrderListModel>() {
            @Override
            public void onResponse(Call<OldOrderListModel> call, Response<OldOrderListModel> response) {
                pd.dismiss();
                OldOrderListModel oldOrderListModel = response.body();
                Log.d("TTTT", "onResponse: "+oldOrderListModel.getOrders().size());
                if (oldOrderListModel.getOrders().size()<1) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "There is no orders", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else {
                    oldOrderModels.addAll(oldOrderListModel.getOrders());
                    oldOrderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<OldOrderListModel> call, Throwable t) {
                pd.dismiss();
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Check your Internet Connection", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        });
    }
}
