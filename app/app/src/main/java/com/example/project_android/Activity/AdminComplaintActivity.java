package com.example.project_android.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.MenuGroupAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Dish;
import com.example.project_android.Model.GroupDish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.List;

public class AdminComplaintActivity extends AppCompatActivity {
    private ImageView imageView21, imageView;
    private EditText editTextText2;
    private Button button, button9;
    private RecyclerView menu;
    private MenuGroupAdapter adapter;
    private RetrofitService retrfitService;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint);

        imageView = findViewById(R.id.imageView);
        imageView21 = findViewById(R.id.imageView21);
        editTextText2 = findViewById(R.id.editTextText2);
        button = findViewById(R.id.button);
        button9 = findViewById(R.id.button9);
        menu = findViewById(R.id.menu);
        retrfitService = new RetrofitService();

        checkPermissions();

        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDish();

            }
        });

        menu.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        loadListMenu();


    }

    private void addDish() {
        String name = editTextText2.getText().toString().trim();

        if (name == null) {
            Toast.makeText(AdminComplaintActivity.this, "Giá món và số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(AdminComplaintActivity.this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(AdminComplaintActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

        File imageFile = new File(getRealPathFromURI(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);

        Call<Dish> call = retrfitService.getApiService().groupdish(requestName, body);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    finish();
                } else {
                    Toast.makeText(AdminComplaintActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                Toast.makeText(AdminComplaintActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Log.e("API Error", "Response unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<GroupDish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


      private void populateListView(List<GroupDish> dishes) {
          adapter = new MenuGroupAdapter(dishes, new OnItemClickListener<GroupDish>() {
              @Override
              public void onItemClick(GroupDish item, int position) {
              }

              @Override
              public void onVoucherSelected(int voucherId) {
              }

              @Override
              public void onPriceSelected(double price) {
              }
          });

          menu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
