package www.supcon.com.hsesystem.Base;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.LinkedList;
import java.util.List;

import www.supcon.com.hsesystem.DB.DaoMaster;
import www.supcon.com.hsesystem.DB.DaoSession;

/**
 * Created by yaobing on 2018/3/30.
 *
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
        ZXingLibrary.initDisplayOpinion(this);
    }
    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "task.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }


    /**
     * 释放资源，退出程序时候调用
     */
    private void release() {
        try {
            for (Activity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
                Log.e(TAG, e.toString());
        }
    }

    public final void exit(int code) {
        release();
        System.exit(code);
    }

    private List<Activity> mList = new LinkedList<>();

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void removeActivity(Activity activity) {
        mList.remove(activity);
    }

}
