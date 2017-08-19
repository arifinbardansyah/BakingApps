package com.arifinbardansyah.android.bakingapps.ui.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Steps;

import java.util.List;

/**
 * Created by arifinbardansyah on 8/9/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Steps> mSteps;
    private Context mContext;
    private OnClickStepsListener mListener;

    public StepsAdapter(Context context, List<Steps> steps, OnClickStepsListener listener) {
        mContext = context;
        mSteps = steps;
        mListener = listener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.step_item,parent,false);
        return new StepsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvStep;
        public StepsViewHolder(View itemView) {
            super(itemView);

            tvStep = (TextView)itemView.findViewById(R.id.tv_step);

            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            tvStep.setText(mSteps.get(position).getShortDescription());
        }

        @Override
        public void onClick(View v) {
            mListener.onClickStep(mSteps.get(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnClickStepsListener {
        void onClickStep(Steps step, int position);
    }
}
