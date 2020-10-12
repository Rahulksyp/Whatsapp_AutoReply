package com.rahulkashyap.whatsappbot.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.activity.MainActivity;
import com.rahulkashyap.whatsappbot.adapter.ContactsAdapter;
import com.rahulkashyap.whatsappbot.interfaces.ContactDataPassing;
import com.rahulkashyap.whatsappbot.utils.WhatsappContact;

import fastscroll.app.fastscrollalphabetindex.AlphabetIndexFastScrollRecyclerView;

import static android.widget.LinearLayout.VERTICAL;


public class ContactFragment extends Fragment {

    //    private RecyclerView recyclerView;
    private AlphabetIndexFastScrollRecyclerView recyclerView;

    private ContactsAdapter adapter;
    private WhatsappContact whatsappContact;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.contacts_list);
        loadContact();

    }


    private void loadContact() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        whatsappContact = new WhatsappContact(getContext());
                        adapter = new ContactsAdapter(whatsappContact.readCallLogs(), getContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setIndexTextSize(12);
                        recyclerView.setIndexBarTextColor(R.color.white);
                        recyclerView.setIndexBarTextColor(R.color.colorAccent);
                        recyclerView.setIndexBarColor(R.color.white);
                        recyclerView.setIndexBarCornerRadius(3);
                        recyclerView.setIndexBarTransparentValue((float) 0.10);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


}
