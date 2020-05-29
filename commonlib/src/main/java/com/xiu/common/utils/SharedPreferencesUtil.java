package com.xiu.common.utils;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

public class SharedPreferencesUtil {

    private static final String TAG = "SUtil";
    private static final String SP_NAME = "preferences_skill_up";

    public static <T> void write(Context context, String key, T value) {
        LogUtil.i(TAG, "write value == " + value);
        SharedPreferences.Editor editor = getEditor(context);
        if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            LogUtil.i(TAG, "not support type value");
        }
        editor.commit();
    }

    public static <T> T read(Context context, String key, T defaultValue) {
        T t = null;
        SharedPreferences preferences = getSharedPreferences(context);
        if (defaultValue instanceof Integer) {
            t = (T) ((Integer) preferences.getInt(key, (Integer) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            t = (T) ((Boolean) preferences.getBoolean(key, (Boolean) defaultValue));
        } else if (defaultValue instanceof Long) {
            t = (T) ((Long) preferences.getLong(key, (Long) defaultValue));
        } else if (defaultValue instanceof Float) {
            t = (T) ((Float) preferences.getFloat(key, (Float) defaultValue));
        } else if (defaultValue instanceof String) {
            t = (T) (preferences.getString(key, (String) defaultValue));
        } else {
            LogUtil.i(TAG, "not support read value");
        }
        LogUtil.i(TAG, "read value == " + t);
        return t;
    }

    public static void writeInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        editor.commit();
        LogUtil.i(TAG, "write int , key == " + key + " , value == " + value);
    }

    public static int readInt(Context context, String key, int defaultValue) {
        SharedPreferences preferences = getSharedPreferences(context);
        int result = preferences.getInt(key, defaultValue);
        LogUtil.i(TAG, "read int , key == " + key + " , value == " + result);
        return result;
    }

    public static void writeBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        editor.commit();
        LogUtil.i(TAG, "write boolean , key == " + key + " , value == " + value);
    }

    public static boolean readBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = getSharedPreferences(context);
        boolean result = preferences.getBoolean(key, defaultValue);
        LogUtil.i(TAG, "read boolean , key == " + key + " , value == " + result);
        return result;
    }

    public static void writeString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.commit();
        LogUtil.i(TAG, "write String , key == " + key + " , value == " + value);
    }

    public static String readString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = getSharedPreferences(context);
        String result = preferences.getString(key, defaultValue);
        LogUtil.i(TAG, "read String , key == " + key + " , value == " + result);
        return result;
    }

    public static void writeFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putFloat(key, value);
        editor.commit();
        LogUtil.i(TAG, "write float , key == " + key + " , value == " + value);
    }

    public static float readFloat(Context context, String key, float defaultValue) {
        SharedPreferences preferences = getSharedPreferences(context);
        float result = preferences.getFloat(key, defaultValue);
        LogUtil.i(TAG, "read float , key == " + key + " , value == " + result);
        return result;
    }

    public static void writeLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        editor.commit();
        LogUtil.i(TAG, "write long , key == " + key + " , value == " + value);
    }

    public static long readLong(Context context, String key, long defaultValue) {
        SharedPreferences preferences = getSharedPreferences(context);
        long result = preferences.getLong(key, defaultValue);
        LogUtil.i(TAG, "read long , key == " + key + " , value == " + result);
        return result;
    }

    public static void writeStringSet(Context context, String key, Set<String> value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putStringSet(key, value);
        editor.commit();
    }

    public static Set<String> readStringSet(Context context, String key, Set<String> defaultValue) {
        LogUtil.i(TAG, "read boolean key == " + key + " ,default");
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getStringSet(key, defaultValue);
    }

    public static Map<String, ?> readAll(Context context) {
        LogUtil.i(TAG, "read all");
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getAll();
    }

    public static void clear(Context context) {
        LogUtil.i(TAG, "clear");
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.commit();
    }

    public static void remove(Context context, String key) {
        SharedPreferences preferences = getSharedPreferences(context);
        if (preferences.contains(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(key);
        } else {
            LogUtil.e(TAG, "remove key , but key not exists");
        }
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

}
