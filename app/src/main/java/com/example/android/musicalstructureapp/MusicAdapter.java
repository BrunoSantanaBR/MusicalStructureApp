package com.example.android.musicalstructureapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bruno on 04/03/2018.
 */

public class MusicAdapter extends ArrayAdapter<Music> {

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_main, parent, false);
        }

        Music currentMusic = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.musicName_text_view);
        nameTextView.setText(currentMusic.getmName());


        TextView artistTextView = (TextView) listItemView.findViewById(R.id.musicArtist_text_view);
        artistTextView.setText(currentMusic.getmArtist());


        TextView durationTextView = (TextView) listItemView.findViewById(R.id.musicDuration_text_view);
        durationTextView.setText(currentMusic.getmDuration());


        return listItemView;
    }

    public MusicAdapter(Activity context, ArrayList<Music> musics) {
        super(context, 0, musics);
    }
}
