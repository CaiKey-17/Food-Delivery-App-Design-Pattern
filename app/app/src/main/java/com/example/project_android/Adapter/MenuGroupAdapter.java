package com.example.project_android.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.GroupDish;
import com.example.project_android.R;
import java.util.List;

public class MenuGroupAdapter extends RecyclerView.Adapter<MenuGroupAdapter.MenuGroupViewHolder> {

    private List<GroupDish> groupDishes;
    private int selectedPosition = -1;
    private OnItemClickListener<GroupDish> listener;

    public MenuGroupAdapter(List<GroupDish> groupDishes, OnItemClickListener<GroupDish> listener) {
        this.groupDishes = groupDishes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_group, parent, false);
        return new MenuGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuGroupViewHolder holder, int position) {
        GroupDish groupDish = groupDishes.get(position);
        String dishName = groupDish.getName();
        Log.d("Adapter", "Group name: " + dishName);

        if (dishName != null && !dishName.isEmpty()) {
            holder.radioButton.setText(dishName);
        } else {
            holder.radioButton.setText("Tên không có sẵn");
        }
        holder.radioButton.setChecked(position == selectedPosition);

        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            listener.onItemClick(groupDish, selectedPosition);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return groupDishes.size();
    }

    public static class MenuGroupViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;

        public MenuGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}
