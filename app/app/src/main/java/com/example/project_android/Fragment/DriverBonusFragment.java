package com.example.project_android.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_android.Adapter.AdapterRecycleViewBonus; // Thay đổi nếu bạn đã đặt tên khác
import com.example.project_android.Model.Bonus; // Kiểu dữ liệu cho item trong RecyclerView
import com.example.project_android.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverBonusFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterRecycleViewBonus adapter;
    private List<Bonus> bonusList;
    private TextView todayOption, upcomingOption, pastOption;

    public DriverBonusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_bonus, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bonusList = new ArrayList<>();
        adapter = new AdapterRecycleViewBonus(getContext(), bonusList);
        recyclerView.setAdapter(adapter);

        todayOption = view.findViewById(R.id.today_option);
        upcomingOption = view.findViewById(R.id.upcoming_option);
        pastOption = view.findViewById(R.id.past_option);

        todayOption.setOnClickListener(v -> loadTodayData());
        upcomingOption.setOnClickListener(v -> loadUpcomingData());
        pastOption.setOnClickListener(v -> loadPastData());

        loadTodayData();

        return view;
    }

    private void loadTodayData() {
        bonusList.clear();
        bonusList.add(new Bonus(new Date(), new Date(System.currentTimeMillis() + 86400000), "Bonus Hôm Nay", "Mô tả cho bonus hôm nay."));
        bonusList.add(new Bonus(new Date(), new Date(System.currentTimeMillis() + 86400000), "Bonus Hôm Nay 2", "Mô tả cho bonus hôm nay 2."));
        adapter.notifyDataSetChanged();
    }

    private void loadUpcomingData() {
        bonusList.clear();
        bonusList.add(new Bonus(new Date(System.currentTimeMillis() + 86400000), new Date(System.currentTimeMillis() + 172800000), "Bonus Sắp Tới 1", "Mô tả cho bonus sắp tới 1."));
        bonusList.add(new Bonus(new Date(System.currentTimeMillis() + 86400000), new Date(System.currentTimeMillis() + 172800000), "Bonus Sắp Tới 2", "Mô tả cho bonus sắp tới 2."));
        adapter.notifyDataSetChanged();
    }

    private void loadPastData() {
        bonusList.clear();
        bonusList.add(new Bonus(new Date(System.currentTimeMillis() - 86400000), new Date(), "Bonus Đã Qua 1", "Mô tả cho bonus đã qua 1."));
        bonusList.add(new Bonus(new Date(System.currentTimeMillis() - 86400000), new Date(), "Bonus Đã Qua 2", "Mô tả cho bonus đã qua 2."));
        adapter.notifyDataSetChanged();
    }
}
