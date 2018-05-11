package www.supcon.com.hsesystem.Utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.telephony.TelephonyManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {
    private AppUtils() {
    /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
                (Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String IMEI = telephonyManager.getDeviceId();
        return IMEI;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * [获取应用程序版本号信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 安装一个apk文件
     */
    public static void install(Context context, File uriFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载一个app
     */
    public static void uninstall(Context context, String packageName) {
        //通过程序的包名创建URI
        Uri packageURI = Uri.parse("package:" + packageName);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        //执行卸载程序
        context.startActivity(intent);
    }

    /**
     * 检查手机上是否安装了指定的软件
     */
    public static boolean isAvailable(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 检查手机上是否安装了指定的软件
     */
    public static boolean isAvailable(Context context, File file) {
        return isAvailable(context, getPackageName(context, file.getAbsolutePath()));
    }

    /**
     * 根据文件路径获取包名
     */
    public static String getPackageName(Context context, String filePath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo info = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return appInfo.packageName;  //得到安装包名称
        }
        return null;
    }

    /**
     * 检查某一进程是否存在
     *
     * @param name
     * @param context
     * @return
     */
    public static boolean isServiceAlive(String name, Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appList = am.getRunningAppProcesses();

        for (int i = 0; i < appList.size(); i++) {
            RunningAppProcessInfo rti = (RunningAppProcessInfo) appList.get(i);
            if (rti.processName.equals(name)) {
                return true;
            }
        }

        return false;
    }


}
