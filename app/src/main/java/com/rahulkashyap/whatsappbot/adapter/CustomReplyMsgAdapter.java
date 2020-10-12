package com.rahulkashyap.whatsappbot.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.activity.CustomReplyActivity;
import com.rahulkashyap.whatsappbot.models.utils.ContactModel;
import com.rahulkashyap.whatsappbot.models.utils.CustomMessageModel;
import com.rahulkashyap.whatsappbot.utils.Constants;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class CustomReplyMsgAdapter extends RealmRecyclerViewAdapter<CustomMessageModel,CustomReplyMsgAdapter.MyViewHolder> {
    Context context;
    private Realm realm;

    OrderedRealmCollection<CustomMessageModel> data;

    public CustomReplyMsgAdapter(Context context,@Nullable OrderedRealmCollection<CustomMessageModel> data) {
        super(data, true);
        this.context = context;
        realm = realm.getDefaultInstance();


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_reply_msg, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomMessageModel object = getData().get(position);
        holder.incomingMessage.setText(object.getIncomingMessage());
        holder.replyMessage.setText(object.getReplyMessage());
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();

//        TextDrawable drawable = TextDrawable.builder()
//                .buildRound(String.valueOf(object.getSpecificConName().charAt(0)), color1);
//        holder.imageView.setImageDrawable(drawable);


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void removeItem(int position) {
//        data.remove(position);
//        notifyItemRemoved(position);

        RealmResults<CustomMessageModel> results = realm.where(CustomMessageModel.class).findAll();
        realm.beginTransaction();
        results.deleteFromRealm(position);
        notifyItemRemoved(position);
        realm.commitTransaction();
    }

    public void restoreItem(String item, int position) {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                CustomMessageModel contactModel = new CustomMessageModel();
//                contactModel.setIncomingMessage("test");
//                contactModel.setSpecificConName("hello");
//
//
//                realm.copyToRealm(contactModel);
//            }
//        });
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> "+item);
//        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView incomingMessage,replyMessage,radioText;
        private ImageView imageView;
        private CardView itemClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            incomingMessage = itemView.findViewById(R.id.incomingMessage);
            replyMessage = itemView.findViewById(R.id.replyMessage);
            itemClick = itemView.findViewById(R.id.itemClick);
            imageView = itemView.findViewById(R.id.contactName);

            itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a = String.valueOf(getAdapterPosition());
                    Intent i = new Intent(context, CustomReplyActivity.class);
                    i.putExtra("postion",a);
                    context.startActivity(i);

                }
            });

        }

    }
}
