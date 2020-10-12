package com.rahulkashyap.whatsappbot.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.adapter.CustomReplyMsgAdapter;
import com.rahulkashyap.whatsappbot.models.utils.ContactModel;
import com.rahulkashyap.whatsappbot.models.utils.CustomMessageModel;
import com.rahulkashyap.whatsappbot.utils.Constants;
import com.rahulkashyap.whatsappbot.utils.SwipeToDeleteCallback;

import io.realm.Realm;
import io.realm.RealmResults;

public class CustomMessageListActivity extends AppCompatActivity {

    private TextView swtichText,editTedt;
    private RecyclerView customMsgRecycler;
    private Switch customMsgSwtich;
    private FloatingActionButton floatingActionButton;
    private CustomReplyMsgAdapter customReplyMsgAdapter;
    private RealmResults<CustomMessageModel> customMessageModels;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_message_list);
        toolbar = findViewById(R.id.toolbar);
        editTedt = findViewById(R.id.editText);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        initView();
        getDataFromDb();
        enableSwipeToDeleteAndUndo();


    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final CustomMessageModel item = customReplyMsgAdapter.getData().get(position);
                customReplyMsgAdapter.removeItem(position);

                if (editTedt.getVisibility()==View.VISIBLE){
                    editTedt.setVisibility(View.INVISIBLE);
                }

//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                snackbar.setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        customReplyMsgAdapter.restoreItem(item, position);
//                        customMsgRecycler.scrollToPosition(position);
//                    }
//                });
//
//                snackbar.setActionTextColor(Color.YELLOW);
//                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(customMsgRecycler);

    }

    private void initView() {
        swtichText = findViewById(R.id.cutomSwtichText);
        customMsgRecycler = findViewById(R.id.customMsgRecyclerView);
        customMsgSwtich = findViewById(R.id.cutomMsgSwitch);
        floatingActionButton = findViewById(R.id.customMsfFloat);
        customMsgSwtichFunction();

    }

    private void customMsgSwtichFunction() {
        if (!customMsgSwtich.isChecked()){
            swtichText.setText(R.string.swtich_off_text);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,"Please enable switch button ",Snackbar.LENGTH_LONG).show();
                }
            });

        }else {
            swtichText.setText(R.string.switch_on);

        }

        customMsgSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                if (b){

                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(CustomMessageListActivity.this,CustomReplyActivity.class));
                        }
                    });
                    editor.putBoolean(Constants.CUSTOMENABLED,b).commit();
                    swtichText.setText(R.string.switch_on);


                }else {
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view,"Please enable switch button ",Snackbar.LENGTH_LONG).show();
                        }
                    });
                    editor.putBoolean(Constants.CUSTOMENABLED,b).commit();
                    swtichText.setText(R.string.swtich_off_text);



                }


            }
        });
        customMsgSwtich.setChecked(preferences.getBoolean(Constants.CUSTOMENABLED,false));
        System.out.println("<<<<<>>>>>>>>>>>>> "+preferences.getBoolean(Constants.CUSTOMENABLED,false));

    }

    private void getDataFromDb() {
        customMessageModels = Realm.getDefaultInstance().where(CustomMessageModel.class).findAll();
        customReplyMsgAdapter = new CustomReplyMsgAdapter(this,customMessageModels);
        customMsgRecycler.setLayoutManager(new LinearLayoutManager(this));
        customMsgRecycler.setItemAnimator(new DefaultItemAnimator());
        customMsgRecycler.setAdapter(customReplyMsgAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (customReplyMsgAdapter.getItemCount()==1){
            editTedt.setVisibility(View.VISIBLE);
        }
        customReplyMsgAdapter.notifyDataSetChanged();
        getDataFromDb();
    }
}
