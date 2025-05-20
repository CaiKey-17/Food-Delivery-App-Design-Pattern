package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;

public class Login extends AppCompatActivity {
    private EditText username, password;
    private Button btn_login;
    private TextView btn_signup, btn_reset;
    private CheckBox checkBox;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private Button googbleBtn;
    private int id_user = -1;
    private RetrofitService retrfitService;
    private SharedPreferences sharedPreferences;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        googbleBtn = findViewById(R.id.googbleBtn);
        btn_signup = findViewById(R.id.btn_signup);
        checkBox = findViewById(R.id.checkBox);
        btn_reset = findViewById(R.id.btn_reset);

        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        retrfitService = new RetrofitService();
        APIUser api = retrfitService.getApiService();


        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Reset.class);
                startActivity(intent);
            }
        });


        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            String name = sharedPreferences.getString("name", "");
            int id = sharedPreferences.getInt("id", -1);
            int idFkRole = sharedPreferences.getInt("id_fk_role", 4);
//            navigateToRoleActivity(name, id, idFkRole);
        }


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (password.getWidth() - password.getCompoundDrawables()[2].getBounds().width())) {
                        isPasswordVisible = !isPasswordVisible;
                        if (isPasswordVisible) {
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_person_24, 0, R.drawable.password_unshow, 0);
                        } else {
                            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_person_24, 0, R.drawable.password_show, 0);
                        }
                        password.setSelection(password.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        googbleBtn.setOnClickListener(view -> signIn());

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userN = username.getText().toString();
                String passW = password.getText().toString();
                String passHash = Register.hashPassword(passW);

                if (userN.isEmpty() || passW.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Call<ResponseBody> call = retrfitService.getApiService().login(userN, passHash);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String jsonResponse = response.body().string();
                                    JSONObject jsonObject = new JSONObject(jsonResponse);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");

                                    if (status.equals("success")) {
                                        JSONObject user = jsonObject.getJSONObject("user");

                                        String name = user.getString("name");
                                        String gender = user.getString("gender");
                                        String address = user.getString("address");
                                        String birthday = user.getString("birthday");
                                        String email = user.getString("email");
                                        String image = user.getString("image");
                                        int idFkRole = user.getInt("id_fk_role");
                                        int id = user.getInt("id");


                                        if (checkBox.isChecked()) {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("isLoggedIn", true);
                                            editor.putString("name", name);
                                            editor.putInt("id", id);
                                            editor.putInt("userId", id);
                                            editor.putInt("id_fk_role", idFkRole);
                                            editor.apply();
                                        }
                                        navigateToRoleActivity(name, id, idFkRole, gender, address, userN, email, image);

                                    } else {
                                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException | JSONException e) {
                                    Log.e("Login-Activity", "Error reading response: " + e.getMessage());
                                }
                            } else {
                                Toast.makeText(Login.this, "Không tồn tại tài khoản" + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("Login-Activity", "Request failed: " + t.getMessage(), t);
                            Toast.makeText(Login.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }

    private void navigateToRoleActivity(String name, int id, int idFkRole, String gender, String address, String phone, String email, String image) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("userId", id);
        editor.putInt("idFkRole", idFkRole);
        editor.putString("name", name);
        editor.putString("gender", gender != null ? gender : "");
        editor.putString("address", address != null ? address : "");
        editor.putString("image", image != null ? image : "");
        editor.putString("email", email != null ? email : "");
        editor.putString("phone", phone != null ? phone : "");

        editor.apply();

        Intent intent;
        switch (idFkRole) {
            case 4:
                intent = new Intent(Login.this, MainCustomerActivity.class);
                break;
            case 3:
                intent = new Intent(Login.this, MainDriverActivity.class);
                break;
            case 2:
                intent = new Intent(Login.this, MainRestaurantActivity.class);
                break;
            case 1:
                intent = new Intent(Login.this, MainAdminActivity.class);
                break;
            default:
                intent = new Intent(Login.this, MainCustomerActivity.class);
                break;
        }

        startActivity(intent);
        finish();
    }


    private void signIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String email = account.getEmail();
                String name = account.getDisplayName();
                String imageUrl = (account.getPhotoUrl() != null) ? account.getPhotoUrl().toString() : "default_url";

                Call<ResponseBody> call = retrfitService.getApiService().checkEmail(email);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                Call<Integer> call_id = retrfitService.getApiService().findIdByEmail(email);
                                call_id.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            id_user = response.body();
                                            navigateToRoleActivity(name, id_user, 4, "", "", "", email, imageUrl);

                                        } else {
                                            System.out.println("User not found or error occurred");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });


                            } catch (Exception e) {
                                Log.e("OTPActivity", "Error reading response: " + e.getMessage());
                            }
                        } else if (response.code() == 404) {
                            try {
                                call = retrfitService.getApiService().googleSignIn(email, name);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                Call<Integer> call_id = retrfitService.getApiService().findIdByEmail(email);
                                                call_id.enqueue(new Callback<Integer>() {
                                                    @Override
                                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            id_user = response.body();
                                                            navigateToRoleActivity(name, id_user, 4, "", "", "", email, imageUrl);

                                                        } else {
                                                            System.out.println("User not found or error occurred");
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Integer> call, Throwable t) {
                                                        t.printStackTrace();
                                                    }
                                                });


                                            } catch (Exception e) {
                                                Log.e("OTPActivity", "Error reading response: " + e.getMessage());
                                            }
                                        } else {
                                            Toast.makeText(Login.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("OTPActivity", "Request failed: " + t.getMessage(), t);
                                        Toast.makeText(Login.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            } catch (Exception e) {
                                Log.e("OTPActivity", "Error reading response: " + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("OTPActivity", "Request failed: " + t.getMessage(), t);
                        Toast.makeText(Login.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (ApiException e) {
                Toast.makeText(this, "Sign in failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}