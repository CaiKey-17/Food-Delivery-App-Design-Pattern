package com.example.project_android.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;

public class MenuFoodCustomer extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView name;

    public MenuFoodCustomer(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_item);
        name = itemView.findViewById(R.id.tv_name_item);

    }
}
