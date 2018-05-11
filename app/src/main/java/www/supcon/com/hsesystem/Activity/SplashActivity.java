package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.Base.Constant;
import www.supcon.com.hsesystem.Callback.CommonCallBack;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.ToastUtil;

public class SplashActivity extends BaseActivity {

    private boolean needWait = false;//默认为true,需要等待.等待获取url/版本号/用户列表,当"版本号为最新"或者是"版本号不是最新但是获取完用户列表"后,赋值为false,不需要等待
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getServer();
        Handler mHandler = new Handler();
        goLogin(mHandler);
    }

    //延时后决定是否进入主页
    private void goLogin(Handler mHandler) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!needWait) {
                    Intent intent = new Intent(getMe(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    /**
     * 获取基础url,获取到了才能获取用户版本号
     */
    private void getServer() {
        OkGo.<HashMap>get(Constant.SERVER)
                .params("imei","")
                .params("hse","")
                .tag(this)
                .execute(new CommonCallBack<HashMap>(true,this) {
                    @Override
                    public void onSuccess(Response<HashMap> response) {
                        ToastUtil.showToast(getMe(),response.body().toString());
                        // TODO: 2018/5/11 把获取的server-url赋值到constant中,随后获取版本号
                        getUserVersion();
                    }

                    @Override
                    public void onError(Response<HashMap> response) {
                        super.onError(response);
                        Log.e(TAG,"错误 + " +response);
                    }
                });
    }

    /**
     * 获取所有数据(包括用户列表和巡检计划)的版本
     */
    private void getUserVersion() {
        OkGo.<HashMap>get(Constant.SERVER)
                .params("imei","")
                .params("hse","")
                .tag(this)
                .execute(new CommonCallBack<HashMap>(true,this) {
                    @Override
                    public void onSuccess(Response<HashMap> response) {
                        // TODO: 2018/5/11 如果用户列表是最新的,就直接进入登录页面,如果不是最新的,就要下载
                        // TODO: 2018/5/11 等下载用户列表完毕后进入登录页面

                    }

                    @Override
                    public void onError(Response<HashMap> response) {
                        super.onError(response);
                        Log.e(TAG,"错误 + " +response);
                    }
                });
    }

    /**
     * 获取最新的用户列表
     */
    private void getLastestUserList() {
        OkGo.<HashMap>get(Constant.SERVER)
                .params("imei","")
                .params("hse","")
                .tag(this)
                .execute(new CommonCallBack<HashMap>(true,this) {
                    @Override
                    public void onSuccess(Response<HashMap> response) {
                        // TODO: 2018/5/11 下载成功后进入登录页面
                        // TODO: 2018/5/11 失败也要进入主页面

                    }

                    @Override
                    public void onError(Response<HashMap> response) {
                        super.onError(response);
                        Log.e(TAG,"错误 + " +response);
                    }

                });
    }
}
