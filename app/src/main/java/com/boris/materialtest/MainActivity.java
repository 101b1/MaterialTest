package com.boris.materialtest;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    private String[] drawerItems;
    private ListView drawerList;

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            setFragment(position);
        }
    }

    private void setFragment(int position){
        /*String[] message;
        message = getResources().getStringArray(R.array.drawer_items);
        Toast.makeText(this, "Pressed"+message[position], Toast.LENGTH_SHORT).show();
        */
        Fragment newFragment;
        switch (position) {
            case 0:
                newFragment = new HomeFragment();
                break;
            case 1:
                newFragment = new VkDialogFragment();
                break;
            case 2:
                newFragment = new VkFriendsFragment();
                break;
            case 3:
                newFragment = new SettingsFragment();
                break;
            case 4:
                newFragment = new AboutFragment();
                break;
            default:
                newFragment = new HomeFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, newFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerItems = getResources().getStringArray(R.array.drawer_items);
        drawerList = (ListView)findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

    }
}
