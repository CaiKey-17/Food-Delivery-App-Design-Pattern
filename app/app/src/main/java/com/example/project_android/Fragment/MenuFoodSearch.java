package com.example.project_android.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;

public class MenuFoodSearch extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView name;

    public MenuFoodSearch(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_item_1);
        name = itemView.findViewById(R.id.tv_name_item1);

    }
}
