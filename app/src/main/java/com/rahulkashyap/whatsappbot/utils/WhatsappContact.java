package com.rahulkashyap.whatsappbot.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.rahulkashyap.whatsappbot.models.utils.ContactModel;

import java.util.ArrayList;

public class WhatsappContact {
    private Context context;
    private ArrayList<ContactModel> whatsappContactList;
    private String contactID;

    public WhatsappContact(Context context) {
        this.context = context;
        getWhatsappContact();
    }

    private void getWhatsappContact() {

        whatsappContactList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[]{"com.whatsapp"},ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String name, number;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);
                    ContactModel selectUser = new ContactModel();
                    selectUser.setPhone(number);
                    selectUser.setName(name);
                    whatsappContactList.add(selectUser);
                }
            } finally {
                cursor.close();
            }
        }
//        ContentResolver cr = context.getContentResolver();
//        ArrayList<String> myWhatsappContacts = new ArrayList<>();
////
//        Cursor contactCursor = cr.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID, ContactsContract.RawContacts.CONTACT_ID},
//                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
//                new String[]{"com.whatsapp"}, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

//        if (contactCursor != null) {
//            if (contactCursor.getCount() > 0) {
//                if (contactCursor.moveToFirst()) {
//
//                    do {
//                        //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
//                        String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
//
//                        if (whatsappContactId != null) {
//                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
//                            Cursor whatsAppContactCursor = cr.query(
//                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                    new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
//                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
//                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                                    new String[]{whatsappContactId}, null);
//
//                            if (whatsAppContactCursor != null) {
//                                whatsAppContactCursor.moveToFirst();
//                                String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
//                                final String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                                final String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//
//                                whatsAppContactCursor.close();
//                                ContactModel selectUser = new ContactModel();
//                                selectUser.setPhone(number);
//                                selectUser.setName(name);
//                                whatsappContactList.add(selectUser);
//                                myWhatsappContacts.add(name);
//
//                            }
//                        }
//                    } while (contactCursor.moveToNext());
//                    contactCursor.close();
//                }
//            }
//        }
    }
    private static final String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public ArrayList<ContactModel> readCallLogs() {
        if (whatsappContactList == null)
            getWhatsappContact();
        return whatsappContactList;
    }

}
