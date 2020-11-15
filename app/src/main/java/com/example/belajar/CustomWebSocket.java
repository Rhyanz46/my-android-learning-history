package com.example.belajar;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class CustomWebSocket extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    public CustomWebSocket() {
        super();
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Hello, it's SSaurel !");
        webSocket.send("What's up ?");
        webSocket.send(ByteString.decodeHex("deadbeef"));
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
    }
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.i("onMessage","Receiving : " + text);
    }
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.i("onMessage","Receiving bytes : " + bytes.hex());
    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        Log.i("close","Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.i("onFailure","Error : " + t.getMessage());
    }
}
