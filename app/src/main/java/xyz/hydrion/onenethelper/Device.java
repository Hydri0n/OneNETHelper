package xyz.hydrion.onenethelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import xyz.hydrion.onenethelper.net.model.DataPointsModel;
import xyz.hydrion.onenethelper.net.model.DataStreamsModel;
import xyz.hydrion.onenethelper.net.model.StatusModel;

/**
 *  Created by Hydrion on 2018/7/23.
 */


public class Device {

    public static final String SORT_DESC = "DESC"; //倒序
    public static final String SORT_ASC = "ASC"; //升序

    private String deviceId;
    private String apiKey;
    private StatusModel statusModel;
    private DataPointsModel dataPointsModel;
    private DataStreamsModel dataStreamsModel;

    Device(String deviceId, String apiKey) {
        this.deviceId = deviceId;
        this.apiKey = apiKey;
        statusModel = new StatusModel();
        dataPointsModel = new DataPointsModel();
        dataStreamsModel = new DataStreamsModel();
    }

    /**
     * @Description 查看设备当前状态
     * @Return 设备是否在线
     */
    public boolean isOnline(){
        return statusModel.isOnline(apiKey,deviceId);
    }

    /**
     * @Description 查询设备的名称
     * @Return 设备名称
     */
    public String getDeviceTitle(){
        String title = statusModel.getTitle(apiKey,deviceId);
        if (title == null) return "未知";
        else return title;
    }

    public String getDeviceDescription(){
        return null;
    }

    /**
     * @Description 获取数据流中最新的数据点
     * @Param dataStreamId 数据流的id
     * @Return 数据流最新的数据点,获取失败时返回null
     */
    public DeviceDataPoint getCurrentDataPoint(String dataStreamId){
        Map<String,Object> map = dataPointsModel.getCurrentDataPoint(apiKey,deviceId,dataStreamId);
        try{
            return new DeviceDataPoint(map.get("value"),map.get("time").toString());
        }
        catch (NullPointerException e){
            return null;
        }
    }

    /**
     * @Description 根据设置的上限值，获取数据流中若干数据点
     * @Param dataStreamId 数据流的id，limit 数据点个数上限
     * @Return 数据流中若干数据点，默认升序
     */
    public List<DeviceDataPoint> getDataPointsWithinLimit(String dataStreamId,int limit){
        return getDataPointsWithinLimit(dataStreamId,limit,SORT_ASC);
    }

    /**
     * @Description 根据设置的上限值，获取数据流中若干数据点
     * @Param dataStreamId 数据流的id，limit 数据点个数上限，sort 升序(ASC)/倒序(DESC)
     * @Return 数据流中若干数据点
     */
    public List<DeviceDataPoint> getDataPointsWithinLimit(String dataStreamId,int limit,String sort){
        List<Map<String,Object>> list = dataPointsModel.getSeveralDataPoints(apiKey,deviceId,
                dataStreamId,limit,null,null,null,null,sort);
        List<DeviceDataPoint> dataPoints = new ArrayList<>();
        for (Map<String,Object> map : list){
            try{
                dataPoints.add(new DeviceDataPoint(map.get("value"),map.get("time").toString()));
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return dataPoints;
    }

    /**
     * @Description 根据设置的时间区间，获取数据流中若干数据点
     * @Param dataStreamId 数据流的id，startTime 提取数据点的开始时间,
     *        endTime 提取数据点的结束时间
     * @Return 数据流中若干数据点
     */
    public List<DeviceDataPoint> getDataPointsBetweenTwoTimes(String dataStreamId,
                                                              Date startTime,
                                                              Date endTime){
        return getDataPointsBetweenTwoTimes(dataStreamId,startTime,endTime,null);
    }

    /**
     * @Description 根据设置的时间区间，获取数据流中若干数据点
     * @Param dataStreamId 数据流的id，startTime 提取数据点的开始时间,
     *        endTime 提取数据点的结束时间,sort 升序(ASC)/倒序(DESC)
     * @Return 数据流中若干数据点
     */
    public List<DeviceDataPoint> getDataPointsBetweenTwoTimes(String dataStreamId,
                                                              Date startTime,
                                                              Date endTime,
                                                              String sort){
        List<DeviceDataPoint> dataPoints = new ArrayList<>();
        //转换时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String strStartTime = format.format(startTime);
        String strEndTime = format.format(endTime);
        List<Map<String,Object>> list = dataPointsModel.getSeveralDataPoints(apiKey,deviceId,
                dataStreamId,null,strStartTime,strEndTime,null,null,sort);
        for (Map<String,Object> map : list){
            try{
                dataPoints.add(new DeviceDataPoint(map.get("value"),map.get("time").toString()));
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return dataPoints;
    }

    /**
     * @Description 获取数据流的详细信息（单位、单位符号、创建时间、最新数据、数据更新时间）
     * @Param dataStreamId 数据流Id
     * @Return 一个DeviceDataStream实例，其中未获取到的数据显示为空字符串
     */
    public DeviceDataStream getDataStream(String dataStreamId){
        Map<String,Object> map = dataStreamsModel.getDataStreamDetail(apiKey,deviceId,
                dataStreamId);
        return new DeviceDataStream(map.get("id").toString(),map.get("unit").toString(),
                map.get("unitSymbol").toString(),map.get("createTime").toString(),
                map.get("currentValue").toString(),map.get("updateTime").toString());
    }

}
