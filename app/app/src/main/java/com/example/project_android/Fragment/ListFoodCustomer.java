package com.example.project_android.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;

public class ListFoodCustomer extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView name,price;
    public TextView star;
    public ConstraintLayout detailDishLayout;
    public ListFoodCustomer(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_item_listfood);
        name = itemView.findViewById(R.id.tv_name_listfood);
        star = itemView.findViewById(R.id.sao);
        price = itemView.findViewById(R.id.price);
        detailDishLayout = itemView.findViewById(R.id.detailDishLayout);

    }
}
