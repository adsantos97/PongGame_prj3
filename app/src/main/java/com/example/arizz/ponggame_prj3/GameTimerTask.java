package com.example.arizz.ponggame_prj3;

import android.util.Log;

import java.util.TimerTask;

public class GameTimerTask extends TimerTask {
    public final static String MA = "MainActivity";
    private PongGame game;
    private GameView gameView;

    public GameTimerTask(GameView view) {
        gameView = view;
        game = view.getGame();
    }

    public void run() {
        if(game.isBallDropped()) {
            game.moveBall();
            //Log.w(MA, "getBallSpeedX: " + game.getBallSpeedX());
            //Log.w(MA, "getBallSpeedY " + game.getBallSpeedY());
        }
        if(game.paddleTouch()) {
            //Log.w(MA, "in paddleTouch");
            game.setPaddleHit(true);
            //Log.w(MA, "getBallSpeedX: " + game.getBallSpeedX());
            //Log.w(MA, "getBallSpeedY " + game.getBallSpeedY());
            game.setBallSpeedY(-1*game.getBallSpeedY());
            /*if(game.getBallSpeedX() > 0 && game.getBallSpeedY() > 0) {
                Log.w(MA, "first if");
                game.setBallSpeedY(-1*game.getBallSpeedY());
            }
            else if(game.getBallSpeedX() < 0 && game.getBallSpeedY() > 0) {
                Log.w(MA, "second if");
                game.setBallSpeedY(-1*game.getBallSpeedY());
            }*/
            //game.setBallSpeed(-1*game.getBallSpeedY());
            //Log.w(MA, "paddleTouch() getBallSpeedX: " + game.getBallSpeedX());
            //Log.w(MA, "paddleTouch() getBallSpeedY " + game.getBallSpeedY());
        }

        gameView.postInvalidate();
    }
}
