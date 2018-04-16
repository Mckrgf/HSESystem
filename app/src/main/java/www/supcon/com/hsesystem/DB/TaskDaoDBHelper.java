package www.supcon.com.hsesystem.DB;

import java.util.List;

import www.supcon.com.hsesystem.Base.BaseApplication;

/**
 * Created by yaobing on 2018/4/7.
 * Description xxx
 */

public class TaskDaoDBHelper {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param task
     */
    public static void insertTask(Task task) {
        BaseApplication.getDaoInstant().getTaskDao().insert(task);
    }

    /**
     * 删除数据
     *
     * @param number
     */
    public static Task queryTask(String number) {
        return BaseApplication.getDaoInstant().getTaskDao().queryBuilder().where(TaskDao.Properties.Number.eq(number)).unique();
    }

    /**
     * 更新数据
     *
     * @param task
     */
    public static void updateTask(Task task) {
        BaseApplication.getDaoInstant().getTaskDao().update(task);
    }

    /**
     * 查询全部数据
     */
    public static List<Task> queryAll() {
        return BaseApplication.getDaoInstant().getTaskDao().loadAll();
    }


}
