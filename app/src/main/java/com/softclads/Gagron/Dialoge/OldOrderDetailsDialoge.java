package com.softclads.Gagron.Dialoge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.Models.SpecificOldOrderModel.Example;
import com.softclads.Gagron.Models.SpecificOldOrderModel.Product;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.SpecificOldOrderAdapter;
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

public class OldOrderDetailsDialoge extends AppCompatActivity {
    ProgressDialog pd;
    String i;
    RecyclerView OrderRecyclerView;
    public SpecificOldOrderAdapter orderListAdapter;
    public ArrayList<Product> orderListmodels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_dialoge_details);

        TextView bar_title = findViewById(R.id.toolbar_title);
        ImageView bar_image = findViewById(R.id.toolbarback);
        bar_title.setText("Order Details");
        bar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        pd=new ProgressDialog(this);
        pd.setMessage("Loading..");
        pd.show();
        i= Hawk.get(Constants.Specific_Item);
        OrderRecyclerView = findViewById(R.id.OrderRV);
        orderListmodels = new ArrayList<>();
        orderListAdapter = new SpecificOldOrderAdapter(orderListmodels, this);
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        OrderRecyclerView.setAdapter(orderListAdapter);
        getOrder(i);

    }

    private void getOrder(String order_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getLoginDataService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getLoginDataService getLoginDataService = retrofit.create(Connectors.getLoginDataService.class);
        getLoginDataService.get_my_order(order_id).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                pd.dismiss();
                Log.d("TTTT", "onResponse: "+response.body()+"--"+response.toString());
                Example order = response.body();
                if (order.getOrder().getProducts().size() > 1) {
                    Log.d("TTTT", "onResponse: "+order.getOrder().getProducts().size());

                    orderListmodels.addAll(order.getOrder().getProducts());
                    orderListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                pd.dismiss();


            }
        });
                 /*  Connectors.getLoginDataService getLoginDataService = retrofit.create(Connectors.getLoginDataService.class);
        getLoginDataService.l*/
    }
}
