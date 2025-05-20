package com.example.project_android.Model;

import android.content.Context;
import android.util.Log;

import com.example.project_android.Adapter.PeerConnectionAdapter;
import com.example.project_android.Adapter.SdpObserverAdapter;

import org.webrtc.*;

import java.util.ArrayList;
import java.util.List;
public class WebRTCManager {
    private static final String TAG = "WebRTCManager";
    private PeerConnectionFactory peerConnectionFactory;
    private PeerConnection localPeer;
    private MediaStream localStream;
    private VideoTrack localVideoTrack;
    private AudioTrack localAudioTrack;
    private final Context context;

    private static final String STUN_SERVER = "stun:stun.l.google.com:19302";

    public WebRTCManager(Context context) {
        this.context = context;
        initializeWebRTC();
    }

    private void initializeWebRTC() {
        PeerConnectionFactory.InitializationOptions options =
                PeerConnectionFactory.InitializationOptions.builder(context)
                        .createInitializationOptions();
        PeerConnectionFactory.initialize(options);

        peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory();

        // Khởi tạo các đối tượng video và audio track
        MediaConstraints audioConstraints = new MediaConstraints();
        MediaConstraints videoConstraints = new MediaConstraints();

        // Khởi tạo audio track
        AudioSource audioSource = peerConnectionFactory.createAudioSource(audioConstraints);
        localAudioTrack = peerConnectionFactory.createAudioTrack("audio_track", audioSource);

        // Khởi tạo video track
        VideoSource videoSource = peerConnectionFactory.createVideoSource(false); // false = không dùng camera ngay
        localVideoTrack = peerConnectionFactory.createVideoTrack("video_track", videoSource);

        // Tạo MediaStream
        localStream = peerConnectionFactory.createLocalMediaStream("local_stream");
        localStream.addTrack(localAudioTrack);
        localStream.addTrack(localVideoTrack);
    }

    public void createPeerConnection() {
        // Cấu hình PeerConnection
        PeerConnection.RTCConfiguration rtcConfig = new PeerConnection.RTCConfiguration(getIceServers());
        rtcConfig.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE;
        rtcConfig.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;

        localPeer = peerConnectionFactory.createPeerConnection(rtcConfig, new PeerConnectionAdapter(TAG));

        if (localPeer != null) {
            localPeer.addStream(localStream);
        }
    }

    private List<PeerConnection.IceServer> getIceServers() {
        List<PeerConnection.IceServer> iceServers = new ArrayList<>();
        iceServers.add(PeerConnection.IceServer.builder(STUN_SERVER).createIceServer());
        return iceServers;
    }

    public void createOffer() {
        if (localPeer == null) return;

        MediaConstraints constraints = new MediaConstraints();
        localPeer.createOffer(new SdpObserverAdapter(TAG) {
            @Override
            public void onCreateSuccess(SessionDescription sdp) {
                Log.d(TAG, "Offer created successfully: " + sdp);
                localPeer.setLocalDescription(new SdpObserverAdapter(TAG), sdp);
                // Gửi SDP tới Firebase hoặc các thiết bị khác
            }
        }, constraints);
    }

    public void setRemoteDescription(SessionDescription sdp) {
        if (localPeer != null) {
            localPeer.setRemoteDescription(new SdpObserverAdapter(TAG), sdp);
        }
    }
}