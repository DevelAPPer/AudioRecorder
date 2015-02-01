package com.example.jonathan.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    MediaRecorder mr = null;
    File outputfile = null;
    Button starten,stoppen,abspielen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mr = new MediaRecorder();
        starten = (Button) findViewById(R.id.button);
        stoppen = (Button) findViewById(R.id.button2);
        abspielen = (Button) findViewById(R.id.button3);

        starten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mr.setAudioSource(MediaRecorder.AudioSource.MIC);
                mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                outputfile = getFileStreamPath("outputfile.amr");
                mr.setOutputFile(outputfile.getPath());
                try {
                    mr.prepare();
                    mr.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        stoppen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mr.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        abspielen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (outputfile != null) {
                    MediaPlayer mp = new MediaPlayer();
                    try {
                        FileInputStream fis = new FileInputStream(outputfile);
                        FileDescriptor fd = fis.getFD();
                        mp.setDataSource(fd);
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
