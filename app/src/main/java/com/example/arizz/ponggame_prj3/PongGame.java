package com.example.arizz.ponggame_prj3;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class PongGame {
    public final static String MA = "MainActivity";

    private static final String PREFERENCE_HITS = "highestHits";

    private Rect pongRect;
    private int pongWidth;
    private int deltaTime; // in milliseconds

    private Rect paddleRect;
    private int paddleWidth;
    private int paddleHeight;
    private boolean paddleHit;

    private Point ballCenter;
    private int ballRadius;
    private float ballAngle;
    private float ballSpeedX;
    private float ballSpeedY;
    private boolean ballDropped;

    private int numberHits;

    /*public PongGame(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        setHighestHits(pref.getInt(PREFERENCE_HITS, 0));
    }*/

    public PongGame(Rect newPaddleRect, int newBallRadius, float newBallSpeed,
                    float ballAngle) {
        setPaddleRect(newPaddleRect);
        setBallRadius(newBallRadius);
        setBallSpeed(newBallSpeed);
        setBallAngle(ballAngle);
        ballDropped = false;
        paddleHit = false;
        numberHits = 0;
    }

    // call setPreferences when game ends
    /*public void setPreferenceHits(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREFERENCE_HITS, highestHits);
        editor.commit();
    }*/

    public void setHits(int newHits) {
        numberHits = newHits;
    }

    public int getHits() {
        return numberHits;
    }

    public int getPongWidth() {
        pongWidth = pongRect.right;
        return pongWidth;
    }

    public void setPongRect(Rect newPongRect) {
        if(newPongRect != null) {
            pongRect = newPongRect;
        }
    }

    public void setDeltaTime(int newDeltaTime) {
        if(newDeltaTime > 0) {
            deltaTime = newDeltaTime;
        }
    }

    public Rect getPaddleRect() {
        return paddleRect;
    }

    public void setPaddleRect(Rect newPaddleRect) {
        if (newPaddleRect != null) {
            paddleWidth = newPaddleRect.right - newPaddleRect.left;
            paddleHeight = newPaddleRect.bottom - newPaddleRect.top;
            paddleRect = newPaddleRect;
        }
    }

    public void setPaddlePosition(int left, int right) {
        if (right >= pongRect.right) {
            paddleRect.right = pongRect.right;
            paddleRect.left = paddleRect.right + paddleWidth;
        }
        else if (left <= pongRect.left) {
            paddleRect.left = pongRect.left;
            paddleRect.right = pongRect.left - paddleWidth;
        }
        else {
            paddleRect.left = left;
            paddleRect.right = right;
        }
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public Point getBallCenter() {
        return ballCenter;
    }

    public int getBallRadius() {
        return ballRadius;
    }

    public void setBallRadius(int newBallRadius) {
        if(newBallRadius > 0) {
            ballRadius = newBallRadius;
        }
    }

    public void setBallCenter(int x, int y){
        ballCenter = new Point(x, y);
    }

    public void setBallSpeed(float newBallSpeed) {
        ballSpeedX = newBallSpeed;
        ballSpeedY = newBallSpeed;
    }

    public void setBallAngle(float newBallAngle) {
        ballAngle = newBallAngle;
    }

    public void moveBall() {
        // checking the top
        if (ballCenter.y + ballSpeedY < pongRect.top) {
            ballSpeedY = -ballSpeedY;
        }

        // checking the right and left
        if (ballCenter.x + ballSpeedX > pongRect.right ||
                ballCenter.x + ballSpeedX < pongRect.left) {
            ballSpeedX = -ballSpeedX;
        }

        ballCenter.x += ballSpeedX * Math.cos(ballAngle) * deltaTime;
        ballCenter.y += ballSpeedY * Math.sin(ballAngle) * deltaTime;
    }

    public void loadBall() {
        ballDropped = false;
        setBallCenter(getPongWidth()/2, 2*getBallRadius());
    }

    /**
     * Used for game over state
     * @return true if off screen
     */
    public boolean ballOffScreen() {
        return ballCenter.y + ballSpeedY > pongRect.bottom;
    }

    /**
     * Checks if the ball is dropped
     * @return true if the ball is dropped
     */
    public boolean isBallDropped() {
        return ballDropped;
    }

    /**
     * "Drops" ball
     */
    public void dropBall() {
        ballDropped = true;
    }

    public void setPaddleHit(boolean newPaddleHit) {
        paddleHit = newPaddleHit;
    }

    public float getBallSpeedY() {
        return ballSpeedY;
    }

    public void setBallSpeedY(float newBallSpeedY) {
        ballSpeedY = newBallSpeedY;
    }

    /**
     * Checks if ball intersects paddle
     * @return true if intersects
     */
    public boolean paddleTouch() {
        return paddleRect.intersects(
                ballCenter.x - ballRadius, ballCenter.y - ballRadius,
                ballCenter.x + ballRadius, ballCenter.y + ballRadius);
    }
}
