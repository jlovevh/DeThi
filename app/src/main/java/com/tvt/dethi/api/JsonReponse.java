package com.tvt.dethi.api;

import com.tvt.dethi.model.ListSong;
import com.tvt.dethi.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonReponse {

    @GET(value = "automotive-media/music.json")
    Call<ListSong> getAllSong();



}
