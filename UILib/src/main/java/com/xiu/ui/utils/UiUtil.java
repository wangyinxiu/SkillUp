package com.xiu.ui.utils;

import android.content.Context;

import java.lang.reflect.Field;

public class UiUtil {

    private static final String STATUS_BAR = "status_bar_height";
    private static final String NAVIGATION_BAR = "navigation_bar_height";

    public static int dp2px(Context context, float dp) {
        return ((int) (context.getResources().getDisplayMetrics().density * dp));
    }

    public static int sp2px(Context context, float sp) {
        return ((int) (context.getResources().getDisplayMetrics().scaledDensity * sp));
    }

    public static int getStatusBarHeight(Context context) {
        return calcBarHeight(context, STATUS_BAR);
    }

    public static int getNavigationBar(Context context) {
        return calcBarHeight(context, NAVIGATION_BAR);
    }

    private static int calcBarHeight(Context context, String bar) {
        int height = 0;
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object obj = cls.newInstance();
            Field field = cls.getField(bar);
            height = context.getResources().getDimensionPixelSize(Integer.parseInt(field.get(obj).toString()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return height;
    }

}
