package www.supcon.com.hsesystem.DB;

import java.util.List;

import www.supcon.com.hsesystem.Base.BaseApplication;

/**
 * Created by yaobing on 2018/4/7.
 * Description xxx
 */

public class AirTestDaoDBHelper {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param airTest
     */
    public static void insertAirTest(AirTest airTest) {
        BaseApplication.getDaoInstant().getAirTestDao().insert(airTest);
    }

//    /**
//     * 删除数据
//     *
//     * @param id
//     */
//    public static void deleteAirTest(long id) {
//        BaseApplication.getDaoInstant().getAirTestDao().deleteByKey(id);
//    }

    /**
     * 更新数据
     *
     * @param airTest
     */
    public static void updateAirTest(AirTest airTest) {
        BaseApplication.getDaoInstant().getAirTestDao().update(airTest);
    }

    /**
     * 查询全部数据
     */
    public static List<AirTest> queryAll() {
        return BaseApplication.getDaoInstant().getAirTestDao().loadAll();
    }
}
