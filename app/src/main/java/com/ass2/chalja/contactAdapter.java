package com.ass2.chalja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.contactViewHolder> {
    List<contactModel> ls;
    Context c;

    public contactAdapter(List<contactModel> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public contactAdapter.contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.contact_row,parent,false);
        return new contactViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull contactAdapter.contactViewHolder holder, int position) {
        holder.Name.setText(ls.get(position).getName());
        holder.Number.setText(ls.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public void setFilteredList(List<contactModel> filteredList){
        this.ls = filteredList;
        notifyDataSetChanged();
    }



    public class contactViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Number;
        public contactViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.contactRowIdName);
            Number=itemView.findViewById(R.id.contactRowIdNumber);
        }
    }
}