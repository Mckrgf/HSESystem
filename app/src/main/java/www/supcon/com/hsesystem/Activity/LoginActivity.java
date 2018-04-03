package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_confirm)
    ImageView btnConfirm;
    @BindView(R.id.ll_scan_login)
    LinearLayout llScanLogin;
    @BindView(R.id.tv_nfc_login)
    TextView tvNfcLogin;
    @BindView(R.id.ll_nfc_login)
    LinearLayout llNfcLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String username = String.valueOf(etUsername.getText());
        String password = String.valueOf(etPassword.getText());
    }

    @OnClick({R.id.btn_confirm, R.id.ll_scan_login, R.id.ll_nfc_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_scan_login:
                Toast.makeText(this,"暂未开放",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_nfc_login:
                Toast.makeText(this,"暂未开放",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
