package xyz.hydrion.onenethelper;

import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Hydrion on 2018/7/26.
 */
public class OnScrollRefreshListener implements AbsListView.OnScrollListener {

    private boolean isLoading = false;
    private int lastItem;
    private int totalItemCount;
    private View footer;
    private OnLoadDataListener mListener;

    public OnScrollRefreshListener(View footer) {
        this.footer = footer;
    }

    public void setOnLoadDataListener(OnLoadDataListener listener) {
        mListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //滑动到底部时触发
        if (!isLoading && lastItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {
            //显示加载更多
            footer.setVisibility(View.VISIBLE);
            new Thread(){
                @Override
                public void run(){
                    isLoading = true;
                    mListener.onLoadData();
                    isLoading = false;
                }
            }.start();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    public interface OnLoadDataListener {
        void onLoadData();
    }
}
