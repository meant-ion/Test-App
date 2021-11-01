package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<String> bandList = new ArrayList<>(Arrays.asList("  Alien Weaponry",
            "  Kyuss", "  Reignwolf", "  Probot", "  Jinjer", "  Gojira"));
    private final ArrayList<String> songList = new ArrayList<>(Arrays.asList("Kai Tangata", "Green Machine",
            "Hardcore", "Centuries of Sin", "Picses - Live Session", "L'enfant sauvage"));
    private final ArrayList<Integer> coverList = new ArrayList<>(Arrays.asList(R.drawable.img1,R.drawable.img2,
            R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6));
    private boolean isListView = true;
    private boolean isGridView = false;
    private RecyclerView daView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daView = findViewById(R.id.recycler_view);

        RVClickListener listener = (view,position) -> {
            TextView name = view.findViewById(R.id.artistName);
            Toast.makeText(this,name.getText(),Toast.LENGTH_SHORT).show();
        };

        Adapter adapter = new Adapter(bandList, songList, coverList, listener);
        daView.setHasFixedSize(true);
        daView.setAdapter(adapter);
        registerForContextMenu(daView);
        daView.setLayoutManager(new LinearLayoutManager(this));
    }
    //--------------OPTIONS MENU STUFF-------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        switch (i.getItemId()) {
            case R.id.list_view:
                if (!isListView) {
                    isListView = false;
                    daView.setLayoutManager(new LinearLayoutManager(this));
                    isGridView = true;
                }
                break;
            case R.id.grid_view:
                if (!isGridView) {
                    isListView = true;
                    daView.setLayoutManager(new GridLayoutManager(this,2));
                    isGridView = false;
                }
                break;
            default:
                return false;
        }
        return true;
    }
    //----------------------------------------------------------
}