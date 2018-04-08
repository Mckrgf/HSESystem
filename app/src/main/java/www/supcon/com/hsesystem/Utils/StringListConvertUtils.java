package www.supcon.com.hsesystem.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaobing on 2018/4/8.
 * Description xxx
 */

public class StringListConvertUtils {
    public static String listToString(List<String> list){
        if(list==null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for(String string :list) {
            if(first) {
                first=false;
            }else{
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    public static List StringToList(String string) {
        List list = new ArrayList();
        String[] arr = string.split(",");
        list = java.util.Arrays.asList(arr);
        return list;
    }
}
