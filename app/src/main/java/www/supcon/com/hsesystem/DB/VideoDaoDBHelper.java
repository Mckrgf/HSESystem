package www.supcon.com.hsesystem.DB;

import java.util.List;

import www.supcon.com.hsesystem.Base.BaseApplication;

/**
 * Created by yaobing on 2018/4/7.
 * Description xxx
 */

public class VideoDaoDBHelper {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param video
     */
    public static void insertVideo(Video video) {
        BaseApplication.getDaoInstant().getVideoDao().insert(video);
    }

//    /**
//     * 删除数据
//     *
//     * @param id
//     */
//    public static void deleteVideo(long id) {
//        BaseApplication.getDaoInstant().getVideoDao().deleteByKey(id);
//    }

    /**
     * 更新数据
     *
     * @param video
     */
    public static void updateVideo(Video video) {
        BaseApplication.getDaoInstant().getVideoDao().update(video);
    }

    /**
     * 查询全部数据
     */
    public static List<Video> queryAll() {
        return BaseApplication.getDaoInstant().getVideoDao().loadAll();
    }

    /**
     * 查询全部数据
     */
    public static List<Video> queryAllInOneTask(String taskId) {
        return BaseApplication.getDaoInstant().getVideoDao().queryBuilder().where(VideoDao.Properties.Number.eq(taskId)).list();
    }
}
