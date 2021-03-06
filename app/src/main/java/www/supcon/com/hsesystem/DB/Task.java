package www.supcon.com.hsesystem.DB;

import android.net.Uri;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaobing on 2018/4/7.
 * Description xxx
 */

@Entity
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long id;
    private String status;//未审核，进行中，已完成
    private String name;//任务名称
    private String type;//任务类型，火，电，空间等
    private String man_a;//审核人a
    private String man_b;//审核人b
    private String location;//位置信息
    private String number;//工单编号

    private long time_start;//开始时间
    private long time_stop;//结束时间
    private double lat;//经纬度
    private double lng;//经纬度

    private String work_content;//工作内容
    private String work_mans;//工作组成员
    private String attentions;//注意事项
    private String pic;//项目所属的作业票图片
    private String worK_content;//项目所属的作业票图片


    //新增字段
    private String unit;//证件号
    private String man_c;//申请人
    private String device_detail;//用电设备及功率
    private String power_in;//电源接入点
    private String elec_v;//工作电压
    private String work_unit;//施工单位`

    @Generated(hash = 1422957852)
    public Task(Long id, String status, String name, String type, String man_a,
            String man_b, String location, String number, long time_start,
            long time_stop, double lat, double lng, String work_content,
            String work_mans, String attentions, String pic, String worK_content,
            String unit, String man_c, String device_detail, String power_in,
            String elec_v, String work_unit) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.type = type;
        this.man_a = man_a;
        this.man_b = man_b;
        this.location = location;
        this.number = number;
        this.time_start = time_start;
        this.time_stop = time_stop;
        this.lat = lat;
        this.lng = lng;
        this.work_content = work_content;
        this.work_mans = work_mans;
        this.attentions = attentions;
        this.pic = pic;
        this.worK_content = worK_content;
        this.unit = unit;
        this.man_c = man_c;
        this.device_detail = device_detail;
        this.power_in = power_in;
        this.elec_v = elec_v;
        this.work_unit = work_unit;
    }

    @Generated(hash = 733837707)
    public Task() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMan_a() {
        return this.man_a;
    }

    public void setMan_a(String man_a) {
        this.man_a = man_a;
    }

    public String getMan_b() {
        return this.man_b;
    }

    public void setMan_b(String man_b) {
        this.man_b = man_b;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getWork_content() {
        return this.work_content;
    }

    public void setWork_content(String work_content) {
        this.work_content = work_content;
    }

    public String getWork_mans() {
        return this.work_mans;
    }

    public void setWork_mans(String work_mans) {
        this.work_mans = work_mans;
    }

    public String getAttentions() {
        return this.attentions;
    }

    public void setAttentions(String attentions) {
        this.attentions = attentions;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getTime_start() {
        return this.time_start;
    }

    public void setTime_start(long time_start) {
        this.time_start = time_start;
    }

    public long getTime_stop() {
        return this.time_stop;
    }

    public void setTime_stop(long time_stop) {
        this.time_stop = time_stop;
    }

    //重写toString方法
    public String toString() {

        return getAttentions() +
                getStatus() +
                getTime_start() +
                getNumber() +
                getLocation() +
                getMan_a() +
                getMan_b() +
                getName() +
                getPic() +
                getType() +
                getWork_content() +
                getWork_mans() +
                getId() +
                getTime_stop();

    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMan_c() {
        return this.man_c;
    }

    public void setMan_c(String man_c) {
        this.man_c = man_c;
    }

    public String getDevice_detail() {
        return this.device_detail;
    }

    public void setDevice_detail(String device_detail) {
        this.device_detail = device_detail;
    }

    public String getPower_in() {
        return this.power_in;
    }

    public void setPower_in(String power_in) {
        this.power_in = power_in;
    }

    public String getElec_v() {
        return this.elec_v;
    }

    public void setElec_v(String elec_v) {
        this.elec_v = elec_v;
    }

    public String getWork_unit() {
        return this.work_unit;
    }

    public void setWork_unit(String work_unit) {
        this.work_unit = work_unit;
    }

    public String getWorK_content() {
        return this.worK_content;
    }

    public void setWorK_content(String worK_content) {
        this.worK_content = worK_content;
    }
}
