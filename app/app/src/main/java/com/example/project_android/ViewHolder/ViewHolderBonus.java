package com.example.project_android.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;

import java.util.Date;

public class ViewHolderBonus extends RecyclerView.ViewHolder {
    public TextView time,until,title,des;
    LinearLayout layoutClickBonus;

    public ViewHolderBonus(@NonNull View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.textTimeBonus);
        until = itemView.findViewById(R.id.textUntilBonus);
        title = itemView.findViewById(R.id.titleTextBonus);
        des = itemView.findViewById(R.id.descriptionTextBonus);
        layoutClickBonus = itemView.findViewById(R.id.layoutClickBonus);
    }
}
