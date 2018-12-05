package com.mytechideas.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static boolean firstTime=true;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName,String ingredients,int resourceImage) {
        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        if(!firstTime) {
            views.setViewVisibility(R.id.ingredients_content_widget, View.VISIBLE);
            views.setViewVisibility(R.id.ingredients_label_widget, View.VISIBLE);
            views.setViewVisibility(R.id.recipe_image,View.VISIBLE);
            views.setTextViewText(R.id.appwidget_text, recipeName);
            views.setTextViewText(R.id.ingredients_content_widget, ingredients);
            views.setImageViewResource(R.id.recipe_image,resourceImage);

        }
        Intent intent =new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);

        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        views.setOnClickPendingIntent(R.id.next_label,pendingIntent);
        views.setOnClickPendingIntent(R.id.ingredients_content_widget,pendingIntent);
        views.setOnClickPendingIntent(R.id.recipe_image, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if(firstTime){
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId,null,null,0);
            }
            firstTime=false;
        }
    }

    public static  void updateRecipeWidgets(Context context,AppWidgetManager appWidgetManager, int[] appWidgetIds, String recipeName,String ingredients, int resourceImage){

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,recipeName,ingredients,resourceImage);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

