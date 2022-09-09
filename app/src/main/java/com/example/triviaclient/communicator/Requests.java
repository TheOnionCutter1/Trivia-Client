package com.example.triviaclient.communicator;

public class Requests {
    public static class Login {
        public String username;
        public String password;
    }

    public static class Signup {
        public String username;
        public String password;
        public String email;
    }

    public static class GetPlayersInRoom {
        public int roomId;
    }

    public static class JoinRoom {
        public int roomId;
    }

    public static class CreateRoom {
        public String roomName;
        public int maxPlayers;
        public int questionCount;
        public int timePerQuestion;
    }

    public static class SubmitAnswer {
        public int answerId;
    }
}
