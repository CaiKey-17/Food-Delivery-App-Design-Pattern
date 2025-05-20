package com.example.project_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.project_android.R;

public class MainSplashScreen extends AppCompatActivity {

    private ImageView logo, background;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash_screen);

        logo = findViewById(R.id.img_logo);
        background = findViewById(R.id.img_background);
        lottieAnimationView = findViewById(R.id.lottie_animation);

        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        logo.animate().translationX(-screenHeight).setDuration(1000).setStartDelay(4000);
        background.animate().translationY(-screenHeight - 1000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationX(screenHeight).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        }, 5000);

    }
}