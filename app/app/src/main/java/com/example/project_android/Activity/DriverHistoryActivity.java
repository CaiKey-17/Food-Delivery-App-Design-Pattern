package com.example.project_android.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.project_android.Adapter.AdapterViewPager;
import com.example.project_android.Fragment.MapsFragment;
import com.example.project_android.R;

import java.util.ArrayList;

public class DriverHistoryActivity extends AppCompatActivity {
    private boolean isInvoiceVisible = false;

    private ViewPager2 mainPager;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private Button backButton;
    private TextView toggleInvoice, id, begin, end, date, inCome;
    private LinearLayout invoiceDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = findViewById(R.id.backButton);
        mainPager = findViewById(R.id.mapView);
        id = findViewById(R.id.id);
        begin = findViewById(R.id.begin);
        end = findViewById(R.id.end);
        date = findViewById(R.id.date);
        inCome = findViewById(R.id.inCome);

        id.setText(getIntent().getStringExtra("id"));
        begin.setText(getIntent().getStringExtra("begin"));
        end.setText(getIntent().getStringExtra("end"));
        date.setText(getIntent().getStringExtra("date"));
        inCome.setText(getIntent().getStringExtra("inCome"));

        arrayList.add(new MapsFragment());

        AdapterViewPager adapterViewPagerCustomer = new AdapterViewPager(this, arrayList);
        mainPager.setAdapter(adapterViewPagerCustomer);


        toggleInvoice = findViewById(R.id.toggleInvoice);
        invoiceDetails = findViewById(R.id.invoiceDetails);

        toggleInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInvoiceVisible) {
                    invoiceDetails.setVisibility(View.GONE);
                } else {
                    invoiceDetails.setVisibility(View.VISIBLE);
                }
                isInvoiceVisible = !isInvoiceVisible;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}