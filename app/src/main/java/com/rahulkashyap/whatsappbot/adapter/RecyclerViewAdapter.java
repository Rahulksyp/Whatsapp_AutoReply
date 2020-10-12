package com.rahulkashyap.whatsappbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.interfaces.DataPassing;
import com.rahulkashyap.whatsappbot.models.utils.MessageModel;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<MessageModel,RecyclerViewAdapter.ViewHolder> {
    Context context;
    private int lastSelectedPosition = -1;
    private DataPassing dataPassing;

    public RecyclerViewAdapter(@Nullable OrderedRealmCollection<MessageModel> data, Context context,DataPassing dataPassing) {
        super(data, true);
        this.context = context;
        this.dataPassing=dataPassing;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final MessageModel object = getData().get(position);
        holder.textView1.setText(object.getWhatsMsg());
        holder.textView2.setText(object.getWhatsToReply());
        holder.radioButton.setChecked(object.getCurrentPostion()==position);

        holder.radioButton.setChecked(lastSelectedPosition==position);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lastSelectedPosition = position;
                notifyDataSetChanged();
                dataPassing.onClick(object.getWhatsMsg(),object.getWhatsToReply());

            }
        });


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1,textView2;
        private RadioGroup radioGroup;
        private RadioButton radioButton;
        private ImageView itemSelected;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.tvWhats);
            textView2 = itemView.findViewById(R.id.tvWhatsFrom);
//            radioGroup = itemView.findViewById(R.id.radioGroup);
            radioButton = itemView.findViewById(R.id.radioButton1);
            itemSelected = itemView.findViewById(R.id.icSelect);
            cardView = itemView.findViewById(R.id.cardSelect);

        }
    }
}
