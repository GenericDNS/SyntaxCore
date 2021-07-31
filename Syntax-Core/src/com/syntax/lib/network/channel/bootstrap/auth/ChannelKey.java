package com.syntax.lib.network.channel.bootstrap.auth;

import com.syntax.lib.network.channel.SyntaxChannel;

import java.util.UUID;

public class ChannelKey {

    private final UUID keyID;

    public ChannelKey() {
        this.keyID = UUID.randomUUID();
    }

    public ChannelKey(SyntaxChannel syntaxChannel) {
        this.keyID = UUID.randomUUID();
        register(syntaxChannel);
    }

    public UUID getKeyID() {
        return keyID;
    }

    @Override
    public String toString() {
        return keyID.toString();
    }

    public void register(SyntaxChannel syntaxChannel) {
        syntaxChannel.keys.add(this.keyID);
    }

}
