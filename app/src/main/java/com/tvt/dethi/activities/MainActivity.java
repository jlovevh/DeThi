package com.tvt.dethi.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tvt.dethi.R;
import com.tvt.dethi.adapter.SongAdapter;
import com.tvt.dethi.api.APIUtils;
import com.tvt.dethi.api.JsonReponse;
import com.tvt.dethi.model.ListSong;
import com.tvt.dethi.model.Song;
import com.tvt.dethi.service.SongService;
import com.tvt.dethi.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SongAdapter adapter;
    private List<Song> arrSong ;
    private SongService songService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.list_item);
       APIUtils.getAPIClient().getAllSong().enqueue(new Callback<ListSong>() {
           @Override
           public void onResponse(Call<ListSong> call, Response<ListSong> response) {
                ListSong listSong=response.body();

               arrSong=new ArrayList<>( Arrays.asList(listSong.getSong()));
               adapter=new SongAdapter(MainActivity.this,arrSong);
               listView.setAdapter(adapter);
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onFailure(Call<ListSong> call, Throwable t) {
               Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
           }
       });


       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this,SongService.class);
                intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                intent.putParcelableArrayListExtra("song", (ArrayList<? extends Parcelable>) arrSong);
                intent.putExtra("position",position);
                startService(intent);
                Log.d("tien", "posit:" + position);
                Log.d("tien.mtp","Size: "+arrSong.size());
//                bindService(intent,serviceConnection,BIND_AUTO_CREATE);

           }
       });
    }
}
