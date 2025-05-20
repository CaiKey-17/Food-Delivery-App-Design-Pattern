package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Model.Bonus;
import com.example.project_android.R;
import com.example.project_android.ViewHolder.ViewHolderBonus;

import java.util.List;

public class AdapterRecycleViewBonus extends RecyclerView.Adapter<ViewHolderBonus> {
    Context context;
    List<Bonus> items;

    public AdapterRecycleViewBonus(Context context, List<Bonus> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderBonus onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderBonus(LayoutInflater.from(context).inflate(R.layout.item_bonus_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBonus holder, int position) {
        holder.time.setText(items.get(position).getTime().toString());
        holder.until.setText(items.get(position).getUntil().toString());
        holder.title.setText(items.get(position).getTitle());
        holder.des.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
