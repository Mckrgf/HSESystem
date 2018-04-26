package www.supcon.com.hsesystem.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;

public class TicketIMGActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_a)
    ImageView ivA;
    @BindView(R.id.iv_b)
    ImageView ivB;
    @BindView(R.id.iv_return)
    ImageView ivReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_img);
        ButterKnife.bind(this);
        tvTitle.setText("作业票");
        int type = getIntent().getIntExtra("type",0);
        switch (type) {
            case 1:
                ivA.setImageResource(R.mipmap.back_a);
                break;
            case 2:
                ivA.setImageResource(R.mipmap.back_a_a);
                break;
            case 3:
                ivA.setImageResource(R.mipmap.back_a_b);
                break;
            case 4:
                ivA.setImageResource(R.mipmap.back_a_c);
                break;
        }
        long status = getIntent().getLongExtra("status",0);
        if (new Date().getTime()>status) {
            ivB.setImageResource(R.mipmap.back_b_outdate);
        }else if (new Date().getTime()<status) {
            ivB.setImageResource(R.mipmap.back_b_indate);
        }
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
