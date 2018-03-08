package com.example.android.musicalstructureapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list);

        final ArrayList<Music> musics = new ArrayList<Music>();

        musics.add(new Music("Lovers On The Sun", "David Guetta ft Sam Martin", "03:43",R.raw.david_guetta_lovers_on_the_sun, R.drawable.david_guetta_lovers_of_the_sun_remix));
        musics.add(new Music("Whatever It Takes", "Imagine Dragons", "03:39",R.raw.imagine_dragons_whatever_it_takes, R.drawable.imagine_dragons_evolve));
        musics.add(new Music("Thunder", "Imagine Dragons", "03:23", R.raw.imagine_dragons_thunder, R.drawable.imagine_dragons_evolve));
        musics.add(new Music("Radioactive", "Imagine Dragons", "04:21",R.raw.imagine_dragons_radioactive, R.drawable.imagine_dragons_night_visions));
        musics.add(new Music("On Top Of The World", "Imagine Dragons", "04:01",R.raw.imagine_dragons_on_top_of_the_world, R.drawable.imagine_dragons_night_visions));
        musics.add(new Music("It's Time", "Imagine Dragons", "04:06",R.raw.imagine_dragons_its_time, R.drawable.imagine_dragons_night_visions));
        musics.add(new Music("I Bet My Life", "Imagine Dragons", "03:33",R.raw.imagine_dragons_i_bet_my_life, R.drawable.imagine_dragons_smoke_and_mirrors));
        musics.add(new Music("Warriors", "Imagine Dragons", "02:50",R.raw.imagine_dragons_warriors, R.drawable.imagine_dragons_smoke_and_mirrors));


        MusicAdapter adapter = new MusicAdapter(this, musics);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Intent MusicIntent = new Intent(getBaseContext(), PlayMusicActivity.class);
                MusicIntent.putExtra("index",index);
                MusicIntent.putParcelableArrayListExtra("musics",musics);

                startActivity(MusicIntent);
            }
        });


    }
}
