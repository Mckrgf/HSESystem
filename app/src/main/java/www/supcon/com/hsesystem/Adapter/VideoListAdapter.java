package www.supcon.com.hsesystem.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import www.supcon.com.hsesystem.Activity.VideoPlayActivity;
import www.supcon.com.hsesystem.Activity.VideoRecordActivity;
import www.supcon.com.hsesystem.Activity.WorkMissionActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.Video;
import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/4/4.
 * Description xxx
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> implements View.OnClickListener {
    private List datas;
    private Context context;
    private OnItemClickListener mItemClickListener;
    private Task task;

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    public void setData(List<Video> videos) {
        this.datas = videos;
    }

    public void setTask(Task task1) {
        task = task1;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public VideoListAdapter(Context context, List data) {
        this.datas = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < datas.size()) {
            //对数据进行操作
            final Video video = (Video) datas.get(position);
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(video.getVideoUrl());
            Bitmap bitmap = media.getFrameAtTime();
            holder.iv_video.setImageBitmap(bitmap);
            holder.iv_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //视频列表,点击查看视频
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra("uri", video.getVideoUrl());
                    intent.putExtra("video", video);
                    context.startActivity(intent);
                }
            });

            holder.tv_video_time.setText(video.getDate());

            holder.itemView.setTag(position);
            holder.iv_add_video.setVisibility(View.GONE);
        } else {
            //多出来地一个
            //动态申请相机权限
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //如果没有权限，就申请，然后走回调方法，在回调成功的时候调用拍照方法
                ActivityCompat.requestPermissions(new WorkMissionActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                holder.iv_add_video.setVisibility(View.VISIBLE);
                holder.iv_add_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!task.getStatus().equals("已完成")) {
                            Intent intent2 = new Intent(context, VideoRecordActivity.class);
                            intent2.putExtra("task", task);
                            context.startActivity(intent2);
                        } else {
                            Toast.makeText(context, "任务已完成,不可操作", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }

    }


    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_video_time;
        ImageView iv_video;
        ImageView iv_add_video;

        private ViewHolder(View view) {
            super(view);
            tv_video_time = view.findViewById(R.id.tv_video_time);
            iv_video = view.findViewById(R.id.iv_video);
            iv_add_video = view.findViewById(R.id.iv_add_video);
        }
    }

}
