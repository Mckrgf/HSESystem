package www.supcon.com.hsesystem.Base;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yaobing on 2018/3/30.
 * Description xxx
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    /**
     * 释放资源，退出程序时候调用
     */
    private final void release() {
        try {
            for (Activity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e != null) {
                Log.e(TAG, e.toString());
            } else {
                Log.e(TAG, "数据异常" + TAG);
            }
        }
    }

    public final void exit(int code) {
        release();
        System.exit(code);
    }

    private List<Activity> mList = new LinkedList<Activity>();

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void removeActivity(Activity activity) {
        mList.remove(activity);
    }

}
