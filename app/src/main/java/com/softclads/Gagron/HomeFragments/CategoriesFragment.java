package com.softclads.Gagron.HomeFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softclads.Gagron.MainCategoryActivity;
import com.softclads.Gagron.Models.CategoryModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.CategroiesAdapter;
import com.softclads.Gagron.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    RecyclerView CategryRV;
    CategroiesAdapter mCategroiesAdapter;
    ArrayList<CategoryModel> categoryModels;


    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        CategryRV = view.findViewById(R.id.frgmen_catgry_RV);
        categoryModels = new ArrayList<>();
        categoryModels = Hawk.get("mMainCategriesResponse");
        mCategroiesAdapter = new CategroiesAdapter(categoryModels, getContext(), "3", new CategroiesAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Intent intent=new Intent(getContext(),MainCategoryActivity.class);
                intent.putExtra(Constants.CategorieNumb,categoryModels.get(position).getId()+"");
                startActivity(intent);

            }
        });
        CategryRV.setAdapter(mCategroiesAdapter);
        CategryRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

}
