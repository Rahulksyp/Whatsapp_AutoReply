package com.rahulkashyap.whatsappbot.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.models.utils.ContactModel;
import com.rahulkashyap.whatsappbot.models.utils.CustomMessageModel;
import com.rahulkashyap.whatsappbot.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SpecificContactAdapter extends RecyclerView.Adapter<SpecificContactAdapter.ViewHolder> implements SectionIndexer {

    public List<ContactModel> contactModelList;
    private Context ctx;
    private SharedPreferences preferences;
    private ArrayList<String> myWhatsappContacts = new ArrayList<>();
    private String status = "status";
    private ArrayList<Integer> mSectionPositions;
    private SharedPreferences.Editor editor;
    private int mCheckedPostion = -1;
    private Realm realm;



    public SpecificContactAdapter(List<ContactModel> items, Context ctx) {
        this.contactModelList = items;
        this.ctx = ctx.getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        realm = realm.getDefaultInstance();

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

        holder.icSelect.setChecked(position == mCheckedPostion);

        holder.icSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == mCheckedPostion) {
                    holder.icSelect.setChecked(false);
                    mCheckedPostion = -1;
                } else {
                    mCheckedPostion = position;
                    editor.putString("conName",object.getName()).commit();
                    editor.putString("conNum",object.getPhone()).commit();

                }
            }
        });


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
