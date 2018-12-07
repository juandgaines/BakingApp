package com.mytechideas.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

public class ListWidgetService extends RemoteViewsService {

    private static final String ARRAY ="ingredients" ;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent.getStringArrayListExtra(ARRAY));
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    ArrayList<String> mIngredients;

    public ListRemoteViewsFactory(Context applicationContext, ArrayList<String> ingredients ){
        mContext=applicationContext;
        mIngredients=ingredients;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients==null)
            return 0;
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        String ingredient= mIngredients.get(i);
        RemoteViews views=new RemoteViews(mContext.getPackageName(),R.layout.ingredient_item_widget);

        views.setTextViewText(R.id.ingredient_listview_item,ingredient);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
