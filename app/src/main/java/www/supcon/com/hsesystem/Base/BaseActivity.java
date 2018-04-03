package www.supcon.com.hsesystem.Base;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yaobing on 2018/3/30.
 * Description xxx
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getMyApplication().addActivity(this);
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
