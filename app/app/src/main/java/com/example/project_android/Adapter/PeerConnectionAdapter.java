package com.example.project_android.Adapter;

import android.util.Log;

import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.RtpReceiver;

public class PeerConnectionAdapter implements PeerConnection.Observer {
    private String tag;

    public PeerConnectionAdapter(String tag) {
        this.tag = tag;
    }

    @Override
    public void onIceCandidate(IceCandidate iceCandidate) {
        Log.d(tag, "onIceCandidate: " + iceCandidate.toString());
    }

    @Override
    public void onDataChannel(DataChannel dataChannel) {
    }

    @Override
    public void onIceConnectionChange(PeerConnection.IceConnectionState newState) {
        Log.d(tag, "onIceConnectionChange: " + newState);
    }

    @Override
    public void onIceConnectionReceivingChange(boolean receiving) {
        Log.d(tag, "onIceConnectionReceivingChange: " + receiving);
    }

    @Override
    public void onAddStream(MediaStream mediaStream) {
        Log.d(tag, "onAddStream: " + mediaStream.toString());
    }

    @Override
    public void onRemoveStream(MediaStream mediaStream) {
        Log.d(tag, "onRemoveStream: " + mediaStream.toString());
    }

    @Override
    public void onRenegotiationNeeded() {
        Log.d(tag, "onRenegotiationNeeded");
    }

    @Override
    public void onSignalingChange(PeerConnection.SignalingState signalingState) {
        Log.d(tag, "onSignalingChange: " + signalingState);
    }

    @Override
    public void onAddTrack(RtpReceiver receiver, MediaStream[] mediaStreams) {
        Log.d(tag, "onAddTrack: " + receiver);
    }

    // Implement phương thức onIceGatheringChange
    @Override
    public void onIceGatheringChange(PeerConnection.IceGatheringState newState) {
        Log.d(tag, "onIceGatheringChange: " + newState);
    }

    // Implement phương thức onIceCandidatesRemoved
    @Override
    public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
        Log.d(tag, "onIceCandidatesRemoved: " + iceCandidates.length);
    }
}