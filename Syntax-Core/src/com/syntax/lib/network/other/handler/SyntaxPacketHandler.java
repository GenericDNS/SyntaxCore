package com.syntax.lib.network.other.handler;

import com.syntax.lib.network.other.SyntaxPacket;

import java.util.HashMap;
import java.util.Map;

public class SyntaxPacketHandler implements PacketHandler {

    private final Map<Byte, Class<? extends SyntaxPacket>> registeredPackets = new HashMap<>();

    @Override
    public void registerPacket(Byte id, Class<? extends SyntaxPacket> packet) {
        this.registeredPackets.put(id,packet);
    }

    @Override
    public void unregisterPacket(Byte id) {
        this.registeredPackets.remove(id);
    }

    public Map<Byte, Class<? extends SyntaxPacket>> getRegisteredPackets() {
        return registeredPackets;
    }
}
