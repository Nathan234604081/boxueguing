package cn.edu.gdmec.android.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 23460 on 2017/12/28.
 */

public class SQLiteHelper extends SQLiteOpenHelper{
    private static  final int DB_VERSION =1;
    public static  String DB_NAME ="bxg.db";
    public static  final String U_USERINFO = "userinfo";//个人资料
    public  static final String U_VIDEO_PALY_LIST = "videopalylist";//视频播放列表
    public SQLiteHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /*
       创建个人信息表
        */
       db.execSQL("CREATE TABLE IF NOT EXISTS " + U_USERINFO +"("
       +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
       +"userName VARCHAR,"
       +"nickName VARCHAR,"
       +"sex VARCHAR,"
       +"signature VARCHAR"+")");

    //创建视频播放记录表
    db.execSQL("CREATE TABLE IF NOT EXISTS " + U_VIDEO_PALY_LIST + "("
            +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"userName VARCHAR,"
            +"chapterId INT ,"
            +"videoPath VARCHAR ,"
            +"title VARCHAR ,"
            +"secondTitle VARCHAR"+")");
}
    /*
    当数据库版本号增加时会调用此方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVertion) {
        db.execSQL("DROP TABLE IF EXISTS "+U_USERINFO );
        db.execSQL("DROP TABLE IF EXISTS "+U_VIDEO_PALY_LIST );
        onCreate(db);

    }
}
