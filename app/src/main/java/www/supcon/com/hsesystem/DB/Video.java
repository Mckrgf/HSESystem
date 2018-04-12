package www.supcon.com.hsesystem.DB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yaobing on 2018/4/12.
 * Description xxx
 */
@Entity
public class Video implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long id;
    private String videoUrl;
    private String number;
    private String status;//未上传 已上传 上传失败
    private String date;//拍摄时间
    @Generated(hash = 2075295141)
    public Video(Long id, String videoUrl, String number, String status,
            String date) {
        this.id = id;
        this.videoUrl = videoUrl;
        this.number = number;
        this.status = status;
        this.date = date;
    }
    @Generated(hash = 237528154)
    public Video() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVideoUrl() {
        return this.videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
