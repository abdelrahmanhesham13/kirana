package com.softclads.Gagron.NavItemsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softclads.Gagron.R;

public class AdressBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_book);
        TextView bar_title = findViewById(R.id.toolbar_title);
        ImageView bar_image = findViewById(R.id.toolbarback);
        bar_title.setText("Adress Book");
        bar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
