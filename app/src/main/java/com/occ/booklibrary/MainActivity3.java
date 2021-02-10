package com.occ.booklibrary;
//17070006056 OSMAN CAN CINAR
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    TextView book_name, author_name, page_numbers;
    String title, author, pages, ID;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        book_name = findViewById(R.id.bookName);
        author_name = findViewById(R.id.author);
        page_numbers = findViewById(R.id.pageNumber);

        Intent intent = getIntent();
        ID = intent.getStringExtra("recordID");
        try{
            database = this.openOrCreateDatabase("Books",MODE_PRIVATE,null);
            cursor = database.rawQuery("SELECT * FROM books WHERE id = ?",new String[] {ID});

            int titleIndex = cursor.getColumnIndex("title");
            int authorIndex = cursor.getColumnIndex("author");
            int pageIndex = cursor.getColumnIndex("pages");

            while(cursor.moveToNext()){
                book_name.setText(cursor.getString(titleIndex));
                author_name.setText(cursor.getString(authorIndex));
                page_numbers.setText(cursor.getString(pageIndex));
            }
            cursor.close();
            database.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateClicked(View view) {
        title = book_name.getText().toString();
        author = author_name.getText().toString();
        pages = page_numbers.getText().toString();

        try{
            SQLiteDatabase database = this.openOrCreateDatabase("Books",MODE_PRIVATE,null);
            String query = "UPDATE books SET title = ?, author = ?, pages = ? WHERE id = ?";
            SQLiteStatement sqLiteStatement = database.compileStatement(query);
            sqLiteStatement.bindString(1,title);
            sqLiteStatement.bindString(2,author);
            sqLiteStatement.bindString(3,pages);
            sqLiteStatement.bindString(4,ID);
            sqLiteStatement.execute();
            Toast.makeText(this,"Updated Successfully!",Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(this,"It's Not Possible To UPDATE Right Now!",Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(MainActivity3.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void deleteClicked(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete "+ book_name.getText().toString()+" ?");
        alert.setMessage("Are you sure you want to delete "+book_name.getText().toString()+" ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try{
                    SQLiteDatabase database = MainActivity3.this.openOrCreateDatabase("Books",MODE_PRIVATE,null);
                    database.execSQL("DELETE FROM books WHERE id = ?",new String[] {ID});
                    Toast.makeText(MainActivity3.this,"Deleted Successfully!",Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(MainActivity3.this,"It's Not Possible To DELETE Right Now!",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(MainActivity3.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity3.this,"Not Saved",Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }
}
