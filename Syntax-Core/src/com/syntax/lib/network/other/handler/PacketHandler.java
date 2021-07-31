package com.syntax.lib.network.other.handler;

import com.syntax.Syntax;
import com.syntax.lib.network.other.SyntaxPacket;

public interface PacketHandler {

    void registerPacket(Byte id, Class<? extends SyntaxPacket> packet);

    void unregisterPacket(Byte id);

}
