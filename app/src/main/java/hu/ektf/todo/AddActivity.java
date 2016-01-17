package hu.ektf.todo;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class AddActivity extends AppCompatActivity {
    private TodosDataSource datasource;
    private EditText addTodo;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addTodo = (EditText) findViewById(R.id.addTodo);
        datasource = new TodosDataSource(this);
        datasource.open();
    }

    public void onClick(View view){
        // add new item
        Todo todo = datasource.createTodo(addTodo.getText().toString());

        addTodo.setText(null);
        Toast.makeText(AddActivity.this, "Todo added", Toast.LENGTH_SHORT).show();
        Log.v(TAG, "Todo added");
        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(i);
        finish();
    }
}
