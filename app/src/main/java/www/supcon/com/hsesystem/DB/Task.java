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
public class Task implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long id;
    private String status;//未审核，进行中，已完成
    private String name;//任务名称
    private String type;//任务类型，火，电，空间等
    private String man_a;//审核人a
    private String man_b;//审核人b
    private String location;//位置信息
    private String  number ;//工单编号
    private double  lat ;//经纬度
    private double  lng ;//经纬度

    private String work_content;//工作内容
    private String work_mans;//工作组成员
    private String attentions;//注意事项
    private String pic;//项目所属的作业票图片
    @Generated(hash = 206082121)
    public Task(Long id, String status, String name, String type, String man_a,
            String man_b, String location, String number, double lat, double lng,
            String work_content, String work_mans, String attentions, String pic) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.type = type;
        this.man_a = man_a;
        this.man_b = man_b;
        this.location = location;
        this.number = number;
        this.lat = lat;
        this.lng = lng;
        this.work_content = work_content;
        this.work_mans = work_mans;
        this.attentions = attentions;
        this.pic = pic;
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


}
