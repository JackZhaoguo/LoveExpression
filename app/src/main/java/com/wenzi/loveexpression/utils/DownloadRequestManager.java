package com.wenzi.loveexpression.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/*
*   管理同时请求下载同一个url.
*/
public class DownloadRequestManager {

    private volatile static DownloadRequestManager managerInstance;

    /**
     * HashMap of Cache keys -> BatchedImageRequest used to track in-flight requests so
     * that we can coalesce multiple requests to the same URL into a single network request.
     */
    private final HashMap<String, DownloadRequest> mInFlightRequests = new HashMap<>();

    public static DownloadRequestManager getInstance() {
        if (managerInstance == null) {
            synchronized (DownloadRequestManager.class) {
                if (managerInstance == null) {
                    managerInstance = new DownloadRequestManager();
                }
            }
        }
        return managerInstance;
    }

    private DownloadRequestManager() {
    }

    public void putRequest(DownloadRequest request) {
        if (null != request && !mInFlightRequests.containsKey(request.mRequestUrl)) {
            mInFlightRequests.put(request.mRequestUrl, request);
        }
    }

    public DownloadRequest getRequest(String url) {
        return mInFlightRequests.get(url);
    }

    public void removeRequest(String url) {
        if (null != url && mInFlightRequests.containsKey(url)) {
            DownloadRequest request = getRequest(url);
            request.removeAllListener();
            mInFlightRequests.remove(request.mRequestUrl);
        }
    }

    public void removeAllRequest() {
        Collection<DownloadRequest> downloadRequests = mInFlightRequests.values();
        for (DownloadRequest dr : downloadRequests) {
            if (null != dr) {
                dr.removeAllListener();
            }
        }
        mInFlightRequests.clear();
    }

    public boolean isRequestInFlight(String url) {
        return mInFlightRequests.containsKey(url);
    }

    public static DownloadRequest createNewDownloadRequest(String url, ActListener listener) {
        return new DownloadRequest(url, listener);
    }

    public static class DownloadRequest {
        String mRequestUrl;

        /** List of all of the active listener that are interested in the request */
        private final List<ActListener> mActListeners = new ArrayList<>();

        DownloadRequest(String requestUrl, ActListener listener) {
            mRequestUrl = requestUrl;
            mActListeners.add(listener);
        }

        public void addActListener(ActListener listener) {
            mActListeners.add(listener);
        }

        public void removeAllListener() {
            mActListeners.clear();
        }

        public void notifyResponse(String resourcesDri) {
            for (ActListener actListener : mActListeners) {
                actListener.onActResponse(resourcesDri);
            }
            DownloadRequestManager.getInstance().removeRequest(mRequestUrl);
        }
    }

    public interface ActListener {
        void onActResponse(String resourcesDir);
    }
}
