package com.example.triviaclient.communicator;

public enum ResponseCode {
    ERROR(0),
    SUCCESS(0);

    final int value;

    ResponseCode(int value) {
        this.value = value;
    }
}
