package com.softclads.Gagron.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.Models.SpecificOldOrderModel.Product;
import com.softclads.Gagron.R;

import java.util.ArrayList;

public class SpecificOldOrderAdapter extends RecyclerView.Adapter<SpecificOldOrderAdapter.ViewHolder> {
    private ArrayList<Product> ordermodels;
    private Context context;

    public SpecificOldOrderAdapter(ArrayList<Product> ordermodels, Context context) {
        this.ordermodels = ordermodels;
        this.context = context;
    }

    @NonNull
    @Override
    public SpecificOldOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sepecific_order_items_list, parent, false);
        return new SpecificOldOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificOldOrderAdapter.ViewHolder holder, int position) {
        final Product ordermodel = ordermodels.get(position);

       /* DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float twoDigitsF = Float.valueOf(decimalFormat.format(f));
       */
       holder.total.setText(ordermodel.getTotalPrice().substring(0,ordermodel.getTotalPrice().length()-2) + " ₹");
        holder.Description.setText(ordermodel.getName());
        holder.Quantity.setText(ordermodel.getQuantity());
        // holder.oldprice.setVisibility(View.GONE);
        holder.newprice.setText(ordermodel.getUnitPrice().substring(0,ordermodel.getUnitPrice().length()-2) + " ₹");
        // holder.Img.setVisibility(View.GONE);
        holder.Img.setVisibility(View.GONE);
        holder.Add.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return ordermodels.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView total, Description, Quantity, oldprice, newprice;
        ImageView Img;
        Button Add;
        LinearLayout item;
        CardView cdView;

        public ViewHolder(View itemView) {
            super(itemView);
            total = (TextView) itemView.findViewById(R.id.Total);
            Description = (TextView) itemView.findViewById(R.id.homeitemdes);
            Quantity = (TextView) itemView.findViewById(R.id.Quantity);
            //  oldprice = (TextView) itemView.findViewById(R.id.homeitemoldprice);
            newprice = (TextView) itemView.findViewById(R.id.homeitemnewprice);
            Img = itemView.findViewById(R.id.homeitemimg);
            Add = itemView.findViewById(R.id.homeitemButton);
            item = itemView.findViewById(R.id.homeitemview);
            cdView = itemView.findViewById(R.id.cdView);
        }

    }

    public interface OnItemClick {
        void setOnItemClick(int position);
    }
}
