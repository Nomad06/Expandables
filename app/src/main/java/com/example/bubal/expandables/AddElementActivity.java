package com.example.bubal.expandables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Map;

public class AddElementActivity extends Activity {

    Spinner spinner;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_element);

        spinner = (Spinner) findViewById(R.id.groupsSpinner);
        editText = (EditText) findViewById(R.id.itemName);

        ArrayList<Map<String, String>> groups = (ArrayList<Map<String, String>>) getIntent().getSerializableExtra("group");
        AdapterHelper ah = new AdapterHelper(this);
        ah.setGroup(groups);
        ArrayAdapter<String> arrayAdapter = ah.getSpinnerAdapter();
        spinner.setAdapter(arrayAdapter);
    }

    public void addElement(View view){
        Intent intent = new Intent();
        intent.putExtra("name", editText.getText());
        intent.putExtra("groupNumber", spinner.getSelectedItemPosition());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelAdding(View view){
        setResult(RESULT_CANCELED);
        finish();
    }
}
