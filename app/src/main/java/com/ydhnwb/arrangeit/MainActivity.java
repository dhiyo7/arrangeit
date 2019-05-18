package com.ydhnwb.arrangeit;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ydhnwb.arrangeit.fragments.DashboardFragment;
import com.ydhnwb.arrangeit.fragments.ScheduleFragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private static boolean openFirst = true;
    private static int navStatus = -1;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponents();
        if(savedInstanceState == null){
            openFirst = true;
            MenuItem item = navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { super.onBackPressed(); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_dashboard :
                if(navStatus == 0 && !openFirst){
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    navStatus = 0;
                    openFirst = false;
                    fragment = new DashboardFragment();
                }
            case R.id.nav_schedule :
                if(navStatus == 1 && !openFirst){
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    navStatus = 1;
                    openFirst = false;
                    fragment = new ScheduleFragment();
                }
            default:
                navStatus = 0;
                openFirst = false;
                fragment = new DashboardFragment();
        }
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.screen_container, fragment);
            fragmentTransaction.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void listenerAuth(Boolean b){
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    firebaseUser = firebaseAuth.getCurrentUser();
                }else{
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        if(b){
            firebaseAuth.addAuthStateListener(authStateListener);
        }else{
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void initComponents(){
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listenerAuth(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        listenerAuth(false);
    }
}