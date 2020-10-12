package com.rahulkashyap.whatsappbot.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rahulkashyap.whatsappbot.R;
import com.rahulkashyap.whatsappbot.interfaces.SelectTextPassing;
import com.rahulkashyap.whatsappbot.models.utils.SelectTextModel;

import java.util.ArrayList;

public class SelectTextAdapter extends RecyclerView.Adapter<SelectTextAdapter.ViewHolder> {

    private ArrayList<SelectTextModel> selectTextAdapters;
    private SelectTextPassing selectTextPassing;
    private Context context;
    private SharedPreferences preferences;


    public SelectTextAdapter(ArrayList<SelectTextModel> selectTextAdapters, SelectTextPassing selectTextPassing, Context context) {
        this.selectTextAdapters = selectTextAdapters;
        this.selectTextPassing = selectTextPassing;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout_select_text_recycler, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SelectTextModel object = selectTextAdapters.get(position);

        holder.tv.setText(object.getText());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTextPassing.onClick(object.getText());
            }
        });


    }

    @Override
    public int getItemCount() {
        return selectTextAdapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.slectText);
            layout = itemView.findViewById(R.id.onClickTask);
        }
    }
}
