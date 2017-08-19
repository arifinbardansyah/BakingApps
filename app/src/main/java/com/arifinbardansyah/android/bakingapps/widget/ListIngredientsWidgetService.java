package com.arifinbardansyah.android.bakingapps.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by arifinbardansyah on 8/18/17.
 */

public class ListIngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingAppsWidgetFactory(this.getApplicationContext());
    }
}
