package www.supcon.com.hsesystem.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.Base.BaseApplication;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.ImageCompressUtil;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class WorkTicketActivity extends BaseActivity {

    @BindView(R.id.bt_nav_1)
    Button btNav1;
    @BindView(R.id.bt_nav_2)
    Button btNav2;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_work_number)
    TextView tvWorkNumber;
    @BindView(R.id.tv_work_name)
    TextView tvWorkName;
    @BindView(R.id.tv_work_type)
    TextView tvWorkType;
    @BindView(R.id.tv_man_a)
    TextView tvManA;
    @BindView(R.id.tv_man_b)
    TextView tvManB;
    @BindView(R.id.tv_work_place)
    TextView tvWorkPlace;
    @BindView(R.id.tv_work_status)
    TextView tvWorkStatus;
    @BindView(R.id.tv_work_content)
    TextView tvWorkContent;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.bt_abort)
    Button btAbort;
    @BindView(R.id.bt_stop)
    Button btStop;
    @BindView(R.id.bt_take_pic)
    Button btTakePic;
    @BindView(R.id.iv_work_permission)
    ImageView ivWorkPermission;

    private boolean isRunning = true;//默认任务正在进行中，实际需要从后台获取任务状态

    private Task task;
    private static final String TAG = "WorkTicketActivity";
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_ticket);
        ButterKnife.bind(this);
        tvTitle.setText("中控智能HSE-工作票详情页面");
        task = (Task) getIntent().getSerializableExtra("TASK");
        initData();
    }

    private void initData() {
        tvWorkNumber.setText(task.getNumber());
        tvWorkType.setText(task.getType());
        tvWorkName.setText(task.getName());
        tvWorkPlace.setText(task.getLocation());
        tvManA.setText(task.getMan_a());
        tvManB.setText(task.getMan_b());
        tvWorkStatus.setText(task.getStatus());
        tvWorkContent.setText(task.getWork_content());
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.iv_return, R.id.bt_start, R.id.bt_stop, R.id.bt_abort, R.id.bt_take_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_nav_1:
                Intent intent = new Intent(getMe(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_return:
                finish();
                break;
            case R.id.bt_start:
                //点击开始， 后可点击暂停和结束
                //拍照成功后可以开始
                btStart.setBackgroundColor(getResources().getColor(R.color.gray));
                btStart.setClickable(false);
                btStop.setBackgroundColor(getResources().getColor(R.color.white));
                btStop.setClickable(true);                //拍照成功后可以开始
                btAbort.setBackgroundColor(getResources().getColor(R.color.white));
                btAbort.setClickable(true);
                break;
            case R.id.bt_stop:
                finish();
                break;
            case R.id.bt_abort:
                if (isRunning) {
                    //正在运行，点击暂停
                    btAbort.setText("继续");
                } else {
                    //已经暂停，点击继续
                    btAbort.setText("暂停");
                }
                isRunning = !isRunning;
                break;
            case R.id.bt_nav_2:
                Intent intent1 = new Intent(getMe(), WorkListActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_take_pic:
                //动态申请相机权限
//                if (ContextCompat.checkSelfPermission(getMe(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    //如果没有权限，就申请，然后走回调方法，在回调成功的时候调用拍照方法
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
//                    break;
//                }else {
//                    //如果有权限，进入拍照
//                    take_pic();
//                }
                take_pic();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）

                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                take_pic();
                break;
            default:
                break;
        }
    }


    private void take_pic() {
        Log.i(TAG, "现场拍照");
        File PHOTO_DIR = new File(BaseApplication.photoPath);
        // 创建照片的存储目录
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();
        }


        filename = MyDateUtils.getCurTimeFormat(MyDateUtils.date_Format3);
        File out = new File(PHOTO_DIR, filename + ".jpg");

        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(out);
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            uri = FileProvider.getUriForFile(getMe(), "hse", out);
            // 给目标应用一个临时授权
            imageCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(out);
        }





        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.8);
        startActivityForResult(imageCaptureIntent, 104);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int no = TaskDaoDBHelper.queryAll().size();
        tvTaskNo.setText(String.valueOf(no));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 104:
                // 调用压缩的方法。对图片进行一个分辨率的压缩，
                String filepath = BaseApplication.photoPath + File.separator + filename + ".jpg";
                File file = new File(filepath);
                long size = file.length();
                if (size > 0) {
                    //压缩拍后的图片
                    ImageCompressUtil.compressBitmap(filepath, 1024, 768, 80, filepath);
                    ivWorkPermission.setImageURI(Uri.parse(filepath));
                }

                //拍照成功后可以开始
                btStart.setBackgroundColor(getResources().getColor(R.color.white));
                btStart.setClickable(true);
                break;
        }
    }

}
