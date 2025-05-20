package com.example.project_android.Interface;

public interface OnItemClickListener<T> {
    void onItemClick(T item, int position);


    void onVoucherSelected(int voucherId);
    void onPriceSelected(double price);

}
