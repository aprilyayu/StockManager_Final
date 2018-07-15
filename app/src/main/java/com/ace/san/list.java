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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ace.san.Login.LoginActivity;
import com.ace.san.db.Item;
import com.ace.san.db.ItemList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //variabel List View untuk item
    ListView lvitem;

    //Referensi Database databaseItem dari Firebase
    DatabaseReference databaseItem;

    Button detail;

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //Item dikembalikan sebagai itemList
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //variabel lvlitem memanggil id lvitem
        lvitem = (ListView) findViewById(R.id.lvitem);

        detail = (Button) findViewById(R.id.detail);

        databaseItem = FirebaseDatabase.getInstance().getReference("item");
        itemList = new ArrayList<>();

        lvitem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = itemList.get(i);
                showUpdateDeleteDialog(item.getName(), item.getCategory(), item.getStatus(), item.getPlace(), item.getQuantity());
                return true;
            }
        });

        //Validasi User
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(list.this,LoginActivity.class));
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

    //untuk membuat pop-up notifikasi detail item
    private void showUpdateDeleteDialog(final String itemName, String itemCat, String itemStatus, String itemPlace, String itemQty) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextStat = (EditText) dialogView.findViewById(R.id.editTextStatus);
        final EditText editTextPlace = (EditText) dialogView.findViewById(R.id.editTextPlace);
        final EditText editTextQty = (EditText) dialogView.findViewById(R.id.editTextQuantity);
        final Spinner spinnerCat = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(itemName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String status = editTextStat.getText().toString().trim();
                String place = editTextPlace.getText().toString().trim();
                String quantity = editTextQty.getText().toString().trim();
                String category = spinnerCat.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateItem(name, status,place,quantity, category );
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteItem(itemName);
                b.dismiss();
            }
        });
    }

    //untuk edit item
    private boolean updateItem(String name, String category, String status , String place, String quantity) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("item");

        //updating artist
        Item artist = new Item(name, category, status, place, quantity);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Item Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    //untuk hapus item
    private boolean deleteItem(String name) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("item");

        //removing artist
        dR.removeValue();

        return true;
    }
    @Override
    //untuk menampilkan isi dari database item
    protected void onStart() {
        super.onStart();

        databaseItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot itemsnapshot : dataSnapshot.getChildren()){
                    Item item = itemsnapshot.getValue(Item.class);

                    itemList.add(item);
                }

                ItemList adapter = new ItemList(list.this, itemList);
                lvitem.setAdapter(adapter);
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



// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            //Validasi User
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(list.this, LoginActivity.class));
                    finish();
                }
            }
        };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Bagian intent antar menu
        int id=item.getItemId();
        switch (id){

            case R.id.home:
                Intent h= new Intent(list.this,Home.class);
                startActivity(h);
                break;
            case R.id.add:
                Intent i= new Intent(list.this,Add.class);
                startActivity(i);
                break;
            case R.id.list:
                Intent j= new Intent(list.this,list.class);
                startActivity(j);
                break;
            case R.id.transaction:
                Intent l= new Intent(list.this,Transaction.class);
                startActivity(l);
                break;
            case R.id.translist:
                Intent n= new Intent(list.this,list2.class);
                startActivity(n);
                break;
            case R.id.logout:
                Intent m= new Intent(list.this,Logout.class);
                startActivity(m);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
