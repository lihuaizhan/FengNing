package com.example.tps900.tps900.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wo on 2017/3/31.
 */

public class GalasysListView extends ListView {

    public GalasysListView(Context context) {
        super(context);
    }

    public GalasysListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalasysListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }


}
