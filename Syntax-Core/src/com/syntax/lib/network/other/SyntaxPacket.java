package com.syntax.lib.network.other;

import java.io.Serializable;
import java.util.Objects;

public class SyntaxPacket implements Serializable {

    private final String id;
    private final PacketGroup packetGroup;

    public SyntaxPacket(String id, PacketGroup packetGroup){
        this.id = id;
        this.packetGroup = packetGroup;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SyntaxPacket objects1 = (SyntaxPacket) o;
        return Objects.equals(id, objects1.id);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), id);
        return result;
    }

    public String getId() {
        return id;
    }
}
