package com.example.triviaclient.communicator;

import java.util.ArrayList;
import java.util.HashMap;

public class Responses {
    public static class Data {
        public static class RoomData {
            public int id;
            public String name;
            public int maxPlayers;
            public int questionCount;
            public int timePerQuestion;
            public boolean isActive;
        }

        public static class Question {
            public int id;
            public String question;
            public ArrayList<String> possibleAnswers;
            public int correctAnswer;
            public int subcategory;
        }

        public static class Statistics {
            public float avgAnswerTime;
            public int answerCount;
            public int correctAnswersCount;
            public int gameCount;
        }

        public static class PlayerResults {
            public String username;
            public int correctAnswerCount;
            public int wrongAnswerCount;
            public int avgTimePerAnswer;
            public int score;
        }
    }

    private static class Success {
        public int status;
    }

    public static class Error {
        public String message;
    }

    public static class Login extends Success {
    }

    public static class Signup extends Success {
    }

    public static class Logout extends Success {
    }

    public static class GetRooms extends Success {
        public int status;
        public ArrayList<Data.RoomData> rooms;
    }

    public static class GetPlayersInRoom {
        public ArrayList<String> players;
    }

    public static class GetHighScore extends Success {
        public HashMap<String, Integer> statistics;
    }

    public static class GetPersonalStats extends Success {
        public Data.Statistics statistics;
    }

    public static class JoinRoom extends Success {
    }

    public static class StartGame extends Success {
    }

    public static class CreateRoom extends Success {
    }

    public static class GetRoomState extends Success {
        public boolean isActive;
        public int questionCount;
        public int timePerQuestion;
        public ArrayList<String> players;
    }

    public static class GetQuestion extends Success {
        public String question;
        public HashMap<Integer, String> answers;
    }

    public static class SubmitAnswer extends Success {
        public int correctAnswerId;
    }

    public static class GetGameResults extends Success {
        public ArrayList<Data.PlayerResults> results;
    }

    public static class ManageQuestions extends Success {
    }

    public static class GetQuestionList extends Success {
        ArrayList<Data.Question> questions;
    }

    public static class LeaveQuestionManager extends Success {
    }

    public static class InsertQuestion extends Success {
    }

    public static class DeleteQuestion extends Success {
    }
}
