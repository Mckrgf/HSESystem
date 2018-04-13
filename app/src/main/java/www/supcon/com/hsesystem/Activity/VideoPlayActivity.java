package www.supcon.com.hsesystem.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.DB.Video;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_take_video)
    TextView tvTakeVideo;
    @BindView(R.id.vv_video)
    VideoView vvVideo;
    @BindView(R.id.bt_replay)
    Button btReplay;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.bt_abort)
    Button btAbort;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.tv_task_count)
    TextView tvTaskCount;
    @BindView(R.id.tv_test_count)
    TextView tvTestCount;
    private String url;
    private Video video;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("uri");
        video = (Video) getIntent().getSerializableExtra("video");
        task = TaskDaoDBHelper.queryTask(video.getNumber());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            vvVideo.setVideoURI(Uri.parse(url));
        }

        MediaController controller = new MediaController(this);

        vvVideo.setVideoPath(url);
        vvVideo.setMediaController(controller);

        controller.setMediaPlayer(vvVideo);


        vvVideo.requestFocus();
        vvVideo.start();

        count_time_task();
        count_time_test();
    }

    @OnClick({R.id.iv_return, R.id.bt_replay, R.id.bt_start, R.id.bt_abort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                //返回
                finish();
                break;
            case R.id.bt_replay:
                //重放
                vvVideo.resume();
                break;
            case R.id.bt_start:
                //开始
                if (!vvVideo.isPlaying()) {
                    vvVideo.start();
                }
                break;
            case R.id.bt_abort:
                //暂停
                if (vvVideo.isPlaying()) {
                    vvVideo.pause();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    vvVideo.setVideoURI(Uri.parse(url));
                } else {
                    Toast.makeText(this, "无法播放视频,没有权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    /**
     * 工单倒计时处理
     */
    private void count_time_task() {
        long time = task.getTime_stop();
        long timeGetTime = new Date().getTime();//当前时间戳
        final long count = time - timeGetTime;
        final String[] count_s = {MyDateUtils.formatDuring(count)};
        tvTaskCount.setText(count_s[0]);
        CountDownTimer timer = new CountDownTimer(count, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count_s[0] = MyDateUtils.formatDuring(millisUntilFinished);
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

    private static final String TAG = "VideoPlayActivity";
}
