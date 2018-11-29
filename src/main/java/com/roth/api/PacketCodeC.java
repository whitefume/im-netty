package com.roth.api;

import com.roth.api.request.LoginRequestPacket;
import com.roth.api.request.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.roth.api.Command.LOGIN_REQUEST;
import static com.roth.api.Command.LOGIN_RESPONSE;

@Slf4j
public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> PACKETTYPEMAP;

    private static final Map<Byte, Serializer> SERIALIZERMAP;

    static {
        PACKETTYPEMAP = new HashMap<>();
        PACKETTYPEMAP.put(LOGIN_REQUEST, LoginRequestPacket.class);
        PACKETTYPEMAP.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        SERIALIZERMAP = new HashMap<>();
        Serializer jsonSerializer = new JSONSerializer();
        SERIALIZERMAP.put(jsonSerializer.getSerializerAlgorithm(), jsonSerializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAlloc, Packet packet) {
        ByteBuf byteBuf = byteBufAlloc.ioBuffer();
        byte[] bytes = Serializer.DEFAULT_SERIALIZER.serialize(packet);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT_SERIALIZER.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);
        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serizlizeAlgorithm) {
        return SERIALIZERMAP.get(serizlizeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return PACKETTYPEMAP.get(command);
    }
}
