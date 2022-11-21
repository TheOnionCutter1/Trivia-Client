package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.triviaclient.communicator.Communicator;
import com.example.triviaclient.communicator.RequestCode;
import com.example.triviaclient.communicator.Responses;
import com.example.triviaclient.communicator.Serializer;
import com.example.triviaclient.databinding.ActivityPersonalStatsBinding;

import java.io.IOException;
import java.util.ArrayList;

public class PersonalStatsActivity extends AppCompatActivity {
    private ActivityPersonalStatsBinding _binding;
    private Communicator _communicator;

    private void _fetchStats() {
        ArrayList<Byte> response;
        Responses.Data.Statistics stats;

        try {
            this._communicator.sendMessage(
                    Serializer.serializeRequest(RequestCode.GET_PLAYER_STATS, null)
            );
            response = this._communicator.receiveMessage();
        } catch (IOException e) {
            // Connectivity error
            this.startActivity(new Intent(this, LoadingScreenActivity.class));
            this.finish();

            return;
        }

        stats = Serializer.<Responses.GetPersonalStats>deserializeResponse(
                response, Responses.GetPersonalStats.class
        ).statistics;
        this._binding.textViewNumOfGames.append(Integer.toString(stats.gameCount));
        this._binding.textViewCorrectAnswers.append(Integer.toString(stats.correctAnswersCount));
        this._binding.textViewWrongAnswers.append(
                Integer.toString(stats.answerCount - stats.correctAnswersCount)
        );
        this._binding.textViewTimePerQuestion.append(stats.avgAnswerTime + " seconds");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._communicator = Communicator.getInstance();
        this._binding = ActivityPersonalStatsBinding.inflate(this.getLayoutInflater());
        setContentView(this._binding.getRoot());

        new Thread(this::_fetchStats).start();
    }
}
