package www.supcon.com.hsesystem.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.supcon.com.hsesystem.Adapter.VideoListAdapter;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.Video;
import www.supcon.com.hsesystem.DB.VideoDaoDBHelper;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.MyDateUtils;
import www.supcon.com.hsesystem.View.MyDecoration;

/**
 * Created by yaobing on 2018/4/17.
 * Description xxx
 */

@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment {
    View view;
    private static final String KEY = "title";
    Unbinder unbinder;
    @BindView(R.id.ll_videos)
    RecyclerView llVideos;
    private static Task task1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        //设置为GridView
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        llVideos.setLayoutManager(manager);

        List<Video> videos = VideoDaoDBHelper.queryAllInOneTask(task1.getNumber());
        VideoListAdapter videoListAdapter = new VideoListAdapter(getActivity(), videos);
        videoListAdapter.setTask(task1);
        llVideos.setAdapter(videoListAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //设置为GridView
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        llVideos.setLayoutManager(manager);

        List<Video> videos = VideoDaoDBHelper.queryAllInOneTask(task1.getNumber());
        VideoListAdapter videoListAdapter = new VideoListAdapter(getActivity(), videos);
        videoListAdapter.setTask(task1);
        llVideos.setAdapter(videoListAdapter);
    }

    public static VideoFragment newInstance(Task task) {
        task1 = task;
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private static final String TAG = "VideoFragment";


}
