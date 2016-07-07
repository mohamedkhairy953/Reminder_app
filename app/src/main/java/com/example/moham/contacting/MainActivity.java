package com.example.moham.contacting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public boolean is_in_action_mod = false;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ContacterModel> my_peaple_arraylist;
    ArrayList<ContacterModel> selection_array_list = new ArrayList();
    DBController dbController = new DBController(this);
    int counter = 0;
    Toolbar toolbar;
    private TextView counterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        counterText = (TextView) findViewById(R.id.countertext_id);
        counterText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        my_peaple_arraylist = dbController.get_all_people();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        adapter = new recycler_adapter(this, my_peaple_arraylist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_icon:
                startActivity(new Intent(MainActivity.this, AddContacter_activity.class));
                break;
            case R.id.delete_all_icon:
                recycler_adapter recycleviewAdapter = (recycler_adapter) adapter;
                recycleviewAdapter.update_adapter(selection_array_list);
                clear_action_mode();
                break;
            case android.R.id.home:
                clear_action_mode();
                adapter.notifyDataSetChanged();
                break;
            case R.id.limit_icon:
                toolbar.getMenu().clear();
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                toolbar.inflateMenu(R.menu.menu_contextual);
                is_in_action_mod = true;
                counterText.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        recycler_adapter adapter2 = (recycler_adapter) this.adapter;
        adapter2.add_element(my_peaple_arraylist);
        my_peaple_arraylist = dbController.get_all_people();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (is_in_action_mod) {
            clear_action_mode();
            adapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }

    public void prepare_selection(View v, int position) {
        if (((CheckBox) v).isChecked()) {
            selection_array_list.add(my_peaple_arraylist.get(position));
            counter++;
            update_counter();
        } else {
            selection_array_list.remove(my_peaple_arraylist.get(position));
            counter--;
            update_counter();
        }


    }

    public void update_counter() {
        if (counter == 0) {
            counterText.setText("0 item selected");
        } else {
            counterText.setText(counter + " item selected");
        }
    }

    public void clear_action_mode() {
        toolbar.getMenu().clear();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.inflateMenu(R.menu.main_menu);
        is_in_action_mod = false;
        counterText.setVisibility(View.GONE);
        counterText.setText("0 item selected");
        counter = 0;
        selection_array_list.clear();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


}

