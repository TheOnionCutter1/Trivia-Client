package com.example.triviaclient.communicator;

public enum RequestCode {
    EXIT(0),

    LOGIN(1),
    SIGNUP(2),

    CREATE_ROOM(3),
    GET_ROOMS(4),
    GET_PLAYERS_IN_ROOM(5),
    JOIN_ROOM(6),
    GET_TOP_PLAYERS(7),
    GET_PLAYER_STATS(8),
    LOGOUT(9),

    CLOSE_ROOM(10),
    START_GAME(11),

    GET_ROOM_STATE(12),
    LEAVE_ROOM(13),

    GET_QUESTION(14),
    SUBMIT_ANSWER(15),
    GET_GAME_RESULTS(16);

    final int value;

    RequestCode(int value) {
        this.value = value;
    }
}
