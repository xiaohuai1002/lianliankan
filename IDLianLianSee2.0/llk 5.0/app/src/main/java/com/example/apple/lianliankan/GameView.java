package com.example.apple.lianliankan;

/**
 * Created by apple on 17/5/20.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;



public class GameView extends View {


    private static final int REFRESH_VIEW = 1;

    public static final int WIN = 1;
    public static final int LOSE = 2;
    public static final int PAUSE = 3;
    public static final int PLAY = 4;
    public static final int QUIT = 5;

    private int Help = 3;
    private int Refresh = 3;
    /**
     * ��һ��Ϊ100���ӵ�ʱ��
     */
    private int totalTime;  //100
    //private int leftTime;
    protected int leftTime;

    public static SoundPlay soundPlay;
    public MediaPlayer player;

    private RefreshTime refreshTime;
    private RefreshHandler refreshHandler = new RefreshHandler();
    /**
     * ����ֹͣ��ʱ�����߳�
     */
    private boolean isStop;

    private OnTimerListener timerListener = null;
    private OnStateListener stateListener = null;
    private OnToolsChangeListener toolsChangedListener = null;




    //boardview
    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃
    /**
     * xCount x�᷽���ͼ����+1
     */
    private  int xCount;
    /**
     * yCount yCount y轴方向的图标数+1
     */
    private  int  yCount;
    /**
     * map 连连看游戏棋盘
     */
    private int[][] map ;
    /**
     * iconSize ͼ���С
     */
    private int iconSize;
    /**
     * iconCounts ͼ�����Ŀ
     */
    private int iconCounts=19;
    /**
     * icons ���е�ͼƬ
     */
    private Bitmap[] icons = new Bitmap[iconCounts];

    /**
     * path path 可以连通点的路径
     */
    private Point[] path = null;
    /**
     * selected 选中的图标
     */
    private List<Point> selected = new ArrayList<Point>();

    //gameview
    private List<Point> path2 = new ArrayList<Point>();

    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃

    //boardview
    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃


    public void set(int xCount,int yCount,int totalTime){

        this.xCount = xCount;
        this.yCount = yCount;

        map = new int[xCount][yCount];
        this.totalTime = totalTime;
    }

    /**

     *

     * 计算图标的长宽

     */
    public void calIconSize()
    {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) this.getContext()).getWindowManager()
                .getDefaultDisplay().getMetrics(dm);
        iconSize = dm.widthPixels/xCount;//widthPixels宽度的像素
    }

    /**

     *

     * @param key 特定图标的标识下的资源

     * @param d drawable

     */
    public void loadBitmaps(int key,Drawable d){
        Bitmap bitmap = Bitmap.createBitmap(iconSize,iconSize,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, iconSize, iconSize);
        d.draw(canvas);
        icons[key]=bitmap;
    }


    /**

     * 绘制连通路径，然后将路径以及两个图标清除

     */
    @Override
    protected void onDraw(Canvas canvas) {

        if (path != null && path.length >= 2) {
            for (int i = 0; i < path.length - 1; i++) {
                Paint paint = new Paint();
                paint.setColor(Color.CYAN);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                Point p1 = indextoScreen(path[i].x, path[i].y);
                Point p2 = indextoScreen(path[i + 1].x, path[i + 1].y);
                canvas.drawLine(p1.x + iconSize / 2, p1.y + iconSize / 2,
                        p2.x + iconSize / 2, p2.y + iconSize / 2, paint);
            }
            Point p = path[0];
            map[p.x][p.y] = 0;
            p = path[path.length - 1];
            map[p.x][p.y] = 0;
            selected.clear();
            path = null;
        }
        /**
         * �������̵�����ͼ�� ����������ڵ�ֵ����0ʱ����
         */
        for(int x=0;x<map.length;x+=1){
            for(int y=0;y<map[x].length;y+=1){
                if(map[x][y]>0){
                    Point p = indextoScreen(x, y);
                    canvas.drawBitmap(icons[map[x][y]], p.x,p.y,null);
                }
            }
        }

        /**
         * ����ѡ��ͼ�꣬��ѡ��ʱͼ��Ŵ���ʾ
         */
        for(Point position:selected){
            Point p = indextoScreen(position.x, position.y);
            if(map[position.x][position.y] >= 1){
                canvas.drawBitmap(icons[map[position.x][position.y]],
                        null,
                        new Rect(p.x-5, p.y-5, p.x + iconSize + 5, p.y + iconSize + 5), null);
            }
        }
    }

    /**
     *
     * @param path
     */
    public void drawLine(Point[] path) {
        this.path = path;
        this.invalidate();
    }

    /**
     * ���߷���
     * @param x �����еĺ�����
     * @param y �����е�������
     * @return ��ͼ���������е�����ת������Ļ�ϵ���ʵ����
     */
    public Point indextoScreen(int x,int y){
        return new Point(x* iconSize , y * iconSize );
    }
    /**
     * ���߷���
     * @param x ��Ļ�еĺ�����
     * @param y ��Ļ�е�������
     * @return ��ͼ������Ļ�е�����ת���������ϵ���������
     */
    public Point screenToindex(int x,int y){
        int ix = x/ iconSize;
        int iy = y / iconSize;
        if(ix < xCount && iy <yCount){
            return new Point( ix,iy);
        }else{
            return new Point(0,0);
        }
    }

    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃








    //gameview
    //############### AttributeSet 含义
    //构造方法
    public GameView(Context context, AttributeSet atts) {
        super(context, atts);
        player = MediaPlayer.create(context, R.raw.back2new);
        player.setLooping(true);//设置循环播放����ѭ������
    }

    public static final int ID_SOUND_CHOOSE = 0;
    public static final int ID_SOUND_DISAPEAR = 1;
    public static final int ID_SOUND_WIN = 4;
    public static final int ID_SOUND_LOSE = 5;
    public static final int ID_SOUND_REFRESH = 6;
    public static final int ID_SOUND_TIP = 7;
    public static final int ID_SOUND_ERROR = 8;

    public void startPlay(){

        calIconSize();

        Resources r = getResources();
        loadBitmaps(1, r.getDrawable(R.drawable.idol_1));
        loadBitmaps(2, r.getDrawable(R.drawable.idol_2));
        loadBitmaps(3, r.getDrawable(R.drawable.idol_3));
        loadBitmaps(4, r.getDrawable(R.drawable.idol_4));
        loadBitmaps(5, r.getDrawable(R.drawable.fruit_05));
        loadBitmaps(6, r.getDrawable(R.drawable.fruit_06));
        loadBitmaps(7, r.getDrawable(R.drawable.fruit_07));
        loadBitmaps(8, r.getDrawable(R.drawable.fruit_08));
        loadBitmaps(9, r.getDrawable(R.drawable.fruit_09));
        loadBitmaps(10, r.getDrawable(R.drawable.fruit_10));
        loadBitmaps(11, r.getDrawable(R.drawable.fruit_11));
        loadBitmaps(12, r.getDrawable(R.drawable.fruit_12));
        loadBitmaps(13, r.getDrawable(R.drawable.fruit_13));
        loadBitmaps(14, r.getDrawable(R.drawable.fruit_14));
        loadBitmaps(15, r.getDrawable(R.drawable.fruit_15));
        loadBitmaps(16, r.getDrawable(R.drawable.fruit_16));
        loadBitmaps(17, r.getDrawable(R.drawable.fruit_17));
        loadBitmaps(18, r.getDrawable(R.drawable.fruit_18));

        Help = 3;
        Refresh = 3;
        isStop = false;
        toolsChangedListener.onRefreshChanged(Refresh);
        toolsChangedListener.onTipChanged(Help);

        leftTime = totalTime;
        initMap();
        player.start();
        refreshTime = new RefreshTime();
        refreshTime.start();
        GameView.this.invalidate();//无效
    }

    public void startNextPlay(){
        //��һ��Ϊ��һ�ؼ�ȥ10���ʱ��
        if (totalTime > 60)
            totalTime-=20;
        startPlay();
    }

    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃
    public void initMap() {
        int x = 1;
        int y = 0;
        //图片位置偏移量和图片数量
        for (int i = 1; i < xCount -1 ; i++) { //xcount
            for (int j = 1; j < yCount -1 ; j++) { //ycount
                map[i][j] = x;
                if (y == 1) {
                    x++;
                    y = 0;
                    if (x == iconCounts) {
                        x = 1;
                    }
                } else {
                    y = 1;
                }
            }
        }
        change();
    }


    public static void initSound(Context context){
        soundPlay = new SoundPlay();
        soundPlay.initSounds(context);
        soundPlay.loadSfx(context, R.raw.choose, ID_SOUND_CHOOSE);
        soundPlay.loadSfx(context, R.raw.disappear1, ID_SOUND_DISAPEAR);
        soundPlay.loadSfx(context, R.raw.win, ID_SOUND_WIN);
        soundPlay.loadSfx(context, R.raw.lose, ID_SOUND_LOSE);
        soundPlay.loadSfx(context, R.raw.item1, ID_SOUND_REFRESH);
        soundPlay.loadSfx(context, R.raw.item2, ID_SOUND_TIP);
        soundPlay.loadSfx(context, R.raw.alarm, ID_SOUND_ERROR);
    }

    public void setOnTimerListener(OnTimerListener timerListener){
        this.timerListener = timerListener;
    }

    public void setOnStateListener(OnStateListener stateListener){
        this.stateListener = stateListener;
    }

    public void setOnToolsChangedListener(OnToolsChangeListener toolsChangedListener){
        this.toolsChangedListener = toolsChangedListener;
    }

    public void stopTimer(){
        isStop = true;
    }


    //die  change   win  setmode
   //RefreshHandler 用来停止计时器的线程
    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_VIEW) {
                GameView.this.invalidate();
                if (win()) {//判断是否成功完成
                    setMode(WIN);
                    soundPlay.play(ID_SOUND_WIN, 0);
                    isStop = true;
                } else if (die()) {
                    change();
                }
            }
        }

        public void sleep(int delayTime) {
            this.removeMessages(0);
            Message message = new Message();
            message.what = REFRESH_VIEW;
            sendMessageDelayed(message, delayTime);
        }
    }

    class RefreshTime extends Thread {
        public void run() {
            while (leftTime >= 0 && !isStop) {
                timerListener.onTimer(leftTime);
                leftTime--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!isStop){
                setMode(LOSE);
                soundPlay.play(ID_SOUND_LOSE, 0);
            }
        }
    }

    public int getTotalTime(){
        return totalTime;
    }

    public int getTipNum(){
        return Help;
    }

    public int getRefreshNum(){
        return Refresh;
    }



    //link
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = screenToindex(x, y);
        //路径判断
        if (map[p.x][p.y] > 0) {
            if (selected.size() == 1) {
                if (link(selected.get(0), p)) {//当两个相等图片相邻时，直接消除
                    selected.add(p);
                    drawLine(path2.toArray(new Point[] {}));
                    soundPlay.play(ID_SOUND_DISAPEAR, 0);
                    refreshHandler.sleep(500);
                } else {
                    selected.clear();
                    selected.add(p);
                    soundPlay.play(ID_SOUND_CHOOSE, 0);
                    GameView.this.invalidate();
                }
            } else {
                selected.add(p);
                soundPlay.play(ID_SOUND_CHOOSE, 0);
                GameView.this.invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃

    private void change() {
        Random random = new Random();
        int tmpV, tmpX, tmpY;
        for (int x = 1; x < xCount - 1; x++) {
            for (int y = 1; y < yCount - 1; y++) {
                tmpX = 1 + random.nextInt(xCount - 2);
                tmpY = 1 + random.nextInt(yCount - 2);
                tmpV = map[x][y];
                map[x][y] = map[tmpX][tmpY];
                map[tmpX][tmpY] = tmpV;
            }
        }
        if (die()) {
            change();
        }
        GameView.this.invalidate();
    }

    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃
    public void setMode(int stateMode) {
        this.stateListener.OnStateChanged(stateMode);
    }

    //＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃
    private boolean die() {
        for (int y = 1; y < yCount - 1; y++) {
            for (int x = 1; x < xCount - 1; x++) {
                if (map[x][y] != 0) {
                    for (int j = y; j < yCount - 1; j++) {
                        if (j == y) {
                            for (int i = x + 1; i < xCount - 1; i++) {
                                if (map[i][j] == map[x][y]
                                        && link(new Point(x, y),
                                        new Point(i, j))) {
                                    return false;
                                }
                            }
                        } else {
                            for (int i = 1; i < xCount - 1; i++) {
                                if (map[i][j] == map[x][y]
                                        && link(new Point(x, y),
                                        new Point(i, j))) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    List<Point> p1E = new ArrayList<Point>();
    List<Point> p2E = new ArrayList<Point>();



    //################################################
    private boolean link(Point p1, Point p2) {
        if (p1.equals(p2)) {
            return false;
        }
        path2.clear();
        if (map[p1.x][p1.y] == map[p2.x][p2.y]) {
            if (linkD(p1, p2)) {
                path2.add(p1);
                path2.add(p2);
                return true;
            }

            Point p = new Point(p1.x, p2.y);
            if (map[p.x][p.y] == 0) {
                if (linkD(p1, p) && linkD(p, p2)) {
                    path2.add(p1);
                    path2.add(p);
                    path2.add(p2);
                    return true;
                }
            }
            p = new Point(p2.x, p1.y);
            if (map[p.x][p.y] == 0) {
                if (linkD(p1, p) && linkD(p, p2)) {
                    path2.add(p1);
                    path2.add(p);
                    path2.add(p2);
                    return true;
                }
            }
            expandX(p1, p1E);
            expandX(p2, p2E);

            for (Point pt1 : p1E) {
                for (Point pt2 : p2E) {
                    if (pt1.x == pt2.x) {
                        if (linkD(pt1, pt2)) {
                            path2.add(p1);
                            path2.add(pt1);
                            path2.add(pt2);
                            path2.add(p2);
                            return true;
                        }
                    }
                }
            }

            expandY(p1, p1E);
            expandY(p2, p2E);
            for (Point pt1 : p1E) {
                for (Point pt2 : p2E) {
                    if (pt1.y == pt2.y) {
                        if (linkD(pt1, pt2)) {
                            path2.add(p1);
                            path2.add(pt1);
                            path2.add(pt2);
                            path2.add(p2);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }

    private boolean linkD(Point p1, Point p2) {
        if (p1.x == p2.x) {
            int y1 = Math.min(p1.y, p2.y);
            int y2 = Math.max(p1.y, p2.y);
            boolean flag = true;
            for (int y = y1 + 1; y < y2; y++) {
                if (map[p1.x][y] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return true;
            }
        }
        if (p1.y == p2.y) {
            int x1 = Math.min(p1.x, p2.x);
            int x2 = Math.max(p1.x, p2.x);
            boolean flag = true;
            for (int x = x1 + 1; x < x2; x++) {
                if (map[x][p1.y] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

    private void expandX(Point p, List<Point> l) {
        l.clear();
        for (int x = p.x + 1; x < xCount; x++) {
            if (map[x][p.y] != 0) {
                break;
            }
            l.add(new Point(x, p.y));
        }
        for (int x = p.x - 1; x >= 0; x--) {
            if (map[x][p.y] != 0) {
                break;
            }
            l.add(new Point(x, p.y));
        }
    }

    private void expandY(Point p, List<Point> l) {
        l.clear();
        for (int y = p.y + 1; y < yCount; y++) {
            if (map[p.x][y] != 0) {
                break;
            }
            l.add(new Point(p.x, y));
        }
        for (int y = p.y - 1; y >= 0; y--) {
            if (map[p.x][y] != 0) {
                break;
            }
            l.add(new Point(p.x, y));
        }
    }
    //################################################

    private boolean win() {
        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                if (map[x][y] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void autoClear() {
        if (Help == 0) {
            soundPlay.play(ID_SOUND_ERROR, 0);
        }else{
            soundPlay.play(ID_SOUND_TIP, 0);
            Help--;
            toolsChangedListener.onTipChanged(Help);
            drawLine(path2.toArray(new Point[] {}));
            refreshHandler.sleep(500);
        }
    }

    public void refreshChange(){
        if(Refresh == 0){
            soundPlay.play(ID_SOUND_ERROR, 0);
            return;
        }else{
            soundPlay.play(ID_SOUND_REFRESH, 0);
            Refresh--;
            toolsChangedListener.onRefreshChanged(Refresh);
            change();
        }
    }





}
