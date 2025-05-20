package com.example.project_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_android.MainRepository;
import com.example.project_android.R;
import com.permissionx.guolindev.PermissionX;

public class TestLoginActivity extends AppCompatActivity {
    private MainRepository mainRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnTest = findViewById(R.id.testLogin);
        EditText textView = findViewById(R.id.editUserName);
        mainRepository = MainRepository.getInstance();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionX.init(TestLoginActivity.this) // Sử dụng TestLoginActivity.this thay vì this
                        .permissions(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO)
                        .request((allGranted, grantedList, deniedList) -> {
                            if (allGranted) {
                                // Đăng nhập vào Firebase
                                mainRepository.login(
                                        textView.getText().toString(), getApplicationContext(), () -> {
                                            // Nếu thành công, chuyển đến CallActivity
                                            startActivity(new Intent(TestLoginActivity.this, CallActivity.class));
                                        }
                                );
                            }
                        });
            }
        });
    }
}
