package www.supcon.com.hsesystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import www.supcon.com.hsesystem.Activity.TicketIMGActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/4/4.
 * Description xxx
 */

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.ViewHolder> implements View.OnClickListener {
    private List datas;
    private Context context;
    private OnItemClickListener mItemClickListener;

    @Override
    public void onClick(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    public void setData(List<Task> tasks_check) {
        datas = tasks_check;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public WorkListAdapter(List data,Context context) {
        this.datas = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item1, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //对数据进行操作
        final Task task = (Task) datas.get(position);

        holder.tv_work_number.setText(String.valueOf(task.getNumber()));
        holder.tv_work_name.setText(String.valueOf(task.getName()));
        holder.tv_work_type.setText(String.valueOf(task.getType()));
        holder.tv_man_a.setText(String.valueOf(task.getMan_a()));
        holder.tv_man_b.setText(String.valueOf(task.getMan_b()));
        holder.tv_work_place.setText(String.valueOf(task.getLocation()));
        holder.tv_work_status.setText(String.valueOf(task.getStatus()));
        String status = String.valueOf(task.getStatus());
        if (status.equals("未审核")){
            holder.tv_work_status.setBackground(context.getDrawable(R.drawable.bg_status_green));
            holder.tv_ticket.setVisibility(View.GONE);
        }else if (status.equals("进行中")) {
            holder.tv_work_status.setBackground(context.getDrawable(R.drawable.bg_status_yellow));
            holder.tv_ticket.setVisibility(View.VISIBLE);
        }else if (status.equals("已完成")) {
            holder.tv_work_status.setBackground(context.getDrawable(R.drawable.bg_status_blue));
            holder.tv_ticket.setVisibility(View.VISIBLE);
        }
        String name = task.getName();
        if (name.contains("A区")) {
            holder.tv_ticket.setImageResource(R.mipmap.ticket_security);
        }else if (name.contains("B区")) {
            holder.tv_ticket.setImageResource(R.mipmap.ticket_elec);
        }else if (name.contains("C区")) {
            holder.tv_ticket.setImageResource(R.mipmap.ticket_high);
        }else {
            holder.tv_ticket.setImageResource(R.mipmap.ticket_freedom);
        }
        holder.tv_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TicketIMGActivity.class);
                int type = 0;
                if (task.getType().contains("动火")) {
                    type = 1;
                }else if (task.getType().contains("用电")){
                    type = 2;
                }else if (task.getType().contains("高空")){
                    type = 3;
                }else if (task.getType().contains("受限")){
                    type = 4;
                }
                intent.putExtra("status",task.getTime_stop());
                intent.putExtra("type",type);
                context.startActivity(intent);
            }
        });
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
        TextView tv_work_number;
        TextView tv_work_name;
        TextView tv_work_type;
        TextView tv_man_a;
        TextView tv_man_b;
        TextView tv_work_place;
        TextView tv_work_status;
        ImageView tv_ticket;

        private ViewHolder(View view) {
            super(view);
            tv_work_number = view.findViewById(R.id.tv_work_number);
            tv_work_name = view.findViewById(R.id.tv_work_name);
            tv_work_type = view.findViewById(R.id.tv_work_type);
            tv_man_a = view.findViewById(R.id.tv_man_a);
            tv_man_b = view.findViewById(R.id.tv_man_b);
            tv_work_place = view.findViewById(R.id.tv_work_place);
            tv_work_status = view.findViewById(R.id.tv_work_status);
            tv_ticket = view.findViewById(R.id.tv_ticket);
        }
    }

}
