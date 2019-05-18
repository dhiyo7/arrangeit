package com.ydhnwb.arrangeit;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.ydhnwb.arrangeit.adapters.StudentAdapter;
import com.ydhnwb.arrangeit.models.StudentModel;
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

public class StudentActivity extends AppCompatActivity {
    private List<StudentModel> studentList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FastScrollRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
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
            public void onClick(View view) { Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student,menu);
        MenuItem searchItem = menu.findItem(R.menu.menu_student);
        if(searchItem != null){
            SearchView sv = (SearchView) searchItem.getActionView();
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //doSearch()
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //donothing or search immediately
                    return false;
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

    private void hideKeyboard(){
        View v = getCurrentFocus();
        if(v != null){
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    private void initComponents(){
        recyclerView = findViewById(R.id.rv_student);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentActivity.this));
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void fetchData(Boolean b){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        StudentModel s = ds.getValue(StudentModel.class);
                        studentList.add(s);
                        recyclerView.setAdapter(new StudentAdapter(studentList, StudentActivity.this));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ydhnwb", databaseError.getMessage());
                Toast.makeText(StudentActivity.this, "Error when getting data", Toast.LENGTH_SHORT).show();
            }
        };

        databaseReference = firebaseDatabase.getReference(Constants.REF_STUDENTS);
        if(b){ databaseReference.addValueEventListener(listener);
        }else{ databaseReference.removeEventListener(listener); }
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
