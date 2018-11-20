package com.example.arizz.ponggame_prj3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //public static PongGame game;

    public final static String MA = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //game = new PongGame(this);
        setContentView(R.layout.activity_main);
    }

    /*public void onStart() {
        super.onStart();
        updateView();
    }*/

    /*public void updateView() {
        TextView bestScoreTV = (TextView) findViewById(R.id.best_score);
        bestScoreTV.setText(game.getHighestHits());
    }*/

    /**
     * When the Play Pong button gets clicked
     * @param v the view
     */
    public void playGame(View v) {
        Intent playIntent = new Intent(this, GameActivity.class);
        this.startActivity(playIntent);
        overridePendingTransition(R.anim.slide_from_left, 0);
    }
}
