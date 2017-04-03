package com.enduringstack.retrofitdemo7.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.enduringstack.retrofitdemo7.R;
import com.enduringstack.retrofitdemo7.model.Repository;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Repository> items;

    public CardAdapter() {
        super();
        items = new ArrayList<Repository>();
    }

    public void addData(Repository repo) {
        items.add(repo);
        notifyItemInserted(0);
    }

    public void clear() {
        int count = items.size();
        items.clear();
        notifyItemRangeRemoved(0, count - 1);
    }

    public Repository getItemAtPosition(int position) {
        return items.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Repository repo = items.get(i);
        viewHolder.name.setText(repo.getName());
        viewHolder.desc.setText(repo.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
        }
    }
}