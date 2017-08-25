package com.shiro.common.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.UUID;

public class KeyUtils {
    
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String s = uuidToBase64(uuid.toString());
        return "user"+s;
    }

    public static String commodityId(){
        UUID uuid = UUID.randomUUID();
        String s = uuidToBase64(uuid.toString());
        return "cdi"+s;
    }

    private static String uuidToBase64(String str) {
        Base64 base64 = new Base64();
        UUID uuid = UUID.fromString(str);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return base64.encodeBase64URLSafeString(bb.array());
    }
    
    public static void main(String[] args) {
        String id = "uuid: " + uuid();
        System.out.println(id);
        System.out.println(id.length());
    }
}
