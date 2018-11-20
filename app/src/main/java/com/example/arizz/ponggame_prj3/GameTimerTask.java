package com.example.arizz.ponggame_prj3;

import java.util.TimerTask;

public class GameTimerTask extends TimerTask {
    private PongGame game;
    private GameView gameView;

    public GameTimerTask(GameView view) {
        gameView = view;
        game = view.getGame();
    }

    public void run() {
        if(game.ballOffScreen()) {
            game.loadBall();
        }
        else if (game.isBallDropped()) {
            game.moveBall();
        }
        gameView.postInvalidate();
    }
}
