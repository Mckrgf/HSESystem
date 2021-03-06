package www.supcon.com.hsesystem.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.Video;
import www.supcon.com.hsesystem.DB.VideoDaoDBHelper;
import www.supcon.com.hsesystem.Eventbus.HseEvent;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class VideoRecordActivity extends BaseActivity implements SurfaceHolder.Callback {

    @BindView(R.id.tv_title)
    TextView tvTaskCount;
    @BindView(R.id.tv_test_count)
    TextView tvTestCount;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.rl_air_test)
    RelativeLayout rlAirTest;
    private SurfaceView mSurfaceview;
    private TextView mBtnStartStop;
    private boolean mStartedFlg = false;
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private String path = "";
    private int recordTime = 0;
    private LinearLayout llTime;
    private TextView tvTime;
    private TimerTask recordTask;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (mRecorder != null) {
                    mRecorder.stop();
                    mRecorder.reset();
                }
                if (recordTask != null) {
                    recordTask.cancel();
                }
                Intent intent = new Intent();
                intent.putExtra("path", path);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    };
    private Task task;
    private boolean video_start = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (ContextCompat.checkSelfPermission(getMe(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //如果没有权限，就申请，然后走回调方法，在回调成功的时候调用拍照方法
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 2);
        }
        task = (Task) getIntent().getSerializableExtra("task");
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_video_record);
        ButterKnife.bind(this);
        initView();
        count_time_task();
        count_time_test();
        mBtnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starRecordVideo();
            }
        });
        rlAirTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (video_start) {
                    stopRecord(true);
                } else {
                    sendMsg();
                }

            }
        });

    }

    /**
     * 气体检测倒计时处理
     */
    private void count_time_test() {
        long time_stop = new Date().getTime();
        int a = new Date().getHours();
        a = a + 1;//下一个整点
        try {
            String aa = MyDateUtils.getDateFromLong(new Date().getTime(), MyDateUtils.date_Format2);
            String bb = aa + " " + a + ":00:00";
            time_stop = MyDateUtils.getLongDateFromString(bb, MyDateUtils.date_Format);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long timeGetTime = new Date().getTime();//当前时间戳
        final long count = time_stop - timeGetTime;
        final String[] count_s = {MyDateUtils.formatDuringNodays(count)};
        tvTestCount.setText(count_s[0]);
        CountDownTimer timer = new CountDownTimer(count, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count_s[0] = MyDateUtils.formatDuringNodays(millisUntilFinished);
                String aaaa = String.valueOf(count_s[0].charAt(0));
                if (aaaa.equals(1)) {
                    Toast.makeText(getMe(), "该检测了", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "该检测了");
                }
                tvTestCount.setText(count_s[0]);
            }

            @Override
            public void onFinish() {
                tvTestCount.setTextColor(Color.RED);
                tvTestCount.setText("已超时");
            }
        };
        timer.start();
    }

    /**
     * 初始化控件以及录制视频的分辨率
     */
    private void initView() {
        mSurfaceview = (SurfaceView) findViewById(R.id.surfaceview);
//        llTime = (LinearLayout) findViewById(R.id.ll_time);
        tvTime = (TextView) findViewById(R.id.text);
//        pbRecord = (ProgressBar) findViewById(R.id.progressBar);
//        pbRecord.setMax(240);//设置录制最大时间为120s
        mSurfaceHolder = mSurfaceview.getHolder();
        //设置屏幕分辨率
        mSurfaceHolder.setFixedSize(640, 480);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mBtnStartStop = (TextView) findViewById(R.id.btnStartStop);
        SurfaceHolder holder = mSurfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回调接口
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**
     * 工单倒计时处理
     */
    private void count_time_task() {
        long time = task.getTime_stop();
        long timeGetTime = new Date().getTime();//当前时间戳
        final long count = time - timeGetTime;
        final String[] count_s = {MyDateUtils.formatDuringNoSecond(count)};
        tvTaskCount.setText(count_s[0]);
        CountDownTimer timer = new CountDownTimer(count, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count_s[0] = MyDateUtils.formatDuringNoSecond(millisUntilFinished);
                if (count_s[0].equals("已超时")) {
                    tvTaskCount.setTextColor(Color.RED);
                }
                tvTaskCount.setText(count_s[0]);
            }

            @Override
            public void onFinish() {
                tvTaskCount.setTextColor(Color.RED);
                tvTaskCount.setText("已超时");
            }
        };
        timer.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）

                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


    private void starRecordVideo() {
        if (!mStartedFlg) {
            startRecord();
        } else if (mStartedFlg) {
            stopRecord(false);
        }
        mStartedFlg = true; // Set button status flag

    }

    private void startRecord() {
        // Start
        if (mRecorder == null) {
            mRecorder = new MediaRecorder(); // Create MediaRecorder
        }
        try {
            /**
             * 解锁camera
             * 设置输出格式为mpeg_4（mp4），此格式音频编码格式必须为AAC否则网页无法播放
             */
            mCamera.unlock();
            mRecorder.setCamera(mCamera);
            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //音频编码格式对应应为AAC
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //视频编码格式对应应为H264
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mRecorder.setVideoSize(640, 480);
            mRecorder.setVideoEncodingBitRate(600 * 1024);
            mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

            /**
             * 设置输出地址
             */
            String sdPath = getSDPath();
            if (sdPath != null) {
                File dir = new File(sdPath + "/VideoAndAudio");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                path = dir + "/" + getDate() + ".mp4";

                mRecorder.setOutputFile(path);
                mRecorder.setOrientationHint(90);
                mRecorder.prepare();
                mRecorder.start();   // Recording is now started
//                    llTime.setVisibility(View.VISIBLE);
                starRecordTimer();
                mStartedFlg = true;
//                    updateProgress();
                mBtnStartStop.setText("停止");
                //设置按钮为停止的按钮
                ivRecord.setImageResource(R.mipmap.video_stop);
            }
            video_start = true;
        } catch (Exception e) {
            /**
             * 当用户拒绝录音权限会执行这里
             */
            Toast.makeText(getMe(), "没有录音权限", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void stopRecord(boolean airtest) {
        try {
            mRecorder.stop();
            if (recordTask != null) {
                recordTask.cancel();
            }
            mRecorder.reset();
            mStartedFlg = false;
            Toast.makeText(getMe(), "录制完成" + "视频地址:" + path, Toast.LENGTH_SHORT).show();

            Video video = new Video();
            video.setNumber(task.getNumber());
            video.setStatus("0");
            video.setVideoUrl(path);
            video.setDate(MyDateUtils.getDateFromLong(new Date().getTime(), MyDateUtils.date_Format));
            VideoDaoDBHelper.insertVideo(video);

            if (airtest) {
                sendMsg();
            } else {
                sendMsgToLocatTOVideoFragment();
            }
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e));
        }


    }

    private void sendMsgToLocatTOVideoFragment() {
        HseEvent event = new HseEvent();
        event.setTAG(2);//AddUserDeviceActivity
        EventBus.getDefault().post(event);
        finish();
    }

    private void sendMsg() {
//        EventBus.getDefault().register(this);//注册
        HseEvent event = new HseEvent();
        event.setTAG(1);//AddUserDeviceActivity
        EventBus.getDefault().post(event);
        finish();
//        EventBus.getDefault().unregister(this);//街注册
    }

//    private void updateProgress() {
//        /**
//         * 进度条线程
//         */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (progress < 240) {
//                    if (mStartedFlg) {
//                        progress++;
//                        Log.e("ssd", progress + "");
//                        try {
//                            Thread.sleep(500);
//                            handler.sendEmptyMessage(1);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        break;
//                    }
//                }
//            }
//        }).start();
//    }

    /**
     * 开启计时
     */
    private void starRecordTimer() {
        recordTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recordTime++;
                        int h = recordTime / 3600;
                        int m = recordTime / 60;
                        int s = recordTime % 60;
                        String hour = h + "";
                        String strm = m + "";
                        String strs = s + "";
                        if (m < 10) {
                            strm = "0" + m;
                        }
                        if (s < 10) {
                            strs = "0" + s;
                        }
                        if (h < 10) {
                            hour = "0" + h;
                        }
                        tvTime.setText(hour + ":" + strm + ":" + strs);
                    }
                });
            }
        };
        Timer recordTimer = new Timer(true);
        recordTimer.schedule(recordTask, 0, 1000);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = holder;
        startPreView(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // surfaceDestroyed的时候同时对象设置为null
        mSurfaceview = null;
        mSurfaceHolder = null;
        if (mRecorder != null) {
            mRecorder.release(); // Now the object cannot be reused
            mRecorder = null;
        }
    }

    /**
     * 开启预览
     *
     * @param holder
     */
    private void startPreView(SurfaceHolder holder) {
        try {
            if (mCamera == null) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
            }
            if (mRecorder != null) {
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();
                /**
                 * Camera自动对焦
                 */
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            }
        } catch (Exception e) {
            /**
             * 用户拒绝录像权限
             */
            Toast.makeText(getMe(), "用户拒绝了录像权限", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒
        String date = "" + year + (month + 1) + day + hour + minute + second;
        Log.d(TAG, "date:" + date);

        return date;
    }

    private static final String TAG = "VideoRecordActivity";

    /**
     * 获取SD path
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }

        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        llTime.setVisibility(View.GONE);
        recordTime = 0;
        tvTime.setText("00.00");
        mStartedFlg = false;
        if (recordTask != null) {
            recordTask.cancel();
        }
        // 如果正在使用MediaRecorder，首先需要释放它。
        releaseMediaRecorder();
        // 在暂停事件中立即释放摄像头
        releaseCamera();
    }


    private void releaseMediaRecorder() {
        if (mRecorder != null) {
            // 清除recorder配置
            mRecorder.reset();
            // 释放recorder对象
            mRecorder.release();
            mRecorder = null;
            // 为后续使用锁定摄像头
            mCamera.lock();
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            // 为其它应用释放摄像头
            mCamera.release();
            mCamera = null;
        }
    }

    @OnClick({R.id.iv_return, R.id.iv_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_record:
                starRecordVideo();
                break;
        }
    }
}
