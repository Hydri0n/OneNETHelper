package xyz.hydrion.onenethelper;

/**
 * Created by Hydrion on 2018/7/23.
 */
public class DeviceDataPoint {
    private Object value;
    private String time;

    //构造函数不向用户开放，只能通过Device类内部方法得到实例
    DeviceDataPoint(Object value, String time) {
        this.value = value;
        this.time = time;
    }

    public String getValue() {
        return value.toString()
                .replace("{","")
                .replace("}","");
    }

    public String getTime() {
        return time;
    }

}
