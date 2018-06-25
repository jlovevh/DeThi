package com.tvt.dethi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tvt.dethi.R;
import com.tvt.dethi.model.Song;
import com.tvt.dethi.utils.Const;

import java.util.List;

public class SongAdapter extends BaseAdapter {
    private Context context;
    private List<Song> arrSong;

    public SongAdapter(Context context, List<Song> arrSong) {
        this.context = context;
        this.arrSong = arrSong;
    }

    @Override
    public int getCount() {
        return arrSong.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class ViewHolder{
        private ImageView img;
        private TextView txtTitle,txtArtist;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
           holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            holder.img=convertView.findViewById(R.id.img);
            holder.txtTitle=convertView.findViewById(R.id.title);
            holder.txtArtist=convertView.findViewById(R.id.artist);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Song song=arrSong.get(position);
        Picasso.with(context).load(Const.SOURCE_URL+song.getImage()).error(R.drawable.default_album_art).placeholder(R.mipmap.ic_launcher_round).into(holder.img);

        holder.txtTitle.setText(song.getTitle());
        holder.txtArtist.setText(song.getArtist());

        return convertView;
    }
}
