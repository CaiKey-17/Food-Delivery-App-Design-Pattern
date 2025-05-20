package com.example.project_android.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.airbnb.lottie.LottieAnimationView;
import com.example.project_android.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainShipperRunCustomerActivity extends AppCompatActivity {

    private CardView cardCheck, cardCbi, cardShipper;
    private LottieAnimationView loading1, loading2, loading3;
    private TextView tvCheck, tvCbi, tvShipper;
    private ImageView imgCheck, imgCbi, imgShipper;

    private Button btnCheck, btnCbi, btnShipper;

    private boolean isCheckPressed = false;
    private boolean isCbiPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shipper_run_customer);

        View bottomSheet = findViewById(R.id.frame_shipper_run);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(600);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        cardCheck = findViewById(R.id.card_check);
        cardCbi = findViewById(R.id.card_cbi);
        cardShipper = findViewById(R.id.card_shipper);

        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2);
        loading3 = findViewById(R.id.loading3);

        tvCheck = findViewById(R.id.tv_check);
        tvCbi = findViewById(R.id.tv_cbi);
        tvShipper = findViewById(R.id.tv_shipper);

        imgCheck = findViewById(R.id.img_check);
        imgCbi = findViewById(R.id.img_cbi);
        imgShipper = findViewById(R.id.img_shipper);

        btnCheck = findViewById(R.id.btn_check);
        btnCbi = findViewById(R.id.btn_cbi);
        btnShipper = findViewById(R.id.btn_shipper);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardCheck.setCardBackgroundColor(getResources().getColor(R.color.nenDriver));
                loading1.playAnimation();
                tvCheck.setTextColor(getResources().getColor(R.color.mauchu));
                tvCheck.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                imgCheck.setImageResource(R.drawable.house1);

                isCheckPressed = true;
            }
        });

        btnCbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheckPressed) {
                    cardCbi.setCardBackgroundColor(getResources().getColor(R.color.nenDriver));
                    loading2.playAnimation();
                    tvCbi.setTextColor(getResources().getColor(R.color.mauchu));
                    tvCbi.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    imgCbi.setImageResource(R.drawable.pot1);
                    loading1.setProgress(1.0f);
                    loading1.cancelAnimation();
                    isCbiPressed = true;
                }
            }
        });

        btnShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCbiPressed) {
                    cardShipper.setCardBackgroundColor(getResources().getColor(R.color.nenDriver));
                    loading3.playAnimation();
                    tvShipper.setTextColor(getResources().getColor(R.color.mauchu));
                    tvShipper.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    loading2.setProgress(1.0f);
                    loading2.cancelAnimation();
                    imgShipper.setImageResource(R.drawable.shipping1);
                }
            }
        });


    }
}