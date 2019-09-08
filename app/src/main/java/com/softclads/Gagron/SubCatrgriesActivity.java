package com.softclads.Gagron;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softclads.Gagron.Models.CategoryModel;
import com.softclads.Gagron.Models.ExampleSubCatgModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.CategroiesAdapter;
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

public class SubCatrgriesActivity extends AppCompatActivity {

    RecyclerView SubCategryRV;
    CategroiesAdapter mSubCatrgryAdapter;
    ArrayList<CategoryModel> categoryModels;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catrgries);
        TextView bar_title = findViewById(R.id.toolbar_title);
        ImageView bar_image = findViewById(R.id.toolbarback);
        bar_title.setText("SubCategories");
        pd=new ProgressDialog(this);
        pd.setMessage("Loading..");
        pd.show();
        bar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final String falg;
        falg = getIntent().getStringExtra(Constants.SubCategorieNumb);
        getSubCatgries(falg);

        categoryModels=new ArrayList<>();
        SubCategryRV = findViewById(R.id.SubCatgryRV);
        mSubCatrgryAdapter = new CategroiesAdapter(categoryModels, getApplicationContext(), "3", new CategroiesAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Intent intent=new Intent(getApplicationContext(),categoryItemsActivties.class);
                intent.putExtra(Constants.Item_Type_Bundle,"SUBCAT");
                intent.putExtra(Constants.Item_ID_Bundle,categoryModels.get(position).getId()+"");
                Hawk.put(Constants.Name_name,categoryModels.get(position).getName());
                startActivity(intent);

            }
        });
        SubCategryRV.setAdapter(mSubCatrgryAdapter);
        SubCategryRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }
    public void getSubCatgries(final String Catgry) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getSubCategroiesServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getSubCategroiesServices getSubCategroiesServices = retrofit.create(Connectors.getSubCategroiesServices.class);
        getSubCategroiesServices.getSubCatries(Catgry + "").enqueue(new Callback<ExampleSubCatgModel>() {
            @Override
            public void onResponse(@NonNull Call<ExampleSubCatgModel> call, @NonNull Response<ExampleSubCatgModel> response) {
                pd.dismiss();
                ExampleSubCatgModel exampleCatgModel = response.body();
                ArrayList<CategoryModel> categoryModels1 = null;
                if (exampleCatgModel != null) {
                    categoryModels1 = (ArrayList<CategoryModel>) exampleCatgModel.getSubcategories();
                    if(exampleCatgModel.getSubcategories().isEmpty()||exampleCatgModel.getSubcategories()==null){

                        Intent intent=new Intent(getApplicationContext(),categoryItemsActivties.class);
                        finish();
                        intent.putExtra(Constants.Item_Type_Bundle,"SUBCAT");
                        intent.putExtra(Constants.Item_ID_Bundle,Catgry+"");
                        startActivity(intent);
                    }
                }
                Log.i("SUBCATGSize", categoryModels1.size()+"");

                categoryModels.clear();
                categoryModels.addAll(categoryModels1);
                mSubCatrgryAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<ExampleSubCatgModel> call, Throwable t) {
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
