package org.test.fbpost;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class AddActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    String title, content;
    EditText edit_title, edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edit_title = (EditText) findViewById(R.id.title);
        edit_content = (EditText) findViewById(R.id.content);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void write(View v) {
        String title = edit_title.getText().toString();
        String content = edit_content.getText().toString();

        String query="INSERT INTO tableName VALUES(null, ?, ?, '" + LocalDateTime.now() + "');";
        String[] selections={title, content};
        db.execSQL(query, selections);

        Toast.makeText(getApplicationContext(), "추가 성공: " + LocalDateTime.now(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 100);
        finish();
    }

    public void back(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}
