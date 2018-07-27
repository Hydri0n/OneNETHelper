package xyz.hydrion.onenethelper.net.bean;

import java.util.List;

/**
 * Created by Hydrion on 2018/7/24.
 */
public class DataPoints {

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

        private int count;
        private List<DatastreamsBean> datastreams;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DatastreamsBean> getDatastreams() {
            return datastreams;
        }

        public void setDatastreams(List<DatastreamsBean> datastreams) {
            this.datastreams = datastreams;
        }

        public static class DatastreamsBean {

            private String id;
            private List<DatapointsBean> datapoints;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<DatapointsBean> getDatapoints() {
                return datapoints;
            }

            public void setDatapoints(List<DatapointsBean> datapoints) {
                this.datapoints = datapoints;
            }

            public static class DatapointsBean {

                private String at;
                private Object value;

                public String getAt() {
                    return at;
                }

                public void setAt(String at) {
                    this.at = at;
                }

                public String getValue() {
                    return value.toString();
                }

                public void setValue(Object value) {
                    this.value = value;
                }
            }
        }
    }
}
