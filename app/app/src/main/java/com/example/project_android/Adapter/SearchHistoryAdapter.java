package com.example.project_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Activity.SearchCustomerActivity;
import com.example.project_android.R;
import com.example.project_android.Activity.CustomerListDishSearch;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.TextViewHolder> {

    private List<String> searchHistoryList;
    private Context context;
    private int id; // Thêm ID của người dùng

    public SearchHistoryAdapter(Context context, List<String> searchHistoryList, int id) {
        this.context = context;
        this.searchHistoryList = searchHistoryList;
        this.id = id; // Lưu ID của người dùng
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_searches, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        String searchText = searchHistoryList.get(position);
        holder.textView.setText(searchText);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerListDishSearch.class);
                intent.putExtra("id", id);
                intent.putExtra("nameDish", searchText);
                context.startActivity(intent);
            }
        });
        holder.delete_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ép kiểu context để gọi phương thức removeSearchHistoryItem trong SearchCustomerActivity
                if (context instanceof SearchCustomerActivity) {
                    ((SearchCustomerActivity) context).removeSearchHistoryItem(searchText);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchHistoryList.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView,delete_his;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_search_history);
            delete_his = itemView.findViewById(R.id.delete_his);

        }
    }
}
