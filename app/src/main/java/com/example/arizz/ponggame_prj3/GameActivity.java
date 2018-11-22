package com.example.arizz.ponggame_prj3;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Timer;

public class GameActivity extends Activity {
    public final static String MA = "MainActivity";

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
        public boolean onSingleTapConfirmed(MotionEvent event) {
            if(!game.isBallDropped()) {
                game.dropBall();
                game.moveBall();
            }
            return true;
        }

        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                                float d1, float d2) {
            updatePaddle(event2);
            return true;
        }

        public void updatePaddle(MotionEvent event) {
            int right = (int)event.getX() + (game.getPaddleWidth()/2);
            int left = right - game.getPaddleWidth();

            /*Log.w(MA, "getX: " + event.getX());
            Log.w(MA, "paddle center: " + game.getPaddleWidth());
            Log.w(MA, "left: " + left);
            Log.w(MA, "paddle left: " + game.getPaddleRect().left);
            Log.w(MA, "left pong: " + game.getPongRect().left);
            Log.w(MA, "right: " + right);
            Log.w(MA, "paddle right: " + game.getPaddleRect().right);
            Log.w(MA, "right pong: " + game.getPongRect().right);*/

            //Log.w(MA, "paddle left: " + game.getPaddleRect().left);
            //Log.w(MA, "paddle right: " + game.getPaddleRect().right);
            game.setPaddlePosition(right, left);
            /*
            if(right <= game.getPongRect().left) {
                Log.w(MA, "in first if");
                game.setPaddleRect(new Rect(game.getPongRect().left, game.getPaddleRect().top,
                        game.getPaddleWidth(), game.getPaddleRect().bottom));
            }
            else {
                Log.w(MA, "in else");
                game.setPaddleRect(new Rect(left, game.getPaddleRect().top,
                        right, game.getPaddleRect().bottom));
            }
            */
        }
    }
}
