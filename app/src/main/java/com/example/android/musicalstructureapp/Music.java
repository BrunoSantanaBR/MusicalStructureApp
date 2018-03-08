package com.example.android.musicalstructureapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by bruno on 04/03/2018.
 */

public class Music implements Parcelable {

    private String mName;
    private String mArtist;
    private String mDuration;
    private int mMusicReferenceId;
    private int mImageMusicReferenceId;

    public Music(String name, String artist, String duration, int musicReferenceId, int imageMusicReferenceId) {
        mName = name;
        mArtist = artist;
        mDuration = duration;
        mMusicReferenceId = musicReferenceId;
        mImageMusicReferenceId = imageMusicReferenceId;
    }

    public String getmName() {
        return mName;
    }

    public String getmArtist() {
        return mArtist;
    }

    public String getmDuration() {
        return mDuration;
    }

    public int getmMusicReferenceId() {
        return mMusicReferenceId;
    }

    public int getmImageMusicReferenceId() {
        return mImageMusicReferenceId;
    }

    //Begin Parcelable Methods

    public Music(Parcel parcel) {
        String[] data = new String[4];
        parcel.readStringArray(data);
        this.mName = data[0];
        this.mArtist = data[1];
        this.mMusicReferenceId = Integer.parseInt(data[2]);
        this.mImageMusicReferenceId = Integer.parseInt(data[3]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.mName,
                String.valueOf(this.mArtist),
                String.valueOf(this.mMusicReferenceId),
                String.valueOf(this.mImageMusicReferenceId)});

    }

    private void readFromParcel(Parcel parcel) {
        mName = parcel.readString();
        mArtist = parcel.readString();
        mMusicReferenceId = parcel.readInt();
        mImageMusicReferenceId = parcel.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Music createFromParcel(Parcel parcel) {
            return new Music(parcel);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    //End Parcelable Methods


}
