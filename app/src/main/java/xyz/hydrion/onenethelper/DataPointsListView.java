package xyz.hydrion.onenethelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.hydrion.onenethelper.net.model.DataPointsModel;

/**
 * @Description 显示数据流的历史数据点的ListView,使用时需要调用setDataStream()方法
 *              设置数据流的相关信息。设置完成后组件会自动异步加载数据，支持上拉加载下
 *              一页。
 * @Author Hydrion
 * @CreateDate 2018/7/25
 * @Version 1.0
 */
public class DataPointsListView extends ListView implements OnScrollRefreshListener.OnLoadDataListener{

    private final static int COUNT = 20;    //分页查询每一页的记录数量
    private final static int DURATION = 31536000;   //查询时的参数，表示每一次查询的时间区间，此处为一年

    private String endTime = null;
    private String deviceId;
    private String apiKey;
    private String dataStreamId;

    private DataPointsAdapter adapter;
    private View footer;
    private DataPointsModel dataPointsModel = new DataPointsModel();

    public DataPointsListView(Context context) {
        super(context);
        init(context);
    }

    public DataPointsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DataPointsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        footer = LayoutInflater.from(context).inflate(R.layout.footer,null);
        footer.setVisibility(View.GONE);
        OnScrollRefreshListener listener = new OnScrollRefreshListener(footer);
        listener.setOnLoadDataListener(this);
        this.setOnScrollListener(listener);
        addFooterView(footer);
        adapter = new DataPointsAdapter();
        setAdapter(adapter);
    }

    /**
     * @Description 设置ListView显示的数据流参数
     * @Param devId 设备ID, apiKey 产品apiKey, dataStreamId 数据流ID
     */
    public void setDataStream(String devId,String apiKey,String dataStreamId){
        this.deviceId = devId;
        this.apiKey = apiKey;
        this.dataStreamId = dataStreamId;
        load();
    }

    /**
     * @Description 改变ListView显示的数据流并刷新界面
     * @Param devId 设备ID, apiKey 产品apiKey, dataStreamId 数据流ID
     */
    public void changeDataStream(String devId,String apiKey,String dataStreamId){
        this.deviceId = devId;
        this.apiKey = apiKey;
        this.dataStreamId = dataStreamId;
        List<DeviceDataPoint> list = new ArrayList<>();
        adapter.changeData(list);
        endTime = null;
        load();
    }

    private void load() {
        dataPointsModel.loadDataPointsAsync(new DataPointsModel.OnLoadDataPointsListener() {
            @Override
            public void OnLoadSuccess(List<Map<String, Object>> list) {
                List<DeviceDataPoint> dataPoints = new ArrayList<>();
                try{
                    for (int i = 0;i < list.size();i++){
                        //每次多取一条数据，记下其时间作为下一页的节点
                        if (i != 0 && i == (list.size() - 1))
                            endTime = list.get(i).get("time").toString().replace(" ","T");
                        else
                            dataPoints.add(new DeviceDataPoint(list.get(i).get("value"),
                                list.get(i).get("time").toString()));
                    }
                    adapter.addData(dataPoints);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void OnLoadFailed() {

            }
        },apiKey,deviceId,dataStreamId,COUNT + 1,
                null,endTime,DURATION,null,Device.SORT_DESC);   //通过endtime + limit + 长度为一年的duration为参数，实现分页查询历史数据点
    }

    @Override
    public void onLoadData(){
        load();
    }
}
