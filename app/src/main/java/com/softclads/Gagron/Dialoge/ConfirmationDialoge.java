package com.softclads.Gagron.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.softclads.Gagron.Models.ORDERMODEL;
import com.softclads.Gagron.Models.ProductsModel;
import com.softclads.Gagron.Models.SpecificItemModel.ItemProductModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.Utils.Constants;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ConfirmationDialoge extends Dialog {
    public Activity c;
    public Dialog d;
    ItemProductModel productModel;
    ProductsModel productsModel;
    TextView name, price;
    ImageView image;
    ElegantNumberButton elegantButton;
    Button ConfirmButton;
    ArrayList<ORDERMODEL> ordermodels;
    String IMGLINK;
    float priceflaot, All;

    public ConfirmationDialoge(Activity a, ItemProductModel productModel) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.productModel = productModel;
    }

    public ConfirmationDialoge(Activity a, ProductsModel productsModel) {
        super(a);
        this.productsModel = productsModel;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        d = this;
        setContentView(R.layout.confirmation_dialoge);

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        elegantButton = findViewById(R.id.elegantButton);


        if (productModel == null) {
            Picasso.get().load(productsModel.getImage()).placeholder(R.drawable.lemon1).fit().into(image);
            name.setText(productsModel.getName());
            price.setText(productsModel.getPrice()+"₹");
            elegantButton.setRange(1, 1000);

        } else {
            Picasso.get().load(productModel.getImages().get(0)).placeholder(R.drawable.lemon1).fit().into(image);
            name.setText(productModel.getName());
            price.setText(productModel.getPrice()+"₹");
            elegantButton.setRange(1, Integer.valueOf(productModel.getQuantity()));

        }
        ConfirmButton = findViewById(R.id.ConfirmButton);
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(elegantButton.getNumber());
                priceflaot = Float.parseFloat((price.getText().subSequence(0,price.getText().length()-1)) + "");
                All = q * priceflaot;

                if (Hawk.contains(Constants.ORDER_LIST)) {
                    Log.d("TTTT", "onClick: --" + All);
                    ordermodels = Hawk.get(Constants.ORDER_LIST);
                } else {
                    Log.d("TTTT", "onClick: --" + All);
                    ordermodels = new ArrayList<>();
                }
                if (productModel == null) {
                    ordermodels.add(new ORDERMODEL(productsModel.getId() + "", elegantButton.getNumber() + "", All + "", "" + productsModel.getName(), "" + productsModel.getImage(), "" + productsModel.getPrice()));
                    //
                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "The product has been added to your shopping cart", Snackbar.LENGTH_LONG)
                            .setAction("Shopping Cart", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_light))
                            .show();
                } else {
                    ordermodels.add(new ORDERMODEL(productModel.getId() + "", elegantButton.getNumber() + "", All + "", "" + productModel.getName(), "" + productModel.getImages().get(0), "" + productModel.getPrice()));
                    //
                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "The product has been added to your shopping cart", Snackbar.LENGTH_LONG)
                            .setAction("Shopping Cart", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(ContextCompat.getColor(c, android.R.color.holo_green_light))
                            .show();
                }
                Hawk.put(Constants.ORDER_LIST, ordermodels);
                d.dismiss();


            }

        });

    }
}
