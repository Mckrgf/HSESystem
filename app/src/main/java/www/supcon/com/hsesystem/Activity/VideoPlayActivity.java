package www.supcon.com.hsesystem.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;

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
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("uri");

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
}
