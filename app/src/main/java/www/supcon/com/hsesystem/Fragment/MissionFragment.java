package www.supcon.com.hsesystem.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.supcon.com.hsesystem.Base.BaseApplication;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

/**
 * Created by yaobing on 2018/4/17.
 * Description xxx
 */

@SuppressLint("ValidFragment")
public class MissionFragment extends Fragment {
    View view;
    private static final String KEY = "title";
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_examine_company_content)
    TextView tvExamineCompanyContent;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_examine_level_content)
    TextView tvExamineLevelContent;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_examine_man_content)
    TextView tvExamineManContent;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_examine_way_content)
    TextView tvExamineWayContent;
    @BindView(R.id.ll_type_man_location)
    LinearLayout llTypeManLocation;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_stop)
    TextView tvTimeStop;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.tv_examine_charge)
    TextView tvExamineCharge;
    @BindView(R.id.tv_examine_charge_content)
    TextView tvExamineChargeContent;
    @BindView(R.id.tv_examine_workman)
    TextView tvExamineWorkman;
    @BindView(R.id.tv_examine_workman_content)
    TextView tvExamineWorkmanContent;
    @BindView(R.id.ll_task_mans)
    LinearLayout llTaskMans;
    @BindView(R.id.tv_work_task)
    TextView tvWorkTask;
    @BindView(R.id.tv_work_task_content)
    TextView tvWorkTaskContent;
    @BindView(R.id.tv_examine_workmans)
    TextView tvExamineWorkmans;
    @BindView(R.id.tv_examine_workmans_content)
    TextView tvExamineWorkmansContent;
    @BindView(R.id.ll_task_man)
    LinearLayout llTaskMan;
    Unbinder unbinder;
    private static Task task1;
    @BindView(R.id.tv_pic)
    TextView tvPic;
    @BindView(R.id.tv_text_pic)
    TextView tvTextPic;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.iv_takePic)
    ImageView ivTakePic;
    @BindView(R.id.rl_take_pic)
    RelativeLayout rlTakePic;
    @BindView(R.id.vi_divide)
    View viDivide;
    @BindView(R.id.tv_work_number)
    TextView tvWorkNumber;
    @BindView(R.id.tv_work_name)
    TextView tvWorkName;
    @BindView(R.id.tv_work_status)
    TextView tvWorkStatus;
    @BindView(R.id.rl_name_number_status)
    RelativeLayout rlNameNumberStatus;
    @BindView(R.id.vv_divide)
    View vvDivide;
    @BindView(R.id.tv_work_type)
    TextView tvWorkType;
    @BindView(R.id.tv_work_place)
    TextView tvWorkPlace;
    @BindView(R.id.tv_man_a)
    TextView tvManA;
    @BindView(R.id.tv_man_b)
    TextView tvManB;
    @BindView(R.id.tv_ticket)
    ImageView tvTicket;
    @BindView(R.id.mission_fragment)
    ScrollView missionFragment;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mission, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTimeStart.setText(MyDateUtils.getDateFromLong(task1.getTime_start(), MyDateUtils.date_Format));
        tvTimeStop.setText(MyDateUtils.getDateFromLong(task1.getTime_stop(), MyDateUtils.date_Format));

        tvTicket.setVisibility(View.GONE);
        tvWorkNumber.setText(task1.getNumber());
        tvWorkType.setText(task1.getType());
        tvWorkName.setText(task1.getName());
        tvWorkPlace.setText(task1.getLocation());
        tvManA.setText(task1.getMan_a());
        tvManB.setText(task1.getMan_b());
        tvWorkStatus.setText(task1.getStatus());
        String status = task1.getStatus();
        if (status.equals("未审核")) {
            tvWorkStatus.setBackground(getActivity().getApplicationContext().getDrawable(R.drawable.bg_status_green));
        } else if (status.equals("进行中")) {
            tvWorkStatus.setBackground(getActivity().getApplicationContext().getDrawable(R.drawable.bg_status_yellow));
        } else if (status.equals("已完成")) {
            tvWorkStatus.setBackground(getActivity().getApplicationContext().getDrawable(R.drawable.bg_status_blue));
        }

        rlTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //动态申请相机权限
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //如果没有权限，就申请，然后走回调方法，在回调成功的时候调用拍照方法
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    //如果有权限，进入拍照
                    if (!task1.getStatus().equals("已完成")) {
                        take_pic();
                    } else {
                        Toast.makeText(getActivity(), "任务已完成,不可操作", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        String path = task1.getPic();
        if (!TextUtils.isEmpty(path)) {
            ivPic.setImageURI(Uri.parse(path));
        }

        return view;
    }

    public static MissionFragment newInstance(Task task) {
        task1 = task;
        MissionFragment fragment = new MissionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("TASK", task);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private static final String TAG = "MissionFragment";

    private void take_pic() {
        Log.i(TAG, "现场拍照");
        File PHOTO_DIR = new File(BaseApplication.photoPath);
        // 创建照片的存储目录
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();
        }


        String filename = MyDateUtils.getCurTimeFormat(MyDateUtils.date_Format3);
        File out = new File(PHOTO_DIR, filename + ".jpg");

        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(out);
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            uri = FileProvider.getUriForFile(getActivity(), "hse", out);
            // 给目标应用一个临时授权
            imageCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(out);
        }

        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.8);
        BaseApplication.filename = filename;
        getActivity().startActivityForResult(imageCaptureIntent, 104);
    }

    public ImageView getImageView() {
        if (null == ivPic) {
            return null;
        }
        return ivPic;
    }
}
