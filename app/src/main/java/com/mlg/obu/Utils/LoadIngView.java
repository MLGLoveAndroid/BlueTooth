package com.mlg.obu.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.mlg.obu.R;

public class LoadIngView extends ProgressDialog {
    public LoadIngView(Context context) {
        super(context);
    }

    public LoadIngView(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        View inflate = LayoutInflater.from(context).inflate(R.layout.loading, null);
        ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.pb_load);
        FadingCircle doubleBounce = new FadingCircle();
        doubleBounce.setColor(Color.BLACK);
        doubleBounce.setBounds(0, 0, 100, 100);
        progressBar.setIndeterminateDrawable(doubleBounce);
        setContentView(inflate);

    }

    @Override
    public void show() {//开启
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }
}
