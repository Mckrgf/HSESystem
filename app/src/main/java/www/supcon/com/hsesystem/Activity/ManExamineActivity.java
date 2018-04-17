package www.supcon.com.hsesystem.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class ManExamineActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
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
    private Task task;
    private int height;
    private boolean judge_status = true;//true为底部
    private boolean sign_status = true;//true为弹出

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
        int width = judgeLayout.getMeasuredWidth();
        ObjectAnimator.ofFloat(judgeLayout, "translationY", 0, height).setDuration(10).start();
    }

    private void initData() {
        tvWorkNumber.setText(task.getNumber());
        tvWorkType.setText(task.getType());
        tvWorkName.setText(task.getName());
        tvWorkPlace.setText(task.getLocation());
        tvManA.setText(task.getMan_a());
        tvManB.setText(task.getMan_b());
        tvWorkStatus.setText(task.getStatus());
        tvWorkTaskContent.setText(task.getWork_content());
        String time_start = MyDateUtils.getDateFromLong(task.getTime_start(), MyDateUtils.date_Format);
        String time_stop = MyDateUtils.getDateFromLong(task.getTime_stop(), MyDateUtils.date_Format);
        tvTimeStart.setText(time_start);
        tvTimeStop.setText(time_stop);

        cbA.setOnCheckedChangeListener(this);
        cbB.setOnCheckedChangeListener(this);
        cbC.setOnCheckedChangeListener(this);
        cbD.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.mongolia, R.id.tv_ensure, R.id.iv_return, R.id.tv_refuse, R.id.tv_judge, R.id.tv_sign})
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
                //负责关闭签名项
                close_judge();
                break;
            case R.id.mongolia:
                //蒙层负责关闭签名项
                close_judge();
                break;
            case R.id.tv_sign:
                //负责弹出关闭签名项
                break;
            case R.id.tv_refuse:
                finish();
                //审核通过页面
                task.setStatus("进行中");
                TaskDaoDBHelper.updateTask(task);
                Intent intent = new Intent(getMe(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void open_judge() {
        judge_status = !judge_status;
        llJudge.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(judgeLayout, "translationY", height, 0).setDuration(100).start();
        try {
            Thread.sleep(150);
            mongolia.setVisibility(View.VISIBLE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void close_judge() {
        judge_status = !judge_status;
        mongolia.setVisibility(View.GONE);
        llJudge.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(judgeLayout, "translationY", 0, height).setDuration(100).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int no = TaskDaoDBHelper.queryAll().size();
        tvTaskNo.setText(String.valueOf(no));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (cbA.isChecked() && cbB.isChecked() && cbC.isChecked() && cbD.isChecked()) {

        } else {

        }
    }
}
