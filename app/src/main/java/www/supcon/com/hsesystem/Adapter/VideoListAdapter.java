package www.supcon.com.hsesystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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

    @Override
    public void onClick(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    public void setData(List<Video> videos) {
        this.datas = videos;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public VideoListAdapter(Context context,List data) {
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
        //对数据进行操作
        final Video video = (Video) datas.get(position);
        MediaMetadataRetriever media =new MediaMetadataRetriever();
        media.setDataSource(video.getVideoUrl());
        Bitmap bitmap = media.getFrameAtTime();
        holder.iv_video.setImageBitmap(bitmap);
        holder.iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //视频列表,点击查看视频
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                Uri data = Uri.parse(video.getVideoUrl());
                intent.setData(data);
                context.startActivity(intent);
            }
        });

        holder.tv_video_time.setText(video.getDate());

        holder.itemView.setTag(position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_video_time;
        ImageView iv_video;

        private ViewHolder(View view) {
            super(view);
            tv_video_time = view.findViewById(R.id.tv_video_time);
            iv_video = view.findViewById(R.id.iv_video);
        }
    }

}
