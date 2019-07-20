package com.hackathon.wah.widget.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomFontTextView extends AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
         * information about the TextView textStyle:
         * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
         */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.get("MYRIADPRO-BOLD.OTF", context);

            case Typeface.ITALIC: // italic
                return FontCache.get("MYRIADPRO-SEMIBOLDIT.OTF", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return FontCache.get("MYRIADPRO-BOLDIT.OTF", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.get("MYRIADPRO-REGULAR.OTF", context);
        }
    }
}