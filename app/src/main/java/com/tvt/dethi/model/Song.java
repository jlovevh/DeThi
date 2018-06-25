package com.tvt.dethi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("album")
    @Expose
    private String album;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("trackNumber")
    @Expose
    private Integer trackNumber;
    @SerializedName("totalTrackCount")
    @Expose
    private Integer totalTrackCount;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("site")
    @Expose
    private String site;

    public Song(String title, String album, String artist, String genre, String source, String image, Integer trackNumber, Integer totalTrackCount, Integer duration, String site) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.genre = genre;
        this.source = source;
        this.image = image;
        this.trackNumber = trackNumber;
        this.totalTrackCount = totalTrackCount;
        this.duration = duration;
        this.site = site;
    }

    protected Song(Parcel in) {
        title = in.readString();
        album = in.readString();
        artist = in.readString();
        genre = in.readString();
        source = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            trackNumber = null;
        } else {
            trackNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            totalTrackCount = null;
        } else {
            totalTrackCount = in.readInt();
        }
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        site = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Integer getTotalTrackCount() {
        return totalTrackCount;
    }

    public void setTotalTrackCount(Integer totalTrackCount) {
        this.totalTrackCount = totalTrackCount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeString(genre);
        dest.writeString(source);
        dest.writeString(image);
        if (trackNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trackNumber);
        }
        if (totalTrackCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalTrackCount);
        }
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeString(site);
    }
}