package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Restaurant;
import com.example.project_android.Model.SellingOrder;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.CustomerOrderViewHolder> {

    private List<SellingOrder> dishList;
    private Context context;
    private OnItemClickListener<SellingOrder> listener;
    double rating_result_shiper = 0.0;
    double rating_result_res = 0.0;

    public CustomerOrderAdapter(Context context, List<SellingOrder> dishList, OnItemClickListener<SellingOrder> listener) {
        this.context = context;
        this.dishList = dishList;
        this.listener = listener;
    }




    @NonNull
    @Override
    public CustomerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordering_food, parent, false);
        return new CustomerOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderViewHolder holder, int position) {
        SellingOrder dish = dishList.get(position);
        holder.orderId.setText(dish.getId());

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(dish.getTotal()) + "đ";

        holder.total.setText(formattedTotal);

        String pro = dish.getProcess();
        if(pro.equals("Dang lam mon")){
            holder.status.setText("Nhà hàng đang làm món...");
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.chudao));
            holder.note.setVisibility(ViewGroup.GONE);
            holder.huy.setVisibility(ViewGroup.GONE);
        }
        if(pro.equals("Dang tim tai xe")){
            holder.status.setText("Đang tìm tài xế...");
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), com.google.android.libraries.places.R.color.quantum_googred));
            holder.note.setVisibility(ViewGroup.GONE);
            holder.huy.setVisibility(ViewGroup.GONE);
        }
        if(pro.equals("Tai xe da nhan don")){
            holder.status.setText("Tài xế đang đến nhà hàng...");
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), com.google.android.libraries.places.R.color.quantum_googred));
            holder.note.setVisibility(ViewGroup.GONE);
            holder.huy.setVisibility(ViewGroup.GONE);
        }


        if(pro.equals("Dang giao")){
            holder.status.setText("Tài xế đang đến bạn...");
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), com.google.android.libraries.places.R.color.quantum_googred));
            holder.note.setVisibility(ViewGroup.GONE);
            holder.huy.setVisibility(ViewGroup.GONE);
        }
        if(pro.equals("Da danh gia")){
            holder.status.setText("Hoàn tất");
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),  R.color.chudao));
            holder.note.setVisibility(ViewGroup.GONE);
            holder.huy.setVisibility(ViewGroup.GONE);
        }

        if (pro.equals("Xong")) {
            holder.status.setText("Xong");
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.chudao));
            holder.note.setVisibility(ViewGroup.GONE);
            holder.huy.setVisibility(ViewGroup.GONE);
            holder.danhgia.setVisibility(ViewGroup.VISIBLE);
            holder.ratingBar1.setVisibility(View.GONE);
            holder.ratingBar2.setVisibility(View.GONE);

            holder.danhgia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.ratingBar1.setVisibility(View.VISIBLE);
                    holder.ratingBar2.setVisibility(View.VISIBLE);

                    holder.taixe.setVisibility(View.VISIBLE);
                    holder.nhahang.setVisibility(View.VISIBLE);

                    holder.danhgia.setVisibility(View.GONE);
                    holder.total.setVisibility(View.GONE);
                    holder.status.setVisibility(View.GONE);
                    holder.orderId.setVisibility(View.GONE);
                    holder.xacnhan.setVisibility(View.VISIBLE);
                    holder.ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            if (fromUser) {
                                rating_result_shiper = rating;
                            }
                        }
                    });

                    holder.ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            if (fromUser) {
                                rating_result_res = rating;

                            }
                        }
                    });
                    holder.xacnhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            RetrofitService retrofitService = new RetrofitService();
                            APIUser api = retrofitService.getApiService();

                            Call<ResponseBody> call = api.ratingCus(dish.getId(),dish.getId_fk_restaurant(), rating_result_res,dish.getId_fk_shiper(), rating_result_shiper );
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(context, "Đã gửi đánh giá thành công!", Toast.LENGTH_SHORT).show();
                                        dish.setProcess("Da danh gia");
                                        notifyItemChanged(position);

                                    } else {
                                        Toast.makeText(context, "Lỗi khi gửi đánh giá: " + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Không thể gửi đánh giá. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });




                }
            });
        }

        if(pro.equals("Da huy")){
            holder.status.setText("Đã huỷ");
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), com.google.android.libraries.places.R.color.quantum_googred));
            holder.note.setVisibility(ViewGroup.GONE);
            holder.huy.setVisibility(ViewGroup.GONE);
        }



        holder.list_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(dish, position); // Trigger listener on click
            }
        });

        holder.huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<ResponseBody> call = api.cancel(dish.getId()); // Giả sử dish.getId() trả về đúng ID
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            dishList.remove(position);
                            Toast.makeText(view.getContext(), "Đã huỷ thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Xử lý lỗi mạng hoặc khi không thể thực hiện yêu cầu
                        System.out.println("Failure: " + t.getMessage());
                    }
                });
            }
        });




    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class CustomerOrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,total,status,note,nhahang,taixe;
        LinearLayout list_order;
        Button huy,danhgia,xacnhan;
        RatingBar ratingBar1,ratingBar2;
        public CustomerOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            total = itemView.findViewById(R.id.total);
            status = itemView.findViewById(R.id.status);
            note = itemView.findViewById(R.id.note);
            huy = itemView.findViewById(R.id.huy);
            danhgia = itemView.findViewById(R.id.danhgia);
            list_order = itemView.findViewById(R.id.list_order);
            ratingBar1 = itemView.findViewById(R.id.ratingBarShiper);
            ratingBar2 = itemView.findViewById(R.id.ratingBarRes);
            xacnhan = itemView.findViewById(R.id.xacnhan);
            nhahang = itemView.findViewById(R.id.nhahang);
            taixe = itemView.findViewById(R.id.taixe);
        }
    }
}