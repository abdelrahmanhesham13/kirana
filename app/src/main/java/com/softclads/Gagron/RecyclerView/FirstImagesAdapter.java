
package com.softclads.Gagron.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.Dialoge.ConfirmationDialoge;
import com.softclads.Gagron.Models.ProductsModel;
import com.softclads.Gagron.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FirstImagesAdapter extends RecyclerView.Adapter<FirstImagesAdapter.ViewHolder> {

    private ArrayList<ProductsModel> homeItemsModels;
    private Context context;
    private Activity activity;
    int flag;
    private OnItemClick onItemClick;



    public FirstImagesAdapter(Activity activity,Context context, ArrayList<ProductsModel> homeItemsModels, int flag, OnItemClick onItemClick) {


        this.homeItemsModels = homeItemsModels;
        this.context = context;
        this.flag = flag;
        this.flag = flag;
        this.activity = activity;
        this.onItemClick = onItemClick;
    }

    @Override
    public FirstImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (flag == 0) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.itemhomeitem, parent, false);
            return new ViewHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemhomeitem, parent, false);

            return new ViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(FirstImagesAdapter.ViewHolder holder, final int position) {

        final ProductsModel N = homeItemsModels.get(position);

        if (true) {
            holder.off.setVisibility(View.GONE);
        } else {

            holder.off.setText("");
        }
        /////
        if (N.getName() == null) {
            holder.Description.setVisibility(View.GONE);
        } else {
            holder.Description.setText(N.getName() + "");
        }
        /////
        if (N.getOldPrice() == null) {
            holder.oldprice.setVisibility(View.GONE);
        } else {
            holder.oldprice.setText(N.getOldPrice().substring(0,N.getOldPrice().length()-2) + "₹");
            holder.oldprice.setPaintFlags(holder.oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        /////
        if (N.getPrice() == null) {
            holder.newprice.setVisibility(View.GONE);
        } else {
            holder.newprice.setText(N.getPrice().substring(0,N.getPrice().length()-2) + "₹");
        }
        /////
        holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmationDialoge cdd = new ConfirmationDialoge(activity,homeItemsModels.get(position));
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });
        /////
        if (N.getImage() == null || N.getImage().equals("")) {
            holder.Img.setVisibility(View.GONE);
        } else {
            Picasso.get().load(N.getImage()).placeholder(R.drawable.lemon1).fit().into(holder.Img);
        }
        ////
        /*holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              *//*  String[] strings = {N.getTitle(), N.getDescription(), N.getUrl(), N.getPublishedAt(), N.getUrlToImage()};
                Bundle bundle = new Bundle();
                bundle.putStringArray(Constants.mBundlearticle, strings);

                //context.startActivity(intent);
                Intent intent1 = new Intent(context, EmptyActivty.class);
                intent1.putExtras(bundle);
                context.startActivity(intent1);
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.emptyContainer, myFragment).addToBackStack(null).commit();

*//*

            }
        });*/
    }


    @Override
    public int getItemCount() {
        return homeItemsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView off, Description, unit, oldprice, newprice;
        ImageView Img;
        Button Add;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            off = (TextView) itemView.findViewById(R.id.homeitemoff);
            Description = (TextView) itemView.findViewById(R.id.homeitemdes);
            unit = (TextView) itemView.findViewById(R.id.homeitemunit);
            oldprice = (TextView) itemView.findViewById(R.id.homeitemoldprice);
            newprice = (TextView) itemView.findViewById(R.id.homeitemnewprice);
            Img = itemView.findViewById(R.id.homeitemimg);
            Add = itemView.findViewById(R.id.homeitemButton);
            item = itemView.findViewById(R.id.homeitemview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClick.setOnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void setOnItemClick(int position);
    }
}
