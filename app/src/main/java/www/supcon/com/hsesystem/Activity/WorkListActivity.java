package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.supcon.com.hsesystem.Adapter.WorkListAdapter;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;

public class WorkListActivity extends BaseActivity implements WorkListAdapter.OnItemClickListener {

    @BindView(R.id.rl_work_list)
    RecyclerView rlWorkList;
    @BindView(R.id.bt_nav_1)
    TextView btNav1;
    @BindView(R.id.bt_nav_2)
    TextView btNav2;
    @BindView(R.id.bt_nav_3)
    TextView btNav3;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.et_search)
    EditText etSearch;
    private List works;
    private WorkListAdapter workListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        ButterKnife.bind(this);
        tvTitle.setText("中控智能HSE-工作票列表页面");
        initView();
        initData();
    }

    private void initView() {
        btNav2.setTextColor(getResources().getColor(R.color.green_selected));
        etSearch.setSingleLine();
        etSearch.addTextChangedListener(new EditChangedListener());
    }

    private void initData() {
        //列表初始化
        LinearLayoutManager manager = new LinearLayoutManager(getMyApplication(), LinearLayoutManager.VERTICAL, false);
        rlWorkList.setLayoutManager(manager);
        rlWorkList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @NonNull
    private List fake_data() {
        List<Task> works = new ArrayList();
        works = TaskDaoDBHelper.queryAll();

        return works;
    }

    @Override
    protected void onResume() {
        super.onResume();
        works = fake_data();
        workListAdapter = new WorkListAdapter(works);
        workListAdapter.setItemClickListener(this);
        rlWorkList.setAdapter(workListAdapter);
        int no = TaskDaoDBHelper.queryAll().size();
        tvTaskNo.setText(String.valueOf(no));
    }

    @OnClick({R.id.bt_nav_1, R.id.bt_nav_2, R.id.bt_nav_3, R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_nav_1:
                finish();
                break;
            case R.id.bt_nav_2:
                break;
            case R.id.bt_nav_3:
                break;
            case R.id.iv_return:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Task task = (Task) works.get(position);
        if (task.getStatus().contains("未")) {
            //进入审核页面
            Intent intent = new Intent(getMe(), ManExamineActivity.class);
            intent.putExtra("TASK", task);
            startActivity(intent);
        } else {
            //进入详情页面
            Intent intent = new Intent(getMe(), WorkTicketActivity.class);
            intent.putExtra("TASK", task);
            startActivity(intent);
        }

    }

    private static final String TAG = "WorkListActivity";
    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG, s.toString());
            List<Task> tasks = TaskDaoDBHelper.queryAll();
            List<Task> tasks_check = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                String task = tasks.get(i).toString();
                if (task.contains(s)) {
                    tasks_check.add(tasks.get(i));
                }
            }
            workListAdapter.setData(tasks_check);

        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    }

}
