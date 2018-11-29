package com.roth.protocol;

import com.roth.protocol.request.LoginRequestPacket;
import com.roth.protocol.request.MessageRequestPacket;
import com.roth.protocol.response.LoginResponsePacket;
import com.roth.protocol.response.MessageResponsePacket;
import com.roth.serialize.Serializer;
import com.roth.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.roth.protocol.command.Command.*;

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
        PACKETTYPEMAP.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        PACKETTYPEMAP.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        SERIALIZERMAP = new HashMap<>();
        Serializer jsonSerializer = new JSONSerializer();
        SERIALIZERMAP.put(jsonSerializer.getSerializerAlgorithm(), jsonSerializer);
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
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
