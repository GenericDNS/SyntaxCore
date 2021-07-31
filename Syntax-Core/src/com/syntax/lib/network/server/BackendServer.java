package com.syntax.lib.network.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.UUID;

public abstract class BackendServer {

    private UUID uniqueID;

    private InetSocketAddress address;

    protected int timeout;

    protected Socket loginSocket;

    private boolean interrupted;

    @Deprecated
    public BackendServer(String host, int port, int timeout) {
        this(UUID.randomUUID(), host, port, timeout);
    }

    @Deprecated
    public BackendServer(UUID uniqueID, String host, int port, int timeout) {
        this.interrupted = true;
        this.uniqueID = uniqueID;
        this.address = new InetSocketAddress(host, port);
        this.timeout = timeout;
    }

    public void start() {
        interrupted = false;
        login();
    }

    public void login() {
        if (interrupted) return;
        try {
            loginSocket = new Socket();
            loginSocket.connect(this.address, this.timeout);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
