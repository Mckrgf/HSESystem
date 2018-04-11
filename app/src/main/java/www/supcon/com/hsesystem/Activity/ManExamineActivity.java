package www.supcon.com.hsesystem.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

public class ManExamineActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.bt_pass)
    Button btPass;
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
    @BindView(R.id.cb_a)
    CheckBox cbA;
    @BindView(R.id.cb_b)
    CheckBox cbB;
    @BindView(R.id.cb_c)
    CheckBox cbC;
    @BindView(R.id.cb_d)
    CheckBox cbD;
    @BindView(R.id.bt_refuse)
    Button btRefuse;
    @BindView(R.id.tv_examine_time_content)
    TextView tvExamineTimeContent;
    private Task task;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_examine);
        task = (Task) getIntent().getSerializableExtra("TASK");
        ButterKnife.bind(this);
        tvTitle.setText("中控智能HSE-审核页面" + "\n" + "正在对" + task.getNumber() + "进行审核");
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
        String time_start = MyDateUtils.getDateFromLong(task.getTime_start(), MyDateUtils.date_Format);
        String time_stop = MyDateUtils.getDateFromLong(task.getTime_stop(), MyDateUtils.date_Format);
        tvExamineTimeContent.setText(time_start + " 至 " + time_stop);

        cbA.setOnCheckedChangeListener(this);
        cbB.setOnCheckedChangeListener(this);
        cbC.setOnCheckedChangeListener(this);
        cbD.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.bt_pass, R.id.iv_return, R.id.bt_refuse})
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
            case R.id.bt_refuse:
                finish();
                break;
            case R.id.bt_pass:
                //审核通过页面
                task.setStatus("进行中");
                TaskDaoDBHelper.updateTask(task);
                Intent intent = new Intent(getMe(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
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
            btPass.setBackgroundColor(getResources().getColor(R.color.white));
            btPass.setClickable(true);
        } else {
            btPass.setBackgroundColor(getResources().getColor(R.color.gray));
            btPass.setClickable(false);
        }
    }
}
