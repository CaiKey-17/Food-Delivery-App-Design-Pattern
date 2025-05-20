package com.example.project_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Model.Trip;
import com.example.project_android.Adapter.TripHistoryAdapter;
import com.example.project_android.R;

import java.util.ArrayList;
import java.util.List;

public class DriverListHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TripHistoryAdapter adapter;
    private List<Trip> tripList;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_list_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data
        tripList = new ArrayList<>();
        tripList.add(new Trip("123123112", "2024-10-01", "Điểm đón: A", "Điểm đến: B", 123123));
        tripList.add(new Trip("123", "2024-10-02", "Điểm đón: C", "Điểm đến: D", 123123));
        tripList.add(new Trip("asd", "2024-10-03", "Điểm đón: E", "Điểm đến: F", 12341412));

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new TripHistoryAdapter(tripList, new TripHistoryAdapter.OnItemClickListener() {
            @Override
            public void onHistoryClick(int position) {
                Trip clickedTrip = tripList.get(position);
                Intent intent = new Intent(DriverListHistoryActivity.this, DriverHistoryActivity.class);
                intent.putExtra("date", clickedTrip.getDate());
                intent.putExtra("begin", clickedTrip.getBegin());
                intent.putExtra("end", clickedTrip.getEnd());
                intent.putExtra("id", clickedTrip.getId());
                intent.putExtra("inCome", clickedTrip.getIncome());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }
}
