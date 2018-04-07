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
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;

public class WorkTicketActivity extends BaseActivity {

    @BindView(R.id.bt_pass)
    Button btPass;
    @BindView(R.id.bt_nav_1)
    Button btNav1;
    @BindView(R.id.bt_nav_2)
    Button btNav2;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_ticket);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_pass, R.id.bt_nav_1, R.id.bt_nav_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_pass:
                break;
            case R.id.bt_nav_1:
                finish();
                break;
            case R.id.bt_nav_2:
                Intent intent = new Intent(getMe(), WorkListActivity.class);
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
