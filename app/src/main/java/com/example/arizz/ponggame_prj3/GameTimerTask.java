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
        if(game.isBallDropped()) {
            game.moveBall();
        }

        if(game.paddleTouch()) {
            game.setPaddleHit(true);
            ((GameActivity) gameView.getContext()).playHitSound();
            game.setHits(game.getHits() + 1);
            game.setPaddleHit(true);
            game.setBallSpeedY(-1*game.getBallSpeedY());
        }

        gameView.postInvalidate();
    }
}
