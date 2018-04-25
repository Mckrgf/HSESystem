package www.supcon.com.hsesystem.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.DB.Task;
import www.supcon.com.hsesystem.DB.TaskDaoDBHelper;
import www.supcon.com.hsesystem.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.tv_user_code)
    TextView tvUserCode;
    @BindView(R.id.bt_nav_1)
    TextView btNav1;
    @BindView(R.id.bt_nav_2)
    TextView btNav2;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;
    @BindView(R.id.rl_tasks)
    RelativeLayout rlTasks;
    @BindView(R.id.rl_warning)
    RelativeLayout rlWarning;
    @BindView(R.id.iv_hide)
    ImageView ivHide;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.rl_latest_task)
    RelativeLayout rlLatestTask;
    @BindView(R.id.tv_latest_task_name)
    TextView tvLatestTaskName;
    @BindView(R.id.bt_locate)
    ImageView btLocate;
    private AMap aMap;
    private ArrayList<Marker> marks;
    private Task task;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            double lat = aMapLocation.getLatitude();
            double lng = aMapLocation.getLongitude();
            Log.i(TAG,"定位成功");
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 10));
        }
    };
    private AMapLocationClientOption option;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initView();
        map.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        } else {
            initMap();
        }
        initMarker();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tvLatestTaskName.setText(task.getName());
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        btNav1.setTextColor(getResources().getColor(R.color.green_selected));
        ivMap.setImageResource(R.mipmap.map_checked);

        //设置全屏,并且控件在状态栏上也有显示,
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.title_color_trans));
        }
        //设置标题栏
        int statusBarHeight1 = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(title.getLayoutParams());
        lp.setMargins(0, statusBarHeight1, 0, 0);
        title.setLayoutParams(lp);

        rlMap.setOnClickListener(this);
        rlTasks.setOnClickListener(this);
        rlWarning.setOnClickListener(this);
        ivHide.setOnClickListener(this);
        rlLatestTask.setOnClickListener(this);
        btLocate.setOnClickListener(this);

    }

    /**
     * 初始化任务点信息,其余用户位置信息,危险区域信息
     */
    private void initMarker() {
        setZone();
        marks = new ArrayList<>();
        List<Task> taskList = TaskDaoDBHelper.queryAll();


        LatLng latLngA = new LatLng(32.298409, 119.856629);
        Marker markerA = aMap.addMarker(new MarkerOptions().position(latLngA));
        markerA.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.location_other)));
        marks.add(markerA);

        LatLng latLngB = new LatLng(32.29783, 119.855288);
        Marker markerB = aMap.addMarker(new MarkerOptions().position(latLngB));
        markerB.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.location_other)));
        marks.add(markerB);

        //任务信息
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            LatLng latLng1 = new LatLng(task.getLat(), task.getLng());
            Marker marker1 = aMap.addMarker(new MarkerOptions().position(latLng1));
            marker1.setTitle("作业票：" + task.getNumber());
            marker1.setSnippet(task.getStatus());
            marker1.setObject(task);
            String status = task.getStatus();
            if (status.equals("未审核")) {
                marker1.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.mark_task_green)));
            } else if (status.equals("进行中")) {
                marker1.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.mark_task_yellow)));
            } else if (status.equals("已完成")) {
                marker1.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.mark_task)));
            }
            marker1.showInfoWindow();

            marks.add(marker1);
        }
        //任务点击事件
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                if (marker.getTitle().contains("作业票")) {
                    Task task1 = (Task) marker.getObject();
                    String status = task1.getStatus();
                    View view = null;
                    if (status.equals("未审核")) {
                        view = View.inflate(getMe(), R.layout.info_window_green, null);
                    } else if (status.equals("进行中")) {
                        view = View.inflate(getMe(), R.layout.info_window_yellow, null);
                    } else if (status.equals("已完成")) {
                        view = View.inflate(getMe(), R.layout.info_window, null);
                    }
                    TextView textView = view.findViewById(R.id.tv_title);
                    TextView textView1 = view.findViewById(R.id.tv_content);
                    textView.setText(marker.getTitle());
                    textView1.setText(marker.getSnippet());
                    return view;
                } else {
                    return null;
                }

            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        //定位至指定位置
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(32.295121, 119.855541),//新的中心点坐标
                16, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));

        //危险区域绘制坐标
        aMap.addMarker(new MarkerOptions()
                .position(new LatLng(32.294907, 119.855773))
                .icon(BitmapDescriptorFactory.fromBitmap(getMyBitmap(""))));

    }

    protected Bitmap getMyBitmap(String pm_val) {
        Bitmap bitmap = BitmapDescriptorFactory.fromView(View.inflate(this, R.layout.dengerous, null)).getBitmap();
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight());
        Canvas canvas = new Canvas(bitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(44f);
        textPaint.setColor(getResources().getColor(R.color.white));
        canvas.drawText(pm_val, 17, 35, textPaint);// 设置bitmap上面的文字位置
        return bitmap;
    }

    private static final String TAG = "MainActivity";

    private void setZone() {
        // 定义多边形的5个点点坐标,暂定于高港地区
        LatLng latLng1 = new LatLng(32.296056, 119.854639);
        LatLng latLng2 = new LatLng(32.295453, 119.854172);
        LatLng latLng3 = new LatLng(32.293938, 119.856758);
        LatLng latLng4 = new LatLng(32.294523, 119.857246);
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(latLng1, latLng2, latLng3, latLng4);
        polygonOptions.strokeWidth(1) // 多边形的边框
                .strokeColor(getResources().getColor(R.color.black)) // 边框颜色
                .fillColor(getResources().getColor(R.color.red));   // 多边形的填充色
        aMap.addPolygon(polygonOptions);
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        //初始化地图控制器对象
        aMap = null;
        aMap = map.getMap();
        MyLocationStyle myLocationStyle;

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.location_me)));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        UiSettings uiSettings = aMap.getUiSettings();
//        uiSettings.setCompassEnabled(true);

//        aMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听


        //地图点击事件
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //点击地图后隐藏marker信息
                for (int i = 0; i < marks.size(); i++) {
                    Marker marker = marks.get(i);
                    if (null != marker) {
                        if (marker.isInfoWindowShown()) {
                            marker.hideInfoWindow();
                        }
                    }
                }
            }
        });

        //marker点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //点击marker,显示marker信息
                if (null != marker) {
                    if (!marker.isInfoWindowShown()) {
                        marker.showInfoWindow();
                    }
                }
                return true;//点击marker时不会定位到中心点
            }
        });

        //infowindow点击事件，根据不同的任务，进入任务详情界面
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Task task = (Task) marker.getObject();
                if (task.getStatus().contains("未")) {
                    //进入审核页面
                    Intent intent = new Intent(getMe(), ManExamineActivity.class);
                    intent.putExtra("TASK", task);
                    startActivity(intent);
                } else {
                    //进入详情页面
                    Intent intent = new Intent(getMe(), WorkMissionActivity.class);
                    intent.putExtra("TASK", task);
                    startActivity(intent);
                }
                Toast.makeText(getMyApplication(), "任务详情", Toast.LENGTH_SHORT).show();
            }
        });

        //自定义定位按钮所需变量

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //单次定位
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //超时时间
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initMap();

                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                initMarker();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
        int no = TaskDaoDBHelper.queryAll().size();
        tvTaskNo.setText(String.valueOf(no));
        initMap();
        initMarker();
        List<Task> tasks = TaskDaoDBHelper.queryAll();
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).getStatus().equals("已完成")) {
                task = tasks.get(i);
                initData();
                break;
            } else {
                //如果是 已完成
                if (i == tasks.size() - 1) {
                    tvLatestTaskName.setText("当前没有任务");
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.bt_locate:
                if (null != mLocationClient) {
                    mLocationClient.setLocationOption(option);
                    //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                    mLocationClient.stopLocation();
                    mLocationClient.startLocation();
                }
                break;
            case R.id.rl_tasks:
                Intent intent = new Intent(getMe(), WorkListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_warning:
                Toast.makeText(getMe(), "暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_hide:
                rlUserInfo.setVisibility(View.GONE);
                break;
            case R.id.rl_latest_task:
                if (task.getStatus().contains("未审核")) {
                    //进入审核页面
                    Intent intent1 = new Intent(getMe(), ManExamineActivity.class);
                    intent1.putExtra("TASK", task);
                    startActivity(intent1);
                } else {
                    //进入详情页面
                    Intent intent2 = new Intent(getMe(), WorkMissionActivity.class);
                    intent2.putExtra("TASK", task);
                    startActivity(intent2);
                }
                break;
        }
    }
}
