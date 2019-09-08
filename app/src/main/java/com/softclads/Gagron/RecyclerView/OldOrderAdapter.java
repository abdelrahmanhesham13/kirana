package com.softclads.Gagron.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.Models.OldOrderModel;
import com.softclads.Gagron.R;

import java.util.ArrayList;

public class OldOrderAdapter extends RecyclerView.Adapter<OldOrderAdapter.ViewHolder> {
    private ArrayList<OldOrderModel> ordermodels;
    private Context context;
    private OrderListAdapter.OnItemClick mOnItemClick;

    public OldOrderAdapter(ArrayList<OldOrderModel> ordermodels, Context context, OrderListAdapter.OnItemClick mOnItemClick) {
        this.ordermodels = ordermodels;
        this.context = context;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.old_order_item_, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OldOrderModel oldOrderModel = ordermodels.get(position);

        holder.total.setText(oldOrderModel.getOrderTotal().substring(0,oldOrderModel.getOrderTotal().length()-2)+" ₹");
        if (oldOrderModel.getOrderStatus().equals("10"))
            holder.newprice.setText("Pending");
        else{
            holder.newprice.setText("Deliverd");


        }
        holder.Quantity.setText(oldOrderModel.getOrderDate());
        holder.oldprice.setVisibility(View.GONE);
        holder.Description.setText("Shipping Status");
        // holder.Img.setVisibility(View.GONE);
        holder.Add.setText("Order Details");
      /*  holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return ordermodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView total, Description, Quantity, oldprice, newprice;
        ImageView Img;
        Button Add;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            total = (TextView) itemView.findViewById(R.id.Total);
            Description = (TextView) itemView.findViewById(R.id.homeitemdes);
            Quantity = (TextView) itemView.findViewById(R.id.Quantity);
            oldprice = (TextView) itemView.findViewById(R.id.homeitemoldprice);
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
