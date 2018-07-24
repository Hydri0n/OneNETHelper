package xyz.hydrion.onenethelper.net.bean;

import java.util.List;

/**
 * Created by Hydrion on 2018/7/24.
 */
public class DataStreams {

    private int errno;
    private String error;
    private List<DataBean> data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String create_time;
        private String update_at;
        private String id;
        private String current_value;
        private String unit;
        private String unit_symbol;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCurrent_value() {
            return current_value;
        }

        public void setCurrent_value(String current_value) {
            this.current_value = current_value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnit_symbol() {
            return unit_symbol;
        }

        public void setUnit_symbol(String unit_symbol) {
            this.unit_symbol = unit_symbol;
        }
    }
}
