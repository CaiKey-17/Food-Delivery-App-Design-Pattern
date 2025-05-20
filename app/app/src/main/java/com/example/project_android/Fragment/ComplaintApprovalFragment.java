package com.example.project_android.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_android.Adapter.AdminComplaintAdapter;
import com.example.project_android.Model.Complaint;
import com.example.project_android.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComplaintApprovalFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private AdminComplaintAdapter adapter;
    private List<Complaint> complaintList;
    private String mParam1;
    private String mParam2;

    public ComplaintApprovalFragment() {
    }

    public static ComplaintApprovalFragment newInstance(String param1, String param2) {
        ComplaintApprovalFragment fragment = new ComplaintApprovalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_approval, container, false);

        recyclerView = view.findViewById(R.id.complaint_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        complaintList = new ArrayList<>();
        complaintList.add(new Complaint("1", "Quán C", "Khiếu nại về dịch vụ", "Chi tiết khiếu nại...", new Date(1231123), false));
        complaintList.add(new Complaint("2", "Quán D", "Khiếu nại về tài xế", "Chi tiết khiếu nại...", new Date(12312312), false));

        adapter = new AdminComplaintAdapter(getContext(), complaintList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}