package com.example.joserv_1.loginscreen;

import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity {
    private List<Item_sliding_menu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private RelativeLayout maincontent;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView=(ListView)findViewById(R.id.lv_sliding_menu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_Layout);
        maincontent=(RelativeLayout)findViewById(R.id.main_content);
        listSliding=new ArrayList<>();

        //add item
        listSliding.add(new Item_sliding_menu(R.mipmap.ic_launcher, "logo"));
        listSliding.add(new Item_sliding_menu(R.mipmap.ic_launcher, "My Requst"));
        listSliding.add(new Item_sliding_menu(R.mipmap.ic_launcher, "Prfile"));
        adapter=new SlidingMenuAdapter(this, listSliding);
        listView.setAdapter(adapter);

        //Display icons to open/close sliding list
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //set title
        setTitle(listSliding.get(0).getTitle());
        //item selected
        listView.setItemChecked(0, true);
        //close menu
        drawerLayout.closeDrawer(listView);

        //hanlde on item click

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //set title
                setTitle(listSliding.get(position).getTitle());
                //item selected
                listView.setItemChecked(position, true);
                //close menu
                drawerLayout.closeDrawer(listView);
            }
        });

        drawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.drawer_opened,R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
