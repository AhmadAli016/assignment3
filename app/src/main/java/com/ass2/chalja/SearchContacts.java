package com.ass2.chalja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchContacts extends AppCompatActivity {
    RecyclerView contactRV;
    List<contactModel> contactList;
    contactAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);
        contactRV = findViewById(R.id.searchContactRV);
        searchView = findViewById(R.id.searchContactETSearch);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filerList(newText);
                return true;
            }
        });

        contactList=new ArrayList<>();
//        getContacts();
        getContactList();

        adapter=new contactAdapter(contactList, SearchContacts.this);
        contactRV.setAdapter(adapter);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(SearchContacts.this);
        contactRV.setLayoutManager(lm);
    }

    private void filerList(String newText) {
        List<contactModel> filteredList = new ArrayList<>();
        for(contactModel item: contactList){
            if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(SearchContacts.this, "Filerted List is Emply", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    @SuppressLint("Range")
    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id =cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        Log.i("TAG", "Name: " + name);
//                        Log.i("TAG", "Phone Number: " + number);
//                        if(name != null && number != null) {
                        contactList.add(new contactModel(name, number));
//                        }
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

//    @NotNull
//    private void getContacts() {
//        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
//        ,null
//        ,null
//        ,null
//        ,null);
//
//        while(cursor != null && cursor.moveToNext()){
//            String name=getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String number=getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//            if(name != null && number != null){
//                contactList.add(new contactModel(name, number));
//            }
//        }
//
//    }
}