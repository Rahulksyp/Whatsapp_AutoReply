package com.rahulkashyap.whatsappbot;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.PhoneNumberUtils;

import com.rahulkashyap.whatsappbot.models.Action;
import com.rahulkashyap.whatsappbot.models.utils.ContactModel;
import com.rahulkashyap.whatsappbot.models.utils.CustomMessageModel;
import com.rahulkashyap.whatsappbot.services.NotificationUtils;
import com.rahulkashyap.whatsappbot.utils.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmResults;

@SuppressLint("OverrideAbstract")
public class WhatsappMsgReceiver extends NotificationListenerService {

    Realm realm = null;
    ArrayList<String> contactData = new ArrayList<>();
    String finalReceive = null;


    @Override
    public void onCreate() {
        super.onCreate();
        realm = Realm.getDefaultInstance();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public StatusBarNotification[] getActiveNotifications(String[] keys) {
        return super.getActiveNotifications(keys);
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        WhatsappMsgReceiver.this.cancelNotification(sbn.getKey());
        final Action replyAction = NotificationUtils.getQuickReplyAction(sbn.getNotification(), getPackageName());
        final Bundle extras = sbn.getNotification().extras;

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ContactModel> results = realm.where(ContactModel.class).equalTo("status", Constants.STATUS).findAll();
                for (ContactModel data : results) {
                    contactData.add(data.getName());
                }
            }
        });

        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final String sent = preferences.getString(Constants.SENT_TEXT, "");
            final String contactName = sbn.getNotification().extras.getString("android.title");
            final String whatsTextRecieved = extras.getCharSequence("android.text").toString();
            final Boolean customMessageEnbled = preferences.getBoolean(Constants.CUSTOMENABLED, false);


            if (customMessageEnbled == true) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<CustomMessageModel> realmResults = realm.where(CustomMessageModel.class).findAll();
                        for (CustomMessageModel messageModel : realmResults) {

                            if (messageModel.getSpecificConName().equals(contactName) && whatsTextRecieved.toLowerCase().equals(messageModel.getIncomingMessage().toLowerCase()) ){
                                System.out.println(">>>>>>>>>>>>>>> "+contactName +" >> "+messageModel.getIncomingMessage());
                                if (replyAction != null) {
                                    try {

                                        if (whatsTextRecieved.toLowerCase().equals(messageModel.getIncomingMessage().toLowerCase())) {
                                            replyAction.sendReply(getApplicationContext(), messageModel.getReplyMessage());

                                        }
                                    } catch (PendingIntent.CanceledException e) {
                                    }
                                }
                            }
                           /*else {
                                for (int i = 0; i < contactData.size(); i++) {
                                    Set<String> set = new HashSet<>(contactData);
                                    contactData.clear();
                                    contactData.addAll(set);
                                    if (contactName.equals(contactData.get(i))) {

                                        if (replyAction != null) {

                                            try {

                                                if (whatsTextRecieved.toLowerCase().equals(messageModel.getIncomingMessage().toLowerCase())) {
                                                    replyAction.sendReply(getApplicationContext(), messageModel.getReplyMessage());

                                                }
                                            } catch (PendingIntent.CanceledException e) {
                                            }
                                        }

                                    } else {
                                        System.out.println("notMatch " + contactData.get(i));

                                    }


                                }
                            }
                            */
                        }
                    }
                });


            } else {

                for (int i = 0; i < contactData.size(); i++) {
                    if (contactName.equals(contactData.get(i))) {
                        System.out.println("getMatch " + contactData.get(i));
                        contactData.clear();

                        if (replyAction != null) {
                            try {
                                replyAction.sendReply(getApplicationContext(), sent);
                                System.out.println("whatsIRece "+whatsTextRecieved);
//                                Intent myIntent = new Intent("android.intent.action.MAIN");
//                                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                myIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
//                                myIntent.putExtra("abc", PhoneNumberUtils.stripSeparators("917042353004")+"@s.whatsapp.net");
//                                startActivity(myIntent);


                            } catch (PendingIntent.CanceledException e) {
                            }
                        }
                    } else {
                        System.out.println("notMatch " + contactData.get(i));

                    }


                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("DataFRomSender " + sbn.getNotification().extras.getString("android.title"));
            System.out.println("whatMSGWAS " + sbn.getId());
            System.out.println("Package " + sbn.getPackageName());
            System.out.println("Title " + extras.get("android.title"));
            System.out.println("Text " + extras.getCharSequence("android.text").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//                RealmResults<MessageStore> results = realm.where(MessageStore.class).findAll();
//                for (MessageStore employee : results) {
//
//                }
//            }
//        });


//        if (sbn.getNotification().extras.getString("android.title").equals("My Number")){
////            if (action != null) {
////                try {
////                    action.sendReply(getApplicationContext(), "Hello! Rahul is not available i am his bot If you have some imporatant work so call him");
////                } catch (PendingIntent.CanceledException e) {
////                }
////            } else {
////            }
//
//            if (extras.getCharSequence("android.text").toString().equals(value1)){
//                if (action != null) {
//                    try {
//                        action.sendReply(getApplicationContext(), value2);
//                    } catch (PendingIntent.CanceledException e) {
//                    }
//                }
//            }else {
//                if (action != null) {
//                    try {
//                        action.sendReply(getApplicationContext(), "nikl be");
//                    } catch (PendingIntent.CanceledException e) {
//                    }
//                }
//            }
//        }


    }
}
