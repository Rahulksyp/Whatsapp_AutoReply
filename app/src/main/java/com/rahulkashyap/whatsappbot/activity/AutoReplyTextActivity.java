package com.rahulkashyap.whatsappbot.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.models.utils.SelectTextModel;
import com.rahulkashyap.whatsappbot.utils.Constants;

import java.util.ArrayList;

public class AutoReplyTextActivity extends AppCompatActivity {

    private EditText autoReplTextEdit;
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CardView cardView;
    private ArrayList<SelectTextModel> selectTextModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_reply_text);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor =preferences.edit();

        autoReplTextEdit = findViewById(R.id.editAutoReply);
        cardView = findViewById(R.id.custOmMessageCard);
        String seletedText = getIntent().getStringExtra("text");
        autoReplTextEdit.setText(seletedText);
        toolbar = findViewById(R.id.toolbar3);
        toolbarFun();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AutoReplyTextActivity.this,CustomMessageListActivity.class));
            }
        });

    }

    private void toolbarFun() {
        toolbar.setTitle("AutocReply");
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
                editor.putString(Constants.SELECTED_TEXT_ITEM, autoReplTextEdit.getText().toString()).commit();
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
