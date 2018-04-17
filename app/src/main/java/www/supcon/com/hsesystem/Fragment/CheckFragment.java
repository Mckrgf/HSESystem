package www.supcon.com.hsesystem.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/4/17.
 * Description xxx
 */

@SuppressLint("ValidFragment")
public class CheckFragment extends Fragment {
    View view;
    private static final String KEY = "title";
    @BindView(R.id.cb_a)
    CheckBox cbA;
    @BindView(R.id.cb_b)
    CheckBox cbB;
    @BindView(R.id.cb_c)
    CheckBox cbC;
    @BindView(R.id.cb_d)
    CheckBox cbD;
    @BindView(R.id.rg_inspection)
    LinearLayout rgInspection;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.judge_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        cbA.setChecked(true);
        cbB.setChecked(true);
        cbC.setChecked(true);
        cbD.setChecked(true);
        cbA.setClickable(false);
        cbB.setClickable(false);
        cbC.setClickable(false);
        cbD.setClickable(false);
        llMore.setVisibility(View.GONE);
        tvEnsure.setVisibility(View.GONE);
        return view;
    }

    public static CheckFragment newInstance(String s) {
        CheckFragment fragment = new CheckFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
