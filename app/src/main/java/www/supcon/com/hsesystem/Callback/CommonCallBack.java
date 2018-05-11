package www.supcon.com.hsesystem.Callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yaobing on 2018/3/11.
 * Description callback的基础类
 */

public abstract class CommonCallBack<T> extends AbsCallback<T> {
    private static final String TAG = "CommonCallBack";
    private boolean hasDialog = false;
    private ProgressDialog dialog;
    private Context context;

    public CommonCallBack(boolean hasDialog, Context context) {
        this.hasDialog = hasDialog;
        this.context = context;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (hasDialog) {
            showProgress();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        closeProgress();
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        String result = response.body().string();
        JSONObject jsonObject = new JSONObject(result);
        HashMap map = (HashMap) parseGson_map(String.valueOf(jsonObject));
        return (T) map;
    }

    /**
     * json转map
     *
     * @param results
     * @return
     * @throws Exception
     */
    public static Map<String, Object> parseGson_map(String results) throws Exception {
        String responseData = results;
        if (TextUtils.isEmpty(responseData)) return null;
        JSONTokener jsonParser = new JSONTokener(responseData);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
        HashMap<String, Object> json = new HashMap();
        json = jsonObjToMap(jsonObj);

        return json;
    }

    /**
     * JSONObject转map
     *
     * @param json
     * @return
     * @throws JSONException
     */
    private static HashMap<String, Object> jsonObjToMap(JSONObject json) throws JSONException {
        if (json == null) {
            HashMap<String, Object> map = new HashMap();
            return map;
        }
        HashMap<String, Object> map = new HashMap();
        Iterator keys = json.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                value = jsonObjToMap((JSONObject) value); //递归调用
            } else if (value instanceof JSONArray) {
                value = jsonArrayToArrayList((JSONArray) value); //递归调用
            }
            //字符串直接放进去
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONArray转ArrayList
     *
     * @param json
     * @return
     * @throws JSONException
     */
    private static ArrayList jsonArrayToArrayList(JSONArray json) throws JSONException {
        if (json == null
                || json.length() <= 0) {
            ArrayList al = new ArrayList();
            return al;
        }
        ArrayList al = new ArrayList();
        for (int i = 0; i < json.length(); i++) {
            Object obj = json.get(i);
            if (obj instanceof JSONObject) {
                al.add(jsonObjToMap((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                al.add(jsonArrayToArrayList((JSONArray) obj));
            } else {
                al.add(obj);
            }
        }
        return al;
    }

    private void showProgress() {
        if (null == dialog) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("加载中...");
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void closeProgress() {
        if (null!=dialog&&dialog.isShowing()) {
            dialog.cancel();
        }
    }
}
