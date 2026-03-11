package com.example.adjustment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import androidx.navigation.NavController;




public class DictionarySearch extends AppCompatActivity {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String param1 = null;
    private String param2 = null;
    TextView txt1, txt2;
    YouTubePlayerView videoView;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_search);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null && arguments.containsKey(ARG_PARAM1) && arguments.containsKey(ARG_PARAM2)) {
            String param1 = arguments.getString(ARG_PARAM1);
            String param2 = arguments.getString(ARG_PARAM2);
            // Do something with param1 and param2
        }
        txt1 = findViewById(R.id.textView1);
        txt2 = findViewById(R.id.textView2);
        videoView = findViewById(R.id.videoView);

        String word = getIntent().getStringExtra("name");
        String example = getIntent().getStringExtra("example");
        String url = getIntent().getStringExtra("url");
        //Toast.makeText(this, url, Toast.LENGTH_LONG).show();
        txt1.setText(word);
        txt2.setText(example);

        // Find the VideoView within the parent layout

        videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                // Add your code here

                youTubePlayer.loadVideo(url,0);
                videoView.requestFocus();
                youTubePlayer.play();
            }
        });

        //bottom nav
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle item selection here
                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.btnHome:
                        fragment = new HomeFragment();
                        break;
                    case R.id.btnSign:
                        fragment = new ScanFragment();
                        break;
                    case R.id.btnContest:
                        fragment = new ContestFragment();
                        break;
                    case R.id.btnSettings:
                        fragment = new SettingFragment();
                        break;
                    default:
                        return false;
                }

                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
        });
    }
    private void navigateToHomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new HomeFragment());
        fragmentTransaction.commit();
    }

    private void navigateToScanFragment() {
        // Navigate to the Profile fragment or activity
        // Add your navigation logic here
    }

    private void navigateToContestFragment() {
        // Navigate to the Profile fragment or activity
        // Add your navigation logic here
    }

    private void navigateToSettingsFragment() {
        // Navigate to the Settings fragment or activity
        // Add your navigation logic here
    }


    public static Intent newInstance(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DictionarySearch.class);
        intent.putExtra(ARG_PARAM1, param1);
        intent.putExtra(ARG_PARAM2, param2);
        return intent;
    }


}