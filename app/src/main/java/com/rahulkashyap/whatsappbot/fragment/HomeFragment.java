package com.rahulkashyap.whatsappbot.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.activity.AutoReplyTextActivity;
import com.rahulkashyap.whatsappbot.activity.CustomMessageListActivity;
import com.rahulkashyap.whatsappbot.adapter.SelectTextAdapter;
import com.rahulkashyap.whatsappbot.interfaces.SelectTextPassing;
import com.rahulkashyap.whatsappbot.models.utils.CustomMessageModel;
import com.rahulkashyap.whatsappbot.models.utils.SelectTextModel;
import com.rahulkashyap.whatsappbot.utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class HomeFragment extends Fragment implements SelectTextPassing {

    private TextView autoReplyText, autReplyOnOf;
    private Switch aSwitchButton;
    private EditText showSelectedMessage;
    private ImageView editMessageIcon, customMessageIcon;
    private Realm realm;
    private RecyclerView selectMessageOptionList;
    private SelectTextAdapter adapter;
    private ArrayList<SelectTextModel> selectTextModels;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
//    private RealmResults<CustomMessageModel> dataadapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor =preferences.edit();

        autReplyOnOf = view.findViewById(R.id.tvOff);
        autoReplyText = view.findViewById(R.id.tvText);
        aSwitchButton = view.findViewById(R.id.cutomMsgSwitch);
        editMessageIcon = view.findViewById(R.id.editTextBtn);
        customMessageIcon = view.findViewById(R.id.buttonMsg);
        showSelectedMessage = view.findViewById(R.id.selctTextEdit);
        selectMessageOptionList = view.findViewById(R.id.selectTectrecyclerview);

        functionForSwitchButton();
        loadDataToRecyclerView();
        handlingButtonClick();

    }

    private void handlingButtonClick() {
        customMessageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CustomMessageListActivity.class));
            }
        });

        editMessageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),AutoReplyTextActivity.class);
                intent.putExtra("text",showSelectedMessage.getText().toString());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setText();
    }

    private void loadDataToRecyclerView() {
        selectTextModels = new ArrayList<>();
        selectTextModels.add(new SelectTextModel(Constants.BUSY_TEXT));
        selectTextModels.add(new SelectTextModel(Constants.DRIVING_TEXT));
        selectTextModels.add(new SelectTextModel(Constants.SLEEPING_TEXT));
        selectTextModels.add(new SelectTextModel(Constants.CANT_TALK_NOW));
        selectTextModels.add(new SelectTextModel(Constants.AT_MOVIES_TEXT));
        selectTextModels.add(new SelectTextModel(Constants.AT_WORK_TEXT));
        selectTextModels.add(new SelectTextModel(Constants.AT_MEETING_TEXT));

        adapter = new SelectTextAdapter(selectTextModels, this, getContext());
        selectMessageOptionList.setLayoutManager(new LinearLayoutManager(getContext()));
        selectMessageOptionList.setAdapter(adapter);

    }

    private void functionForSwitchButton() {


        if (Settings.Secure.getString(getActivity().getContentResolver(), "enabled_notification_listeners").contains(getActivity().getPackageName())) {
            aSwitchButton.setChecked(true);
            autReplyOnOf.setText("Auto Reply ON");

        } else {
            aSwitchButton.setChecked(false);
            autReplyOnOf.setText("Auto Reply OFF");

        }
        aSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    if (Settings.Secure.getString(getActivity().getContentResolver(), "enabled_notification_listeners").contains(getActivity().getPackageName())) {
                        System.out.println("Permission is enabled");
                    } else {
                        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }

                    autReplyOnOf.setText("Auto Reply ON");


                } else {
                    if (Settings.Secure.getString(getActivity().getContentResolver(), "enabled_notification_listeners").contains(getActivity().getPackageName()) == true) {
                        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));

                    }
                    autReplyOnOf.setText("Auto Reply OFF");

                }
            }
        });
    }

    private void setText() {
        final String receive = preferences.getString(Constants.SELECTED_TEXT_ITEM, Constants.BUSY_TEXT);
        showSelectedMessage.setText(receive);
        editor.putString(Constants.SENT_TEXT, showSelectedMessage.getText().toString()).commit();
    }

    @Override
    public void onClick(String text) {
        editor.putString(Constants.SELECTED_TEXT_ITEM, text).commit();
        setText();

    }
}
