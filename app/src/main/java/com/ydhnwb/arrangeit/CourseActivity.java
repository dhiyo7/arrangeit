package com.ydhnwb.arrangeit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.ydhnwb.arrangeit.adapters.CourseAdapter;
import com.ydhnwb.arrangeit.models.CourseModel;
import com.ydhnwb.arrangeit.utilities.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private FastScrollRecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<CourseModel> courseList = new ArrayList<>();
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
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
                Intent i = new Intent(CourseActivity.this, CourseFlowActivity.class);
                i.putExtra("ISNEW", true);
                startActivity(i);
            }
        });
    }

    private void initComponents(){
        recyclerView = findViewById(R.id.rv_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(CourseActivity.this));
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constants.REF_COURSES);
    }

    private void fetchData(Boolean b){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        CourseModel course = ds.getValue(CourseModel.class);
                        courseList.add(course);
                    }
                }
                recyclerView.setAdapter(new CourseAdapter(courseList, CourseActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ydhnwb", databaseError.getMessage());
                Toast.makeText(CourseActivity.this, "Cannot fetch data from server", Toast.LENGTH_SHORT).show();
            }
        };
        if(b){
            databaseReference.orderByChild("name_idiomatic").addListenerForSingleValueEvent(listener);
        }else{
            databaseReference.removeEventListener(listener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        fetchData(false);
    }

    private void searchByName(String query){
        courseList.clear();
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        CourseModel c = ds.getValue(CourseModel.class);
                        courseList.add(c);
                    }
                    recyclerView.setAdapter(new CourseAdapter(courseList, CourseActivity.this));
                }else{
                    Toast.makeText(CourseActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ydhnwb", databaseError.getMessage());
            }
        };
        databaseReference.orderByChild("name_idiomatic").startAt(query).endAt(query+"\uf8ff").addListenerForSingleValueEvent(listener);
    }

    private void hideKeyboard(){
        View v = getCurrentFocus();
        if(v != null){
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        if(menuItem != null){
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchByName(query.toLowerCase());
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText.length() == 0){
                        fetchData(true);
                    }
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
    }
}
