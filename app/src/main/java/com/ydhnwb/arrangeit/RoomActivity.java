package com.ydhnwb.arrangeit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ydhnwb.arrangeit.adapters.RoomAdapter;
import com.ydhnwb.arrangeit.models.RoomModel;
import com.ydhnwb.arrangeit.utilities.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RoomModel> roomList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initComponents();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoomActivity.this, RoomFlowActivity.class);
                i.putExtra("ISNEW", true);
                startActivity(i);
            }
        });
    }

    private void initComponents(){
        recyclerView = findViewById(R.id.rv_room);
        recyclerView.setLayoutManager(new GridLayoutManager(RoomActivity.this, 2));
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void fetchData(Boolean b){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        RoomModel room = ds.getValue(RoomModel.class);
                        roomList.add(room);
                    }
                }
                recyclerView.setAdapter(new RoomAdapter(roomList, RoomActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ydhnwb", databaseError.getMessage());
                Toast.makeText(RoomActivity.this, "Cannot fetch data from server", Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.REF_ROOMS);
        if(b){
            databaseReference.orderByChild("name_idiomatic").addValueEventListener(listener);
        }else{
            databaseReference.removeEventListener(listener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchData(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        fetchData(false);
    }
}