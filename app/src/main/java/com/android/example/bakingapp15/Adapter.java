package com.android.example.bakingapp15;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> data;
    ListItemClickListener mOnClickListener;
    Adapter(Context context, List<String> data, ListItemClickListener listener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        mOnClickListener = listener;

    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        // bind the textview with data received
        String title = JsonUtil.getRecipeName(i);
        viewHolder.textTitle.setText(title);
        String sServings = "Number Of Servings is " + JsonUtil.getRecipeServings(i).toString();
        viewHolder.testDescription.setText(sServings);
        String imageURL = JsonUtil.getRecipeImage(i);
        if (imageURL.length() == 0) {
            viewHolder.mRecipeImage.setBackgroundResource(R.drawable.green);
        } else {
            // load image using picasso
            Picasso.get().load(imageURL).into(viewHolder.mRecipeImage);
            viewHolder.mRecipeImage.setBackgroundResource(R.drawable.green);
        }
    }

    @Override
    public int getItemCount() {
        int Size = JsonUtil.getRecipeArraySize();

        if (Size > 0){
            return Size;
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener  {

        TextView textTitle, testDescription;
        ImageView mRecipeImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.imageView);
            textTitle = itemView.findViewById(R.id.textTitle);
            testDescription = itemView.findViewById(R.id.textDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
