package www.supcon.com.hsesystem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.Base.BaseApplication;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.DB.Video;
import www.supcon.com.hsesystem.DB.VideoDaoDBHelper;
import www.supcon.com.hsesystem.Fragment.CheckFragment;
import www.supcon.com.hsesystem.Fragment.MissionFragment;
import www.supcon.com.hsesystem.Fragment.VideoFragment;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.ImageCompressUtil;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class WorkMissionActivity extends BaseActivity {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tb_type)
    TabLayout tbType;
    @BindView(R.id.vp_mission)
    ViewPager vpMission;
    @BindView(R.id.bt_stop)
    TextView btStop;
    @BindView(R.id.bt_start)
    TextView btStart;
    @BindView(R.id.ll_charge)
    LinearLayout llCharge;
    @BindView(R.id.activity_work_mission)
    RelativeLayout activityWorkMission;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    private ArrayList<String> listTitles;
    private ArrayList<Fragment> fragments;
    private ArrayList<TextView> listTextViews;
    private Task task;
    private FragmentPagerAdapter fragmentPagerAdapter;
    boolean task_status = false; //false 未开始  true已开始

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_mission);
        ButterKnife.bind(this);

        initView();

        initData();

    }

    private void initData() {

    }

    private void initView() {
        task = (Task) getIntent().getSerializableExtra("TASK");

        listTitles = new ArrayList<>();
        fragments = new ArrayList<>();
        listTextViews = new ArrayList<>();

        listTitles.add("工单内容");
        listTitles.add("视频");
        listTitles.add("检查项");

        //把三个fragment都添加到列表中
        MissionFragment fragment = MissionFragment.newInstance(task);
        fragments.add(fragment);
        VideoFragment videoFragment = VideoFragment.newInstance("视频");
        fragments.add(videoFragment);
        CheckFragment checkFragment = CheckFragment.newInstance("检查项");
        fragments.add(checkFragment);

        ///
        //mTabLayout.setTabMode(TabLayout.SCROLL_AXIS_HORIZONTAL);//设置tab模式，当前为系统默认模式
        for (int i = 0; i < listTitles.size(); i++) {
            tbType.addTab(tbType.newTab().setText(listTitles.get(i)));//添加tab选项
        }


        //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return listTitles.get(position);
            }
        };
        vpMission.setAdapter(fragmentPagerAdapter);

        tbType.setupWithViewPager(vpMission);//将TabLayout和ViewPager关联起来。
        tbType.setTabsFromPagerAdapter(fragmentPagerAdapter);//给Tabs设置适配器
    }

    @OnClick({R.id.bt_start, R.id.bt_stop, R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if (task_status) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("操作提示");
                    builder.setMessage("是否要暂停作业");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btStart.setText("暂停作业");
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //任务未开始
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("操作提示");
                    builder.setMessage("是否要继续作业");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btStart.setText("继续作业");
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
            case R.id.bt_stop:
                //停止
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("操作提示");
                builder.setMessage("是否要结束作业");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.setStatus("已完成");
                        TaskDaoDBHelper.updateTask(task);
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.iv_return:
                finish();
                break;
        }
    }

    private static final String TAG = "WorkMissionActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 104:
                // 调用压缩的方法。对图片进行一个分辨率的压缩，
                String filename = BaseApplication.filename;
                String filepath = BaseApplication.photoPath + File.separator + filename + ".jpg";
                File file = new File(filepath);
                long size = file.length();
                if (size > 0) {
                    //压缩拍后的图片
                    ImageCompressUtil.compressBitmap(filepath, 1024, 768, 80, filepath);

                    //获取fragment中iv
                    MissionFragment missionFragment = (MissionFragment) fragmentPagerAdapter.getItem(0);
                    ImageView iv_pic = missionFragment.getImageView();
                    iv_pic.setImageURI(Uri.parse(filepath));
                    task.setPic(filepath);
                    TaskDaoDBHelper.updateTask(task);
                }
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
}



