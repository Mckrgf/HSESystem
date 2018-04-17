package www.supcon.com.hsesystem.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/4/17.
 * Description xxx
 */

@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment {
    View view;
    private static final String KEY = "title";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);

        return view;
    }

    public static VideoFragment newInstance(String s) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, s);
        fragment.setArguments(bundle);
        return fragment;
    }
}
