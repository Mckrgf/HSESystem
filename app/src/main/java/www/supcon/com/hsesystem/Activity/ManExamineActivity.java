package www.supcon.com.hsesystem.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class ManExamineActivity extends BaseActivity {
    @BindView(R.id.bt_nav_1)
    TextView btNav1;
    @BindView(R.id.bt_nav_2)
    TextView btNav2;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    @BindView(R.id.tv_work_task_content)
    TextView tvWorkTaskContent;
    @BindView(R.id.cb_a)
    CheckBox cbA;
    @BindView(R.id.cb_b)
    CheckBox cbB;
    @BindView(R.id.cb_c)
    CheckBox cbC;
    @BindView(R.id.cb_d)
    CheckBox cbD;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_stop)
    TextView tvTimeStop;
    @BindView(R.id.vv_divide)
    View vvDivide;
    @BindView(R.id.vi_divide)
    View viDivide;
    @BindView(R.id.judge_layout)
    RelativeLayout judgeLayout;
    @BindView(R.id.sign_layout)
    RelativeLayout signLayout;
    @BindView(R.id.tv_judge)
    TextView tvJudge;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.mongolia)
    View mongolia;
    @BindView(R.id.ll_judge)
    LinearLayout llJudge;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    @BindView(R.id.tv_ensure_sign)
    TextView tvEnsureSign;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_face_a)
    ImageView ivFaceA;
    @BindView(R.id.iv_face_b)
    ImageView ivFaceB;
    @BindView(R.id.iv_face_c)
    ImageView ivFaceC;
    @BindView(R.id.iv_face_d)
    ImageView ivFaceD;
    private Task task;
    private int height;
    private boolean judge_status = true;//true为底部
    private boolean sign_status = true;//true为弹出
    private int checks = 0;//检查项的勾选数目
    private boolean task_pass = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_examine);
        task = (Task) getIntent().getSerializableExtra("TASK");
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        tvTitle.setText("中控智能HSE-审核页面" + "\n" + "正在对" + task.getNumber() + "进行审核");
        vvDivide.setVisibility(View.VISIBLE);
        viDivide.setVisibility(View.GONE);

        //隐藏审核项和签名项
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        judgeLayout.measure(w, h);
        height = judgeLayout.getMeasuredHeight();
        ObjectAnimator.ofFloat(judgeLayout, "translationY", 0, height).setDuration(10).start();
        signLayout.measure(w, h);
        height = signLayout.getMeasuredHeight();
        ObjectAnimator.ofFloat(signLayout, "translationY", 0, height).setDuration(10).start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initData() {
        tvWorkNumber.setText(task.getNumber());
        tvWorkType.setText(task.getType());
        tvWorkName.setText(task.getName());
        tvWorkPlace.setText(task.getLocation());
        tvManA.setText(task.getMan_a());
        tvManB.setText(task.getMan_b());
        tvWorkStatus.setText(task.getStatus());
        tvWorkTaskContent.setText(task.getWork_content());
        String status = task.getStatus();
        if (status.equals("未审核")) {
            tvWorkStatus.setBackground(getApplicationContext().getDrawable(R.drawable.bg_status_green));
        } else if (status.equals("进行中")) {
            tvWorkStatus.setBackground(getApplicationContext().getDrawable(R.drawable.bg_status_yellow));
        } else if (status.equals("已完成")) {
            tvWorkStatus.setBackground(getApplicationContext().getDrawable(R.drawable.bg_status_blue));
        }
        String time_start = MyDateUtils.getDateFromLong(task.getTime_start(), MyDateUtils.date_Format);
        String time_stop = MyDateUtils.getDateFromLong(task.getTime_stop(), MyDateUtils.date_Format);
        tvTimeStart.setText(time_start);
        tvTimeStop.setText(time_stop);
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.iv_face_a, R.id.iv_face_b, R.id.iv_face_c, R.id.iv_face_d, R.id.mongolia, R.id.tv_ensure, R.id.iv_return, R.id.tv_refuse, R.id.tv_judge, R.id.tv_sign, R.id.tv_ensure_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_nav_1:
                //首页（地图的那个页面）
                Intent intent0 = new Intent(getMe(), MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.bt_nav_2:
                finish();
                break;
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_judge:
                //负责弹出关闭审核项
                open_judge();
                break;
            case R.id.tv_ensure:
                //负责关闭检查项
                close_judge();
                //确认检查项的填写情况
                judge_check();
                //关闭签名项
                close_sign();
                break;
            case R.id.tv_ensure_sign:
                //负责关闭签名项
                close_sign();
                break;
            case R.id.mongolia:
                //关闭签名项
                close_judge();
                //确认检查项的填写情况
                judge_check();
                //关闭签名项
                close_sign();
                break;
            case R.id.tv_sign:
                //负责弹出关闭签名项
                open_sign();
                break;
            case R.id.iv_face_a:
//                Intent intenta = new Intent(getMe(), FaceActivity.class);
//                startActivity(intenta);
                break;
            case R.id.iv_face_b:
//                Intent intentb = new Intent(getMe(), FaceActivity.class);
//                startActivity(intentb);
                break;
            case R.id.iv_face_c:
//                Intent intentc = new Intent(getMe(), FaceActivity.class);
//                startActivity(intentc);
                break;
            case R.id.iv_face_d:
//                Intent intentd = new Intent(getMe(), FaceActivity.class);
//                startActivity(intentd);
                break;
            case R.id.tv_refuse:
                if (task_pass) {
                    task.setStatus("进行中");
                    TaskDaoDBHelper.updateTask(task);
                    Intent intent = new Intent(getMe(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getMe(), "审核项未完成", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @SuppressLint("ResourceType")
    private void judge_check() {
        checks = 0;
        if (cbA.isChecked()) {
            checks++;
        }
        if (cbB.isChecked()) {
            checks++;
        }
        if (cbC.isChecked()) {
            checks++;
        }
        if (cbD.isChecked()) {
            checks++;
        }
        tvJudge.setText("审核项(" + checks + "/4)");
        if (checks == 4) {
            tvRefuse.setText("审核通过");
            tvRefuse.setBackgroundColor(getResources().getColor(R.color.green_text));
            task_pass = true;
        } else {
            tvRefuse.setText("审核拒绝");
            tvRefuse.setBackgroundColor(getResources().getColor(R.color.red));
            task_pass = false;
        }
    }

    /**
     * 打开审核
     */
    private void open_judge() {
        if (judge_status) {
            judge_status = !judge_status;
            llJudge.setVisibility(View.GONE);
            ObjectAnimator.ofFloat(judgeLayout, "translationY", height, 0).setDuration(100).start();
            try {
                Thread.sleep(150);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mongolia.getLayoutParams();
                params.addRule(RelativeLayout.ABOVE, R.id.judge_layout);
                mongolia.setVisibility(View.VISIBLE);
                mongolia.setVisibility(View.VISIBLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭审核
     */
    private void close_judge() {
        if (!judge_status) {
            judge_status = !judge_status;
            mongolia.setVisibility(View.GONE);
            llJudge.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(judgeLayout, "translationY", 0, height).setDuration(100).start();
        }
    }

    /**
     * 打开签名
     */
    private void open_sign() {
        if (sign_status) {
            llJudge.setVisibility(View.GONE);
            ObjectAnimator.ofFloat(signLayout, "translationY", height, 0).setDuration(100).start();
            try {
                Thread.sleep(150);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mongolia.getLayoutParams();
                params.addRule(RelativeLayout.ABOVE, R.id.sign_layout);
                mongolia.setVisibility(View.VISIBLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sign_status = !sign_status;
        }


    }

    /**
     * 关闭签名
     */
    private void close_sign() {
        if (!sign_status) {
            sign_status = !sign_status;
            mongolia.setVisibility(View.GONE);
            llJudge.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(signLayout, "translationY", 0, height).setDuration(100).start();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int no = TaskDaoDBHelper.queryAll().size();
        tvTaskNo.setText(String.valueOf(no));
    }

}
