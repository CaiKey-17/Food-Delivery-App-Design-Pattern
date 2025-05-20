package com.example.project_android.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;

public class ListFoodHorCustomer extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView name;
    public TextView star;
    public TextView location;

    public ListFoodHorCustomer(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_listfood_hor);
        name = itemView.findViewById(R.id.tv_name_cart);
        star = itemView.findViewById(R.id.tv_star_hor);
        location = itemView.findViewById(R.id.tv_location_hor);
    }
}
