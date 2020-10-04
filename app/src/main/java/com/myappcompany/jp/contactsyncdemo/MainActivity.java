package com.myappcompany.jp.contactsyncdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve names from phone's contact list and save in mNames
        getNames();

        //Apply changes to phone's contact list
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name;
                for(int i = 0 ;i< mNames.size(); i++) {
                    name = mNames.get(i);
                    ContactsManager.addContact(MainActivity.this, new MyContact(name));
                }
            }
        })

    }

    public void getNames() {
        int hasPhone;
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,
                null,null,null, null);
        if((c != null) && c.moveToFirst()) {
            while (c.moveToNext()) {
                hasPhone = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasPhone == 1) {
                    mNames.add(c.getString(c.getColumnIndexOrThrow(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                }
            }
            c.close();
        }
    }
}