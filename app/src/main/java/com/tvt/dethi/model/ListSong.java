package com.tvt.dethi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListSong {

    @SerializedName("music")
    private Song[] arrSong;

    public Song[] getSong() {
        return arrSong;
    }
}
