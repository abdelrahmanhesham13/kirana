package com.softclads.Gagron.HomeFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.softclads.Gagron.ItemDescriptionActivity;
import com.softclads.Gagron.Models.ExampleProductsModel;
import com.softclads.Gagron.Models.ProductsModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.FirstImagesAdapter;
import com.softclads.Gagron.Utils.Connectors;
import com.softclads.Gagron.Utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    ArrayList<ProductsModel> mSearchProduct;
    FirstImagesAdapter mFirstImagesAdapter;
    RecyclerView mitemHomeItemRV;
    TextView CantFind;
    EditText SearchArea;
    ProgressDialog pd;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        SearchArea = view.findViewById(R.id.SearchArea);
        SearchArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // milliseconds

            @Override
            public void afterTextChanged(final Editable s) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Searching..");

                timer.cancel();
                timer = new Timer();
                if (s.toString().equals("")) {
                } else {
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    // TODO: do what you need here (refresh list)
                                    getActivity().runOnUiThread(new Runnable() {

                                        public void run() {

                                            getItemsBY(s.toString());
                                            pd.show();

                                        }
                                    });
                                }


                            },
                            DELAY
                    );
                }
                CantFind.setVisibility(View.GONE);


            }

        });
        CantFind = view.findViewById(R.id.CantFind);
        CantFind.setVisibility(View.GONE);
        mitemHomeItemRV = view.findViewById(R.id.Search_items);
        mSearchProduct = new ArrayList<>();
        mFirstImagesAdapter = new FirstImagesAdapter(getActivity(), getContext(), mSearchProduct, 0, new FirstImagesAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Intent intent = new Intent(getContext(), ItemDescriptionActivity.class);
                intent.putExtra(Constants.Item_Desctibtion_ID_Bundle, mSearchProduct.get(position).getId());
                getActivity().startActivity(intent);

            }
        });
        mitemHomeItemRV.setAdapter(mFirstImagesAdapter);
        mitemHomeItemRV.setLayoutManager(new GridLayoutManager(getContext(), 2));


        return view;
    }

    private void getItemsBY(final String Search) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getProductsServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getProductsServices getProductsServices = retrofit.create(Connectors.getProductsServices.class);
        getProductsServices.getSearchedProducts(Search).enqueue(new Callback<ExampleProductsModel>() {
            @Override
            public void onResponse(Call<ExampleProductsModel> call, Response<ExampleProductsModel> response) {
                pd.dismiss();
                ExampleProductsModel exampleProductsModel = response.body();
                ArrayList<ProductsModel> productsModels = new ArrayList<>();
                productsModels = exampleProductsModel.getProducts();
                if (productsModels.size() < 1) {
                    SearchFragment.this.mSearchProduct.clear();
                    CantFind.setVisibility(View.VISIBLE);
                } else {
                    SearchFragment.this.mSearchProduct.clear();
                    SearchFragment.this.mSearchProduct.addAll(productsModels);
                    mFirstImagesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ExampleProductsModel> call, Throwable t) {
                pd.dismiss();


            }
        });

    }


}
