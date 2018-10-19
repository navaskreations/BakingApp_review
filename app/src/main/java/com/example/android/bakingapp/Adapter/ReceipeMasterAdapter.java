package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.util.ArrayList;
import java.util.List;

public class ReceipeMasterAdapter extends RecyclerView.Adapter<ReceipeMasterAdapter.ReceipeMasterViewHolder> {

    private static final String TAG = ReceipeMasterAdapter.class.getSimpleName();
    private List<ReceipeSteps> receipeSteps = new ArrayList<>();
    private List<Ingredients> receipeIngredients = new ArrayList<>();
    private final ReceipeMasterAdapter.ReceipeMasterAdapterOnClickHandler mClickHandler;

    public ReceipeMasterAdapter(ReceipeMasterAdapter.ReceipeMasterAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public interface ReceipeMasterAdapterOnClickHandler {
        void onClick(ReceipeSteps receipeSteps, int position);
        void onClick(List<Ingredients> ingredients);
    }

    class ReceipeMasterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView receipeStepTextView;

        public ReceipeMasterViewHolder(View itemView) {
            super(itemView);
            receipeStepTextView = (TextView) itemView.findViewById(R.id.tv_receipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition == 0)
                mClickHandler.onClick(receipeIngredients);
            else {
                ReceipeSteps receipeStepSelected = receipeSteps.get(adapterPosition - 1);
                mClickHandler.onClick(receipeStepSelected,adapterPosition-1);
            }
        }
    }

    @Override
    public ReceipeMasterAdapter.ReceipeMasterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.receipecard;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ReceipeMasterAdapter.ReceipeMasterViewHolder viewHolder = new ReceipeMasterAdapter.ReceipeMasterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReceipeMasterAdapter.ReceipeMasterViewHolder holder, int position) {
        if (position == 0)
            holder.receipeStepTextView.setText("Ingredients");
        else
            holder.receipeStepTextView.setText(receipeSteps.get(position - 1).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == receipeSteps) return 0;
        return receipeSteps.size() + 1;
    }

    public void setReceipeSteps(List<ReceipeSteps> steps) {
        receipeSteps = steps;
        notifyDataSetChanged();
    }

    public void setReceipeIngredients(List<Ingredients> ingredients) {
        receipeIngredients = ingredients;
        notifyDataSetChanged();
    }
}
