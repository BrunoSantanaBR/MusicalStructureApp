package com.example.android.musicalstructureapp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by bruno on 04/03/2018.
 */

public class PlayMusicActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private SeekBar mSeekBar;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChange;


    private int progress = 0;
    private Thread thread = null;
    private Runnable runnable;

    private DecimalFormat twoDigits = new DecimalFormat("00");
    private int min = 0;
    private int sec = 0;

    private int index;

    ArrayList<Music> musics;
    private TextView musicDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing);

        //Makes the window not go to sleep mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent playMusicIntent = getIntent();

        index = playMusicIntent.getIntExtra("index", 0);
        musics = playMusicIntent.getParcelableArrayListExtra("musics");

        setMusic(index, true);

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int state) {
                if (state == AudioManager.AUDIOFOCUS_GAIN) {
                    startPlayMusic();
                } else if (state == AudioManager.AUDIOFOCUS_LOSS) {
                    mMediaPlayer.pause();
                } else if (state == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    mMediaPlayer.pause();
                }
            }
        };

        musicDuration = findViewById(R.id.music_duration_text_view);
        mSeekBar = findViewById(R.id.show_duration_seekbar);

        final android.os.Handler handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {

                musicDuration.setText(setMusicDuration());
                mSeekBar.setProgress(progress);

            }
        };

        runnable = new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Toast.makeText(PlayMusicActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                    }


                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                        int temp;
                        synchronized (this) {
                            progress = (mMediaPlayer.getCurrentPosition() * 100 / mMediaPlayer.getDuration());
                            temp = mMediaPlayer.getCurrentPosition();
                        }
                        temp /= 1000;
                        min = temp / 60;
                        sec = temp % 60;
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        };

        //Click Listener Previous Button - Change to the previous music
        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index -= 1;
                setMusic(index, false);
            }
        });

        //Click Listener Play/Pause Button
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mMediaPlayer.isPlaying()) {
                    changeIconPlayPause(true);
                    mMediaPlayer.pause();
                    thread = null;
                } else {
                    changeIconPlayPause(false);
                    startPlayMusic();
                }
            }
        });

        //Click Listener Foward Button - Change to the next music
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index += 1;
                setMusic(index, false);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo((int) (mMediaPlayer.getDuration() * seekBar.getProgress() / 100.0));
            }
        });
    }

    @Override
    protected void onStart() {
        int state = mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (state == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            super.onStart();
            startPlayMusic();
        } else {
            Toast.makeText(PlayMusicActivity.this, "Can not gain audio focus", Toast.LENGTH_SHORT).show();
        }
    }

    private void startPlayMusic() {

        mMediaPlayer.start();
        thread = new Thread(runnable);
        thread.start();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                musicDuration = findViewById(R.id.music_duration_text_view);
                mSeekBar = findViewById(R.id.show_duration_seekbar);

                progress = 0;
                min = 0;
                sec = 0;

                musicDuration.setText(setMusicDuration());
                mSeekBar.setProgress(progress);
                mMediaPlayer.seekTo(progress);

                changeIconPlayPause(true);
            }
        });

    }

    private void changeIconPlayPause(boolean isPlay) {

        ImageView iconView = findViewById(R.id.play);
        String iconName;
        int iconId;

        if (isPlay) {
            iconName = "play_foreground";
        } else {
            iconName = "pause_foreground";
        }

        iconId = getResources().getIdentifier(iconName, "mipmap", getPackageName());
        iconView.setImageResource(iconId);

    }

    private void setMusic(int index, boolean firstMusic) {

        int musicReferenceId;
        int musicImageReferenceId;
        String musicName;
        String musicArtist;

        if (index == -1) {
            index = musics.size() - 1;
        } else if (index == musics.size()) {
            index = 0;
        }

        musicName = musics.get(index).getmName();
        musicArtist = musics.get(index).getmArtist();
        musicReferenceId = musics.get(index).getmMusicReferenceId();
        musicImageReferenceId = musics.get(index).getmImageMusicReferenceId();

        ((TextView) findViewById(R.id.music_name_text_view)).setText(musicName);
        ((TextView) findViewById(R.id.music_artist_text_view)).setText(musicArtist);
        ((ImageView) findViewById(R.id.album_image_view)).setImageResource(musicImageReferenceId);

        if (firstMusic) {
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(this, musicReferenceId);
            }
            startPlayMusic();
        } else {
            finish();
            Intent playMusicIntent = new Intent(getBaseContext(), PlayMusicActivity.class);
            playMusicIntent.putExtra("index", index);
            playMusicIntent.putParcelableArrayListExtra("musics", musics);
            startActivity(playMusicIntent);
        }
    }

    private String setMusicDuration() {

        return twoDigits.format(min) + ":" + twoDigits.format(sec);
    }


}



