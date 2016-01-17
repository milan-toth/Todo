package hu.ektf.todo;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends ListActivity {
    private TodosDataSource datasource;
    private ArrayAdapter<Todo> adapter;
    private ListView myList;
    private Todo todo;
    private FloatingActionButton fab;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        myList = (ListView) findViewById(android.R.id.list);
        datasource = new TodosDataSource(this);
        datasource.open();

        List<Todo> values = datasource.getAllTodos();

        adapter = new ArrayAdapter<Todo>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        adapter.notifyDataSetChanged();


        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // delete item
                if (getListAdapter().getCount() > 0) {
                    todo = (Todo) getListAdapter().getItem((int) id);
                    datasource.deleteTodo(todo);
                    adapter.remove(todo);
                    Toast.makeText(MainActivity.this, "Todo deleted", Toast.LENGTH_SHORT).show();
                    Log.v(TAG,"Todo deleted");
                }

                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),
                        AddActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        datasource.open();
        adapter.notifyDataSetChanged();

        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

