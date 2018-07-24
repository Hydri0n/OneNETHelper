package xyz.hydrion.onenethelper.net.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import xyz.hydrion.onenethelper.net.RetrofitHelper;
import xyz.hydrion.onenethelper.net.bean.DataPoints;
import xyz.hydrion.onenethelper.net.service.DeviceService;

/**
 * Created by Hydrion on 2018/7/24.
 */
public class DataPointsModel {

    private DeviceService service;

    public DataPointsModel() {
        Retrofit retrofit = RetrofitHelper.getInstance();
        service = retrofit.create(DeviceService.class);
    }

    public Map<String,Object> getCurrentDataPoint(String apiKey, String deviceId, String dataStreamId){
        Map<String,Object> map = new HashMap<>();
        try {
            DataPoints.DataBean.DatastreamsBean.DatapointsBean datapointsBean = getDataPoints(apiKey,
                    deviceId,dataStreamId,
                    null,null,null,null,null,null)
                    .get(0);
            map.put("value",datapointsBean.getValue());
            map.put("time",datapointsBean.getAt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<Map<String,Object>> getSeveralDataPoints(String apiKey,
                                                          String deviceId,
                                                          String dataStreamId,
                                                          Integer limit,
                                                          String start,
                                                          String end,
                                                          Integer duration,
                                                          Integer cursor,
                                                          String sort){
        List<Map<String,Object>> list = new ArrayList<>();
        try{
            List<DataPoints.DataBean.DatastreamsBean.DatapointsBean> beanList
                    = getDataPoints(apiKey, deviceId, dataStreamId,
                    limit, start, end, duration, cursor, sort);
            for (DataPoints.DataBean.DatastreamsBean.DatapointsBean bean : beanList) {
                Map<String,Object> map = new HashMap<>();
                map.put("value",bean.getValue());
                map.put("time",bean.getAt());
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<DataPoints.DataBean.DatastreamsBean.DatapointsBean> getDataPoints(String apiKey,
                                                                    String deviceId,
                                                                    String dataStreamId,
                                                                    Integer limit,
                                                                    String start,
                                                                    String end,
                                                                    Integer duration,
                                                                    Integer cursor,
                                                                    String sort) throws IOException {
        Call<DataPoints> call = service.getDataPoints(apiKey,deviceId,dataStreamId,
                limit,start,end,duration,cursor,sort);
        retrofit2.Response<DataPoints> response = null;
        DataPoints dataPoints = null;
        response = call.execute();
        dataPoints = (DataPoints) response.body();
        if (dataPoints == null)
            throw new RuntimeException("信息解析失败，返回内容:" + response.toString());
        DataPoints.DataBean dataBean = dataPoints.getData();
        if (dataBean == null)
            throw new RuntimeException("Data获取失败,错误信息:" + dataPoints.getError());
        if(dataBean.getDatastreams().isEmpty())
            throw new RuntimeException("未查询到数据");
        DataPoints.DataBean.DatastreamsBean datastreamsBean = dataBean.getDatastreams().get(0);
        if (datastreamsBean.getDatapoints().isEmpty())
            throw new RuntimeException("未查询到数据点记录");
        return datastreamsBean.getDatapoints();

    }
}
