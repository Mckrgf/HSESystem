package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;

public class ManExamineActivity extends BaseActivity {
    private static final String TAG = "ManExamineActivity";
    @BindView(R.id.bt_pass)
    Button btPass;
    @BindView(R.id.bt_nav_1)
    Button btNav1;
    @BindView(R.id.bt_nav_2)
    Button btNav2;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_examine);
        task = (Task) getIntent().getSerializableExtra("TASK");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.bt_pass})
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
            case R.id.bt_pass:
                task.setStatus("进行中");
                TaskDaoDBHelper.updateTask(task);
                Intent intent = new Intent(getMe(), WorkTicketActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int no = TaskDaoDBHelper.queryAll().size();
        tvTaskNo.setText(String.valueOf(no));
    }
}