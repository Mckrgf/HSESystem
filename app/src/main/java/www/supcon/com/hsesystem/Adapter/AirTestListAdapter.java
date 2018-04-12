package www.supcon.com.hsesystem.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.supcon.com.hsesystem.DB.AirTest;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/4/4.
 * Description xxx
 */

public class AirTestListAdapter extends RecyclerView.Adapter<AirTestListAdapter.ViewHolder> implements View.OnClickListener {
    private List datas;
    private OnItemClickListener mItemClickListener;

    @Override
    public void onClick(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    public void setData(List<AirTest> airTests) {
        this.datas = airTests;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public AirTestListAdapter(List data) {
        this.datas = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.air_test_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //对数据进行操作
        AirTest airTest = (AirTest) datas.get(position);

        holder.tv_info.setText(String.valueOf(airTest.getInfo()));
        holder.tv_man.setText(String.valueOf(airTest.getMan()));
        holder.tv_test_location.setText(String.valueOf(airTest.getLocation()));
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
        TextView tv_info;
        TextView tv_man;
        TextView tv_test_location;

        private ViewHolder(View view) {
            super(view);
            tv_info = view.findViewById(R.id.tv_test_info);
            tv_man = view.findViewById(R.id.tv_test_man);
            tv_test_location = view.findViewById(R.id.tv_test_location);
        }
    }

}
