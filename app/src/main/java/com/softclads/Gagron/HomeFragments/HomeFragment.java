package com.softclads.Gagron.HomeFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softclads.Gagron.ItemDescriptionActivity;
import com.softclads.Gagron.MainCategoryActivity;
import com.softclads.Gagron.Models.CategoryModel;
import com.softclads.Gagron.Models.ExampleCatgModel;
import com.softclads.Gagron.Models.ExampleMainFactorModel;
import com.softclads.Gagron.Models.ExampleProductsModel;
import com.softclads.Gagron.Models.Manufacturer;
import com.softclads.Gagron.Models.ProductsModel;
import com.softclads.Gagron.Models.SliderImageModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.RecyclerView.CategroiesAdapter;
import com.softclads.Gagron.RecyclerView.FirstImagesAdapter;
import com.softclads.Gagron.RecyclerView.ManufactorsAdapter;
import com.softclads.Gagron.Utils.Connectors;
import com.softclads.Gagron.Utils.Constants;
import com.softclads.Gagron.categoryItemsActivties;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView mitemHomeItemRV, mitemHomeItemRV1, mitemHomeItemRV2, CategryRV;
    FirstImagesAdapter mfirstImagesAdapterm, mfirstImagesAdapterm1, mfirstImagesAdapterm2, mfirstImagesAdapterm3;
    ArrayList<ProductsModel> mFeaturedProduct, mBestSellProduct;
    ArrayList<Manufacturer> mhomeManufactor;
    SliderLayout sliderLayout;
    ImageView allCatgries;
    LinearLayout searchbarhome;
    CategroiesAdapter mCategroiesAdapter;
    ManufactorsAdapter mMainfactorAdapter;
    ArrayList<CategoryModel> categoryModels;
    OnSearchBarHomeClicked mOnSearchBarHomeClicked;
    OnCategroiesHomeClicked mOnCategroiesHomeClicked;
    ArrayList<String> SliderImagess;
    SliderImageModel SliderImages;
    ProgressDialog pd;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

////////////////////////
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();
        getSliderImages();
        sliderLayout = view.findViewById(R.id.imageSliderHomeFragment);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :


        ////////////////////////

        ////


        mitemHomeItemRV = view.findViewById(R.id.home_items);
        mitemHomeItemRV1 = view.findViewById(R.id.home_items1);
        mitemHomeItemRV2 = view.findViewById(R.id.home_items2);

        ///////////

        //////////
        searchbarhome = view.findViewById(R.id.searchbarhome);

        //////////
        searchbarhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSearchBarHomeClicked.setOnSearchBarHomeClicked(1);
            }


        });

        //////////
        //RecyclerView of Catgries
        CategryRV = view.findViewById(R.id.Main_Categories_home);
        categoryModels = new ArrayList<>();
       // if (Hawk.get("mMainCategriesResponse") == null)
            getCategries("1");
        /*else {
           // categoryModels = Hawk.get("mMainCategriesResponse");
        }
        */
        getItemsBY("1");
        getItemsBY("2");
        mCategroiesAdapter = new CategroiesAdapter(categoryModels, getContext(), "1", new CategroiesAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Log.d("ONCLICK", "cocoyCoco");
                Intent intent = new Intent(getContext(), MainCategoryActivity.class);
                intent.putExtra(Constants.CategorieNumb, categoryModels.get(position).getId() + "");
                startActivity(intent);
            }
        });
        CategryRV.setAdapter(mCategroiesAdapter);
        CategryRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        allCatgries = view.findViewById(R.id.homesection3button);
        allCatgries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCategroiesHomeClicked.setOnCateriesHomeClicked(1);
            }
        });

        ////////
        mFeaturedProduct = new ArrayList<>();
        mBestSellProduct = new ArrayList<>();

        mhomeManufactor = new ArrayList<>();
        getManufactorItems();
        mMainfactorAdapter = new ManufactorsAdapter(mhomeManufactor, getContext(), "1", new ManufactorsAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
            /*    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), ItemDescriptionActivity.class);
                startActivity(i);
            */
                Intent i = new Intent(getContext(), categoryItemsActivties.class);
                i.putExtra(Constants.Item_ID_Bundle, mhomeManufactor.get(position).getId() + "");
                i.putExtra(Constants.Item_Type_Bundle, "MANUFACT");
                Hawk.put(Constants.Name_name,mhomeManufactor.get(position).getName());
                startActivity(i);
            }
        });
        mitemHomeItemRV.setAdapter(mMainfactorAdapter);
        mitemHomeItemRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
/////////////////////////////
        mfirstImagesAdapterm1 = new FirstImagesAdapter(getActivity(),getContext(), mFeaturedProduct, 0, new FirstImagesAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Intent intent = new Intent(getContext(), ItemDescriptionActivity.class);
                intent.putExtra(Constants.Item_Desctibtion_ID_Bundle, mFeaturedProduct.get(position).getId());
                getActivity().startActivity(intent);

            }
        });
        mitemHomeItemRV1.setAdapter(mfirstImagesAdapterm1);
        mitemHomeItemRV1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
/////////////////////////////
        mfirstImagesAdapterm2 = new FirstImagesAdapter(getActivity(),getContext(), mBestSellProduct, 0, new FirstImagesAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Intent intent = new Intent(getContext(), ItemDescriptionActivity.class);
                intent.putExtra(Constants.Item_Desctibtion_ID_Bundle, mBestSellProduct.get(position).getId());
                getActivity().startActivity(intent);


            }
        });
        mitemHomeItemRV2.setAdapter(mfirstImagesAdapterm2);
        mitemHomeItemRV2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
/////////////////////////////

        return view;
    }

    private void getCategries(String type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getCategoriesService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getCategoriesService getCategoriesService = retrofit.create(Connectors.getCategoriesService.class);
        getCategoriesService.getCatries(type).enqueue(new Callback<ExampleCatgModel>() {
            @Override
            public void onResponse(@NonNull Call<ExampleCatgModel> call, @NonNull Response<ExampleCatgModel> response) {
                ExampleCatgModel categoryModel = response.body();
                ArrayList<CategoryModel> categoryModels = new ArrayList<>();
                if (categoryModel != null) {
                    categoryModels = (ArrayList<CategoryModel>) categoryModel.getCategories();
                }
                HomeFragment.this.categoryModels.addAll(categoryModels);
                mCategroiesAdapter.notifyDataSetChanged();
                Hawk.put("mMainCategriesResponse", categoryModels);
            }

            @Override
            public void onFailure(@NonNull Call<ExampleCatgModel> call, @NonNull Throwable t) {
            }
        });
    }

    private void getItemsBY(final String type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getProductsServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getProductsServices getProductsServices = retrofit.create(Connectors.getProductsServices.class);
        getProductsServices.getFeaturedProducts(type).enqueue(new Callback<ExampleProductsModel>() {
            @Override
            public void onResponse(Call<ExampleProductsModel> call, Response<ExampleProductsModel> response) {
                ExampleProductsModel exampleProductsModel = response.body();
                ArrayList<ProductsModel> productsModels = new ArrayList<>();
                productsModels = exampleProductsModel.getProducts();
                if (type.equals("1")) {
                    HomeFragment.this.mFeaturedProduct.addAll(productsModels);
                    mfirstImagesAdapterm1.notifyDataSetChanged();
                    Log.d("Response_products", "onResponse: " + productsModels.size() + productsModels.get(0).getImage());
                } else {

                    HomeFragment.this.mBestSellProduct.addAll(productsModels);
                    mfirstImagesAdapterm2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ExampleProductsModel> call, Throwable t) {

            }
        });

    }

    private void getManufactorItems() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getMainfactorServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getMainfactorServices getMainfactorServices = retrofit.create(Connectors.getMainfactorServices.class);
        getMainfactorServices.getMainfactor().enqueue(new Callback<ExampleMainFactorModel>() {
            @Override
            public void onResponse(@NonNull Call<ExampleMainFactorModel> call, @NonNull Response<ExampleMainFactorModel> response) {
                pd.dismiss();
                ExampleMainFactorModel exampleCatgModel = response.body();
                ArrayList<Manufacturer> manufacturers = new ArrayList<>();
                if (exampleCatgModel != null) {
                    manufacturers = (ArrayList<Manufacturer>) exampleCatgModel.getManufacturers();
                }
                HomeFragment.this.mhomeManufactor.addAll(manufacturers);
                mMainfactorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ExampleMainFactorModel> call, Throwable t) {

            }
        });
    }

    private void getSliderImages() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getMainfactorServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getMainfactorServices getMainfactorServices = retrofit.create(Connectors.getMainfactorServices.class);
        getMainfactorServices.getSliderImages().enqueue(new Callback<SliderImageModel>() {
            @Override
            public void onResponse(Call<SliderImageModel> call, Response<SliderImageModel> response) {
                SliderImages = new SliderImageModel();
                SliderImages = response.body();
                SliderImagess = new ArrayList<>();
                SliderImagess = SliderImages.getSliderImages();
                setSliderViews(SliderImagess);
                Log.d("Images", "onResponse: " + SliderImagess.get(0));

            }

            @Override
            public void onFailure(Call<SliderImageModel> call, Throwable t) {

            }
        });

    }

    public interface OnSearchBarHomeClicked {
        void setOnSearchBarHomeClicked(int type);
    }

    public void setOnSearchBarHomeClicked(OnSearchBarHomeClicked mOnSearchBarHomeClicked) {
        this.mOnSearchBarHomeClicked = mOnSearchBarHomeClicked;
    }

    public interface OnCategroiesHomeClicked {
        void setOnCateriesHomeClicked(int type);
    }

    public void setOnCateriesHomeClicked(OnCategroiesHomeClicked mOnCategroiesHomeClicked) {
        this.mOnCategroiesHomeClicked = mOnCategroiesHomeClicked;
    }


    private void setSliderViews(ArrayList<String> sliderViewsArraylist) {


        for (int i = 0; i < sliderViewsArraylist.size(); i++) {

            SliderView sliderView = new SliderView(getContext());

            //sliderView.setImageUrl(sliderViewsArraylist.get(i));

            if (i == i) {
                sliderView.setImageUrl(sliderViewsArraylist.get(i).replaceFirst("", ""));
            }
                 sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);


            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
}