package org.test.fbpost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MemoAdapter memoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, 101);
                finish();
            }
        });

        cursor = db.rawQuery("SELECT * FROM tableName", null);
        startManagingCursor(cursor);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        memoAdapter = new MemoAdapter(getApplicationContext());

        while(cursor.moveToNext()) {
            Memo memo = new Memo(cursor.getString(1), cursor.getString(2), new Date(cursor.getLong(3)*1000L));
            memoAdapter.addItem(memo);
        }

        mRecyclerView.setAdapter(memoAdapter);
        memoAdapter.setOnItemClickListener(new MemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MemoAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                cursor.moveToPosition(position);
                intent.putExtra("id", Integer.parseInt(cursor.getString(0)));
                setResult(Activity.RESULT_OK, intent);
                cursor.close();
                startActivity(intent);
                finish();
            }
        });

        runAnimation(mRecyclerView);

    }

    private void runAnimation(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;
        //controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);
        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_from_right);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
