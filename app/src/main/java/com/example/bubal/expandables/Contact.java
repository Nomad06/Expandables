package com.example.bubal.expandables;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by bubal on 05.03.2017.
 */

public class Contact {

    private String id;
    private String name;
    private ArrayList<String> numbers;

    Contact(String id, String name, ArrayList<String> numbers){
        this.id = id;
        this.name = name;
        this.numbers = numbers;
    }

    Contact(){}

    public static Contact getContactFromBook(Context context, Uri contactUri) throws Exception {
        String hasPhone;
        String contactId;
        String contactName;
        ArrayList<String> phoneNumbers;
        try(Cursor c = context.getContentResolver().query(contactUri, null, null, null, null);){
            if(c.moveToNext()){
                phoneNumbers = new ArrayList<>();
                contactId =  c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                contactName = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if(hasPhone.equalsIgnoreCase("1")){
                    try(Cursor phoneNumber = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);){
                        while (phoneNumber.moveToNext()){
                            phoneNumbers.add(phoneNumber.getString(phoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }
                    }
                }
                return new Contact(contactId, contactName, phoneNumbers);
            }
        }
        throw new Exception();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getNumber() {
        return numbers;
    }

    public void setNumber(ArrayList<String> numbers) {
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

