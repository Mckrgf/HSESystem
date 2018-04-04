package www.supcon.com.hsesystem.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/4/4.
 * Description xxx
 */

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList datas;
    private OnItemClickListener mItemClickListener;

    @Override
    public void onClick(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public WorkListAdapter(ArrayList data) {
        this.datas = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //对数据进行操作
        HashMap data = (HashMap) datas.get(position);

        holder.tv_work_name.setText(String.valueOf(data.get("name")));
        holder.tv_work_type.setText(String.valueOf(data.get("type")));
        holder.tv_man_a.setText(String.valueOf(data.get("man_a")));
        holder.tv_man_b.setText(String.valueOf(data.get("man_b")));
        holder.tv_work_place.setText(String.valueOf(data.get("location")));
        holder.tv_work_status.setText(String.valueOf(data.get("status")));
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
        TextView tv_work_name;
        TextView tv_work_type;
        TextView tv_man_a;
        TextView tv_man_b;
        TextView tv_work_place;
        TextView tv_work_status;

        private ViewHolder(View view) {
            super(view);
            tv_work_name = view.findViewById(R.id.tv_work_name);
            tv_work_type = view.findViewById(R.id.tv_work_type);
            tv_man_a = view.findViewById(R.id.tv_man_a);
            tv_man_b = view.findViewById(R.id.tv_man_b);
            tv_work_place = view.findViewById(R.id.tv_work_place);
            tv_work_status = view.findViewById(R.id.tv_work_status);
        }
    }

}
