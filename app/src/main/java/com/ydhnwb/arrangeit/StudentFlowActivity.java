package com.ydhnwb.arrangeit;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ydhnwb.arrangeit.models.StudentModel;
import com.ydhnwb.arrangeit.utilities.Constants;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class StudentFlowActivity extends AppCompatActivity {
    private TextInputEditText et_name, et_nim, et_email;
    private AppCompatSpinner sp_semester;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_flow);
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
        fillForms();
        saveChanges();
    }


    private void initComponents(){
        et_name = findViewById(R.id.et_name);
        et_nim = findViewById(R.id.et_nim);
        et_email = findViewById(R.id.et_email);
        sp_semester = findViewById(R.id.sp_semester);
        fab = findViewById(R.id.fab);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constants.REF_STUDENTS);
    }

    private void saveChanges(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String nim = et_nim.getText().toString().trim();
                int semester = Integer.parseInt(sp_semester.getSelectedItem().toString());
                boolean b = !name.isEmpty() && !email.isEmpty() && !nim.isEmpty();
                if(isNew()){
                    if(b){
                        String key = databaseReference.push().getKey();
                        StudentModel student = new StudentModel(nim, email, name, name.toLowerCase(), key, semester);
                        databaseReference.child(key).setValue(student);
                        Toast.makeText(StudentFlowActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(StudentFlowActivity.this, "Please fill all forms", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(b){
                        StudentModel student = new StudentModel(nim, email, name, name.toLowerCase(), getStudent().getKey(), semester);
                        databaseReference.child(getStudent().getKey()).setValue(student);
                        Toast.makeText(StudentFlowActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(StudentFlowActivity.this, "Please fill all forms", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void fillForms(){
        if(!isNew()){
            StudentModel student = getStudent();
            et_name.setText(student.getName());
            et_nim.setText(student.getNim());
            et_email.setText(student.getEmail());
            sp_semester.setSelection(student.getSemester()-1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isNew()){
            return super.onCreateOptionsMenu(menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_student_flow, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            AlertDialog.Builder db = new AlertDialog.Builder(StudentFlowActivity.this);
            db.setMessage("Are you sure to delete this student?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseReference.child(getStudent().getKey()).removeValue();
                    Toast.makeText(StudentFlowActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
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

    private Boolean isNew(){ return getIntent().getBooleanExtra("ISNEW", true); }
    private StudentModel getStudent(){ return getIntent().getParcelableExtra("STUDENT"); }
}