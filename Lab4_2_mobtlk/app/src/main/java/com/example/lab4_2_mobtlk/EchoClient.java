package com.example.lab4_2_mobtlk;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface EchoClientInterface{
    void onMessage(String message);
    void onStatusChange(String newStatus);
}

public class EchoClient extends WebSocketClient {

    EchoClientInterface observer;

    public EchoClient(URI serverUri, EchoClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        observer.onStatusChange("Connection open");
    }

    @Override
    public void onMessage(String message) {
        observer.onStatusChange("Received Message");
        observer.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        observer.onStatusChange("Socket closed");
    }

    @Override
    public void onError(Exception ex) {
        observer.onStatusChange("Error in socket:" + ex.toString() );
    }
}
