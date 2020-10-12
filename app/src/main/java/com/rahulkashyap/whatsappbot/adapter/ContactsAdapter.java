package com.rahulkashyap.whatsappbot.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.activity.MainActivity;
import com.rahulkashyap.whatsappbot.interfaces.ContactDataPassing;
import com.rahulkashyap.whatsappbot.interfaces.DataPassing;
import com.rahulkashyap.whatsappbot.models.utils.ContactModel;
import com.rahulkashyap.whatsappbot.models.utils.MessageStore;
import com.rahulkashyap.whatsappbot.utils.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> implements SectionIndexer {

    public List<ContactModel> contactModelList;
    private Context ctx;
    private Realm realm;
    private SharedPreferences preferences;
    private ArrayList<String> myWhatsappContacts = new ArrayList<>();
    private String status = "status";
    private ArrayList<Integer> mSectionPositions;

    public ContactsAdapter(List<ContactModel> items, Context ctx) {
        this.contactModelList = items;
        this.ctx = ctx.getApplicationContext();
        realm = realm.getDefaultInstance();
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactlist_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ContactModel object = contactModelList.get(position);
        holder.title.setText(object.getName());
        holder.phone.setText(object.getPhone());

        holder.icSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()){
                    upDatingDb(true, object, position);

                }else {
                    upDatingDb(false, object, position);

                }
            }
        });

        upDatingContactListAsCheckOrNot(position,holder);


    }

    private void upDatingContactListAsCheckOrNot(final int position, final ViewHolder holder) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ContactModel> results = realm.where(ContactModel.class).equalTo(status, Constants.STATUS).findAll();
                for (ContactModel data : results) {
                    if (position == data.getSelectedPosition()) {
                        holder.icSelect.setChecked(true);
                    }
                }
            }
        });
    }

    private void upDatingDb(boolean isTrue, final ContactModel object, final int position) {

        if (isTrue) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ContactModel contactModel = new ContactModel();
                    contactModel.setSelectedPosition(position);
                    contactModel.setName(object.getName());
                    contactModel.setPhone(object.getPhone());
                    contactModel.setStatus(Constants.STATUS);
                    realm.copyToRealm(contactModel);
                }
            });

        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<ContactModel> rows = realm.where(ContactModel.class).equalTo(status, Constants.STATUS).findAll();
                    rows.deleteAllFromRealm();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>();
        mSectionPositions = new ArrayList<>();
        for (int i = 0, size = contactModelList.size(); i < size; i++) {
            String section = String.valueOf(contactModelList.get(i).getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int i) {
        return mSectionPositions.get(i);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView phone;
        private CardView callImg;
        private CheckBox icSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            title = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.no);
            callImg = itemView.findViewById(R.id.card);
            icSelect = itemView.findViewById(R.id.icSelect);
        }
    }

}
