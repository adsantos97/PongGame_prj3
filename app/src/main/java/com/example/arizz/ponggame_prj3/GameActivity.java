package com.example.arizz.ponggame_prj3;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Timer;

public class GameActivity extends Activity {

    private GameView gameView;
    private GestureDetector detector;
    private PongGame game;

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

        // write data in PongGame to user preferences
        //game.setPreferenceHits(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    private class TouchHandler extends GestureDetector.SimpleOnGestureListener {
        public boolean onDoubleTapEvent(MotionEvent event) {
            updatePaddle(event);
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent event) {
            if(!game.isBallDropped()) {
                game.dropBall();
            }
            return true;
        }

        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                                float d1, float d2) {
            updatePaddle(event2); // move motion event
            return true;
        }

        public void updatePaddle(MotionEvent event) {
            //float x = event.getX() - game.getPaddleRect().x;
            //float y = game.getPaddleCenter().y - event.getY();
        }
    }
}
