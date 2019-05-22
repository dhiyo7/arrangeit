package com.ydhnwb.arrangeit;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ydhnwb.arrangeit.models.RoomModel;
import com.ydhnwb.arrangeit.utilities.Constants;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class RoomFlowActivity extends AppCompatActivity {
    private TextInputEditText et_name, et_desc;
    private DatabaseReference databaseReference;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_flow);
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
        et_name = findViewById(R.id.et_name);
        et_desc = findViewById(R.id.et_description);
        fab = findViewById(R.id.fab);
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.REF_ROOMS);
    }

    private void saveChanges(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                boolean b = !name.isEmpty() && !desc.isEmpty();
                if(b){
                    if(isNew()){
                        String key = databaseReference.push().getKey();
                        RoomModel r = new RoomModel(key, name,name.toLowerCase(), desc);
                        databaseReference.child(key).setValue(r);
                        Toast.makeText(RoomFlowActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        RoomModel r = new RoomModel(getRoom().getKey(), name, name.toLowerCase(), desc);
                        databaseReference.child(r.getKey()).setValue(r);
                        Toast.makeText(RoomFlowActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    Snackbar.make(view , "Please fill all forms first", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isNew()){
            return super.onCreateOptionsMenu(menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_room_flow, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            AlertDialog.Builder db = new AlertDialog.Builder(RoomFlowActivity.this);
            db.setMessage("Are you sure to delete this room?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseReference.child(getRoom().getKey()).removeValue();
                    Toast.makeText(RoomFlowActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
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

    private void fillAll(){
        if (!isNew()){
            et_desc.setText(getRoom().getDescription());
            et_name.setText(getRoom().getName());
        }
    }

    private Boolean isNew(){ return getIntent().getBooleanExtra("ISNEW", true);}
    private RoomModel getRoom() { return getIntent().getParcelableExtra("ROOM");}
}