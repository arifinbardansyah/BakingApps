package com.arifinbardansyah.android.bakingapps.utility;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by arifinbardansyah on 8/20/17.
 */

public class SixteenNineImageView extends android.support.v7.widget.AppCompatImageView {
    public SixteenNineImageView(Context context) {
        super(context);
    }

    public SixteenNineImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SixteenNineImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(widthMeasureSpec) * 9 / 16;
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
