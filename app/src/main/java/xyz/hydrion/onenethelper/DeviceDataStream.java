package xyz.hydrion.onenethelper;

/**
 * Created by Hydrion on 2018/7/23.
 */
public class DeviceDataStream {
    private String id;
    private String unit;
    private String unitSymbol;
    private String createTime;
    private String currentValue;
    private String updateTime;

    DeviceDataStream(String id, String unit, String unitSymbol,
                            String createTime, String currentValue,
                            String updateTime) {
        this.id = id;
        this.unit = unit;
        this.unitSymbol = unitSymbol;
        this.createTime = createTime;
        this.currentValue = currentValue;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
