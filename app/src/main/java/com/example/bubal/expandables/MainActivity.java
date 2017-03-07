package com.example.bubal.expandables;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends Activity {

    private String[] relatives = new String[]{"Parents", "Brothers", "Sisters"};
    private String[] parents = new String[]{"Father", "Mother"};
    private String[] brothers = new String[]{"Ah'mad"};
    private String[] sisters = new String[]{"Ayshat", "Fatima", "Asya", "Rabiya", "Salima"};

    ExpandableListView expandableListView;
    ArrayList<Map<String, String>> group;
    ArrayList<ArrayList<Map<String, String>>> childData;
    SimpleExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        group = new ArrayList<Map<String, String>>();
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        group = fillArray("groupName", relatives, group, null);
        fillArray("relative", parents, new ArrayList<Map<String, String>>(), childData);
        fillArray("relative", brothers, new ArrayList<Map<String, String>>(), childData);
        fillArray("relative", sisters, new ArrayList<Map<String, String>>(), childData);

        String[] groupFrom = new String[]{"groupName"};
        int[] groupTo = new int[]{android.R.id.text1};

        String[] childFrom = new String[]{"relative"};
        int[] childTo = new int[]{android.R.id.text1};

        adapter = new SimpleExpandableListAdapter(this, group, android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo, childData, android.R.layout.simple_list_item_1,
                childFrom, childTo);

        expandableListView = (ExpandableListView) findViewById(R.id.expandable);
        expandableListView.setAdapter(adapter);

        Uri contact = Uri.parse("content://contacts/people");
        Intent intent = new Intent(Intent.ACTION_PICK, contact);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ArrayList fillArray(String keyName, String[] dataMass, ArrayList<Map<String, String>> arrayToFill, ArrayList<ArrayList<Map<String, String>>> childData){
        for (String dataItem : dataMass){
            Map<String, String> m = new HashMap<>();
            m.put(keyName, dataItem);
            arrayToFill.add(m);
        }
        if(childData != null) {
            childData.add(arrayToFill);
            return childData;
        }
        else {
            return arrayToFill;
        }
    }

    public void clickMe(View view){
        Map<String, String> contact = new HashMap<>();
        contact.put("relative", "Me");
        childData.get(1).add(contact);
        adapter.notifyDataSetChanged();
    }
}
