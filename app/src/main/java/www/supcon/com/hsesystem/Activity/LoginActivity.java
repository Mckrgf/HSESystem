package www.supcon.com.hsesystem.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;
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
    @BindView(R.id.iv_lock)
    ImageView ivLock;
    private long time_start;
    private long time_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String username = String.valueOf(etUsername.getText());
        String password = String.valueOf(etPassword.getText());
        if (ContextCompat.checkSelfPermission(getMe(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //如果没有权限，就申请，然后走回调方法，在回调成功的时候调用拍照方法
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @OnClick({R.id.btn_confirm, R.id.ll_scan_login, R.id.ll_nfc_login, R.id.bt_reset, R.id.iv_lock})
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
            case R.id.iv_lock:
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
        String date_Format = "yyyy-MM-dd HH:mm:ss";
        try {
            time_start = MyDateUtils.getLongDateFromString("2018-04-19 09:00:00", date_Format);
            time_stop = MyDateUtils.getLongDateFromString("2018-04-19 12:00:00", date_Format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (null == list | list.size() != 4) {
            Task task = new Task();
            task.setName("A区域动火作业");
            task.setType("动火作业");
            task.setMan_a("施工负责人a");
            task.setMan_b("安全负责人b");
            task.setLocation("A区域");
            task.setStatus("未审核");
            task.setNumber("DH_20180404001");
            task.setLat(32.296232);
            task.setLng(119.851951);
            task.setAttentions(attention);
            task.setWork_mans(colleague);
            task.setWork_content("基础开挖");
            task.setTime_start(time_start);
            task.setTime_stop(time_stop);
            task.setUnit("T441232432142224678");
            TaskDaoDBHelper.insertTask(task);

            Task task2 = new Task();
            task2.setName("B区域临时用电安全作业");
            task2.setType("用电作业");
            task2.setMan_a("施工负责人c");
            task2.setMan_b("安全负责人d");
            task2.setLocation("B区域");
            task2.setStatus("未审核");
            task2.setNumber("YD_20180404001");
            task2.setLat(32.296957);
            task2.setLng(119.852509);
            task2.setAttentions(attention);
            task2.setWork_mans(colleague);
            task2.setWork_content("接电,接b区3号电箱,用电设备为1号压缩机,工作电压220V");
            task2.setTime_start(time_start);
            task2.setTime_stop(time_stop);
            task2.setUnit("T441900197811201978");
            TaskDaoDBHelper.insertTask(task2);

            Task task3 = new Task();
            task3.setName("C区域高空安全作业");
            task3.setType("高空作业");
            task3.setMan_a("施工负责人e");
            task3.setMan_b("安全负责人f");
            task3.setLocation("C区域");
            task3.setStatus("未审核");
            task3.setNumber("GK_20180404001");
            task3.setLat(32.292731);
            task3.setLng(119.857895);
            task3.setAttentions(attention);
            task3.setWork_mans(colleague);
            task3.setWork_content("吊装");
            task3.setTime_start(time_start);
            task3.setTime_stop(time_stop);
            task3.setUnit("T620422198706216711");
            TaskDaoDBHelper.insertTask(task3);

            Task task4 = new Task();
            task4.setName("D区域受限区域安全作业");
            task4.setType("受限作业");
            task4.setMan_a("施工负责人g");
            task4.setMan_b("安全负责人h");
            task4.setLocation("D区域");
            task4.setStatus("未审核");
            task4.setNumber("SX_20180404001");
            task4.setLat(32.295198);
            task4.setLng(119.858474);
            task4.setAttentions(attention);
            task4.setWork_mans(colleague);
            task4.setWork_content("塔内清理,原有介质为水泥.注意危险因素(窒息/爆炸/吸入性粉尘)");
            task4.setTime_start(time_start);
            task4.setTime_stop(time_stop);
            task3.setUnit("T110221985051367123");
            TaskDaoDBHelper.insertTask(task4);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
