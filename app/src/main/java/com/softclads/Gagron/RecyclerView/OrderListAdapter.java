package com.softclads.Gagron.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.Dialoge.EditOrderDialoge;
import com.softclads.Gagron.Models.ORDERMODEL;
import com.softclads.Gagron.NavItemsActivities.ShoppingCartActivity;
import com.softclads.Gagron.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<ORDERMODEL> ordermodels;
    private Context context;
    private OrderListAdapter.OnItemClick mOnItemClick;
     ShoppingCartActivity a;
    //private Activity activity;

    public OrderListAdapter(ArrayList<ORDERMODEL> ordermodels, Context context, ShoppingCartActivity a,OrderListAdapter.OnItemClick mOnItemClick) {
        this.ordermodels = ordermodels;
        this.context = context;
        this.mOnItemClick=mOnItemClick;
        this.a=a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ORDERMODEL ordermodel = ordermodels.get(position);

        holder.total.setText(ordermodel.getTotalPrice() +" ₹");
        holder.Description.setText(ordermodel.getName());
        holder.Quantity.setText(ordermodel.getQuantity());
//        holder.oldprice.setVisibility(View.GONE);
        holder.newprice.setText(ordermodel.getPrice().substring(0,ordermodel.getPrice().length()-2) +" ₹");
        Picasso.get().load(ordermodel.getImage()).placeholder(R.drawable.lemon1).fit().into(holder.Img);
        // holder.Img.setVisibility(View.GONE);
        holder.Add.setText("Delete");
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditOrderDialoge cdd = new EditOrderDialoge(a,ordermodel,position);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
       /* holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordermodels.remove(position);

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return ordermodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView total, Description, Quantity, oldprice, newprice;
        ImageView Img;
        Button Add;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            total = (TextView) itemView.findViewById(R.id.Total);
            Description = (TextView) itemView.findViewById(R.id.homeitemdes);
            Quantity = (TextView) itemView.findViewById(R.id.Quantity);
         //   oldprice = (TextView) itemView.findViewById(R.id.homeitemoldprice);
            newprice = (TextView) itemView.findViewById(R.id.homeitemnewprice);
            Img = itemView.findViewById(R.id.homeitemimg);
            Add = itemView.findViewById(R.id.homeitemButton);
            Add.setOnClickListener(this);
            item = itemView.findViewById(R.id.homeitemview);

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

