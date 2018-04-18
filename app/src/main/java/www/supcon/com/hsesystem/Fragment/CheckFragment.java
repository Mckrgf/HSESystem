package www.supcon.com.hsesystem.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.supcon.com.hsesystem.Adapter.AirTestListAdapter;
import www.supcon.com.hsesystem.DB.AirTest;
import www.supcon.com.hsesystem.DB.AirTestDaoDBHelper;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;

/**
 * Created by yaobing on 2018/4/17.
 * Description xxx
 */

@SuppressLint("ValidFragment")
public class CheckFragment extends Fragment {
    View view;
    private static final String KEY = "title";
    Unbinder unbinder;
    private static Task task1;
    @BindView(R.id.rv_air_test)
    RecyclerView rvAirTest;

    @BindView(R.id.tv_test_count)
    TextView tvTestCount;
    @BindView(R.id.tv_airtest_title)
    TextView tvAirtestTitle;
    @BindView(R.id.rl_air_test)
    RelativeLayout rlAirTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check, container, false);
        unbinder = ButterKnife.bind(this, view);
        count_time_test();
        rlAirTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return view;
    }

    public static CheckFragment newInstance(Task task) {
        task1 = task;
        CheckFragment fragment = new CheckFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //列表初始化
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvAirTest.setLayoutManager(manager);
        List<AirTest> airTests = AirTestDaoDBHelper.queryAllInOneTask(task1.getNumber());
        AirTestListAdapter airTestListAdapter = new AirTestListAdapter(airTests);
        rvAirTest.setAdapter(airTestListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private static final String TAG = "CheckFragment";

    /**
     * 气体检测倒计时处理
     */
    private void count_time_test() {
        long time_stop = new Date().getTime();
        int a = new Date().getHours();
        a = a + 1;//下一个整点
        try {
            String aa = MyDateUtils.getDateFromLong(new Date().getTime(), MyDateUtils.date_Format2);
            String bb = aa + " " + a + ":00:00";
            time_stop = MyDateUtils.getLongDateFromString(bb, MyDateUtils.date_Format);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long timeGetTime = new Date().getTime();//当前时间戳
        final long count = time_stop - timeGetTime;
        final String[] count_s = {MyDateUtils.formatDuringNodays(count)};
        tvTestCount.setText(count_s[0]);
        CountDownTimer timer = new CountDownTimer(count, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count_s[0] = MyDateUtils.formatDuringNodays(millisUntilFinished);
                String aaaa = String.valueOf(count_s[0].charAt(0));
                if (aaaa.equals(1)) {
                    Toast.makeText(getActivity(), "该检测了", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "该检测了");
                }
                tvTestCount.setText(count_s[0]);
            }

            @Override
            public void onFinish() {
                tvTestCount.setTextColor(Color.RED);
                tvTestCount.setText("已超时");
            }
        };
        timer.start();
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();

        View view = View.inflate(getActivity(), R.layout.air_test_dialog, null);
        // dialog.setView(view);// 将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText et_man = view
                .findViewById(R.id.et_man);
        final EditText et_info = view
                .findViewById(R.id.et_info);
        final EditText et_location = view
                .findViewById(R.id.et_location);

        Button btnOK = view.findViewById(R.id.bt_upload);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String info = String.valueOf(et_info.getText());
                String man = String.valueOf(et_man.getText());
                String location = String.valueOf(et_location.getText());
                if (!TextUtils.isEmpty(info) && !TextUtils.isEmpty(man) && !TextUtils.isEmpty(location)) {
                    AirTest airTest = new AirTest();
                    airTest.setInfo(String.valueOf(et_info.getText()));
                    airTest.setMan(String.valueOf(et_man.getText()));
                    airTest.setLocation(String.valueOf(et_location.getText()));
                    airTest.setNumber(task1.getNumber());
                    airTest.setTime_b(String.valueOf(new Date().getTime()));
                    AirTestDaoDBHelper.insertAirTest(airTest);
                    Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                    List<AirTest> airTests = AirTestDaoDBHelper.queryAllInOneTask(task1.getNumber());
                    AirTestListAdapter airTestListAdapter = new AirTestListAdapter(airTests);
                    rvAirTest.setAdapter(airTestListAdapter);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "所有内容均不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }
}
