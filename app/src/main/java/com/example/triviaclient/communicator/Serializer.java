package com.example.triviaclient.communicator;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Serializer {
    /**
     * Serialize a message to be sent to the server.
     *
     * @param code    The code of the message (message type, applicative)
     * @param request A struct of a request to be serialized.
     * @return A buffer with the serialized message.
     */
    public static ArrayList<Byte> SerializeRequest(RequestCode code, Object request) {
        Gson gson = new Gson();
        ArrayList<Byte> result = new ArrayList<>();
        // Serialize the message contents/data
        byte[] dataArr = gson.toJson(request).getBytes(StandardCharsets.UTF_8);
        byte[] dataLengthArr = {0};

        // Get length of the serialized data
        for (int i = 0; i < Integer.SIZE / Byte.SIZE; i++) {
            dataLengthArr[i] = (byte) (dataArr.length &
                    (0xFF << (Byte.SIZE * i)) >> (Byte.SIZE * i));
        }

        // Get the message code
        result.add(code.value);

        // Append message length and message contents to message code
        for (byte b : dataLengthArr) {
            result.add(b);
        }
        for (byte b : dataArr) {
            result.add(b);
        }

        return result;
    }
}
