package com.roth.utils;

import java.util.UUID;

public class IDUti {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
