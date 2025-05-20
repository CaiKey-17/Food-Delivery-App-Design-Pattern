package com.example.project_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Model.ListFoodModel;
import com.example.project_android.R;

import java.util.ArrayList;
import java.util.List;

public class FoodListMenu extends AppCompatActivity {
    private RecyclerView recSearchRice;
    private TextView tvTopic;
    private ImageView imgSearch;
    private CardView cvSearch;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice_list_menu);

        recSearchRice = findViewById(R.id.rec_search_rice);
        tvTopic = findViewById(R.id.tv_rice_topic);
        imgSearch = findViewById(R.id.img_search_rice);
        cvSearch = findViewById(R.id.cv_search_rice);
        edtSearch = findViewById(R.id.edt_search_rice);


        Intent intent = getIntent();
        String foodType = intent.getStringExtra("items_id");


        List<ListFoodModel> items = new ArrayList<>();


        if (foodType != null && !foodType.isEmpty()) {
            tvTopic.setText(foodType);
            if (foodType.equalsIgnoreCase("Cơm")) {
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));

                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));

                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));

                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));
                items.add(new ListFoodModel(1, "Cơm Minh Luân", "5.0", "0.5km", R.drawable.rice_list));


            } else if (foodType.equalsIgnoreCase("Hủ tiếu")) {
            }
        }

        recSearchRice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recSearchRice.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollOffset = recyclerView.computeVerticalScrollOffset();

                if (scrollOffset > 100 && cvSearch.getVisibility() == View.GONE) {
                    tvTopic.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                    cvSearch.setVisibility(View.VISIBLE);
                } else if (scrollOffset < 50 && cvSearch.getVisibility() == View.VISIBLE) {
                    tvTopic.setVisibility(View.VISIBLE);
                    imgSearch.setVisibility(View.VISIBLE);
                    cvSearch.setVisibility(View.GONE);
                }
            }
        });


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTopic.setVisibility(View.GONE);
                imgSearch.setVisibility(View.GONE);
                cvSearch.setVisibility(View.VISIBLE);
                edtSearch.requestFocus();
            }
        });
    }
}
