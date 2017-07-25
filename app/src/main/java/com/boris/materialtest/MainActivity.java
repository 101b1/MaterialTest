package com.boris.materialtest;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
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
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;
import java.util.Locale;

public class MainActivity extends Activity {
    public static final String PREFS_FILE = "MaterialTestPrefs";
    static final String PREF_LANG = "pref_lang";
    static final String PREF_THEME = "pref_theme";
    private boolean preferenceChanged = true;
    private String[] drawerItems;
    private ListView drawerList;
    private String currentLocale;
    private SharedPreferences sharedPreferences;
    private String curTheme;

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
        changeBarTitle(position);
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(drawerList);

        }

    private void changeBarTitle(int position)
    {
        String title;
        if (position==0)
        {
            title = getResources().getString(R.string.app_name);
        } else
        {
            title = drawerItems[position];
        }
        getActionBar().setTitle(title);
    }

    /*private class preferencesChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    preferenceChanged = true;

                    if (key.equals(PREF_LANG))
                    {
                        changeLanguage(sharedPreferences);
                    }
                    else if (key.equals(PREF_THEME))
                    {
                        changeTheme(sharedPreferences);
                    }
                }
            }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener(){
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                preferenceChanged = true;

                if (key.equals(PREF_LANG))
                {
                    changeLanguage(sharedPreferences);
                }
                else if (key.equals(PREF_THEME))
                {
                    changeTheme(sharedPreferences);
                }
            }
        };
       /* PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefListener = new preferencesChangeListener();*/
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesChangeListener);
        int theme = getThemeInt(sharedPreferences.getString(PREF_THEME, "AppTheme"));
        setTheme(theme);

        drawerItems = getResources().getStringArray(R.array.drawer_items);
        drawerList = (ListView)findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        //SETFRAGMENT from Bundle?

    }

    private void changeLanguage(SharedPreferences preferences)
    {
        currentLocale = preferences.getString(PREF_LANG, "ru");
        /*currentLocale = new Locale(newLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(currentLocale);
        getApplicationContext().createConfigurationContext(conf);*/
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    private void  changeTheme(SharedPreferences preferences)
    {
        curTheme = preferences.getString(PREF_THEME, "AppTheme");
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    private int getThemeInt(String themeName)
    {
        if(themeName.equals(getResources().getString(R.string.pref_theme_default_val)))
        {
            return 0;
        }
        else return 1;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_LANG, currentLocale);
        editor.putString(PREF_THEME, curTheme);

        editor.commit();

    }


}
