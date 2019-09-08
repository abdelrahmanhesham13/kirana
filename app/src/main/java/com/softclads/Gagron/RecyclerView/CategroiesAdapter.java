package com.softclads.Gagron.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.MainCategoryActivity;
import com.softclads.Gagron.Models.CategoryModel;
import com.softclads.Gagron.R;
import com.softclads.Gagron.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategroiesAdapter extends RecyclerView.Adapter<CategroiesAdapter.ViewHolder> {


    private ArrayList<CategoryModel> categoryModels;
    private Context context;
    private String flag;
    private OnItemClick mOnItemClick;

    public CategroiesAdapter(ArrayList<CategoryModel> categoryModels, Context context, String flag, OnItemClick mOnItemClick) {
        this.categoryModels = categoryModels;
        this.context = context;
        this.flag = flag;
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public CategroiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (flag.equals("1")) {
            View view = LayoutInflater.from(context).inflate(R.layout.categry_item, parent, false);
            return new CategroiesAdapter.ViewHolder(view);
        } else if(flag.equals("2")) {
            View view = LayoutInflater.from(context).inflate(R.layout.main_categry_item, parent, false);
            return new CategroiesAdapter.ViewHolder(view);
        }
        else {   View view = LayoutInflater.from(context).inflate(R.layout.catgry_fragment_item, parent, false);
            return new CategroiesAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CategroiesAdapter.ViewHolder holder, int position) {
        final CategoryModel N = categoryModels.get(position);
        if (!N.getName().equals(""))
            holder.catgrytitle.setText(N.getName());
        if (!N.getImage().equals(""))
            Picasso.get().load(N.getImage()).fit().placeholder(R.drawable.lemon1).fit().into(holder.catgryImage);
        else
          //  holder.catgryImage.setVisibility(View.GONE);

        holder.catgryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ONCLICK", "onClick: Clicked"+flag);
                if (flag.equals("1")) {
                    Intent intent = new Intent(context, MainCategoryActivity.class);
                    intent.putExtra(Constants.CategorieNumb,N.getId()+"");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(flag.equals("2")){
                    MainCategoryActivity mainCategoryActivity=new MainCategoryActivity();
                    mainCategoryActivity.getSubCatgries(N.getId()+"");

                    } else{
                    Intent intent=new Intent(context,MainCategoryActivity.class);
                    intent.putExtra(Constants.CategorieNumb,N.getId()+"");
                    Log.i("ID_MSG",N.getId()+"");
                    context.startActivity(intent);
                }
            }
        });



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

    public interface OnItemClick{
        void setOnItemClick(int position);
    }
}
