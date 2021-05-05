package com.mlg.obu.Utils;
import java.io.File;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mlg.obu.R;

public class LEDView extends LinearLayout {
    private static final String file = "fonts" + File.separator + "digital.ttf";
    private TextView ledNumber;

    public LEDView(Context context) {
        this(context, null);
    }

    public LEDView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LEDView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ledview, this);
        ledNumber = (TextView) view.findViewById(R.id.ledview_number);

        AssetManager assets = context.getAssets();
        final Typeface font = Typeface.createFromAsset(assets, file);
        ledNumber.setTypeface(font);// 设置字体样式
    }

    /**
     * 显示电子数字
     * @param size 字体大小
     *
     * @param number 需要显示的数字样式
     */
    public void setLedView(int size, String number) {
        ledNumber.setTextSize(size);
        ledNumber.setText(number);
    }

}
