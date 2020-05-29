package com.xiu.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class XButton extends AppCompatButton {

    public XButton(Context context) {
        super(context);
    }

    public XButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextSafe(String text, String defaultStr) {
        setText(text == null ? defaultStr : text);
    }

    public void setTextSafe(String text){
        setTextSafe(text,"");
    }
}
