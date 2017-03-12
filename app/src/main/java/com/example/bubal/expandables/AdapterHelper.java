package com.example.bubal.expandables;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.SpinnerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bubal on 07.03.2017.
 */

public class AdapterHelper implements Serializable{

    final String ATTR_GROUP_NAME= "groupName";
    final String ATTR_RELATIVE_NAME= "relative";

    private Context context;
    private SimpleExpandableListAdapter adapter;

    private String[] relatives = new String[]{"Parents", "Brothers", "Sisters"};
    private String[] parents = new String[]{"Father", "Mother"};
    private String[] brothers = new String[]{"Ah'mad"};
    private String[] lostBrothers = new String[]{"Movsar"};
    private String[] sisters = new String[]{"Ayshat", "Fatima", "Asya", "Rabiya", "Salima"};

    private ArrayList<Map<String, String>> group;
    private ArrayList<ArrayList<Map<String, String>>> childData;

    public ArrayList<Map<String, String>> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Map<String, String>> group) {
        this.group = group;
    }

    public ArrayList<ArrayList<Map<String, String>>> getChildData() {
        return childData;
    }

    public void setChildData(ArrayList<ArrayList<Map<String, String>>> childData) {
        this.childData = childData;
    }

    AdapterHelper(Context context){
        this.context = context;
    }

    public ArrayAdapter<String> getSpinnerAdapter(){
        ArrayList<String> spinnerData = new ArrayList<>();
        for (Map<String, String> curGroup : group){
            spinnerData.add(curGroup.get("groupName"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    public SimpleExpandableListAdapter getSimpleExpandableListAdapter(){
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

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(context, group, android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo, childData, android.R.layout.simple_list_item_1,
                childFrom, childTo);


        return adapter;
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

    public void addItemToList(String itemName, int groupPosition){
        ArrayList<Map<String, String>> chosenGroup = childData.get(groupPosition);
        Map<String, String> newItem = new HashMap<>();
        newItem.put("relative", itemName);
        chosenGroup.add(newItem);
    }

    public void addGroup(String itemName){
        Map<String, String> newItem = new HashMap<>();
        newItem.put("groupName", itemName);
        group.add(newItem);
        childData.add(new ArrayList<Map<String, String>>());
    }

    String getGroupText(int groupPos) {
        return ((Map<String,String>)(adapter.getGroup(groupPos))).get(ATTR_GROUP_NAME);
    }

    String getChildText(int groupPos, int childPos) {
        return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(ATTR_RELATIVE_NAME);
    }

    String getGroupChildText(int groupPos, int childPos) {
        return getGroupText(groupPos) + " " +  getChildText(groupPos, childPos);
    }
}
