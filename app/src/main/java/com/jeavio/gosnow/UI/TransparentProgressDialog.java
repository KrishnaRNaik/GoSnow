package com.jeavio.gosnow.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.jeavio.gosnow.R;

/**
 * Created by Krishna on 8/5/2016.
 */
public class TransparentProgressDialog extends Dialog {
    private ImageView iv;
    private TextView tv;

    public TransparentProgressDialog(Context context, int resourceIdOfImage, String message) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        //setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        iv = new ImageView(context);
        iv.setImageResource(resourceIdOfImage);
        layout.addView(iv, params);
        tv = new TextView(context);
        tv.setText(message);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(tv);
        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(3000);
        iv.setAnimation(anim);
        iv.startAnimation(anim);
    }

}
