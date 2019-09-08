package com.softclads.Gagron.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.Models.SpecificItemModel.ItemReviewModel;
import com.softclads.Gagron.R;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private ArrayList<ItemReviewModel> reviewModels;
    private Context context;

    public ReviewsAdapter(ArrayList<ItemReviewModel> reviewModels, Context context) {
        this.reviewModels = reviewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catgryImage;
        TextView catgrytitle;
        LinearLayout catgryLayout;

        public ViewHolder(View itemView) {
            super(itemView);
           }


    }
}
