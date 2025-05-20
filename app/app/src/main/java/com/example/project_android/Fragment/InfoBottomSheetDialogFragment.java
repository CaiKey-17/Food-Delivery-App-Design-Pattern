package com.example.project_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_android.Interface.ActiveStatusViewModel1;
import com.example.project_android.Interface.CustomerStatusViewModel;
import com.example.project_android.Interface.RunningStatusViewModel;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.Toast;

public class InfoBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private DismissListener dismissListener;
    private String distance=null;
    private String duration=null;
    private String order = "";
    private ResetMapListener resetMapListener;
    private boolean useMap = false;
    private TextView textDistance, textDuration, textRestaurantName, textRestaurantAddress, textBillName, textBillTotalPrice, textBillPaymentStatus;
    private SharedPreferences sharedPreferences;
    private ActiveStatusViewModel1 viewModel;

    private Double result = 0.0,result1=0.0;

    public InfoBottomSheetDialogFragment(String distance, String duration) {
        this.distance = distance;
        this.duration = duration;

    }

    public InfoBottomSheetDialogFragment() {


    }

    public void setResetMapListener(ResetMapListener resetMapListener) {
        this.resetMapListener = resetMapListener;
    }

    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
        onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!useMap) {
            if (resetMapListener != null) {
                resetMapListener.onResetMap();
            }
        }
    }



    private OnOrderAcceptedListener onOrderAcceptedListener;

    public void setOnOrderAcceptedListener(OnOrderAcceptedListener listener) {
        this.onOrderAcceptedListener = listener;
    }


    public interface OnOrderAcceptedListener {
        void onOrderAccepted();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_sheet_info, container, false);

        Button btnClose = rootView.findViewById(R.id.btnClose);
        Button btnAccept = rootView.findViewById(R.id.btnResetMap);
        Button button8 = rootView.findViewById(R.id.button8);
        LinearLayout linear = rootView.findViewById(R.id.linear);
        if(this.duration == null || this.distance == null){
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("orders", Context.MODE_PRIVATE);
            this.distance = sharedPreferences.getString("distance", "Default Distance");
            this.duration = sharedPreferences.getString("duration", "Default Duration");
            button8.setVisibility(View.VISIBLE);
            linear.setVisibility(View.GONE);
            button8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CustomerStatusViewModel viewModel1 = new ViewModelProvider(getActivity()).get(CustomerStatusViewModel.class);

                    viewModel1.setCustomerStatus(true);


                    RetrofitService retrofitService = new RetrofitService();
                    APIUser api = retrofitService.getApiService();
                    Call<ResponseBody> call = api.delivering(order);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                viewModel.setActiveStatus(3);

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
        }
        else{
            sharedPreferences = requireContext().getSharedPreferences("orders", MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("distance", distance);
            editor.putString("duration", duration);
            editor.apply();



        }


        textDistance = rootView.findViewById(R.id.textDistance);
        textDuration = rootView.findViewById(R.id.textDuration);
        textRestaurantName = rootView.findViewById(R.id.textRestaurantName);
        textRestaurantAddress = rootView.findViewById(R.id.textRestaurantAddress);
        textBillName = rootView.findViewById(R.id.textBillName);
        textBillTotalPrice = rootView.findViewById(R.id.textBillTotalPrice);
        textBillPaymentStatus = rootView.findViewById(R.id.textBillPaymentStatus);
        sharedPreferences = requireContext().getSharedPreferences("orders", MODE_PRIVATE);

        order = sharedPreferences.getString("orderId", "");
        loadInfor();



        textDistance.setText("Khoảng cách: " + distance);
        textDuration.setText("Dự kiến: " + duration);

        btnClose.setOnClickListener(v -> {
            if (resetMapListener != null) {
                resetMapListener.onResetMap();
                dismiss();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.contains("orderId")) {
                    editor.remove("orderId");
                    editor.apply();
                }
            }
        });
        viewModel = new ViewModelProvider(requireActivity()).get(ActiveStatusViewModel1.class);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        loadMoney(userId);
        loadMoneyO(order);

        btnAccept.setOnClickListener(v -> {
            if(result1>result){
                Toast.makeText(getActivity(), "Vui lòng nạp thêm tiền", Toast.LENGTH_SHORT).show();
            }
            else {
                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<ResponseBody> call = api.ok(order, userId);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            RetrofitService retrofitService = new RetrofitService();
                            APIUser api = retrofitService.getApiService();
                            Call<ResponseBody> call1 = api.trutien(userId,result1);
                            call1.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {

                                        Toast.makeText(getActivity(), "Đã nhận đơn hàng", Toast.LENGTH_SHORT).show();
                                    } else {
                                        System.out.println("Error: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    System.out.println("Failure: " + t.getMessage());
                                }
                            });



                            viewModel.setActiveStatus(2);

                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Failure: " + t.getMessage());
                    }
                });


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");

                DatabaseReference orderReference = databaseReference.child(order);

                orderReference.child("status").setValue("1")
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("Firebase", "Status updated successfully to 1");
                            } else {
                                Log.e("Firebase", "Failed to update status: " + task.getException());
                            }
                        });
                useMap = true;

                RunningStatusViewModel viewModel = new ViewModelProvider(getActivity()).get(RunningStatusViewModel.class);

                viewModel.setRunningStatus(true);


                onDestroyView();
            }
        });



        return rootView;
    }

    private void loadMoneyO(String order) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        Call<Double> call2 = api.tonghoadon(order);
        call2.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()) {
                    result1 = response.body();
                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                System.out.println("Failure: " + t.getMessage());
            }
        });
    }

    private void loadMoney(int userId) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        Call<Double> call1 = api.sodu(userId);
        call1.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                System.out.println("Failure: " + t.getMessage());
            }
        });

    }

    private boolean checkStringNull(String s) {
        return !s.equals("");
    }

    private void loadInfor() {
        if (!order.equals("")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String restaurantName = sharedPreferences.getString("restaurantName", "");
            if (checkStringNull(restaurantName))
                textRestaurantName.setText("Nhà hàng: " +restaurantName);
            String restaurantAddress = sharedPreferences.getString("restaurantAddress", "");
            if (checkStringNull(restaurantAddress))
                textRestaurantAddress.setText("Địa chỉ: " +restaurantAddress);
            String billName = sharedPreferences.getString("billName", "");
            if (checkStringNull(billName))
                textBillName.setText("Hoá đơn: " +billName);
            String paymentStatus = sharedPreferences.getString("paymentStatus", "");
            if (checkStringNull(paymentStatus))
                textBillPaymentStatus.setText(paymentStatus.equals("Chua thanh toan") ? "Tình trạng: Chưa thanh toán" : paymentStatus);
            String totalPrice = sharedPreferences.getString("totalPrice", "");
            if (checkStringNull(totalPrice))

                textBillTotalPrice.setText("Tổng hoá đơn: " +totalPrice);

            if(checkStringNull(restaurantName)||checkStringNull(restaurantAddress)||checkStringNull(billName)||checkStringNull(paymentStatus)||checkStringNull(totalPrice)){
                if(!billName.equals("")&&billName.equals(order))
                    return;
            }



        }

        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        Call<String[]> call = api.getInforOrder(order);

        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if (response.isSuccessful()) {
                    String[] temp = response.body();
                    if (temp != null) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        String restaurantName = (temp[4] != null && !temp[4].isEmpty()) ? temp[4] : "Tên nhà hàng không có";
                        String restaurantAddress = (temp[3] != null && !temp[3].isEmpty()) ? temp[3] : "Địa chỉ không có";
                        String billName = (temp[0] != null && !temp[0].isEmpty()) ? temp[0] : "Tên đơn hàng không có";
                        String paymentStatus = (temp[2] != null && !temp[2].isEmpty()) ? temp[2] : "Trạng thái thanh toán không có";

                        String totalPrice = (temp[1] != null && !temp[1].isEmpty()) ? temp[1] : "0";
                        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                        String formattedTotal = numberFormat.format(Double.parseDouble(totalPrice)) + "đ";

                        editor.putString("orderId", order);
                        editor.putString("restaurantName", restaurantName);
                        editor.putString("restaurantAddress", restaurantAddress);
                        editor.putString("billName", billName);
                        editor.putString("paymentStatus", paymentStatus);
                        editor.putString("totalPrice", formattedTotal);
                        editor.putString("customerTotal", totalPrice);

                        editor.apply();

                        textRestaurantName.setText("Nhà hàng: " +restaurantName);
                        textRestaurantAddress.setText("Địa chỉ: " +restaurantAddress);
                        textBillName.setText("Hoá đơn: " +billName);
                        textBillTotalPrice.setText("Tổng hoá đơn: " +formattedTotal);
                        textBillPaymentStatus.setText(paymentStatus.equals("Chua thanh toan") ? "Tình trạng: Chưa thanh toán" : paymentStatus);

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
    }


    @Override
    public void onDismiss(@NonNull android.content.DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }

    public interface DismissListener {
        void onDismiss();
    }

    public interface ResetMapListener {
        void onResetMap();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!useMap) {
            if (resetMapListener != null) {
                resetMapListener.onResetMap();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!useMap) {
            if (resetMapListener != null) {
                resetMapListener.onResetMap();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!useMap) {
            if (resetMapListener != null) {
                resetMapListener.onResetMap();
            }
        }
    }
}