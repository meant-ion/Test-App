package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final ArrayList<String> bandNamesList;
    private final ArrayList<String> songTitlesList;
    private final ArrayList albumNameList;
    private final RVClickListener RVlistener;

    public Adapter(ArrayList nl, ArrayList stl, ArrayList anl, RVClickListener rvcl) {
        this.bandNamesList = nl;
        this.songTitlesList = stl;
        this.albumNameList = anl;
        this.RVlistener = rvcl;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listView, RVlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        int res = (int) albumNameList.get(position);
        holder.albumCover.setImageResource(res);
        holder.songTitle.setText(songTitlesList.get(position));
        holder.bandName.setText(bandNamesList.get(position));
    }

    @Override
    public int getItemCount() {
        return bandNamesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private final TextView bandName;
        private final TextView songTitle;
        private final ImageView albumCover;
        private final RVClickListener listener;
        private final Context c;

        //put these lists here, since the view item needs the links to click on, and doing it some
        //other way was just gonna be a waste of time.
        private final ArrayList<String> songClips = new ArrayList<>(Arrays.asList(
                "https://youtu.be/5kwIkF6LFDc?t=22","https://youtu.be/R-MSfd2S7lo",
                "https://youtu.be/IcSxkipfaYo","https://youtu.be/cgKg3_iPO8E",
                "https://youtu.be/SQNtGoM3FVU","https://youtu.be/BGHlZwMYO9g?t=10"
        ));

        private final ArrayList<String> bandWikis = new ArrayList<>(Arrays.asList(
                "https://en.wikipedia.org/wiki/Alien_Weaponry","https://en.wikipedia.org/wiki/Kyuss",
                "https://en.wikipedia.org/wiki/Reignwolf","https://en.wikipedia.org/wiki/Probot",
                "https://en.wikipedia.org/wiki/Jinjer","https://en.wikipedia.org/wiki/Gojira_(band)"
        ));

        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            this.albumCover = itemView.findViewById(R.id.cover_image);
            this.bandName = itemView.findViewById(R.id.artistName);
            this.songTitle = itemView.findViewById(R.id.songTitle);
            this.c = itemView.getContext();
            itemView.setOnCreateContextMenuListener(this);
            this.listener = passedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("ON_CLICK", "In the onclick in the viewholder class");
            listener.onClick(v, getAdapterPosition());
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(songClips.get(getAdapterPosition())));
            c.startActivity(intent);
        }

        //---------------------------CONTEXT MENU STUFF--------------------------------------------------
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.contextmenu, menu);
            menu.getItem(0).setOnMenuItemClickListener(onMenu);
            menu.getItem(1).setOnMenuItemClickListener(onMenu);
            menu.getItem(2).setOnMenuItemClickListener(onMenu);
        }

        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
          @Override
          public boolean onMenuItemClick(MenuItem item) {
              Log.i("On_Click", "Opening for " + bandName.getText());
              final Intent intent;
              switch (getAdapterPosition()) {
                  case 0:
                      intent = new Intent(Intent.ACTION_VIEW, Uri.parse(songClips.get(getAdapterPosition())));
                      break;
                  case 1://couldn't find a wiki page for the songs, so this is what happened
                  case 2:
                      intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bandWikis.get(getAdapterPosition())));
                      break;
                  default:
                      return false;
              }
              c.startActivity(intent);
              return true;
          }
        };
        //---------------------------CONTEXT MENU STUFF--------------------------------------------------
    }
}
