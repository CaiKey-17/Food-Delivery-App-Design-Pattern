package com.example.project_android.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
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

public class ComplaintListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private AdminComplaintAdapter adapter;
    private List<Complaint> complaintList;
    private SearchView searchView;

    public ComplaintListFragment() {
    }

    public static ComplaintListFragment newInstance(String param1, String param2) {
        ComplaintListFragment fragment = new ComplaintListFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaint_list, container, false);

        recyclerView = view.findViewById(R.id.complaint_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView = view.findViewById(R.id.search_view);

        complaintList = new ArrayList<>();
        complaintList.add(new Complaint("1", "Quán A", "Khiếu nại về dịch vụ", "Chi tiết khiếu nại...", new Date(1231123), false));
        complaintList.add(new Complaint("2", "Quán B", "Khiếu nại về tài xế", "Chi tiết khiếu nại...", new Date(12312312), false));

        adapter = new AdminComplaintAdapter(getContext(), complaintList);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        return view;
    }
}