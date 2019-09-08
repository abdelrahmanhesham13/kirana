package com.softclads.Gagron;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.softclads.Gagron.Dialoge.ConfirmationDialoge;
import com.softclads.Gagron.Models.SpecificItemModel.ItemProductModel;
import com.softclads.Gagron.Models.SpecificItemModel.SpecificItemExampleModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.Utils.Connectors;
import com.softclads.Gagron.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemDescriptionActivity extends AppCompatActivity {
    SliderLayout sliderLayout;
    TextView DesTitle, SpeciTitle, UsageTitle, SourceTitle;
    TextView Des, Speci, Usage, Source;
    String ID;
    TextView itemdescunit, itemdesName, itemdesnewprice, itemdesoldprice;
    Button itemdesButton;
    ScaleRatingBar scaleRatingBar;
    ItemProductModel productModel;
    SpecificItemExampleModel model;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        //////////
        Bundle extras = getIntent().getExtras();
        ID = extras.getString(Constants.Item_Desctibtion_ID_Bundle);
        getProductItem(ID);
        pd = new ProgressDialog(this);
        pd.setMessage("loading ..");
        pd.show();
        pd.setCancelable(false);
        /////////
        DesTitle = findViewById(R.id.itemdesDesTitle);
        SpeciTitle = findViewById(R.id.itemdesSpecialityTitle);
        UsageTitle = findViewById(R.id.itemdesUsageTitle);
        SourceTitle = findViewById(R.id.itemdesProductSourceTitle);
        itemdescunit = findViewById(R.id.itemdescunit);
        itemdesName = findViewById(R.id.itemdesName);
        itemdesnewprice = findViewById(R.id.itemdesnewprice);
        itemdesoldprice = findViewById(R.id.itemdesoldprice);
        itemdesoldprice.setPaintFlags(itemdesoldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //
        scaleRatingBar = findViewById(R.id.simpleRatingBar);
        scaleRatingBar.setClickable(false);
        scaleRatingBar.setClearRatingEnabled(false);
        //
        // scaleRatingBar.setWillNotDraw(false);

        //


        itemdesButton = findViewById(R.id.itemdesButton);
        itemdesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDialoge cdd = new ConfirmationDialoge(ItemDescriptionActivity.this, productModel);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });
        //////
        Des = findViewById(R.id.itemdesDes);
        Speci = findViewById(R.id.itemdesSpeciality);
        Usage = findViewById(R.id.itemdesUsage);
        Source = findViewById(R.id.itemdesSource);
        ////
        sliderLayout = findViewById(R.id.imageSlider);
        //******************************
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        ///
        DesTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Des.setVisibility(View.VISIBLE);
            }
        });
        SpeciTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speci.setVisibility(View.VISIBLE);
            }
        });
        UsageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usage.setVisibility(View.VISIBLE);
            }
        });
        SourceTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Source.setVisibility(View.VISIBLE);
            }
        });


    }


    private void setSliderViews(ArrayList<String> sliderViewsArraylist) {


        for (int i = 0; i < sliderViewsArraylist.size(); i++) {

            SliderView sliderView = new SliderView(ItemDescriptionActivity.this);


            if (i == i) {
                sliderView.setImageUrl(sliderViewsArraylist.get(i).replaceFirst("s", ""));
            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);


            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    private void getProductItem(String ID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getProductsServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getProductsServices getProductsServices = retrofit.create(Connectors.getProductsServices.class);
        getProductsServices.mSpecificProduct(ID).enqueue(new Callback<SpecificItemExampleModel>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<SpecificItemExampleModel> call, Response<SpecificItemExampleModel> response) {
                model = response.body();
                productModel = model.getProduct();
                if (model.getProduct() != null) {
                    pd.dismiss();
                    Log.d("TTTT", "onResponse: " +
                            model.getProduct().getName() + "------" + model.getProduct().getRating());
                    Des.setText("" + model.getProduct().getFullDescription());
                    Speci.setText("" + model.getProduct().getManufacturer().getName());
                    Hawk.put(Constants.Specific_Item_Model, model);
                    if (model.getProduct().getStockAvailable()) {
                        itemdescunit.setVisibility(View.GONE);

                    } else {
                        itemdescunit.setVisibility(View.VISIBLE);
                        itemdescunit.setText("Out of Stock");
                        itemdescunit.setTextColor(android.R.color.holo_red_light);
                    }
                    scaleRatingBar.setRating(Float.parseFloat(model.getProduct().getRating()));
                    itemdesName.setText(model.getProduct().getName());
                    itemdesnewprice.setText(model.getProduct().getPrice().substring(0,model.getProduct().getPrice().length()-2)+ " ₹");
                    itemdesoldprice.setText(model.getProduct().getOldPrice().substring(0,model.getProduct().getPrice().length()-2)+ " ₹");
                    setSliderViews(model.getProduct().getImages());
                } else {

                }
            }

            @Override
            public void onFailure(Call<SpecificItemExampleModel> call, Throwable t) {
                Log.d("TTTTT", "onFailure: "+t.getMessage());
                pd.dismiss();
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


