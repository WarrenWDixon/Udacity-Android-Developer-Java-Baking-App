package com.android.example.bakingapp15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.NumberViewHolder> {

    private static final String TAG = IngredientAdapter.class.getSimpleName();
    private static int viewHolderCount;

    private int mNumberItems;
    private int mRecipeIndex;
    private int mNumIngredients;

    private TextView mQuantityTextView;
    private TextView mMeasureTextView;
    private TextView mIngredientTextView;

    private ArrayList<Ingredients> mIngredientsArrayList;

    public IngredientAdapter(int index) {
        viewHolderCount = 0;
        mRecipeIndex = index;
        mIngredientsArrayList = JsonUtil.getIngredientsForRecipe(mRecipeIndex);
        mNumIngredients = mIngredientsArrayList.size();

        int i = 0;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        int backgroundColorForViewHolder = ColorUtils.getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumIngredients;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {

        public NumberViewHolder(View itemView) {
            super(itemView);
            mQuantityTextView   = (TextView) itemView.findViewById(R.id.tv_quantity);
            mMeasureTextView    = (TextView) itemView.findViewById(R.id.tv_measure);
            mIngredientTextView = (TextView) itemView.findViewById(R.id.tv_ingredient);
        }

        void bind(int listIndex) {
            mQuantityTextView.setText(String.valueOf(mIngredientsArrayList.get(listIndex).quantity));
            mMeasureTextView.setText(mIngredientsArrayList.get(listIndex).measure);
            mIngredientTextView.setText(mIngredientsArrayList.get(listIndex).ingredient);
        }

    }
}


