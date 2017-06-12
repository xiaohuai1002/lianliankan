package com.example.apple.lianliankan;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class level extends AppCompatActivity implements View.OnClickListener,OnTimerListener,OnStateListener,OnToolsChangeListener{


    private ImageButton tip_btn;
    private ImageButton btnRefresh;

    private GameView gameView;

    private SeekBar progress;
    private MyDialog dialog;
    private ImageView clock;
    private TextView textRefreshNum;
    private TextView textTipNum;


    private Button button1;
    private Button button2;
    private Button button3;

    private Button button4;
    private Button button5;
    private Button button6;


    private Button button7;
    private Button button8;
    private Button button9;


    private MediaPlayer player;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    //dialog = new MyDialog(level.this,gameView,"胜利！",gameView.getTotalTime() - progress.getProgress());
                    dialog = new MyDialog(level.this,gameView,"胜利！",gameView.getTotalTime() - gameView.leftTime);
                    dialog.show();
                    break;
                case 1:
                    //dialog = new MyDialog(level.this,gameView,"失败！",gameView.getTotalTime() - progress.getProgress());
                    dialog = new MyDialog(level.this,gameView,"失败！",gameView.getTotalTime() - gameView.leftTime);
                    dialog.show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);


        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);

        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);



        btnRefresh = (ImageButton) findViewById(R.id.refresh_btn);//刷新?
        tip_btn = (ImageButton) findViewById(R.id.tip_btn);//魔法棒？

        gameView = (GameView) findViewById(R.id.game_view);//自定义控件

        clock = (ImageView) findViewById(R.id.clock);
        progress = (SeekBar) findViewById(R.id.timer);

        textRefreshNum = (TextView) findViewById(R.id.text_refresh_num);
        textTipNum = (TextView) findViewById(R.id.text_tip_num);

        //XXX
        progress.setMax(gameView.getTotalTime());
        btnRefresh.setOnClickListener(this);//刷新
        tip_btn.setOnClickListener(this);//魔法棒

        gameView.setOnTimerListener(this);//gameview用法
        gameView.setOnStateListener(this);
        gameView.setOnToolsChangedListener(this);

        GameView.initSound(this);

        //Animation 动画 scale 渐变尺寸伸缩动画效果
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale_anim);
        button1.startAnimation(scale);
        button2.startAnimation(scale);
        button3.startAnimation(scale);
        button4.startAnimation(scale);
        button5.startAnimation(scale);
        button6.startAnimation(scale);
        button7.startAnimation(scale);
        button8.startAnimation(scale);
        button9.startAnimation(scale);

        player = MediaPlayer.create(this, R.raw.bg);
        //looping循环
        player.setLooping(true);//����ѭ������
        player.start();


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                fade();
                gameView.set(6,8,100);
                gameView.startPlay();

                break;
            case R.id.button2:
                fade();
                gameView.set(6,8,80);
                gameView.startPlay();

                break;
            case R.id.button3:
                fade();
                gameView.set(6,8,60);
                gameView.startPlay();
                break;

            case R.id.button4:
                fade();
                gameView.set(8,10,100);
                gameView.startPlay();
                break;

            case R.id.button5:
                fade();
                gameView.set(8,10,80);
                gameView.startPlay();
                break;

            case R.id.button6:
                fade();
                gameView.set(8,10,60);
                gameView.startPlay();
                break;

            case R.id.button7:
                fade();
                gameView.set(10,12,100);
                gameView.startPlay();
                break;

            case R.id.button8:
                fade();
                gameView.set(10,12,80);
                gameView.startPlay();
                break;

            case R.id.button9:
                fade();
                gameView.set(10,12,60);
                gameView.startPlay();
                break;


            case R.id.refresh_btn:
                Animation shake01 = AnimationUtils.loadAnimation(this,R.anim.shake);
                btnRefresh.startAnimation(shake01);
                gameView.refreshChange();
                break;
            case R.id.tip_btn:
                Animation shake02 = AnimationUtils.loadAnimation(this,R.anim.shake);
                tip_btn.startAnimation(shake02);
                gameView.autoClear();
                break;

        }
    }


    public void fade(){

        Animation scaleOut = AnimationUtils.loadAnimation(this,R.anim.scale_anim_out);
        Animation transIn = AnimationUtils.loadAnimation(this,R.anim.trans_in);

        button1.startAnimation(scaleOut);
        button2.startAnimation(scaleOut);
        button3.startAnimation(scaleOut);
        button4.startAnimation(scaleOut);
        button5.startAnimation(scaleOut);
        button6.startAnimation(scaleOut);
        button7.startAnimation(scaleOut);
        button8.startAnimation(scaleOut);
        button9.startAnimation(scaleOut);

        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);
        button6.setVisibility(View.GONE);
        button7.setVisibility(View.GONE);
        button8.setVisibility(View.GONE);
        button9.setVisibility(View.GONE);


        gameView.setVisibility(View.VISIBLE);
        //游戏控件显示
        btnRefresh.setVisibility(View.VISIBLE);
        tip_btn.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        clock.setVisibility(View.VISIBLE);
        textRefreshNum.setVisibility(View.VISIBLE);
        textTipNum.setVisibility(View.VISIBLE);

        btnRefresh.startAnimation(transIn);
        tip_btn.startAnimation(transIn);
        gameView.startAnimation(transIn);
        player.pause();
    }

    public void quit(){
        this.finish();
    }

    @Override
    public void OnStateChanged(int StateMode) {
        switch(StateMode){
            case GameView.WIN:
                handler.sendEmptyMessage(0);
                break;
            case GameView.LOSE:
                handler.sendEmptyMessage(1);
                break;
            case GameView.PAUSE:
                player.stop();
                gameView.player.stop();
                gameView.stopTimer();
                break;
            case GameView.QUIT:
                player.release();
                gameView.player.release();
                gameView.stopTimer();
                break;
        }
    }

    @Override
    public void onTimer(int leftTime) {
        Log.i("onTimer", leftTime+"");
        progress.setProgress(leftTime);
    }

    @Override
    public void onRefreshChanged(int count) {
        textRefreshNum.setText("刷新"+gameView.getRefreshNum());
    }

    @Override
    public void onTipChanged(int count) {
        textTipNum.setText("提示"+gameView.getTipNum());
    }


    @Override
    public void onBackPressed() {
        gameView.stopTimer();
        super.onBackPressed();
    }



}
