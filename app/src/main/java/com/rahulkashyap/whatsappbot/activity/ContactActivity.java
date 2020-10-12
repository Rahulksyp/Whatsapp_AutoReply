package com.rahulkashyap.whatsappbot.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.adapter.ContactsAdapter;
import com.rahulkashyap.whatsappbot.adapter.SpecificContactAdapter;
import com.rahulkashyap.whatsappbot.models.utils.CustomMessageModel;
import com.rahulkashyap.whatsappbot.utils.WhatsappContact;

import fastscroll.app.fastscrollalphabetindex.AlphabetIndexFastScrollRecyclerView;
import io.realm.Realm;

public class ContactActivity extends AppCompatActivity {
    private SpecificContactAdapter adapter;
    private WhatsappContact whatsappContact;
    private AlphabetIndexFastScrollRecyclerView recyclerView;
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String incoming,reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        whatsappContact = new WhatsappContact(this);
        toolbar = findViewById(R.id.toolbar4);

        recyclerView = findViewById(R.id.contacts_list);
        adapter = new SpecificContactAdapter(whatsappContact.readCallLogs(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setIndexTextSize(12);
        recyclerView.setIndexBarTextColor(R.color.white);
        recyclerView.setIndexBarTextColor(R.color.colorAccent);
        recyclerView.setIndexBarColor(R.color.white);
        recyclerView.setIndexBarCornerRadius(3);
        recyclerView.setIndexBarTransparentValue((float) 0.10);
        recyclerView.setAdapter(adapter);

        toolbarFun();
        incoming = getIntent().getStringExtra("incoming");
        reply = getIntent().getStringExtra("reply");

    }

    private void toolbarFun() {
        toolbar.setTitle("Select Contact");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
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
                Intent i = new Intent(ContactActivity.this,CustomReplyActivity.class);
                i.putExtra("incoming",incoming);
                i.putExtra("reply",reply);
                startActivity(i);
                finish();
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
