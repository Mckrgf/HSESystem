package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;

public class ManExamineActivity extends BaseActivity {
    private static final String TAG = "ManExamineActivity";
    @BindView(R.id.bt_pass)
    Button btPass;
    @BindView(R.id.bt_nav_1)
    Button btNav1;
    @BindView(R.id.bt_nav_2)
    Button btNav2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_examine);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2,R.id.bt_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_nav_1:
                //首页（地图的那个页面）
                Intent intent0 = new Intent(getMe(),MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.bt_nav_2:
                finish();
                break;
            case R.id.bt_pass:
                Intent intent = new Intent(getMe(), WorkTicketActivity.class);
                startActivity(intent);
                break;
        }
    }
}
