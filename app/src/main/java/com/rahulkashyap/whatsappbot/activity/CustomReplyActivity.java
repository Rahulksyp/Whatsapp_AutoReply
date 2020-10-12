package com.rahulkashyap.whatsappbot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.models.utils.CustomMessageModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class CustomReplyActivity extends AppCompatActivity {

    private EditText incomingMessage, replyMessage;
    private TextView tvIncoming, tvReplyMessage, slectedContact;
    private RadioButton radioButton;
    private RadioButton extactRadio, conatinRadio;
    private String radioText;
    private RadioGroup radioGroup;
    private Realm realm;
    private Toolbar toolbar;
    private ImageView addContactButton;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_reply);
        realm = Realm.getDefaultInstance();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        incomingMessage = findViewById(R.id.incomingMessage);
        replyMessage = findViewById(R.id.replyMessage);
        tvIncoming = findViewById(R.id.tvIncoming);
        tvReplyMessage = findViewById(R.id.tvReply);
        radioGroup = findViewById(R.id.radioGrp);
        extactRadio = findViewById(R.id.exactMatcRadio);
        conatinRadio = findViewById(R.id.containRadio);
        toolbar = findViewById(R.id.toolbar2);
        addContactButton = findViewById(R.id.addContact);
        slectedContact = findViewById(R.id.selectConatct);


        toolbarFun();
        showingMessageFromEditTExt();
        radioButtonFuc();
        funForSelectContact();
        extactRadio.setChecked(true);
        getDataOnItemClick();

    }

    private void getDataOnItemClick() {
        if (getIntent().getStringExtra("postion") != null) {
            int i = Integer.parseInt(getIntent().getStringExtra("postion"));
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<CustomMessageModel> results = realm.where(CustomMessageModel.class).findAll();
                    incomingMessage.setText(results.get(i).getIncomingMessage());
                    replyMessage.setText(results.get(i).getReplyMessage());
                    slectedContact.setText(results.get(i).getSpecificConName()+"\n"+results.get(i).getSpecificConNum());
                }
            });
        }

    }

    private void funForSelectContact() {
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingMessage.getText().toString().trim().length()>0 && replyMessage.getText().toString().trim().length()>0) {
                    Intent intent = new Intent(CustomReplyActivity.this, ContactActivity.class);
                    intent.putExtra("incoming", incomingMessage.getText().toString());
                    intent.putExtra("reply", replyMessage.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }else {
                    Snackbar.make(view,"Incoming and Reply Message connot be Empty",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toolbarFun() {
        toolbar.setTitle(getString(R.string.customReply));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    private void writeDataIntoDb() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomMessageModel messageModel = realm.createObject(CustomMessageModel.class);
                messageModel.setIncomingMessage(incomingMessage.getText().toString());
                messageModel.setReplyMessage(replyMessage.getText().toString());
                messageModel.setRadioButtonText(radioText);
                messageModel.setSpecificConName(preferences.getString("conName", ""));
                messageModel.setSpecificConNum(preferences.getString("conNum", ""));
                finish();
            }
        });

    }

    private void radioButtonFuc() {
        int select = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(select);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.exactMatcRadio:
                        radioText = "ExactMatch";
                        break;
                    case R.id.containRadio:
                        radioText = "ContainMatch";
                        Toast.makeText(CustomReplyActivity.this, "This Option not available right now", Toast.LENGTH_SHORT).show();
                        break;

                }


            }
        });
    }

    private void showingMessageFromEditTExt() {

        incomingMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() != 0) {

                    tvIncoming.setText(editable.toString());
                } else {
                    tvIncoming.setText(R.string.incoming_message);

                }

            }
        });

        replyMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() != 0) {
                    tvReplyMessage.setText(editable.toString());
                } else {
                    tvReplyMessage.setText(R.string.replyMsg);

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("incoming")!=null && getIntent().getStringExtra("reply")!=null){
            incomingMessage.setText(getIntent().getStringExtra("incoming"));
            replyMessage.setText(getIntent().getStringExtra("reply"));
            slectedContact.setText(preferences.getString("conName", "")+"\n"+preferences.getString("conNum", ""));
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_done:
                if (!slectedContact.getText().toString().equals("Selected contact") && incomingMessage.getText().toString().trim().length()>0 && replyMessage.getText().toString().trim().length()>0) {
                    writeDataIntoDb();
                }else if (slectedContact.getText().toString().equals("Selected contact") && incomingMessage.getText().toString().trim().length()==0 && replyMessage.getText().toString().trim().length()==0) {
                    Toast.makeText(this, "Cannot save empty fields", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this, "Select contact then save", Toast.LENGTH_SHORT).show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button, menu);
        return true;
    }

}
