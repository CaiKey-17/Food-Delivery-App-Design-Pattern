package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;

public class RestaurantMenuUpdate extends AppCompatActivity {
    private ImageView imageView,btn_backDish;
    private EditText edt_tenMon,edt_giaMon,edt_mota,edt_sl;
    private TextView btn_group;
    private Button button,btn_save;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private Uri imageUri;
    private int id_item = -1;
    private int id = -1;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu_update);
        imageView = findViewById(R.id.imageView);
        edt_tenMon = findViewById(R.id.edt_tenMon);
        edt_giaMon = findViewById(R.id.edt_giaMon);
        btn_group = findViewById(R.id.btn_group);
        edt_mota = findViewById(R.id.edt_mota);
        edt_sl = findViewById(R.id.edt_sl);
        button = findViewById(R.id.button);
        btn_save = findViewById(R.id.btn_save);
        btn_backDish = findViewById(R.id.btn_backDish);

        btn_backDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        retrofitService =new RetrofitService();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);



        Intent intent = getIntent();
        id_item = intent.getIntExtra("id_item",-1);

        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.detailDish(id_item).enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Dish dish = response.body();

                    edt_tenMon.setText(dish.getName());
                    edt_giaMon.setText(dish.getPrice()+"");
                    edt_sl.setText(dish.getQuantity()+"");
                    btn_group.setText(dish.getId_fk_group_dish());
                    edt_mota.setText(dish.getDescribe_dish());

                    String imageUrl = IP.network + dish.getImage();
                    Glide.with(RestaurantMenuUpdate.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageView);

                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDish(id,id_item);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Log.d("ImageURI", "Selected Image URI: " + imageUri);
            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    private void updateDish(int restaurantId, int id_item) {
        // Lấy giá trị từ các EditText
        String name = edt_tenMon.getText().toString().trim();
        String price = edt_giaMon.getText().toString().trim();
        String quantity = edt_sl.getText().toString().trim();
        String describe = edt_mota.getText().toString().trim();
        String idGroup = btn_group.getText().toString().trim();


        if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || describe.isEmpty() || idGroup.isEmpty()) {
            Toast.makeText(RestaurantMenuUpdate.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }


        double priceD = 0;
        int quantityD = 0;
        try {
            priceD = Double.valueOf(price);
            quantityD = Integer.valueOf(quantity);
        } catch (NumberFormatException e) {
            Toast.makeText(RestaurantMenuUpdate.this, "Giá món hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }


        if (priceD <= 0 || quantityD <= 0) {
            Toast.makeText(RestaurantMenuUpdate.this, "Giá món và số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(RestaurantMenuUpdate.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();


        if (imageUri != null) {
            File imageFile = new File(getRealPathFromURI(imageUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", imageFile.getName(), requestFile);


            RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_item));
            RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody requestPrice = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(priceD));
            RequestBody requestQuantity = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(quantityD));
            RequestBody requestDescribe = RequestBody.create(MediaType.parse("text/plain"), describe);
            RequestBody id_fk_restaurant = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(restaurantId));
            RequestBody id_fk_group_dish = RequestBody.create(MediaType.parse("text/plain"), idGroup);


            Call<ResponseBody> call = retrofitService.getApiService().updateDish(requestId, requestName, requestPrice, requestQuantity, requestDescribe, id_fk_restaurant, id_fk_group_dish, body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(RestaurantMenuUpdate.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(RestaurantMenuUpdate.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_item));
            RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody requestPrice = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(priceD));
            RequestBody requestQuantity = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(quantityD));
            RequestBody requestDescribe = RequestBody.create(MediaType.parse("text/plain"), describe);
            RequestBody id_fk_restaurant = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(restaurantId));
            RequestBody id_fk_group_dish = RequestBody.create(MediaType.parse("text/plain"), idGroup);


            Call<ResponseBody> call = retrofitService.getApiService().updateDish1(requestId, requestName, requestPrice, requestQuantity, requestDescribe, id_fk_restaurant, id_fk_group_dish);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(RestaurantMenuUpdate.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(RestaurantMenuUpdate.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }


}