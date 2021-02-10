package com.occ.booklibrary;
//17070006056 OSMAN CAN CINAR
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText titleText, authorText, pagesText;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        titleText = findViewById(R.id.editBookName);
        authorText = findViewById(R.id.editAuthor);
        pagesText = findViewById(R.id.editPageNumber);
    }

    public void addClicked(View view) {
        String title = titleText.getText().toString();
        String author = authorText.getText().toString();
        String pages = pagesText.getText().toString();

        try{
            SQLiteDatabase database = this.openOrCreateDatabase("Books",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY, title VARCHAR, author VARCHAR, pages VARCHAR)");
            query = "INSERT INTO books(title, author, pages) VALUES (?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(query);
            sqLiteStatement.bindString(1,title);
            sqLiteStatement.bindString(2,author);
            sqLiteStatement.bindString(3,pages);
            sqLiteStatement.execute();
            Toast.makeText(this,"Added Successfully!",Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(this,"It's Not Possible To ADD Right Now!",Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
