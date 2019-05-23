package com.ydhnwb.arrangeit;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ydhnwb.arrangeit.models.CourseModel;
import com.ydhnwb.arrangeit.utilities.Constants;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CourseFlowActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private TextInputEditText et_name, et_code;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_flow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initComponents();
        saveChanges();
        fillAll();
    }

    private void initComponents(){
        fab = findViewById(R.id.fab);
        et_code = findViewById(R.id.et_code);
        et_name = findViewById(R.id.et_name);
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.REF_COURSES);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isNew()){
            return super.onCreateOptionsMenu(menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_lecturer_flow, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            AlertDialog.Builder db = new AlertDialog.Builder(CourseFlowActivity.this);
            db.setMessage("Are you sure to delete this course?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseReference.child(getCourse().getKey()).removeValue();
                    Toast.makeText(CourseFlowActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog d = db.create();
            d.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveChanges(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString().trim();
                String code = et_code.getText().toString().trim();
                boolean b = !name.isEmpty() && !code.isEmpty();
                if(b){
                    if(isNew()){
                        String key = databaseReference.push().getKey();
                        CourseModel course = new CourseModel(key, name, name.toLowerCase(), code);
                        databaseReference.child(key).setValue(course);
                        Toast.makeText(CourseFlowActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        CourseModel c = new CourseModel(getCourse().getKey(), name, name.toLowerCase(), code);
                        databaseReference.child(c.getKey()).setValue(c);
                        Toast.makeText(CourseFlowActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    Snackbar.make(view, "Please fill all forms", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void fillAll(){
        if(!isNew()){
            et_name.setText(getCourse().getName());
            et_code.setText(getCourse().getCode());
        }
    }

    private Boolean isNew(){ return getIntent().getBooleanExtra("ISNEW", true);}
    private CourseModel getCourse(){ return getIntent().getParcelableExtra("COURSE");}

}
