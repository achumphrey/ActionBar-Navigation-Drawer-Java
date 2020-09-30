package com.example.actionbarnavdrawerjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String [] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionBar;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true); // Show the home button
            actionBar.setDisplayHomeAsUpEnabled(true); // Go to home when clicked
        }

        // This populates the drawer with a list of items.
        titles = getResources().getStringArray(R.array.titles);
        drawerList = findViewById(R.id.drawerList);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                titles);

        drawerList.setAdapter(adapter);

        drawerLayout = findViewById(R.id.drawerLayout);

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }
        /*else
            selectItem(0);*/

       drawerLayout.setDrawerListener(drawerToggle);
       setUpDrawerToggle();


        getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("visible_fragment");

                if (fragment instanceof TopLevelFragment)
                    currentPosition = 0;
                if (fragment instanceof PageOneFragment)
                    currentPosition = 1;
                if (fragment instanceof PageTwoFragment)
                    currentPosition = 2;
                if (fragment instanceof PageThreeFragment)
                    currentPosition = 3;

                setActionBarTitle(currentPosition);
                drawerList.setItemChecked(currentPosition, true);

            }
        });
    }

    // What happens when the drawer item is clicked?
    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position){

        currentPosition = position;

        // Create a new fragment to show based on position
        Fragment fragment;

        switch (position){

            case 1:
                fragment = new PageOneFragment();
                break;

            case 2:
                fragment = new PageTwoFragment();
                break;

            case 3:
                fragment = new PageThreeFragment();
                break;

            default:
                fragment = new TopLevelFragment();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(currentPosition, true);
        drawerList.setSelection(position);
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    // Set the name displayed as title on the screen based on position
    private void setActionBarTitle(int position){

        String barTitle;

        if (position == 0){
            barTitle = getResources().getString(R.string.app_name);
        }else
            barTitle = titles[position];

        getSupportActionBar().setTitle(barTitle);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putInt("position", currentPosition);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // Show the menu on the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // What happens when a menu item is clicked?
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        switch (item.getItemId()){

            case R.id.settings:
                // run code
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_LONG).show();
                return true;

            case R.id.example:
                //run code
                Toast.makeText(this, "Accessibility Clicked", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerToggle.syncState();

    }
}
