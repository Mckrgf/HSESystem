package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Adapter.WorkListAdapter;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;

public class WorkListActivity extends BaseActivity implements WorkListAdapter.OnItemClickListener {

    @BindView(R.id.rl_work_list)
    RecyclerView rlWorkList;
    @BindView(R.id.bt_nav_1)
    Button btNav1;
    @BindView(R.id.bt_nav_2)
    Button btNav2;
    @BindView(R.id.bt_nav_3)
    Button btNav3;
    @BindView(R.id.bt_nav_4)
    Button btNav4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        //列表初始化
        LinearLayoutManager manager = new LinearLayoutManager(getMyApplication(), LinearLayoutManager.VERTICAL, false);
        rlWorkList.setLayoutManager(manager);
        rlWorkList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ArrayList works = fake_data();
        WorkListAdapter workListAdapter = new WorkListAdapter(works);
        workListAdapter.setItemClickListener(this);
        rlWorkList.setAdapter(workListAdapter);
    }

    @NonNull
    private ArrayList fake_data() {
        ArrayList<HashMap<String, Object>> works = new ArrayList();
        HashMap<String,Object> data = new HashMap();
        data.put("name", "xx区域动火作业");
        data.put("type", "施工作业");
        data.put("man_a", "施工负责人a");
        data.put("man_b", "安全负责人a");
        data.put("location", "xx区域");
        data.put("status", "未审核");
        works.add(data);

        data = new HashMap<>();
        data.put("name", "xx区域临时用电安全作业");
        data.put("type", "施工作业");
        data.put("man_a", "施工负责人b");
        data.put("man_b", "安全负责人b");
        data.put("location", "xx区域");
        data.put("status", "已审核");
        works.add(data);

        data = new HashMap<>();
        data.put("name", "xx区域高空安全作业");
        data.put("type", "施工作业");
        data.put("man_a", "施工负责人c");
        data.put("man_b", "安全负责人c");
        data.put("location", "xx区域");
        data.put("status", "已审核");
        works.add(data);

        data = new HashMap<>();
        data.put("name", "xx区域受限区域安全作业");
        data.put("type", "施工作业");
        data.put("man_a", "施工负责人d");
        data.put("man_b", "安全负责人d");
        data.put("location", "xx区域");
        data.put("status", "已审核");
        works.add(data);


        return works;
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.bt_nav_3, R.id.bt_nav_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_nav_1:
                finish();
                break;
            case R.id.bt_nav_2:
                break;
            case R.id.bt_nav_3:
                break;
            case R.id.bt_nav_4:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        if (position==0) {
            //进入审核页面
            Intent intent = new Intent(getMe(),ManExamineActivity.class);
            startActivity(intent);
        }else {
            //进入详情页面
            Intent intent = new Intent(getMe(),ManExamineActivity.class);
            startActivity(intent);
        }

    }
}
