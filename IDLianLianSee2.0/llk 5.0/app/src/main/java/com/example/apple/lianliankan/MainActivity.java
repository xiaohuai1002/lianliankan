package com.example.apple.lianliankan;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private static int currVolume = 0;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale_anim);

        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);



        button1.startAnimation(scale);
        button2.startAnimation(scale);
        button3.startAnimation(scale);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button1:
                Intent intent1 = new Intent(MainActivity.this,level.class);
                startActivity(intent1);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(MainActivity.this,score.class);
                startActivity(intent2);
                break;
            case R.id.button3:
                CloseSpeaker(mContext);
                break;
        }

    }

    public void OpenSpeaker(Context mContext) {
        try{

//判断扬声器是否在打开
            AudioManager audioManager =(AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

            audioManager.setMode(AudioManager.ROUTE_SPEAKER);

//获取当前通话音量
            currVolume =audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);

            if(!audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(true);

                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL ),
                        AudioManager.STREAM_VOICE_CALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭扬声器
    public void CloseSpeaker(Context mContext) {

        try {
            AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            if(audioManager != null) {
                if(audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,currVolume,
                            AudioManager.STREAM_VOICE_CALL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("1","success");
        //Toast.makeText(mContext,"扬声器已经关闭",Toast.LENGTH_SHORT).show();
    }
}
