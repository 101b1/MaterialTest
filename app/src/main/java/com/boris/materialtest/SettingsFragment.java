package com.boris.materialtest;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



public class SettingsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[] settings = getResources().getStringArray(R.array.settings_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_activated_1, settings);
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
