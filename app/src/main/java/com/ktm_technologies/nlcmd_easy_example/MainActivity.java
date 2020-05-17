package com.ktm_technologies.nlcmd_easy_example;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ktm_technologies.nlcmd.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    Log log;
    TextView resView;
    Button butSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resView=findViewById(R.id.ResultView);
        butSpeak=findViewById(R.id.ButSpeak);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

        }
        Nlcmd.action(new String[]{"hallo und willkommen"}, new ScanLambda() {
            @Override
            public void run(HashMap<List<String>, Double> matches, HashMap<String, List<String>> placeholders) {
                log.d("markov","p1");
                resView.setText("Phrase 1");
            }
        });
        Nlcmd.action(new String[]{"was ist hier los"}, new ScanLambda() {
            @Override
            public void run(HashMap<List<String>, Double> matches, HashMap<String, List<String>> placeholders) {
                log.d("markov","p2");
                resView.setText("Phrase 2");
            }
        });
        Nlcmd.action(new String[]{"das ist ein test"},  new ScanLambda() {
            @Override
            public void run(HashMap<List<String>, Double> matches, HashMap<String, List<String>> placeholders) {
                log.d("markov","p3");
                resView.setText("Phrase 3");
            }
        });
        Nlcmd.action(new String[]{"sie nutzen die <location> app"},  new ScanLambda() {
            @Override
            public void run(HashMap<List<String>, Double> matches, HashMap<String, List<String>> placeholders) {
                log.d("markov","p4");
                resView.setText("Phrase 4");
            }
        });
        butSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechInput();
            }
        });
       // Nlcmd.scan("das ist ein Test");
        //Nlcmd.scan("was ist hier los");
        //Nlcmd.scan("das ist ein Test");

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
                log.d("input",result.get(0).toLowerCase());
                Nlcmd.scan(result.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}