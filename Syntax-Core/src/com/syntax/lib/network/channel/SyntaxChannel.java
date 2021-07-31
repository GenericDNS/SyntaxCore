package com.syntax.lib.network.channel;

import com.syntax.lib.network.channel.bootstrap.BootstrapChannel;
import com.syntax.lib.network.channel.bootstrap.auth.ChannelKey;
import com.syntax.lib.network.client.SyntaxClient;
import com.syntax.lib.network.server.SyntaxServer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SyntaxChannel extends ArrayList<SyntaxClient> implements BootstrapChannel {

    public List<UUID> keys = new ArrayList<>();

    private SyntaxClient[] clients = new SyntaxClient[this.maxClients];

    protected SyntaxServer server;

    boolean keyNeeded;

    int clientIndex = 0;

    int maxClients;

    public SyntaxChannel() {
    }

    public SyntaxChannel(SyntaxServer baseServer) {
        this.server = baseServer;
    }

    public SyntaxChannel(boolean keyNeeded, int maxClients) {
        this.maxClients = maxClients;
        this.keyNeeded = keyNeeded;
    }

    public void registerNewClient(ChannelKey channelKey, SyntaxClient syntaxClient) {
        if (keyNeeded) {
            if (keys.contains(channelKey.getKeyID())) {
                clients[clientIndex] = syntaxClient;
                clientIndex++;
            }
        } else {
            add(syntaxClient);
        }
    }

    public void registerServer(SyntaxServer server) {
        this.server = server;
    }

    public SyntaxClient get(int clientIndex) {
        if (clientIndex >= this.clientIndex) {
            return this.clients[clientIndex];
        } throw new IndexOutOfBoundsException();
    }

    public boolean isKeyNeeded() {
        return keyNeeded;
    }

    public int getClientIndex() {
        return clientIndex;
    }

    public int getMaxClients() {
        return maxClients;
    }

    @Override @Deprecated
    public SyntaxChannel open() {
        return this;
    }
}
