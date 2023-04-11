package com.example.triviaclient.communicator;

import java.util.ArrayList;

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

    public static class GetSubcategoryPlayerStats {
        public int subcategory;
    }

    public static class GetQuestionList {
        public int offset;
        public int limit;
    }

    public static class InsertQuestion {
        Responses.Data.Question question;
    }

    public static class DeleteQuestion {
        int id;
    }
}
