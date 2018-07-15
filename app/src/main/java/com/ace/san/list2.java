package com.ace.san;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ace.san.Login.LoginActivity;
import com.ace.san.db.Item;
import com.ace.san.db.ItemList;
import com.ace.san.db.Trans;
import com.ace.san.db.TransList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class list2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Variabel ListView untuk Transaksi
    ListView lvtran;

    DatabaseReference databaseTran;

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    List<Trans> transList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvtran = (ListView) findViewById(R.id.lvtrans);

        databaseTran = FirebaseDatabase.getInstance().getReference("transaction");
        transList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(list2.this,LoginActivity.class));
                }
            }
        };

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    //untuk menampilkan isi database Trans
    protected void onStart() {
        super.onStart();

        databaseTran.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot transSnapshot : dataSnapshot.getChildren()){
                    Trans trans = transSnapshot.getValue(Trans.class);
                    transList.add(trans);
                }

                TransList adapter = new TransList(list2.this, transList);
                lvtran.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }



    // session validasi user
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(list2.this, LoginActivity.class));
                finish();
            }
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //Bagian intent antar menu
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.home:
                Intent h= new Intent(list2.this,Home.class);
                startActivity(h);
                break;
            case R.id.add:
                Intent i= new Intent(list2.this,Add.class);
                startActivity(i);
                break;
            case R.id.list:
                Intent j= new Intent(list2.this,list.class);
                startActivity(j);
                break;
            case R.id.transaction:
                Intent l= new Intent(list2.this,Transaction.class);
                startActivity(l);
                break;
            case R.id.translist:
                Intent n= new Intent(list2.this,list2.class);
                startActivity(n);
                break;
            case R.id.logout:
                Intent m= new Intent(list2.this,Logout.class);
                startActivity(m);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
