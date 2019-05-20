package com.ydhnwb.arrangeit;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ydhnwb.arrangeit.models.LecturerModel;
import com.ydhnwb.arrangeit.utilities.Constants;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class LecturerFlowActivity extends AppCompatActivity {
    private TextInputEditText et_name, et_nipy, et_email;
    private DatabaseReference databaseReference;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_flow);
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
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fillForms();
        saveChanges();
    }

    private void fillForms(){
        if(!isNew()){
            LecturerModel l = getLecturer();
            et_name.setText(l.getName());
            et_nipy.setText(l.getNipy());
            et_email.setText(l.getEmail());
        }
    }

    private void saveChanges(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String nipy = et_nipy.getText().toString().trim();
                boolean b = !name.isEmpty() && !email.isEmpty() && !nipy.isEmpty();
                if(isNew()){
                    if(b){
                        String key = databaseReference.push().getKey();
                        LecturerModel lecturer = new LecturerModel(key, name, name.toLowerCase(), nipy, email);
                        databaseReference.child(key).setValue(lecturer);
                        Toast.makeText(LecturerFlowActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        //do all validation here
                        Toast.makeText(LecturerFlowActivity.this, "Please fill all forms", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(b){
                        LecturerModel lecturer = new LecturerModel(getLecturer().getKey(), name, name.toLowerCase(), nipy, email);
                        databaseReference.child(getLecturer().getKey()).setValue(lecturer);
                        Toast.makeText(LecturerFlowActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        //do more validation here
                        Toast.makeText(LecturerFlowActivity.this, "Please fill all forms", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initComponents(){
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_nipy = findViewById(R.id.et_nipy);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constants.REF_LECTURER);
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
            AlertDialog.Builder db = new AlertDialog.Builder(LecturerFlowActivity.this);
            db.setMessage("Are you sure to delete this lecturer?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseReference.child(getLecturer().getKey()).removeValue();
                    Toast.makeText(LecturerFlowActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
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
    private LecturerModel getLecturer(){ return getIntent().getParcelableExtra("LECTURER"); }


}
