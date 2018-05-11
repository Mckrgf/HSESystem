package www.supcon.com.hsesystem.Base;

import android.os.Environment;

/**
 * Created by yaobing on 2018/5/11.
 * Description 常用常数
 */

public class Constant {
    //图片路径
    public static final String photoPath = Environment.getExternalStorageDirectory() + "/HSE" + "/Pic";
    //拍照的文件名
    public static final String filename = "";
    //网络IP地址
    public static final String SERVER = "https://news-at.zhihu.com/api/4/news/latest";
}
