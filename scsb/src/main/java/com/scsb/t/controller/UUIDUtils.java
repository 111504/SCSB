//package com.scsb.t.controller;
//
//import java.nio.ByteBuffer;
//import java.util.UUID;
//
//public class UUIDUtils {
//    public static String convertByteArrayToString(byte[] byteArray) {
//        UUID uuid = UUID.nameUUIDFromBytes(byteArray);
//        return uuid.toString();
//    }
//    public byte[] convertStringToByteArray(String uuidString) {
//        UUID uuid = UUID.fromString(uuidString);
//        return toBytes(uuid);
//    }
//
//    private byte[] toBytes(UUID uuid) {
//        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
//        bb.putLong(uuid.getMostSignificantBits());
//        bb.putLong(uuid.getLeastSignificantBits());
//        return bb.array();
//    }
//}
