package com.example.bubal.expandables;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class AddElementActivity extends Activity {

    Spinner spinner;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getBooleanExtra("addElement", true)){
            setContentView(R.layout.activity_add_element);
            spinner = (Spinner) findViewById(R.id.groupsSpinner);
            editText = (EditText) findViewById(R.id.itemName);
            addItemPrepareActivity();
        }
        else {
            setContentView(R.layout.activity_add_group);
            editText = (EditText) findViewById(R.id.itemName);
        }

        spinner = (Spinner) findViewById(R.id.groupsSpinner);
        editText = (EditText) findViewById(R.id.itemName);

    }

    private void addItemPrepareActivity(){
        addListenerToEditView();
        spinner.setVisibility(View.VISIBLE);
        ArrayList<Map<String, String>> groups = (ArrayList<Map<String, String>>) getIntent().getSerializableExtra("group");
        AdapterHelper ah = new AdapterHelper(this);
        ah.setGroup(groups);
        ArrayAdapter<String> arrayAdapter = ah.getSpinnerAdapter();
        spinner.setAdapter(arrayAdapter);
    }

    private void addListenerToEditView(){
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pickContactIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Contact contact = null;
        Uri contactUri = data.getData();
        try {
            contact = Contact.getContactFromBook(this, contactUri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String numbers = "";
        for(String number : contact.getNumber()){
            if(contact.getNumber().indexOf(number) != (contact.getNumber().size()-1))
                numbers += number + ", ";
            else numbers += number;
        }

        DB dbHelper = new DB(this);
        dbHelper.open();
        Cursor cursor = dbHelper.getDataById(contact.getId());
        if(cursor.moveToNext()){
            System.out.println("We already have the same contact in list");
            Toast toast = Toast.makeText(this, "We already have the same contact in list. Try another contact!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            dbHelper.addContact(Integer.parseInt(contact.getId()), contact.getName(), contact.getNumber().get(0), "parents");
            editText.setText(contact.getName());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addElement(View view){
        Intent intent = new Intent();
        intent.putExtra("name", editText.getText());
        if(spinner != null) intent.putExtra("groupNumber", spinner.getSelectedItemPosition());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelAdding(View view){
        setResult(RESULT_CANCELED);
        finish();
    }
}
