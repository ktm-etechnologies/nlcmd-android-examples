package com.ktm_technologies.nlcmd_easy_example;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Window;

import com.ktm_technologies.nlcmd.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Log log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Nlcmd.action(new String[]{"hallo und willkommen"}, new ActionLambda() {
            @Override
            public boolean run() {
                log.d("markov","hello");
                return true;
            }
        });
        Nlcmd.action(new String[]{"Das ist ein Test"}, new ActionLambda() {
            @Override
            public boolean run() {
                log.d("markov","Das ist ein Test");
                return true;
            }
        });
        SpeechInput();

    }

    public void SpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_RESULTS,RESULT_CANCELED);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "de-DE");
        //   intent.putExtra(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE,true);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Nlcmd.scan((Arrays.asList(result.get(0).split(("")))));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}