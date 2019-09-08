package com.softclads.Gagron.Dialoge;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.softclads.Gagron.Models.ORDERMODEL;
import com.softclads.Gagron.NavItemsActivities.ShoppingCartActivity;
import com.softclads.Gagron.R;
import com.softclads.Gagron.Utils.Constants;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EditOrderDialoge extends Dialog {
    public ShoppingCartActivity c;
    public Dialog d;
    ORDERMODEL productsModel;
    TextView name, price;
    ImageView image;
    ElegantNumberButton elegantButton;
    Button ConfirmButton;
    ArrayList<ORDERMODEL> ordermodels;
    String IMGLINK;
    int position;
    float priceflaot, All;

    public EditOrderDialoge(ShoppingCartActivity a, ORDERMODEL productsModel, int position) {
        super(a);
        this.productsModel = productsModel;
        this.position = position;
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

        //////////////////

        Picasso.get().load(productsModel.getImage()).placeholder(R.drawable.lemon1).fit().into(image);
        name.setText(productsModel.getName());
        price.setText(productsModel.getPrice() + "₹");
        elegantButton.setNumber(productsModel.getQuantity());
        elegantButton.setRange(1, 1000);

        ////////////////////

        ConfirmButton = findViewById(R.id.ConfirmButton);
        ConfirmButton.setText("Edit");
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(elegantButton.getNumber());
                priceflaot = Float.parseFloat((price.getText().subSequence(0, price.getText().length() - 1)) + "");
                All = q * priceflaot;

                Log.d("TTTT", "---" + position + "onClick: --" + All);
                ordermodels = Hawk.get(Constants.ORDER_LIST);

                ordermodels.add(new ORDERMODEL(productsModel.getProductId() + ""
                        , elegantButton.getNumber() + ""
                        , All + "", "" + productsModel.getName()
                        , "" + productsModel.getImage()
                        , "" + productsModel.getPrice()));
                //
                ordermodels.remove(position);
                ordermodels.trimToSize();

                //ordermodels.remove(productsModel);
              /*  View parentLayout = c.findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "The product has been edited", Snackbar.LENGTH_LONG)

                        .setActionTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_light))
                        .show();*/

                c.orderListmodels.clear();
                c.orderListmodels.addAll(ordermodels);
                c.orderListAdapter.notifyDataSetChanged();
                Hawk.put(Constants.ORDER_LIST, ordermodels);
                d.dismiss();


            }

        });

    }
}