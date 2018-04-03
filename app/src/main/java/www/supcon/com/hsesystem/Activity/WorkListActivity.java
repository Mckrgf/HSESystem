package www.supcon.com.hsesystem.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.supcon.com.hsesystem.Adapter.WorkListAdapter;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;

public class WorkListActivity extends BaseActivity {

    @BindView(R.id.rl_work_list)
    RecyclerView rlWorkList;

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

        ArrayList works = new ArrayList();
        works.add(1);
        works.add(2);
        works.add(3);
        works.add(4);
        WorkListAdapter workListAdapter = new WorkListAdapter(works);
        rlWorkList.setAdapter(workListAdapter);
    }
}
