package com.example.project_android.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomerAccountSettings extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SELECT_IMAGE = 2;
    LinearLayout phoneNumber, name, email, gender, birthday;
    TextView tvGender;
    ConstraintLayout avatar;
    ImageView imgAvatar, imageView25;
    Button btn_save;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 100;


    private TextView tv_phone, tv_name, tv_email, tv_gender, tv_birthday;
    private int id = -1;
    private String phoneNum = "", nameNum = "", emailNum = "";

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account_settings);
        checkPermissions();
        phoneNumber = findViewById(R.id.layout_phone_setting);
        name = findViewById(R.id.layout_name_setting);
        email = findViewById(R.id.layout_email_setting);
        gender = findViewById(R.id.layout_gender_setting);
        btn_save = findViewById(R.id.btn_save);

        avatar = findViewById(R.id.layout_update_avatar);
        imgAvatar = findViewById(R.id.img_avatar);

        tv_phone = findViewById(R.id.tv_phone);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tvGender = findViewById(R.id.tv_gender);
        imageView25 = findViewById(R.id.imageView25);
        imageView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);

        loadInfor();


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gender = tvGender.getText().toString();


                MultipartBody.Part avatarPart = null;
                if (imageUri != null) {
                    if (gender == null || gender.isEmpty()) {
                        Toast.makeText(CustomerAccountSettings.this, "Giới tính không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    File file = new File(getRealPathFromURI(imageUri));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    avatarPart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);


                    RetrofitService retrofitService = new RetrofitService();
                    APIUser api = retrofitService.getApiService();


                    RequestBody idUserRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));
                    RequestBody genderRequestBody = RequestBody.create(MediaType.parse("text/plain"), gender);


                    Call<Integer> call = api.change(idUserRequestBody, genderRequestBody, avatarPart);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(CustomerAccountSettings.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CustomerAccountSettings.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(CustomerAccountSettings.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {

                    if (gender == null || gender.isEmpty()) {
                        Toast.makeText(CustomerAccountSettings.this, "Giới tính không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    RetrofitService retrofitService = new RetrofitService();
                    APIUser api = retrofitService.getApiService();


                    RequestBody idUserRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));
                    RequestBody genderRequestBody = RequestBody.create(MediaType.parse("text/plain"), gender);


                    Call<Integer> call = api.changeTemp(idUserRequestBody, genderRequestBody);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(CustomerAccountSettings.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CustomerAccountSettings.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(CustomerAccountSettings.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerAccountSettings.this, CustomerUpdatePhoneSetting.class);
                startActivityForResult(intent, 100);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerAccountSettings.this, CustomerUpdateNameSetting.class);
                startActivityForResult(intent, 101);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerAccountSettings.this, CustomerUpdateEmailSetting.class);
                startActivityForResult(intent, 102);
            }
        });


        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderSelectionDialog();
            }
        });


    }


    public void loadInfor() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String updatedPhoneNum = sharedPreferences.getString("name", "");

        tv_phone.setText(sharedPreferences.getString("phone", ""));
        tv_name.setText(sharedPreferences.getString("name", ""));
        tv_email.setText(sharedPreferences.getString("email", ""));
        tvGender.setText(sharedPreferences.getString("gender", ""));


        String imageUrl = IP.network + sharedPreferences.getString("image", "");


        Glide.with(CustomerAccountSettings.this)
                .load(imageUrl)
                .placeholder(R.drawable.avatar)
                .into(imgAvatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfor();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                imageUri = data.getData();
                imgAvatar.setImageURI(imageUri);
            }


            if (requestCode == 100 && data != null) {
                String updatedPhoneNum = sharedPreferences.getString("phone", "");
                if (updatedPhoneNum != null) {
                    tv_phone.setText(updatedPhoneNum);
                }
            }
            if (requestCode == 101 && data != null) {
                String updatedName = sharedPreferences.getString("name", "");
                if (updatedName != null) {
                    tv_name.setText(updatedName);
                }
            }
            if (requestCode == 102 && data != null) {
                String updatedEmail = sharedPreferences.getString("email", "");
                if (updatedEmail != null) {
                    tv_email.setText(updatedEmail);
                }
            }
        }
    }

    private void showGenderSelectionDialog() {
        String[] genders = {"Nam", "Nữ", "Không xác định"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn giới tính");
        builder.setItems(genders, (dialog, which) -> {
            tvGender.setText(genders[which]);
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("gender", genders[which]);
            editor.apply();
        });

        builder.show();
    }

//    private void showDatePickerDialog() {
//        final Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                this,
//                (view, selectedYear, selectedMonth, selectedDay) -> {
//
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    Calendar selectedDate = Calendar.getInstance();
//                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
//                    String formattedDate = dateFormat.format(selectedDate.getTime());
//                    tvBirthday.setText(formattedDate);
//
//                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("birthday", formattedDate);
//                    editor.apply();
//
//                },
//                year, month, day);
//
//        datePickerDialog.show();
//    }

}