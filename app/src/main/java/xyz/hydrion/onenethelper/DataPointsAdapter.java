package xyz.hydrion.onenethelper;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hydrion on 2018/7/25.
 */

public class DataPointsAdapter extends BaseAdapter {
    private List<DeviceDataPoint> data = new ArrayList<>();

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(convertView,parent,R.layout.item_listview);
        setItemData(holder,data.get(position));
        return holder.getConvertView();
    }

    private void setItemData(ViewHolder holder,DeviceDataPoint dataPoint){
        holder.setText(R.id.value,dataPoint.getValue());
        holder.setText(R.id.time,dataPoint.getTime());
    }

    public void addData(List<DeviceDataPoint> list){
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void changeData(List<DeviceDataPoint> list){
        notifyDataSetInvalidated();
        data = list;
    }

}

class ViewHolder {
    private SparseArray<View> array;
    private View convertView;

    private ViewHolder(ViewGroup parent,int layout){
        convertView = LayoutInflater.from(parent.getContext()).inflate(layout, null);
        convertView.setTag(this);
        array = new SparseArray<>();
    }

    public static ViewHolder getHolder(View convertView,ViewGroup parent,int layout){
        if (convertView == null) {
            return new ViewHolder(parent,layout);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    private <T extends View> T getView(int viewId) {
        View view = array.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            array.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    public void setText(int viewId, String data) {
        TextView tv = getView(viewId);
        tv.setText(data);
    }

}
