package com.example.project_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android.Interface.CustomerStatusViewModel;
import com.example.project_android.Interface.RunningStatusViewModel;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InforCustomerBottom extends BottomSheetDialogFragment {
    private String order = "";
    private TextView textCustomerPhone, textCustomerFullName, textCustomerAddress, textCustomerTotal, textCustomerOrder, textCustomerPayment;
    private Button btnComplete, btnCall1;
    private double bonus = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_infor_customer_bottom, container, false);
        textCustomerFullName = rootView.findViewById(R.id.textCustomerFullName);
        textCustomerPhone = rootView.findViewById(R.id.textCustomerPhone);
        textCustomerAddress = rootView.findViewById(R.id.textCustomerAddress);
        textCustomerTotal = rootView.findViewById(R.id.textCustomerTotal);
        textCustomerOrder = rootView.findViewById(R.id.textCustomerOrder);
        textCustomerPayment = rootView.findViewById(R.id.textCustomerPayment);
        btnComplete = rootView.findViewById(R.id.btnComplete);
        btnCall1 = rootView.findViewById(R.id.btnCall1);


        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("orders", MODE_PRIVATE);
        order = sharedPreferences.getString("orderId", "");
        loadInfor();

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerStatusViewModel viewModel1 = new ViewModelProvider(getActivity()).get(CustomerStatusViewModel.class);
                RunningStatusViewModel viewModel2 = new ViewModelProvider(getActivity()).get(RunningStatusViewModel.class);

                viewModel1.setCustomerStatus(false);
                viewModel2.setRunningStatus(false);

                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", 0);

                double result_bonus = bonus * 0.3;
                Toast.makeText(getActivity(), result_bonus + "", Toast.LENGTH_SHORT).show();
                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<ResponseBody> call = api.hoantat(order, userId, result_bonus);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Failure: " + t.getMessage());
                    }
                });
                onDestroyView();
            }
        });


        btnCall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("orders", MODE_PRIVATE);

                String phoneNumber = sharedPreferences.getString("customerPhone", "");


                if (!phoneNumber.isEmpty()) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Quyền gọi điện đã được cấp", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Quyền gọi điện bị từ chối", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInfor() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("orders", MODE_PRIVATE);

        String customerName = sharedPreferences.getString("customerName", "");
        String customerPhone = sharedPreferences.getString("customerPhone", "");
        String customerAddress = sharedPreferences.getString("customerAddress", "");
        String customerTotal = sharedPreferences.getString("customerTotal", "");
        String customerPayment = sharedPreferences.getString("customerPayment", "");


        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        Call<String[]> call = api.getInforOrderCus(order);

        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if (response.isSuccessful()) {
                    String[] temp = response.body();
                    if (temp != null) {
                        updateUIWithApiData(temp);
                    } else {
                        Toast.makeText(getActivity(), "Dữ liệu trả về là null", Toast.LENGTH_SHORT).show();
                        Log.e("LoadInfor", "Dữ liệu trả về là null");
                    }
                } else {
                    Log.e("LoadInfor", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Log.e("LoadInfor", "Failure: " + t.getMessage());
                Toast.makeText(getActivity(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (!checkStringNull(customerName) || !checkStringNull(customerPhone) || !checkStringNull(customerAddress) ||
                !checkStringNull(customerTotal) || !checkStringNull(customerPayment)) {

            textCustomerFullName.setText("Khách hàng: " + (checkStringNull(customerName) ? customerName : "Chưa có thông tin"));
            textCustomerPhone.setText("Số điện thoại: " + (checkStringNull(customerPhone) ? customerPhone : "Chưa có thông tin"));
            textCustomerAddress.setText("Địa chỉ: " + (checkStringNull(customerAddress) ? customerAddress : "Chưa có thông tin"));
            textCustomerTotal.setText("Thu: " + formatCurrency(customerTotal));
            textCustomerPayment.setText("Tình trạng: " + (checkStringNull(customerPayment) ? customerPayment : "Chưa có thông tin"));
            btnCall1.setText("Gọi(" + customerPhone + ")");
            bonus = checkStringNull(customerTotal) ? Double.parseDouble(customerTotal) : 0;
            textCustomerOrder.setText("Hoá đơn: " + order);
        }
    }

    private void updateUIWithApiData(String[] temp) {
        SharedPreferences.Editor editor = requireContext().getSharedPreferences("orders", MODE_PRIVATE).edit();

        String customerName = (checkStringNull(temp[4])) ? temp[4] : "Tên khách hàng không có";
        String customerPhone = (checkStringNull(temp[5])) ? temp[5] : "Số điện thoại không có";
        String customerAddress = (checkStringNull(temp[3])) ? temp[3] : "Địa chỉ không có";
        String customerOrder = (checkStringNull(temp[0])) ? temp[0] : "Hóa đơn không có";
        String customerTotal = (checkStringNull(temp[1])) ? temp[1] : "Tổng";
        String customerPayment = (checkStringNull(temp[2])) ? temp[2] : "Tình trạng";

        editor.putString("customerName", customerName);
        editor.putString("customerPhone", customerPhone);
        editor.putString("customerAddress", customerAddress);
        editor.putString("customerOrder", customerOrder);
        editor.putString("customerTotal", customerTotal);
        editor.putString("customerPayment", customerPayment);
        editor.apply();

        textCustomerFullName.setText("Khách hàng: " + customerName);
        textCustomerPhone.setText("Số điện thoại: " + customerPhone);
        textCustomerAddress.setText("Địa chỉ: " + customerAddress);
        textCustomerOrder.setText("Hoá đơn: " + order);
        textCustomerPayment.setText("Tình trạng: " + customerPayment);
        textCustomerTotal.setText("Thu: " + formatCurrency(customerTotal));
        btnCall1.setText("Gọi(" + customerPhone + ")");

        bonus = checkStringNull(customerTotal) ? Double.parseDouble(customerTotal) : 0;
    }


    private String formatCurrency(String value) {
        if (checkStringNull(value)) {
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
            return numberFormat.format(Double.parseDouble(value)) + "đ";
        }
        return "0đ";
    }

    private boolean checkStringNull(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
