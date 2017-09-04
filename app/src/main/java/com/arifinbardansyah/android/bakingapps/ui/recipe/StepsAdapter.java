package com.arifinbardansyah.android.bakingapps.ui.recipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Steps;
import com.arifinbardansyah.android.bakingapps.utility.ThumbnailUtility;
import com.squareup.picasso.Picasso;

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
        View rootView = inflater.inflate(R.layout.step_item, parent, false);
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

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvStep;
        private ImageView ivThumbnail;
        private ProgressBar pbThumbnail;

        public StepsViewHolder(View itemView) {
            super(itemView);

            tvStep = (TextView) itemView.findViewById(R.id.tv_step);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            pbThumbnail = (ProgressBar) itemView.findViewById(R.id.pbThumbnail);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            tvStep.setText(mSteps.get(position).getShortDescription());
            if (!mSteps.get(position).getThumbnailURL().isEmpty()) {
                if (mSteps.get(position).getThumbnailURL().contains(".mp4")) {
                    pbThumbnail.setVisibility(View.VISIBLE);
                    LoadVideoThumbnail load = new LoadVideoThumbnail();
                    load.execute(mSteps.get(position).getThumbnailURL());
                } else {
                    Picasso.with(mContext).load(mSteps.get(position).getThumbnailURL()).into(ivThumbnail);
                }
            } else {
                ivThumbnail.setImageResource(R.drawable.bowl);
            }
        }

        @Override
        public void onClick(View v) {
            mListener.onClickStep(mSteps.get(getAdapterPosition()), getAdapterPosition());
        }

        public class LoadVideoThumbnail extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... objectURL) {
                //return ThumbnailUtils.createVideoThumbnail(objectURL[0], Thumbnails.MINI_KIND);
                try {
                    return ThumbnailUtility.retriveVideoFrameFromVideo(objectURL[0]);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                pbThumbnail.setVisibility(View.GONE);
                ivThumbnail.setImageBitmap(result);
            }

        }

    }

    public interface OnClickStepsListener {
        void onClickStep(Steps step, int position);
    }
}
