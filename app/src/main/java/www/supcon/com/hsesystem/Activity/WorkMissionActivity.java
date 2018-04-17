package www.supcon.com.hsesystem.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.Fragment.CheckFragment;
import www.supcon.com.hsesystem.Fragment.MissionFragment;
import www.supcon.com.hsesystem.Fragment.VideoFragment;
import www.supcon.com.hsesystem.R;

public class WorkMissionActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tb_type)
    TabLayout tbType;
    @BindView(R.id.vp_mission)
    ViewPager vpMission;
    @BindView(R.id.bt_stop)
    TextView btStop;
    @BindView(R.id.bt_start)
    TextView btStart;
    @BindView(R.id.ll_charge)
    LinearLayout llCharge;
    @BindView(R.id.activity_work_mission)
    RelativeLayout activityWorkMission;
    private ArrayList<String> listTitles;
    private ArrayList<Fragment> fragments;
    private ArrayList<TextView> listTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_mission);
        ButterKnife.bind(this);

        listTitles = new ArrayList<>();
        fragments = new ArrayList<>();
        listTextViews = new ArrayList<>();

        listTitles.add("工单内容");
        listTitles.add("视频");
        listTitles.add("检查项");

        //把三个fragment都添加到列表中
        MissionFragment fragment = MissionFragment.newInstance("工单内容");
        fragments.add(fragment);
        VideoFragment videoFragment = VideoFragment.newInstance("视频");
        fragments.add(videoFragment);
        CheckFragment checkFragment = CheckFragment.newInstance("检查项");
        fragments.add(checkFragment);

        ///
        //mTabLayout.setTabMode(TabLayout.SCROLL_AXIS_HORIZONTAL);//设置tab模式，当前为系统默认模式
        for (int i = 0; i < listTitles.size(); i++) {
            tbType.addTab(tbType.newTab().setText(listTitles.get(i)));//添加tab选项
        }


        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return listTitles.get(position);
            }
        };
        vpMission.setAdapter(mAdapter);

        tbType.setupWithViewPager(vpMission);//将TabLayout和ViewPager关联起来。
        tbType.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

    }


}


//    NewsFragment news = new NewsFragment();
//    FragmentManager fm = getSupportFragmentManager();
//    FragmentTransaction transaction = fm.beginTransaction();
//        transaction.add(R.id.activity_work_mission, news).commit();
//
//        tbType.addTab(tbType.newTab().setText("工单内容"), true);//添加 Tab,默认选中
//        tbType.addTab(tbType.newTab().setText("视频"), false);//添加 Tab,默认不选中
//        tbType.addTab(tbType.newTab().setText("检测项"), false);//添加 Tab,默认不选中
//
//    MissionFragment f1 = new MissionFragment(1);
//    MissionFragment f2 = new MissionFragment(2);
//    MissionFragment f3 = new MissionFragment(3);
//
//    ArrayList list_title = new ArrayList<>();
//        list_title.add("工单内容");
//        list_title.add("视频");
//        list_title.add("检测项");
//    ArrayList list_fragment = new ArrayList<>();
//        list_fragment.add(f1);
//        list_fragment.add(f2);
//        list_fragment.add(f3);
//
//    VpMissionAdapter adapter = new VpMissionAdapter(getSupportFragmentManager(),list_fragment,list_title);
//        vpMission.setAdapter(adapter);
//        tbType.setupWithViewPager(vpMission);


