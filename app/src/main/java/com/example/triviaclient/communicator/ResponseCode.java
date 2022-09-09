package com.example.triviaclient.communicator;

public enum ResponseCode {
    ERROR(0),
    SUCCESS(1);

    final byte value;

    ResponseCode(int value) {
        this.value = (byte) value;
    }
}
