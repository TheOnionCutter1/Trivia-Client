package com.example.triviaclient.communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Communicator {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8820;
    private static final byte[] HANDSHAKE_MESSAGE = "Hello".getBytes(StandardCharsets.UTF_8);
    private static final int HANDSHAKE_LENGTH = Communicator.HANDSHAKE_MESSAGE.length;

    Socket _soc;

    public Communicator() {
        this._soc = new Socket();
    }

    /**
     * Perform a handshake with the server.
     *
     * @return Whether the handshake was successful.
     * @throws IOException If the communication with the server has failed.
     */
    private boolean performHandshake() throws IOException {
        byte[] received = new byte[Communicator.HANDSHAKE_LENGTH];
        DataOutputStream out = new DataOutputStream(this._soc.getOutputStream());
        DataInputStream in = new DataInputStream(this._soc.getInputStream());

        out.write(Communicator.HANDSHAKE_MESSAGE);

        return in.read(received) == Communicator.HANDSHAKE_LENGTH &&
                Arrays.equals(received, Communicator.HANDSHAKE_MESSAGE);
    }

    /**
     * Connect to the server.
     *
     * @throws IOException If the communication with the server has failed.
     */
    public void connectToServer() throws IOException {
        this._soc.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

        if (!this.performHandshake()) {
            throw new IOException("Handshake with the server has failed");
        }
    }

    /**
     * Send a message to the server.
     *
     * @param message The message to send.
     * @throws IOException If the communication with the server has failed.
     */
    public void sendMessage(byte[] message) throws IOException {
        DataOutputStream out = new DataOutputStream(this._soc.getOutputStream());

        out.write(message);
    }
}
