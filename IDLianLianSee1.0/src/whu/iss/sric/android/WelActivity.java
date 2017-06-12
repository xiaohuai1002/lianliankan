package whu.iss.sric.android;

import whu.iss.sric.view.GameView;
import whu.iss.sric.view.OnStateListener;
import whu.iss.sric.view.OnTimerListener;
import whu.iss.sric.view.OnToolsChangeListener;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class WelActivity extends Activity
	implements OnClickListener,OnTimerListener,OnStateListener,OnToolsChangeListener{
	
	private ImageButton btnPlay;
	private ImageButton btnRefresh;
	private ImageButton btnTipMF;
	private ImageButton btnTipXN;
	private ImageView imgTitle;
	private GameView gameView;
	private SeekBar progress;
	private MyDialog dialog;
	private ImageView clock;
	private TextView textRefreshNum;
	private TextView textTipMFNum;
	private TextView textTipXNNum;
	
	private MediaPlayer player;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				dialog = new MyDialog(WelActivity.this,gameView,"胜利！",gameView.getTotalTime() - progress.getProgress());
				dialog.show();
				break;
			case 1:
				dialog = new MyDialog(WelActivity.this,gameView,"失败！",gameView.getTotalTime() - progress.getProgress());
				dialog.show();
			}
		}
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        btnPlay = (ImageButton) findViewById(R.id.play_btn);
        btnRefresh = (ImageButton) findViewById(R.id.refresh_btn);
        btnTipMF = (ImageButton) findViewById(R.id.tip_btnMF);
        btnTipXN = (ImageButton) findViewById(R.id.tip_btnXN);
        imgTitle = (ImageView) findViewById(R.id.title_img);
        gameView = (GameView) findViewById(R.id.game_view);
        clock = (ImageView) findViewById(R.id.clock);
        progress = (SeekBar) findViewById(R.id.timer);
        textRefreshNum = (TextView) findViewById(R.id.text_refresh_num);
        textTipMFNum = (TextView) findViewById(R.id.text_tipMF_num);
        textTipXNNum = (TextView) findViewById(R.id.text_tipXN_num);
        //XXX
        progress.setMax(gameView.getTotalTime());
        
        btnPlay.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        btnTipMF.setOnClickListener(this);
        btnTipXN.setOnClickListener(this);
        gameView.setOnTimerListener(this);
        gameView.setOnStateListener(this);
        gameView.setOnToolsChangedListener(this);
        GameView.initSound(this);
        
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale_anim);
        imgTitle.startAnimation(scale);
        btnPlay.startAnimation(scale);
        
        player = MediaPlayer.create(this, R.raw.bg);
        player.setLooping(true);//设置循环播放
        player.start();
        
//        GameView.soundPlay.play(GameView.ID_SOUND_BACK2BG, -1);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	gameView.setMode(GameView.PAUSE);
    }
    
    @Override
	protected void onDestroy() {
    	super.onDestroy();
    	gameView.setMode(GameView.QUIT);
	}

	@Override
	public void onClick(View v) {
    	
    	switch(v.getId()){
    	case R.id.play_btn:
    		Animation scaleOut = AnimationUtils.loadAnimation(this,R.anim.scale_anim_out);
        	Animation transIn = AnimationUtils.loadAnimation(this,R.anim.trans_in);
    		
    		btnPlay.startAnimation(scaleOut);
    		btnPlay.setVisibility(View.GONE);
    		imgTitle.setVisibility(View.GONE);
    		gameView.setVisibility(View.VISIBLE);
    		
    		btnRefresh.setVisibility(View.VISIBLE);
    		btnTipMF.setVisibility(View.VISIBLE);
    		btnTipXN.setVisibility(View.VISIBLE);
    		progress.setVisibility(View.VISIBLE);
    		clock.setVisibility(View.VISIBLE);
    		textRefreshNum.setVisibility(View.VISIBLE);
    		textTipMFNum.setVisibility(View.VISIBLE);
    		textTipXNNum.setVisibility(View.VISIBLE);
    		
    		btnRefresh.startAnimation(transIn);
    		btnTipMF.startAnimation(transIn);
    		btnTipXN.startAnimation(transIn);
    		gameView.startAnimation(transIn);
    		player.pause();
    		gameView.startPlay();
    		break;
    	case R.id.refresh_btn:
    		Animation shake01 = AnimationUtils.loadAnimation(this,R.anim.shake);
    		btnRefresh.startAnimation(shake01);
    		gameView.refreshChange();
    		break;
    	case R.id.tip_btnMF:
    		Animation shake02 = AnimationUtils.loadAnimation(this,R.anim.shake);
    		btnTipMF.startAnimation(shake02);
    		gameView.autoClearMF();
    		break;
    	case R.id.tip_btnXN:
    		Animation shake03 = AnimationUtils.loadAnimation(this,R.anim.shake);
    		btnTipXN.startAnimation(shake03);
    		gameView.autoClearXN();
    		break;
    	}
	}

	@Override
	public void onTimer(int leftTime) {
		Log.i("onTimer", leftTime+"");
		progress.setProgress(leftTime);
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
	public void onRefreshChanged(int count) {
		textRefreshNum.setText(""+gameView.getRefreshNum());
	}

	@Override
	public void onTipMFChanged(int count) {
		textTipMFNum.setText(""+gameView.getTipMFNum());
	}
	
	@Override
	public void onTipXNChanged(int count) {
		textTipXNNum.setText(""+gameView.getTipXNNum());
	}
	
	public void quit(){
		this.finish();
	}
}