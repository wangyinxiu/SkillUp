package com.xiu.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class XTextView extends AppCompatTextView {

    public XTextView(Context context) {
        super(context);
    }

    public XTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextSafe(String text, String defaultStr) {
        setText(text == null ? defaultStr : text);
    }

    public void setTextSafe(String text){
        setTextSafe(text,"");
    }
}
