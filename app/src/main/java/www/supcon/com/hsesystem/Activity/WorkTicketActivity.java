package www.supcon.com.hsesystem.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Adapter.AirTestListAdapter;
import www.supcon.com.hsesystem.Adapter.VideoListAdapter;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.Base.BaseApplication;
import www.supcon.com.hsesystem.DB.AirTest;
import www.supcon.com.hsesystem.DB.AirTestDaoDBHelper;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.DB.Video;
import www.supcon.com.hsesystem.DB.VideoDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.ImageCompressUtil;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class WorkTicketActivity extends BaseActivity implements VideoListAdapter.OnItemClickListener {

    @BindView(R.id.bt_nav_1)
    TextView btNav1;
    @BindView(R.id.bt_nav_2)
    TextView btNav2;
    ;
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
    @BindView(R.id.tv_task_count)
    TextView tvTaskCount;
    @BindView(R.id.tv_test_content)
    TextView tvTestContent;
    @BindView(R.id.bt_report)
    Button btReport;
    @BindView(R.id.rv_air_test)
    RecyclerView rvAirTest;
    @BindView(R.id.tv_take_video)
    TextView tvTakeVideo;
    @BindView(R.id.ll_videos)
    RecyclerView llVideos;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_stop)
    TextView tvTimeStop;

    private boolean isRunning = true;//默认任务正在进行中，实际需要从后台获取任务状态
    private boolean hasPic = false;

    private Task task;
    private static final String TAG = "WorkTicketActivity";
    private String filename;
    private AirTestListAdapter airTestListAdapter;
    private List<Video> videos;
    private VideoListAdapter videoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_ticket);
        ButterKnife.bind(this);
        tvTitle.setText("中控智能HSE-工作票详情页面");
        task = (Task) getIntent().getSerializableExtra("TASK");
        tvTakeVideo.setVisibility(View.VISIBLE);
        initData();
        count_time_task();
        count_time_test();
        //列表初始化
        LinearLayoutManager manager = new LinearLayoutManager(getMyApplication(), LinearLayoutManager.VERTICAL, false);
        rvAirTest.setLayoutManager(manager);
        rvAirTest.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager manager1 = new LinearLayoutManager(getMyApplication(), LinearLayoutManager.VERTICAL, false);
        llVideos.setLayoutManager(manager1);
        llVideos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
        tvTestContent.setText(count_s[0]);
        CountDownTimer timer = new CountDownTimer(count, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count_s[0] = MyDateUtils.formatDuringNodays(millisUntilFinished);
                String aaaa = String.valueOf(count_s[0].charAt(0));
                if (aaaa.equals(1)) {
                    Toast.makeText(getMe(), "该检测了", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "该检测了");
                }
                tvTestContent.setText(count_s[0]);
            }

            @Override
            public void onFinish() {
                tvTestContent.setTextColor(Color.RED);
                tvTestContent.setText("已超时");
            }
        };
        timer.start();
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

        String time_start = MyDateUtils.getDateFromLong(task.getTime_start(), MyDateUtils.date_Format);
        String time_stop = MyDateUtils.getDateFromLong(task.getTime_stop(), MyDateUtils.date_Format);
        tvTimeStart.setText(time_start);
        tvTimeStop.setText(time_stop);

        String path = task.getPic();
        if (!TextUtils.isEmpty(path)) {
            ivWorkPermission.setImageURI(Uri.parse(path));
            //有照片，可以开始
            startEnable(getResources().getColor(R.color.white), true);

            hasPic = true;
        }

        if (task.getStatus().equals("未审核")) {
            isRunning = false;
            if (!TextUtils.isEmpty(task.getPic())) {
                //可以按开始,继续和停止都不能按
            } else {
                //都不能按
            }
        } else if (task.getStatus().equals("进行中")) {
            isRunning = true;
            //可以按继续和停止,开始不能按
            startEnable(getResources().getColor(R.color.gray), false);
            abortEnable(getResources().getColor(R.color.white), true);
            stopEnable(getResources().getColor(R.color.white), true);
        } else if (task.getStatus().equals("已完成")) {
            //都不能按
            isRunning = false;
            startEnable(getResources().getColor(R.color.gray), false);
            abortEnable(getResources().getColor(R.color.gray), false);
            stopEnable(getResources().getColor(R.color.gray), false);
            btTakePic.setClickable(false);
        }

    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.iv_return, R.id.bt_start, R.id.bt_stop, R.id.bt_abort, R.id.bt_take_pic, R.id.bt_report, R.id.tv_take_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_nav_1:
                //首页
                Intent intent = new Intent(getMe(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_return:
                //返回
                finish();
                break;
            case R.id.bt_report:
                //气体检测的弹窗
                openDialog();
                break;
            case R.id.bt_start:
                //点击开始， 后可点击暂停和结束
                //拍照成功后可以开始
                if (hasPic) {
                    startEnable(getResources().getColor(R.color.gray), false);
                    stopEnable(getResources().getColor(R.color.white), true);
                    abortEnable(getResources().getColor(R.color.white), true);
                } else {
                    Toast.makeText(getMe(), "请先拍摄作业证图片", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.bt_stop:
                //停止
                task.setStatus("已完成");
                TaskDaoDBHelper.updateTask(task);
                finish();
                break;
            case R.id.bt_abort:
                //终端
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
                //列表页面
                Intent intent1 = new Intent(getMe(), WorkListActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_take_video:
                //拍摄视频
                //列表页面
                Intent intent2 = new Intent(getMe(), VideoRecordActivity.class);
                intent2.putExtra("task", task);
                startActivity(intent2);
                //动态申请相机权限
//                if (ContextCompat.checkSelfPermission(getMe(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    //如果没有权限，就申请，然后走回调方法，在回调成功的时候调用拍照方法
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
//                    break;
//                } else {
//                    //如果有权限，拍视频
//                    takeVideo();
//                }

                break;
            case R.id.bt_take_pic:
                //动态申请相机权限
                if (ContextCompat.checkSelfPermission(getMe(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //如果没有权限，就申请，然后走回调方法，在回调成功的时候调用拍照方法
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                    break;
                } else {
                    //如果有权限，进入拍照
                    take_pic();
                }
                break;
        }
    }

    /**
     * 进行录像
     */
    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//参数设置可以省略

        startActivityForResult(intent, 102);


    }

    private void abortEnable(int color, boolean clickable) {
        btAbort.setBackgroundColor(color);
        btAbort.setClickable(clickable);
    }

    private void stopEnable(int color, boolean clickable) {
        btStop.setBackgroundColor(color);
        btStop.setClickable(clickable);
    }

    private void startEnable(int color, boolean clickable) {
        btStart.setBackgroundColor(color);
        btStart.setClickable(clickable);
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.air_test_dialog, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText et_man = view
                .findViewById(R.id.et_man);
        final EditText et_info = view
                .findViewById(R.id.et_info);
        final EditText et_location = view
                .findViewById(R.id.et_location);

        Button btnOK = view.findViewById(R.id.bt_upload);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String info = String.valueOf(et_info.getText());
                String man = String.valueOf(et_man.getText());
                String location = String.valueOf(et_location.getText());
                if (!TextUtils.isEmpty(info) && !TextUtils.isEmpty(man) && !TextUtils.isEmpty(location)) {
                    AirTest airTest = new AirTest();
                    airTest.setInfo(String.valueOf(et_info.getText()));
                    airTest.setMan(String.valueOf(et_man.getText()));
                    airTest.setLocation(String.valueOf(et_location.getText()));
                    airTest.setNumber(task.getNumber());
                    airTest.setTime_b(String.valueOf(new Date()));
                    AirTestDaoDBHelper.insertAirTest(airTest);
                    Toast.makeText(getMe(), "上传成功", Toast.LENGTH_SHORT).show();
                    airTestListAdapter.setData(AirTestDaoDBHelper.queryAllInOneTask(task.getNumber()));
                    dialog.dismiss();
                } else {
                    Toast.makeText(getMe(), "所有内容均不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
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
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）

                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                takeVideo();
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
        List<AirTest> tests = AirTestDaoDBHelper.queryAllInOneTask(task.getNumber());
        airTestListAdapter = new AirTestListAdapter(tests);
        rvAirTest.setAdapter(airTestListAdapter);

        List<Video> videos = VideoDaoDBHelper.queryAllInOneTask(task.getNumber());
        videoListAdapter = new VideoListAdapter(this, videos);
        llVideos.setAdapter(videoListAdapter);

        //刷新数据库里的视频
        videos = VideoDaoDBHelper.queryAllInOneTask(task.getNumber());
        videoListAdapter = new VideoListAdapter(this, videos);
        llVideos.setAdapter(videoListAdapter);
        videoListAdapter.setItemClickListener(this);

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
                    task.setPic(filepath);
                    TaskDaoDBHelper.updateTask(task);
                }

                //拍照成功后可以开始
                startEnable(getResources().getColor(R.color.white), true);

                hasPic = true;
                break;
            case 102:
                Uri videoUri = data.getData();
                String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE};
                Cursor cursor = managedQuery(videoUri, null, null, null, null);
                cursor.moveToFirst();//这个必须加，否则下面读取会报错
                int num = cursor.getCount();
                String recordedVideoFilePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                int recordedVideoFileSize = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                Log.i(TAG, recordedVideoFilePath + "\n" + recordedVideoFileSize + "\n" + num + "\n" + projection);
                Video video = new Video();
                video.setNumber(task.getNumber());
                video.setStatus("0");
                video.setVideoUrl(recordedVideoFilePath);
                video.setDate(MyDateUtils.getDateFromLong(new Date().getTime(), MyDateUtils.date_Format));
                VideoDaoDBHelper.insertVideo(video);


                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        //视频列表,点击查看视频
        Video video = videos.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse(video.getVideoUrl());
        intent.setDataAndType(data, "video/mp4");
        startActivity(intent);
    }
}
