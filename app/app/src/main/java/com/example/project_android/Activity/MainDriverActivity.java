package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Fragment.InfoBottomSheetDialogFragment;
import com.example.project_android.Fragment.InforCustomerBottom;
import com.example.project_android.Fragment.MapsDirectionFragment;
import com.example.project_android.Interface.ActiveStatusViewModel;
import com.example.project_android.Interface.ActiveStatusViewModel1;
import com.example.project_android.Interface.CustomerStatusViewModel;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainDriverActivity extends AppCompatActivity {
    private ActiveStatusViewModel1 viewModel1;
    private CardView cardView5;
    private Button btn_connect, btn_temp;
    private String orderId = "";
    private static final int REQUEST_CODE = 1;
    private FrameLayout mainDriver;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    public static boolean Active = false;
    private ImageView imageView4;
    private TextView tv18, tv19, textView18, textView19;
    private int userId = -1;
    private static boolean running = false;
    private int check = 0;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                orderId = data.getStringExtra("orderId");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfor();
        loadInfor1(userId);
        loadStatus(userId);

    }


    public void loadInfor() {

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String image = sharedPreferences.getString("image", "");
        String imageUrl = IP.network + image;
        Glide.with(MainDriverActivity.this)
                .load(imageUrl)
                .placeholder(R.drawable.avatar)
                .into(imageView4);
    }


    public void loadInfor1(int userId) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        Call<String[]> call = api.getMoney(userId);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if (response.isSuccessful()) {
                    String[] result = response.body();
                    if (result != null && result.length >= 2 && result[0] != null && result[1] != null) {
                        try {
                            Double money = Double.valueOf(result[0].trim());
                            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                            String formattedTotal = numberFormat.format(money) + "đ";
                            textView18.setText(formattedTotal);
                            textView19.setText(result[1]);
                        } catch (NumberFormatException e) {
                            textView18.setText("0đ");
                            textView19.setText("0");
                        }
                    } else {
                        textView18.setText("0đ");
                        textView19.setText("0");
                    }
                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                System.out.println("Failure: " + t.getMessage());
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        cardView5 = findViewById(R.id.cardView5);
        btn_connect = findViewById(R.id.btn_connect);
        btn_temp = findViewById(R.id.btn_temp);
        imageView4 = findViewById(R.id.imageView4);
        textView18 = findViewById(R.id.textView18);
        textView19 = findViewById(R.id.textView19);


        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);


        loadInfor();
        loadInfor1(userId);
        loadStatus(userId);

        viewModel1 = new ViewModelProvider(this).get(ActiveStatusViewModel1.class);
        viewModel1.getActiveStatus().observe(this, isActive -> {
            if (isActive == 2) {
                cardView5.setVisibility(View.GONE);
                btn_connect.setVisibility(View.GONE);
                btn_temp.setVisibility(View.VISIBLE);

            } else if (isActive == 3) {
                running = true;
            } else {
                cardView5.setVisibility(View.VISIBLE);
                btn_connect.setVisibility(View.VISIBLE);
                btn_temp.setVisibility(View.GONE);


            }
        });
        CustomerStatusViewModel viewModel2 = new ViewModelProvider(this).get(CustomerStatusViewModel.class);

        viewModel2.getCustomerStatus().observe(this, isCustomer -> {
            if (!isCustomer) {

                running = false;
                viewModel1.setActiveStatus(2);
                cardView5.setVisibility(View.VISIBLE);
                btn_connect.setVisibility(View.VISIBLE);
                btn_temp.setVisibility(View.GONE);
            }
        });

        myRef.setValue("Hello, World!");
        LinearLayout bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        LinearLayout ser = findViewById(R.id.service);
        ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainDriverActivity.this, DriverServiceActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout fav = findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainDriverActivity.this, DriverFavoriteLocationActivity.class);
                startActivity(intent);
            }
        });
        mainDriver = findViewById(R.id.mainDriver);
        btn_connect = findViewById(R.id.btn_connect);
        tv18 = findViewById(R.id.textView18);
        tv19 = findViewById(R.id.textView19);

        ImageView backImage = findViewById(R.id.imageView4);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDriverActivity.this, DriverInfoActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainDriverActivity.this, MainDriverIncomeActivity.class);
                startActivity(intent);
            }
        };
        tv18.setOnClickListener(onClickListener);
        tv19.setOnClickListener(onClickListener);


        if (savedInstanceState == null) {
            MapsDirectionFragment mapsFragment = new MapsDirectionFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.mainDriver, mapsFragment);
            fragmentTransaction.commit();
        }

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check == 1) {

                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                            MainDriverActivity.this, R.style.BottomSheetDialogTheme
                    );
                    View bottomSheetView = LayoutInflater.from(getApplicationContext())
                            .inflate(
                                    R.layout.fragment_driver_connect,
                                    (LinearLayout) findViewById(R.id.bottomSheetContainer)
                            );

                    Switch switch1 = bottomSheetView.findViewById(R.id.switch1);
                    Button btnTurnOff = bottomSheetView.findViewById(R.id.turnoff);


                    if (userId == 0) {
                        Toast.makeText(MainDriverActivity.this, "User ID không tồn tại!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("driveronl");
                    ActiveStatusViewModel viewModel = new ViewModelProvider(MainDriverActivity.this).get(ActiveStatusViewModel.class);

                    viewModel.setActiveStatus(true);

                    Map<String, Object> initialStatus = new HashMap<>();
                    initialStatus.put("userId", userId);
                    initialStatus.put("online", 1);
                    initialStatus.put("auto", switch1.isChecked() ? 1 : 0);
                    databaseReference.child(userId + "").setValue(initialStatus)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(MainDriverActivity.this, "Bạn đã bật hoạt động!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(MainDriverActivity.this, "Lỗi cập nhật trạng thái ban đầu!", Toast.LENGTH_SHORT).show();
                            });

                    switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        databaseReference.child(userId + "").child("auto").setValue(isChecked ? 1 : 0)
                                .addOnSuccessListener(unused -> {
                                    String message = isChecked ? "Auto hoạt động!" : "Auto đã tắt!";
                                    Toast.makeText(MainDriverActivity.this, message, Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(MainDriverActivity.this, "Lỗi cập nhật auto!", Toast.LENGTH_SHORT).show();
                                });
                    });

                    btnTurnOff.setOnClickListener(v -> {
                        Map<String, Object> turnOffStatus = new HashMap<>();
                        turnOffStatus.put("online", 0);
                        turnOffStatus.put("auto", 0);
                        databaseReference.child(userId + "").updateChildren(turnOffStatus)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(MainDriverActivity.this, "Bạn đã tắt hoạt động!", Toast.LENGTH_SHORT).show();
                                    bottomSheetDialog.dismiss();
                                    viewModel.setActiveStatus(false);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(MainDriverActivity.this, "Lỗi tắt hoạt động!", Toast.LENGTH_SHORT).show();
                                });
                    });

                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.setCancelable(true);

                    bottomSheetDialog.show();
                } else {
                    Toast.makeText(MainDriverActivity.this, "Tài khoản chưa được kích hoạt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    InfoBottomSheetDialogFragment bottomSheetDialogFragment = new InfoBottomSheetDialogFragment();
                    bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                } else {
                    InforCustomerBottom inforCustomerBottom = new InforCustomerBottom();
                    inforCustomerBottom.show(getSupportFragmentManager(), inforCustomerBottom.getTag());
                }
            }
        });

    }

    private void loadStatus(int userId) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        Call<Integer> call1 = api.checkStatus(userId);
        call1.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    check = response.body();
                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.println("Failure: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        if (userId != 0) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("driveronl");

            Map<String, Object> statusUpdate = new HashMap<>();
            statusUpdate.put("online", 0);
            statusUpdate.put("auto", 0);

            databaseReference.child(userId + "").updateChildren(statusUpdate)
                    .addOnSuccessListener(unused -> Log.d("FirebaseUpdate", "Trạng thái đã được cập nhật về 0 khi Activity bị hủy"))
                    .addOnFailureListener(e -> Log.e("FirebaseUpdate", "Lỗi cập nhật trạng thái khi destroy: " + e.getMessage()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("driveronl");

        Map<String, Object> initialStatus = new HashMap<>();
        initialStatus.put("userId", userId);
        initialStatus.put("online", 0);
        initialStatus.put("auto", 0);
        databaseReference.child(userId + "").setValue(initialStatus)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(MainDriverActivity.this, "Bạn đã bật hoạt động!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainDriverActivity.this, "Lỗi cập nhật trạng thái ban đầu!", Toast.LENGTH_SHORT).show();
                });
    }
}