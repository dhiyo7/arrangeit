package com.ydhnwb.arrangeit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ydhnwb.arrangeit.LoginActivity;
import com.ydhnwb.arrangeit.R;
import com.ydhnwb.arrangeit.adapters.CarouselAdapter;
import com.ydhnwb.arrangeit.models.CarouselModel;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<CarouselModel> mList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv_overview);
        recyclerView.setHorizontalScrollBarEnabled(true);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);
        mList.add(new CarouselModel("Students", 0));
        mList.add(new CarouselModel("Lecturers", 1));
        mList.add(new CarouselModel("Rooms", 2));
        mList.add(new CarouselModel("Courses", 3));
        recyclerView.setAdapter(new CarouselAdapter(mList, getActivity()));
    }
}
