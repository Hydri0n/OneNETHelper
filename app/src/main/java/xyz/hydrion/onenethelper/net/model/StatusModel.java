package xyz.hydrion.onenethelper.net.model;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import xyz.hydrion.onenethelper.net.RetrofitHelper;
import xyz.hydrion.onenethelper.net.bean.Status;
import xyz.hydrion.onenethelper.net.service.DeviceService;

/**
 * Created by Hydrion on 2018/7/23.
 */
public class StatusModel {
    private DeviceService service;

    public StatusModel() {
        Retrofit retrofit = RetrofitHelper.getInstance();
        service = retrofit.create(DeviceService.class);
    }

    public String getTitle(String apiKey,String deviceId){
        String title = null;
        try {
            title = getDevicesBean(apiKey,deviceId).getTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return title;
    }

    public boolean isOnline(String apiKey,String deviceId){
        try {
            if(getDevicesBean(apiKey,deviceId).isOnline()) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Status.DataBean.DevicesBean getDevicesBean(String apiKey,String deviceId) throws IOException {
        Call<Status> call = service.getDeviceStatus(apiKey,deviceId);
        Response response = null;
        Status status = null;
        response = call.execute();
        status = (Status) response.body();
        if (status == null) throw new RuntimeException("信息解析失败，返回内容:" + response.toString());
        Status.DataBean dataBean = status.getData();
        if (dataBean == null) throw new RuntimeException("Data获取失败,错误信息:" + status.getError());
        Status.DataBean.DevicesBean device = dataBean.getDevices().get(0);
        if (device == null) throw new RuntimeException("查询不到设备的信息");

        return device;
    }
}
