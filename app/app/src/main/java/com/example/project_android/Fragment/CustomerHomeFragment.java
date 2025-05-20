package com.example.project_android.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.widget.*;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.project_android.Activity.*;
import com.example.project_android.Adapter.DishHomeAdapter;
import com.example.project_android.Adapter.DishOfGroupAdapter;
import com.example.project_android.Adapter.MenuFoodCustomerAdapter;
import com.example.project_android.Adapter.SlideAdapter;
import com.example.project_android.Config.IP;

import com.example.project_android.Model.*;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.R;
import com.example.project_android.Receiver.BreakfastAlarmReceiver;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerHomeFragment extends Fragment {

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private CardView cardView;
    private static final String ARG_ID = "id";
    private int id;

    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private ProgressBar progressBarMenu;
    private CardView btn_gantoi, btn_tatca;
    private Button button3;
    private TextView textView3;
    private ImageView imageView6;


    public CustomerHomeFragment() {
    }


    public static CustomerHomeFragment newInstance(int id) {
        CustomerHomeFragment fragment = new CustomerHomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setMealAlarms() {
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), BreakfastAlarmReceiver.class);

        long alarmTime = System.currentTimeMillis();
        intent.putExtra("alarmTime", alarmTime);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar breakfastTime = Calendar.getInstance();
        breakfastTime.set(Calendar.HOUR_OF_DAY, 5);
        breakfastTime.set(Calendar.MINUTE, 0);
        breakfastTime.set(Calendar.SECOND, 0);

        if (breakfastTime.getTimeInMillis() <= System.currentTimeMillis()) {
            breakfastTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, breakfastTime.getTimeInMillis(), pendingIntent);
        }

        Calendar lunchTime = Calendar.getInstance();
        lunchTime.set(Calendar.HOUR_OF_DAY, 9);
        lunchTime.set(Calendar.MINUTE, 0);
        lunchTime.set(Calendar.SECOND, 0);

        if (lunchTime.getTimeInMillis() <= System.currentTimeMillis()) {
            lunchTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, lunchTime.getTimeInMillis(), pendingIntent);
        }

        Calendar dinnerTime = Calendar.getInstance();
        dinnerTime.set(Calendar.HOUR_OF_DAY, 13);
        dinnerTime.set(Calendar.MINUTE, 0);
        dinnerTime.set(Calendar.SECOND, 0);

        if (dinnerTime.getTimeInMillis() <= System.currentTimeMillis()) {
            dinnerTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, dinnerTime.getTimeInMillis(), pendingIntent);
        }

        Calendar eveningTime = Calendar.getInstance();
        eveningTime.set(Calendar.HOUR_OF_DAY, 22);
        eveningTime.set(Calendar.MINUTE, 37);
        eveningTime.set(Calendar.SECOND, 10);

        if (eveningTime.getTimeInMillis() <= System.currentTimeMillis()) {
            eveningTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        intent.putExtra("alarmTime", eveningTime.getTimeInMillis());

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, eveningTime.getTimeInMillis(), pendingIntent);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        textView3 = view.findViewById(R.id.textView3);
        setMealAlarms();


        recyclerView = view.findViewById(R.id.recyclerView_menufood);
        progressBarMenu = view.findViewById(R.id.progressBar_menu_food);
        //Hôm nay ăn gì
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);
        btn_gantoi = view.findViewById(R.id.btn_gantoi);
        button3 = view.findViewById(R.id.button3);
        imageView6 = view.findViewById(R.id.imageView6);

        viewPager2 = view.findViewById(R.id.viewBanner);

        btn_tatca = view.findViewById(R.id.btn_tatca);
        cardView = view.findViewById(R.id.giohang);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomerListDishNew.class);
                startActivity(intent);
            }
        });


        ProgressBar progressBarToday = view.findViewById(R.id.progressBar_list_food);

        RecyclerView recyclerView3 = view.findViewById(R.id.recyclerView3);
        ProgressBar progressBarFamous = view.findViewById(R.id.progressBar_famous_food);

        EditText edt_search = view.findViewById(R.id.edt_search_customerRes);

        List<SlideIten> slideItems = new ArrayList<>();
        slideItems.add(new SlideIten(R.drawable.banner1));
        slideItems.add(new SlideIten(R.drawable.banner2));
        slideItems.add(new SlideIten(R.drawable.banner4));
        slideItems.add(new SlideIten(R.drawable.banner5));

        viewPager2.setAdapter(new SlideAdapter(slideItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        loadListMenu();
        loadListMenu2();
        loadListMenu3("Ăn sáng");

        new Handler().postDelayed(() -> {
            progressBarMenu.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            progressBarToday.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.VISIBLE);

            progressBarFamous.setVisibility(View.GONE);
            recyclerView3.setVisibility(View.VISIBLE);

        }, 700);

        loadInfor();


        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchCustomerActivity.class);
                startActivity(intent);
            }
        });


        btn_gantoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RestaurantNearMe.class);
                startActivity(intent);
            }
        });

        btn_tatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RestaurantAll.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Voucher.class);
                startActivity(intent);
            }
        });

        return view;

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.listGroupDish().enqueue(new Callback<List<GroupDish>>() {
            @Override
            public void onResponse(Call<List<GroupDish>> call, Response<List<GroupDish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GroupDish> dishes = response.body();

                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<GroupDish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<GroupDish> dishes) {
        MenuFoodCustomerAdapter adapter = new MenuFoodCustomerAdapter(requireContext(), dishes, new OnItemClickListener<GroupDish>() {
            @Override
            public void onItemClick(GroupDish item, int position) {
                Intent intent = new Intent(getActivity(), RiceListMenu.class);
                intent.putExtra("items_id", item.getName());
                startActivity(intent);
            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadListMenu2() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listLimit10().enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dish> dishes = response.body();

                    populateListView2(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView2(List<Dish> dishes) {
        DishHomeAdapter adapter = new DishHomeAdapter(requireContext(), dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(getActivity(), ShowDetail.class);
                intent.putExtra("id_item", item.getId());
                startActivity(intent);
            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        }, id);
        recyclerView2.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void loadListMenu3(String text) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listGroupDish(text).enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dish> dishes = response.body();
                    populateListView3(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView3(List<Dish> dishes) {
        DishOfGroupAdapter adapter = new DishOfGroupAdapter(getActivity(), dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(getActivity(), ShowDetail.class);
                intent.putExtra("id_item", item.getId());
                startActivity(intent);
            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        }, id);
        recyclerView3.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void loadInfor() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "");
        String image = sharedPreferences.getString("image", "");
        textView3.setText("Chào, " + name);
        String imageUrl = IP.network + image;
        Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.avatar)
                .into(imageView6);
    }
}