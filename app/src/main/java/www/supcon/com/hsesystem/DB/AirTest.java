package www.supcon.com.hsesystem.DB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yaobing on 2018/4/11.
 * Description xxx
 */
@Entity
public class AirTest  implements Serializable {
    private static final long serialVersionUID = 2L;
    @Id(autoincrement = true)
    private Long id;
    private String time_a;//预定检测时间
    private String time_b;//实际检测时间
    private String man;//检测人
    private String info;//监测信息
    private String number;//所属工单编号
    private String location;//所属工单编号
    @Generated(hash = 265912834)
    public AirTest(Long id, String time_a, String time_b, String man, String info,
            String number, String location) {
        this.id = id;
        this.time_a = time_a;
        this.time_b = time_b;
        this.man = man;
        this.info = info;
        this.number = number;
        this.location = location;
    }
    @Generated(hash = 563603278)
    public AirTest() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime_a() {
        return this.time_a;
    }
    public void setTime_a(String time_a) {
        this.time_a = time_a;
    }
    public String getTime_b() {
        return this.time_b;
    }
    public void setTime_b(String time_b) {
        this.time_b = time_b;
    }
    public String getMan() {
        return this.man;
    }
    public void setMan(String man) {
        this.man = man;
    }
    public String getInfo() {
        return this.info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
