package com.roth.api;

public interface Serializer {

    byte DEFAULT_SERIALIZER_ALGO = SerializerAlgorithm.JSON;

    Serializer DEFAULT_SERIALIZER = new JSONSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
