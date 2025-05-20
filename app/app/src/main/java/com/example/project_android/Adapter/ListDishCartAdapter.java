
package com.example.project_android.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListDishCartAdapter  extends RecyclerView.Adapter<ListDishCartAdapter.DishCartViewHolder> {

    private List<OrderDetail> donhangs;
    private Context context;
    private OnItemClickListener<OrderDetail> listener;
    private RetrofitService retrfitService;
    private double total=0;

    public ListDishCartAdapter(Context context, List<OrderDetail> donhangs, OnItemClickListener<OrderDetail> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;

    }




    @NonNull
    @Override
    public ListDishCartAdapter.DishCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ListDishCartAdapter.DishCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDishCartAdapter.DishCartViewHolder holder, int position) {
        OrderDetail dish = donhangs.get(position);
        total+=dish.getTotal();

        holder.tv_name_cart.setText(dish.getName());


        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(dish.getPrice()) + "đ";

        holder.tv_price_cart.setText("Giá: "+formattedTotal);

        holder.tv_quantity.setText(dish.getQuantity()+"");
        retrfitService = new RetrofitService();
        String imageUrl = dish.getImage();
        if (imageUrl != null) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.dishImage);
        } else {
            Log.e("DishAdapter", "Image URL is null for dish: " + dish.getName());
            holder.dishImage.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.linear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(dish, position);
            }
        });
        holder.btn_tru_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dish.getQuantity() > 1) {
                    Call<String> call = retrfitService.getApiService().minus(dish.getHd(), dish.getId_fk_dish());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                dish.setQuantity(dish.getQuantity() - 1);
                                holder.tv_quantity.setText(String.valueOf(dish.getQuantity()));
                                notifyItemChanged(position);
                            } else {
                                Toast.makeText(view.getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            dish.setQuantity(dish.getQuantity() - 1);
                            holder.tv_quantity.setText(String.valueOf(dish.getQuantity()));
                            notifyItemChanged(position);

                        }
                    });
                }
            }
        });

        holder.btn_cong_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call = retrfitService.getApiService().addMore(dish.getHd(), dish.getId_fk_dish());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            dish.setQuantity(dish.getQuantity() + 1);
                            holder.tv_quantity.setText(String.valueOf(dish.getQuantity()));
                            notifyItemChanged(position);
                        } else {
                            Toast.makeText(view.getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        dish.setQuantity(dish.getQuantity() +1);
                        holder.tv_quantity.setText(String.valueOf(dish.getQuantity()));
                        notifyItemChanged(position);
                    }
                });
            }
        });


        holder.linear_cart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_delete) {

                            Call<String> call = retrfitService.getApiService().deleteCart(dish.getId());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful()) {
                                        donhangs.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, donhangs.size());
                                        Toast.makeText(context, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    donhangs.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, donhangs.size());
                                    Toast.makeText(context, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> call = retrfitService.getApiService().deleteCart(dish.getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            donhangs.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, donhangs.size());
                            Toast.makeText(context, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        donhangs.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, donhangs.size());
                        Toast.makeText(context, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return donhangs.size();
    }

    public static class DishCartViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_cart, tv_price_cart,tv_quantity;
        ImageView dishImage;
        ImageButton btn_tru_cart,btn_cong_cart;
        LinearLayout linear_cart,an;
        Button btn_delete;

        public DishCartViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_cart = itemView.findViewById(R.id.tv_name_cart);
            tv_price_cart = itemView.findViewById(R.id.tv_price_cart);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            btn_tru_cart = itemView.findViewById(R.id.btn_tru_cart);
            dishImage = itemView.findViewById(R.id.img_listfood_hor);

            btn_cong_cart = itemView.findViewById(R.id.btn_cong_cart);
            linear_cart = itemView.findViewById(R.id.linear_cart);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            an = itemView.findViewById(R.id.an);
        }
    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private ListDishCartAdapter mAdapter;

        public SwipeToDeleteCallback(ListDishCartAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.mAdapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            OrderDetail dish = mAdapter.donhangs.get(position);
            DishCartViewHolder holder = (DishCartViewHolder) viewHolder;


            if (direction == ItemTouchHelper.LEFT) {
                holder.btn_delete.setVisibility(View.VISIBLE);
                holder.an.setVisibility(View.GONE);
            } else if (direction == ItemTouchHelper.RIGHT) {
                holder.btn_delete.setVisibility(View.GONE);
                holder.an.setVisibility(View.VISIBLE);
            }


            mAdapter.notifyItemChanged(position);
        }

        @Override
        public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


            int position = viewHolder.getAdapterPosition();
            DishCartViewHolder holder = (DishCartViewHolder) viewHolder;

            if (dX > 0) {

                holder.btn_delete.setVisibility(View.GONE);
                holder.an.setVisibility(View.VISIBLE);
            } else if (dX < 0) {
                holder.btn_delete.setVisibility(View.VISIBLE);
                holder.an.setVisibility(View.GONE);
            }
        }
    }


}


