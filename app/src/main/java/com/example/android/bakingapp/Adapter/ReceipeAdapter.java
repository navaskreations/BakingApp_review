package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Receipe;

import java.util.ArrayList;
import java.util.List;

public class ReceipeAdapter extends RecyclerView.Adapter<ReceipeAdapter.ReceipeViewHolder> {

    private static final String TAG = ReceipeAdapter.class.getSimpleName();
    private final ReceipeAdapterOnClickHandler mClickHandler;
    private List<Receipe> receipeIndex = new ArrayList<>();

    public ReceipeAdapter(ReceipeAdapterOnClickHandler clickHandler, List<Receipe> receipeIndex) {
        this.receipeIndex = receipeIndex;
        mClickHandler = clickHandler;
    }

    public interface ReceipeAdapterOnClickHandler {
        void onClick(Receipe receipe);
    }

    class ReceipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView receipeTextView;

        public ReceipeViewHolder(View itemView) {
            super(itemView);
            receipeTextView = (TextView) itemView.findViewById(R.id.tv_receipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Receipe receipeSelected = receipeIndex.get(adapterPosition);
            mClickHandler.onClick(receipeSelected);
        }
    }

    @Override
    public ReceipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.receipecard;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        ReceipeViewHolder viewHolder = new ReceipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return receipeIndex.size();
    }

    @Override
    public void onBindViewHolder(ReceipeViewHolder holder, int position) {
        holder.receipeTextView.setText(receipeIndex.get(position).getName());
        }
}

