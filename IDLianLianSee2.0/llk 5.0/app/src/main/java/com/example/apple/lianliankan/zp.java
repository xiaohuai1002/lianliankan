package com.example.apple.lianliankan;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.app.Activity;

import static java.security.AccessController.getContext;

public class zp extends Activity {

    private static Drawable backgroundDrawable;
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;

    /*public static void setBackgroundDrawable(Drawable backgroundDrawable) {
        zp.backgroundDrawable = backgroundDrawable;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zp);

        mLuckyPanView = (LuckyPanView) findViewById(R.id.id_luckypan);
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);

       /* Resources resources = this.getContext().getResources();
        Drawable btnDrawable = resources.getDrawable(R.drawable.bg);
        zp.setBackgroundDrawable(btnDrawable);*/

        //zp.setImageDrawable(R.drawable.bg);

        mStartBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!mLuckyPanView.isStart())
                {
                    mStartBtn.setImageResource(R.drawable.stop);
                    mLuckyPanView.luckyStart(1);
                } else
                {
                    if (!mLuckyPanView.isShouldEnd())

                    {
                        mStartBtn.setImageResource(R.drawable.start);
                        mLuckyPanView.luckyEnd();
                    }
                }
            }
        });
    }

    /*public View getContext() {
        return this.getContext();
    }*/
}