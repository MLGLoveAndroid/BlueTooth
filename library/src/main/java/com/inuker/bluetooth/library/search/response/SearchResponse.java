package com.inuker.bluetooth.library.search.response;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Created by dingjikerbo on 2016/9/1.
 */
public interface SearchResponse {
    /*蓝牙列表搜索开始*/
    void onSearchStarted();

    /*蓝牙列表搜索结束*/
    void onSearchStopped();

    /*蓝牙设备建立连接*/
    void onDeviceFounded(SearchResult device);

    /*搜索取消*/
    void onSearchCanceled();
}
