package com.softclads.Gagron.NavItemsActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.softclads.Gagron.Dialoge.AddAdressDialog;
import com.softclads.Gagron.R;
import com.softclads.Gagron.Utils.Constants;
import com.orhanobut.hawk.Hawk;

public class MyAccountActivity extends AppCompatActivity {

    public TextView name,mobile,email,past_purchase;
    Button ChangeAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        TextView bar_title = findViewById(R.id.toolbar_title);
        ImageView bar_image = findViewById(R.id.toolbarback);
        bar_title.setText("My Account");
        bar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name=findViewById(R.id.profile_name);
        mobile=findViewById(R.id.profile_number);
        email=findViewById(R.id.profile_address);
        past_purchase=findViewById(R.id.profile_Past_purchase);
        name.setText(Hawk.get(Constants.muserFirstName)+ " " +Hawk.get(Constants.muserLastName));
        mobile.setText(Hawk.get(Constants.muserMobile)+"");
        email.setText(Hawk.get(Constants.muserAddress)+"");
        past_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyAccountActivity.this,MyOrderActivity.class);
                startActivity(i);
                finish();
            }
        });
        ChangeAddress=findViewById(R.id.ChangeAddress);
        ChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAdressDialog cdd = new AddAdressDialog(MyAccountActivity.this,"1");
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


    }
}
