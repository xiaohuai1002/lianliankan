package com.example.apple.lianliankan;

/**
 * Created by apple on 17/5/26.
 */



//id 1 lyf 、2 zh、 3 dsg 、4 smg
    //num 每个idol的分数
    //设置初始化的分数都为0，传入的分数为增加的分数
public class User {

    private int id;
    private int add = 0;

    public User() {
        super();
    }

    public User(int id, int add) {
        super();
        this.id = id;
        this.add = add;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this. id = id;
    }

    public int getAdd(){
        return add;
    }

    public void setAdd(int add){
        this.add = this.add +add;
    }




    /*private int lyf = 0;
    private int zh = 0;
    private int dsg = 0;
    private int smg = 0;

    public User() {
        super();
    }

    public User(int lyf, int zh, int dsg, int smg) {
        super();

        this.lyf = lyf;
        this.zh = zh;
        this.dsg = dsg;
        this.smg = smg;
    }

    public  void setLyf(int num){
        this.lyf = lyf +num;
    }
    public int getLyf(){
        return lyf;
    }


    public void setZh(int num){
        this.zh = zh + num;
    }
    public int getZh(){
        return zh;
    }


    public void setDsg(int num){
        this.dsg = dsg + num;
    }
    public int getDsg(){
        return lyf;
    }


    public void setSmg(int num){
        this.smg = smg + num;
    }
    public int getSmg(){
        return smg;
    }*/



}
