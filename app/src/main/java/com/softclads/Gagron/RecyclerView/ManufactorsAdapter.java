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

import com.softclads.Gagron.Models.Manufacturer;
import com.softclads.Gagron.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ManufactorsAdapter extends RecyclerView.Adapter<ManufactorsAdapter.ViewHolder> {


    private ArrayList<Manufacturer> categoryModels;
    private Context context;
    private String flag;
    private OnItemClick mOnItemClick;

    public ManufactorsAdapter(ArrayList<Manufacturer> categoryModels, Context context, String flag, OnItemClick mOnItemClick) {
        this.categoryModels = categoryModels;
        this.context = context;
        this.flag = flag;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public ManufactorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categry_item, parent, false);
        return new ManufactorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManufactorsAdapter.ViewHolder holder, int position) {
        final Manufacturer N = categoryModels.get(position);
        if (N.getId()!=null||!N.getName().equals(""))
            holder.catgrytitle.setText(N.getName());
        if (N.getImage()!=null||!N.getImage().equals(""))
            Picasso.get().load(N.getImage()).fit().placeholder(R.drawable.lemon1).fit().into(holder.catgryImage);
        else{}
            //  holder.catgryImage.setVisibility(View.GONE);



    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView catgryImage;
        TextView catgrytitle;
        LinearLayout catgryLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            catgryImage = itemView.findViewById(R.id.catgry_item_img);
            catgrytitle = itemView.findViewById(R.id.catgry_item_txt);
            catgryLayout = itemView.findViewById(R.id.Categry_item_ID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClick.setOnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void setOnItemClick(int position);
    }
}
