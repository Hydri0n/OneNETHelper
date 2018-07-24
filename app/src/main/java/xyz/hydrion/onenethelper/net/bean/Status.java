package xyz.hydrion.onenethelper.net.bean;

import java.util.List;

/**
 * Created by Hydrion on 2018/7/23.
 */
public class Status {

    private int errno;
    private DataBean data;
    private String error;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class DataBean {

        private int total_count;
        private List<DevicesBean> devices;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<DevicesBean> getDevices() {
            return devices;
        }

        public void setDevices(List<DevicesBean> devices) {
            this.devices = devices;
        }

        public static class DevicesBean {

            private String title;
            private boolean online;
            private String id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public boolean isOnline() {
                return online;
            }

            public void setOnline(boolean online) {
                this.online = online;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
