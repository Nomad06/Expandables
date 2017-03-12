package com.example.bubal.expandables;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends Activity {

    private final int ADD_CONTACT_REQUEST = 1;
    private final int ADD_GROUP_REQUEST = 2;

    ExpandableListView expandableListView;
    ArrayList<Map<String, String>> groups;
    ArrayList<ArrayList<Map<String, String>>> childData;
    AdapterHelper ah;
    SimpleExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ah = new AdapterHelper(this);
        adapter = ah.getSimpleExpandableListAdapter();
        groups = ah.getGroup();
        childData = ah.getChildData();
        expandableListView = (ExpandableListView) findViewById(R.id.expandable);
        expandableListView.setAdapter(adapter);
        ExpendableListeners.setListeners(expandableListView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String itemName;
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case ADD_CONTACT_REQUEST:
                    itemName = data.getExtras().get("name").toString();
                    int groupPosition = data.getExtras().getInt("groupNumber");
                    ah.addItemToList(itemName, groupPosition);
                    adapter.notifyDataSetChanged();
                    break;

                case ADD_GROUP_REQUEST:
                    itemName = data.getExtras().get("name").toString();
                    ah.addGroup(itemName);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addElement(View view){
        Intent intent = new Intent(this, AddElementActivity.class);
        intent.putExtra("group", ah.getGroup());
        intent.putExtra("addElement", true);
        startActivityForResult(intent, ADD_CONTACT_REQUEST);
    }

    public void addGroup(View view){
        Intent intent = new Intent(this, AddElementActivity.class);
        intent.putExtra("addElement", false);
        startActivityForResult(intent, ADD_GROUP_REQUEST);
    }
}
