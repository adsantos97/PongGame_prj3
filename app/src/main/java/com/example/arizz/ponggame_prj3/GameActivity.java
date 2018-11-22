package com.example.arizz.ponggame_prj3;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;

public class GameActivity extends Activity {
    public final static String MA = "MainActivity";

    private GameView gameView;
    private GestureDetector detector;
    private PongGame game;

    private SoundPool pool;
    private int hitSoundID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get status bar height
        Resources res = getResources();
        int statusBarHeight = 0;
        int statusBarId =
                res.getIdentifier("status_bar_height", "dimen", "android");
        if(statusBarId > 0)
            statusBarHeight = res.getDimensionPixelSize(statusBarId);

        // set up the game view
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new GameView(this, size.x, size.y - statusBarHeight);
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new GameTimerTask(gameView), 0, GameView.DELTA_TIME);

        game = gameView.getGame();
        TouchHandler th = new TouchHandler();
        detector = new GestureDetector(this, th);
        detector.setOnDoubleTapListener(th);

        SoundPool.Builder poolBuilder = new SoundPool.Builder();
        poolBuilder.setMaxStreams(1);
        pool = poolBuilder.build();
        hitSoundID = pool.load(this, R.raw.paddle_hit, 1);
        // write data in PongGame to user preferences
        //game.setPreferenceHits(this);
    }

    public void playHitSound() {
        pool.play(hitSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    private class TouchHandler extends GestureDetector.SimpleOnGestureListener {
        public boolean onSingleTapConfirmed(MotionEvent event) {
            if(game.ballOffScreen()) {
                game.setHits(0);
                game.loadBall();
            }
            if(!game.isBallDropped()) {
                game.dropBall();
            }
            return true;
        }

        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                                float d1, float d2) {
            if(!game.isBallDropped()) {
                game.dropBall();
            }
            updatePaddle(event2);
            return true;
        }

        public void updatePaddle(MotionEvent event) {
            int right = (int)event.getX() + (game.getPaddleWidth()/2);
            int left = right - game.getPaddleWidth();

            game.setPaddlePosition(right, left);
        }
    }
}
