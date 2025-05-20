package com.example.project_android.Adapter;

import android.util.Log;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

public class SdpObserverAdapter implements SdpObserver {
    private String tag;

    public SdpObserverAdapter(String tag) {
        this.tag = tag;
    }

    @Override
    public void onCreateSuccess(SessionDescription sdp) {
        // Xử lý thành công tạo SDP
        Log.d(tag, "onCreateSuccess: " + sdp);
    }

    @Override
    public void onSetSuccess() {
        // Xử lý thành công khi set SDP
        Log.d(tag, "onSetSuccess");
    }

    @Override
    public void onCreateFailure(String error) {
        // Xử lý khi tạo SDP thất bại
        Log.e(tag, "onCreateFailure: " + error);
    }

    @Override
    public void onSetFailure(String error) {
        // Xử lý khi set SDP thất bại
        Log.e(tag, "onSetFailure: " + error);
    }
}
