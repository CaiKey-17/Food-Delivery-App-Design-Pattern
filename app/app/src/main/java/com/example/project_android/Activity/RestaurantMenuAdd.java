package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;

public class RestaurantMenuAdd extends AppCompatActivity {
    private RetrofitService retrfitService;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 100;

    private EditText edt_tenMon, edt_giaMon, edt_motaMon, edt_soluong;
    private String group = "Chọn nhóm món";
    private ImageView btnBack;
    private TextView btn_group;
    private ImageView imageView;
    private Button button, btn_xacNhan;
    private Uri imageUri;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu_add);


        btnBack = findViewById(R.id.btn_backDish);
        btn_group = findViewById(R.id.btn_group);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        edt_tenMon = findViewById(R.id.edt_tenMon);
        edt_giaMon = findViewById(R.id.edt_giaMon);
        edt_motaMon = findViewById(R.id.edt_motaMon);
        edt_soluong = findViewById(R.id.edt_soluong);
        btn_xacNhan = findViewById(R.id.btn_xacNhan);

        retrfitService = new RetrofitService();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);


        Intent intent1 = getIntent();
        group = intent1.getStringExtra("items_id");
        btn_group.setText(group);


        checkPermissions();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGroup = new Intent(RestaurantMenuAdd.this, RestaurantMenuGroup.class);
                startActivity(intentGroup);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_tenMon.getText().toString();
                String price = edt_giaMon.getText().toString();
                String quantity = edt_soluong.getText().toString();
                String describe = edt_motaMon.getText().toString();
                String idGroup = group;

                if (imageUri == null) {
                    Toast.makeText(RestaurantMenuAdd.this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(RestaurantMenuAdd.this, "Vui lòng nhập tên món", Toast.LENGTH_SHORT).show();
                } else if (price.isEmpty()) {
                    Toast.makeText(RestaurantMenuAdd.this, "Vui lòng nhập giá món", Toast.LENGTH_SHORT).show();
                } else if (quantity.isEmpty()) {
                    Toast.makeText(RestaurantMenuAdd.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (idGroup == null || idGroup.isEmpty() || group == null || group.isEmpty()) {
                    Toast.makeText(RestaurantMenuAdd.this, "Vui lòng chọn nhóm món", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addDish(id);
                }
            }
        });
    }

    private void checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_STORAGE_PERMISSION);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            }
        }
    }

    private void addDish(int restaurantId) {

        String name = edt_tenMon.getText().toString().trim();
        String price = edt_giaMon.getText().toString().trim();
        String quantity = edt_soluong.getText().toString().trim();
        String describe = edt_motaMon.getText().toString().trim();
        String idGroup = group;


        double priceD = 0;
        int quantityD = 0;
        try {
            priceD = Double.valueOf(price);
            quantityD = Integer.valueOf(quantity);
        } catch (NumberFormatException e) {
            Toast.makeText(RestaurantMenuAdd.this, "Giá món hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }


        if (priceD <= 0 || quantityD <= 0) {
            Toast.makeText(RestaurantMenuAdd.this, "Giá món và số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            return;
        }


        if (imageUri == null) {
            Toast.makeText(RestaurantMenuAdd.this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(RestaurantMenuAdd.this, "Thêm món thành công", Toast.LENGTH_SHORT).show();


        File imageFile = new File(getRealPathFromURI(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestPrice = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(priceD));
        RequestBody requestQuantity = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(quantityD));
        RequestBody requestDescribe = RequestBody.create(MediaType.parse("text/plain"), describe);
        RequestBody requestIdRestaurant = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(restaurantId));
        RequestBody requestIdGroup = RequestBody.create(MediaType.parse("text/plain"), idGroup);


        Call<Dish> call = retrfitService.getApiService().addDish(requestName, requestPrice, requestQuantity, requestDescribe, requestIdRestaurant, requestIdGroup, body);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    Intent resultIntent = new Intent(RestaurantMenuAdd.this, MainRestaurantActivity.class);
                    resultIntent.putExtra("navigateTo", "menu");
                    startActivity(resultIntent);
                    finish();
                } else {
                    Toast.makeText(RestaurantMenuAdd.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                Toast.makeText(RestaurantMenuAdd.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
