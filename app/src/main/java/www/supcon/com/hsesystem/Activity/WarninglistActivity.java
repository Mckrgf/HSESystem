package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;

public class WarninglistActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.bt_nav_1)
    TextView btNav1;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.iv_task)
    ImageView ivTask;
    @BindView(R.id.bt_nav_2)
    TextView btNav2;
    @BindView(R.id.rl_tasks)
    RelativeLayout rlTasks;
    @BindView(R.id.iv_warning)
    ImageView ivWarning;
    @BindView(R.id.bt_nav_3)
    TextView btNav3;
    @BindView(R.id.rl_warning)
    RelativeLayout rlWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warninglist);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlMap.setOnClickListener(this);
        rlTasks.setOnClickListener(this);
        rlWarning.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_map:
                Intent intent1 = new Intent(getMe(),MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_tasks:
                Intent intent = new Intent(getMe(), WorkListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
