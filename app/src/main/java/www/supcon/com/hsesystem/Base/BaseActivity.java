package www.supcon.com.hsesystem.Base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/3/30.
 * Description xxx
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getMyApplication().addActivity(this);

//        //设置全屏,并且控件在状态栏上也有显示,
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.title_color_trans));
//        }

    }

    //获得application
    public BaseApplication getMyApplication() {
        Application application = getApplication();
        return (BaseApplication) application;
    }

    /**
     * 使用Activity.this的地方统一使用getMe替代
     *
     * @return 当前上下文
     */
    public Context getMe() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMyApplication().removeActivity(this);
    }
}
