package com.occ.booklibrary;
//17070006056 OSMAN CAN CINAR
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ArrayList<String> id, pages, bookName, authorName; // Those are where I keep the data that I retrieve from SQLite.
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //on create regular stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // I made sure that they are empty.
        pages = new ArrayList<String>();
        bookName = new ArrayList<String>();
        authorName = new ArrayList<String>();
        id = new ArrayList<String>();

        //I used FAB to create a new record.
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent);
            }
        });

        //after referencing to recycler view and setting its layout as vertical, I declared the adapter.
        // then I set the recycler view's adapter as recycler adapter. At the end I called my getData to retrieve data from db.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(authorName,bookName,pages,id);
        recyclerView.setAdapter(recyclerAdapter);
        getData();
    }

    public void getData(){
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Books", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM books", null);
            int idIx = cursor.getColumnIndex("id");
            int titleIx = cursor.getColumnIndex("title");
            int authorIx = cursor.getColumnIndex("author");
            int pageIx = cursor.getColumnIndex("pages");

            while (cursor.moveToNext()) {
                bookName.add(cursor.getString(titleIx));
                authorName.add(cursor.getString(authorIx));
                pages.add(cursor.getString(pageIx));
                id.add(cursor.getString(idIx));
            }
            recyclerAdapter.notifyDataSetChanged();
            //recyclerAdapter.notifyItemInserted(idIx);
            cursor.close();
            database.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This is the way that I display the "delete all" on the menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.welcome,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //do this to delete all recycler rows
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteAll) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete All Records ?");
            alert.setMessage("Are you sure to delete all these records ?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try{
                        SQLiteDatabase database = MainActivity.this.openOrCreateDatabase("Books",MODE_PRIVATE,null);
                        database.execSQL("DELETE FROM books ");
                        Toast.makeText(MainActivity.this,"All of the records are deleted successfully!",Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(MainActivity.this,"None Deleted!",Toast.LENGTH_SHORT).show();
                    }
                    recyclerAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this,"None Deleted!",Toast.LENGTH_SHORT).show();
                }
            });
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}