package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Driver;
import com.example.project_android.Model.Restaurant;
import com.example.project_android.Model.Shiper;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class DriverApprovalAdapter extends RecyclerView.Adapter<DriverApprovalAdapter.DriverViewHolder> {

    private List<Shiper> donhangs;
    private Context context;
    private OnItemClickListener<Shiper> listener;


    public interface OnApproveClickListener {
        void onApproveClick(Driver driver);
    }

    public DriverApprovalAdapter(Context context, List<Shiper> donhangs, OnItemClickListener<Shiper> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver_approval, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Shiper driver = donhangs.get(position);


        holder.tvDriverName.setText(driver.getName());
        holder.tvLicensePlate.setText(driver.getAddress());

        String imageUrl = driver.getImage();
        if (imageUrl != null) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.ivProfileImage);
        } else {

            holder.ivProfileImage.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donhangs.remove(position);

                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<String> call = api.activeShiper(driver.getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {

                            Toast.makeText(view.getContext(), "Phê duyệt thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Phê duyệt thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return donhangs.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView tvDriverName, tvLicensePlate;
        ImageView ivProfileImage;
        Button btnApprove;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDriverName = itemView.findViewById(R.id.tvDriverName);
            tvLicensePlate = itemView.findViewById(R.id.tvLicensePlate);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            btnApprove = itemView.findViewById(R.id.btnApprove);
        }
    }
}