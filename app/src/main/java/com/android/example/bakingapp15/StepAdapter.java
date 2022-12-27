package com.android.example.bakingapp15;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.NumberViewHolder> {

    private static final String TAG = StepAdapter.class.getSimpleName();
    private ArrayList<Steps> stepsRecipeArray;

    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;

    private int mNumberSteps;
    private int mRecipeIndex;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public StepAdapter(ListItemClickListener listener, int index) {
        mOnClickListener = listener;
        viewHolderCount = 0;
        mRecipeIndex = index;
        stepsRecipeArray = JsonUtil.getStepsForRecipe(mRecipeIndex);
        mNumberSteps = stepsRecipeArray.size();
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_item_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d("WWD", "Step" + " # " + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberSteps;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mShortDescriptionTextView;
        private TextView mDescriptionTextView;

        public NumberViewHolder(View itemView) {
            super(itemView);
            mShortDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_shortDescription);
            mDescriptionTextView      = (TextView) itemView.findViewById(R.id.tv_description);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            Log.d("WWD", "step bind listIndex is " + listIndex);
            mShortDescriptionTextView.setText(stepsRecipeArray.get(listIndex).shortDescription);
            mDescriptionTextView.setText(stepsRecipeArray.get(listIndex).description);
            //notifyDataSetChanged();

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}

