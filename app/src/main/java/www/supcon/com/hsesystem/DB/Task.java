package www.supcon.com.hsesystem.DB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

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
    @Generated(hash = 591833109)
    public Task(Long id, String status, String name, String type, String man_a,
            String man_b, String location) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.type = type;
        this.man_a = man_a;
        this.man_b = man_b;
        this.location = location;
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


}
