package xyz.hydrion.onenethelper.net.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import xyz.hydrion.onenethelper.net.RetrofitHelper;
import xyz.hydrion.onenethelper.net.bean.DataStreams;
import xyz.hydrion.onenethelper.net.service.DeviceService;

/**
 * Created by Hydrion on 2018/7/24.
 */
public class DataStreamsModel {
    private DeviceService service;

    public DataStreamsModel() {
        Retrofit retrofit = RetrofitHelper.getInstance();
        service = retrofit.create(DeviceService.class);
    }

    public Map<String,Object> getDataStreamDetail(String apiKey,String deviceId,String dataStreamId){
        Map<String,Object> map = new HashMap<>();
        try{
            DataStreams.DataBean dataBean = getDataBeans(apiKey,deviceId,dataStreamId)
                    .get(0);

            if (dataBean.getId() != null)
                map.put("id",dataBean.getId());
            else map.put("id","");
            if (dataBean.getUnit() != null)
                map.put("unit",dataBean.getUnit());
            else map.put("unit","");
            if (dataBean.getUnit_symbol() != null)
                map.put("unitSymbol",dataBean.getUnit_symbol());
            else map.put("unitSymbol","");
            if (dataBean.getCreate_time() != null)
                map.put("createTime",dataBean.getCreate_time());
            else map.put("createTime","");
            if (dataBean.getUpdate_at() != null)
                map.put("updateTime",dataBean.getUpdate_at());
            else map.put("updateTime","");
            if (dataBean.getCurrent_value() != null)
                map.put("currentValue",dataBean.getCurrent_value());
            else map.put("currentValue","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private List<DataStreams.DataBean> getDataBeans(String apiKey, String deviceId,String dataStreamId)
            throws IOException {
        Call<DataStreams>  call = service.getDataStreams(apiKey,deviceId,dataStreamId);
        Response<DataStreams> response = null;
        DataStreams dataStreams = null;
        response = call.execute();
        dataStreams = response.body();
        if (dataStreams == null)
            throw new RuntimeException("信息解析失败，返回内容:" + response.toString());
        if (dataStreams.getData() == null)
            throw new RuntimeException("Data获取失败,错误信息:" + dataStreams.getError());
        if (dataStreams.getData().isEmpty())
            throw new RuntimeException("未查询到数据");
        return dataStreams.getData();
    }
}
