package com.arifinbardansyah.android.bakingapps.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arifinbardansyah on 8/8/17.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private Context mContext;
    private List<Recipes> mRecipes;

    private OnClickRecipeListener mListener;

    public RecipesAdapter(Context context, List<Recipes> recipes, OnClickRecipeListener listener) {
        mContext = context;
        mRecipes = recipes;
        mListener = listener;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivImage;
        private TextView tvName;
        private TextView tvServings;
        public RecipesViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView)itemView.findViewById(R.id.iv_recipe);
            tvName = (TextView)itemView.findViewById(R.id.tv_recipe_name);
            tvServings = (TextView)itemView.findViewById(R.id.tv_recipe_servings);

            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            if (!mRecipes.get(position).getImage().isEmpty()) {
                Picasso.with(mContext)
                        .load(mRecipes.get(position).getImage())
                        .into(ivImage);
            } else {
                ivImage.setImageResource(R.drawable.chef);
            }
            tvName.setText(mRecipes.get(position).getName());
            String servings = mRecipes.get(position).getServings()+" serving(s)";
            tvServings.setText(servings);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickRecipe(getAdapterPosition());
        }
    }

    public interface OnClickRecipeListener{
        void onClickRecipe(int position);
    }
}
