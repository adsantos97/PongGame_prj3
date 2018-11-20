package com.example.arizz.ponggame_prj3;

import android.graphics.Point;
import android.graphics.Rect;

public class PongGame {
    private static final String PREFERENCE_HITS = "highestHits";

    private Rect pongRect;
    private int deltaTime; // in milliseconds

    private Rect paddleRect;
    private Point paddleCenter;
    private int paddleWidth;
    private int paddleHeight;
    private float paddleSpeed;
    private boolean paddleHit;

    private Point ballCenter;
    private int ballRadius;
    private float ballAngle;
    private float ballSpeed;
    private boolean ballDropped;

    private int numberHits;

    /*public PongGame(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        setHighestHits(pref.getInt(PREFERENCE_HITS, 0));
    }*/

    public PongGame(Rect newPaddleRect, int newBallRadius,
                    float newPaddleSpeed, float newBallSpeed) {
        setPaddleRect(newPaddleRect);
        setPaddleSpeed(newPaddleSpeed);
        setBallRadius(newBallRadius);
        setBallSpeed(newBallSpeed);
        ballDropped = false;
        paddleHit = false;
    }

    /*public void setPreferenceHits(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREFERENCE_HITS, highestHits);
        editor.commit();
    }*/

    /*public void setHighestHits(int newHits) {
        if (newHits >= 0) {
            highestHits = newHits;
        }
    }*/

    /*public int getHighestHits() {
        return highestHits;
    }*/

    public Rect getPongRect() {
        return pongRect;
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
            paddleCenter.x = paddleWidth / 2;
            paddleCenter.y = paddleHeight / 2;
        }
    }

    public void setPaddleSpeed(float newPaddleSpeed) {
        if(newPaddleSpeed > 0) {
            paddleSpeed = newPaddleSpeed;
        }
    }

    public Point getPaddleCenter() {
        return paddleCenter;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    /*public void setPaddle(Point newPaddleCenter, int newPaddleRadius,
                          int newPaddleLength) {
        if(newPaddleCenter != null && newPaddleLength > 0) {
            paddleCenter = newPaddleCenter;
            paddleWidth = newPaddleLength;
            paddleRadius = newPaddleRadius;
        }
    }*/

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

    public void setBallSpeed(float newBallSpeed) {
        if(newBallSpeed > 0) {
            ballSpeed = newBallSpeed;
        }
    }

    public void moveBall() {
        ballCenter.x += ballSpeed * Math.cos(ballAngle) * deltaTime;
        ballCenter.y -= ballSpeed * Math.sin(ballAngle) * deltaTime;
    }

    /**
     * Used to check if the ball hits the sides of the screen
     * Checks right, left, and top
     * @return true if the balls hits a side
     */
    public boolean ballHitSide() {
        return ballCenter.x + ballRadius == pongRect.right // right
                || ballCenter.x - ballRadius == pongRect.left // left
                || ballCenter.y - ballRadius == 0; // top
    }

    /**
     * Used for game over state
     * @return true if off screen
     */
    public boolean ballOffScreen() {
        return ballCenter.y - ballRadius > pongRect.bottom;
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

    public boolean isPaddleHit() {
        return paddleHit;
    }

    public void setPaddleHit(boolean newPaddleHit) {
        paddleHit = newPaddleHit;
    }

    /**
     * Checks if ball intersects paddle
     * @return
     */
    public boolean paddleTouch() {
        return paddleRect.intersects(
                ballCenter.x - ballRadius, ballCenter.y - ballRadius,
                ballCenter.x + ballRadius, ballCenter.y - ballRadius);
    }
}
