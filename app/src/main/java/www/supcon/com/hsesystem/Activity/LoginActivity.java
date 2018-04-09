package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.StringListConvertUtils;

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
    @BindView(R.id.bt_reset)
    Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String username = String.valueOf(etUsername.getText());
        String password = String.valueOf(etPassword.getText());
    }

    @OnClick({R.id.btn_confirm, R.id.ll_scan_login, R.id.ll_nfc_login, R.id.bt_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                fillDB();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_scan_login:
                Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_nfc_login:
                Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_reset:
                resetData();
                break;
        }
    }

    private void resetData() {
        List tasks = TaskDaoDBHelper.queryAll();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            task.setStatus("未审核");
            TaskDaoDBHelper.updateTask(task);
        }
    }

    /**
     * 检测数据库是否有数据，没有就添加四条数据TAsk
     */
    private void fillDB() {
        List list = TaskDaoDBHelper.queryAll();
        List<String> attentions = new ArrayList();
        attentions.add("动火设备内部构件清理干净");
        attentions.add("断开与动火设备相关的所有管线");
        attentions.add("动火点周围易燃物已经清除");
        attentions.add("高处作业已采取防火花飞溅措施");
        String attention = StringListConvertUtils.listToString(attentions);
        List<String> colleagues = new ArrayList<>();
        colleagues.add("张三");
        colleagues.add("李四");
        String colleague = StringListConvertUtils.listToString(colleagues);

        if (null == list | list.size() != 4) {
            Task task = new Task();
            task.setName("xx区域动火作业");
            task.setType("施工作业");
            task.setMan_a("施工负责人a");
            task.setMan_b("施工负责人b");
            task.setLocation("xx区域");
            task.setStatus("未审核");
            task.setNumber("DH_20180404001");
            task.setLat(30.180262);
            task.setLng(120.158726);
            task.setAttentions(attention);
            task.setWork_mans(colleague);
            task.setWork_content("基础开挖");
            TaskDaoDBHelper.insertTask(task);

            Task task2 = new Task();
            task2.setName("xx区域临时用电安全作业");
            task2.setType("施工作业");
            task2.setMan_a("施工负责人a");
            task2.setMan_b("施工负责人b");
            task2.setLocation("xx区域");
            task2.setStatus("未审核");
            task2.setNumber("YD_20180404001");
            task2.setLat(30.180262);
            task2.setLng(120.178726);
            task2.setAttentions(attention);
            task2.setWork_mans(colleague);
            task2.setWork_content("基础开挖");
            TaskDaoDBHelper.insertTask(task2);

            Task task3 = new Task();
            task3.setName("xx区域高空安全作业");
            task3.setType("施工作业");
            task3.setMan_a("施工负责人a");
            task3.setMan_b("施工负责人b");
            task3.setLocation("xx区域");
            task3.setStatus("未审核");
            task3.setNumber("GK_20180404001");
            task3.setLat(30.180262);
            task3.setLng(120.198726);
            task3.setAttentions(attention);
            task3.setWork_mans(colleague);
            task3.setWork_content("基础开挖");
            TaskDaoDBHelper.insertTask(task3);

            Task task4 = new Task();
            task4.setName("xx区域受限区域安全作业");
            task4.setType("施工作业");
            task4.setMan_a("施工负责人a");
            task4.setMan_b("施工负责人b");
            task4.setLocation("xx区域");
            task4.setStatus("未审核");
            task4.setNumber("SX_20180404001");
            task4.setLat(30.180262);
            task4.setLng(120.218726);
            task4.setAttentions(attention);
            task4.setWork_mans(colleague);
            task4.setWork_content("基础开挖");
            TaskDaoDBHelper.insertTask(task4);
        }
    }
}
