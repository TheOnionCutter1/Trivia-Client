package com.example.triviaclient.communicator;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Serializer {
    /**
     * Serialize a message to be sent to the server.
     *
     * @param code    The code of the message (message type, applicative).
     * @param request A struct of a request to be serialized.
     * @return A buffer with the serialized message.
     */
    public static ArrayList<Byte> serializeRequest(RequestCode code, Object request) {
        ArrayList<Byte> result = new ArrayList<>();
        // Serialize the message contents/data
        byte[] dataArr = new Gson().toJson(request).getBytes(StandardCharsets.UTF_8);
        byte[] dataLengthArr = new byte[Integer.SIZE / Byte.SIZE];

        // Get length of the serialized data
        for (int i = 0; i < dataLengthArr.length; i++) {
            dataLengthArr[i] = (byte) ((dataArr.length &
                    (0xFF << (Byte.SIZE * i))) >> (Byte.SIZE * i));
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

    /**
     * Deserialize a message that was received from the server.
     *
     * @param buffer       The message that was received from the server.
     * @param responseType The type of the object that the message will be put in.
     * @param <T>          The type of the object that the message will be put in.
     * @return The deserialized message.
     */
    public static <T> T deserializeResponse(ArrayList<Byte> buffer, Type responseType) {
        String bufferStr = "";

        // Convert the buffer/response to a string
        for (byte b : buffer.subList(Communicator.DATA_START, buffer.size())) {
            bufferStr += (char) b;
        }

        // Deserialize the response
        return new Gson().fromJson(bufferStr, responseType);
    }
}
