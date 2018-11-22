package com.example.arizz.ponggame_prj3;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class PongGame {
    public final static String MA = "MainActivity";

    private static final String PREFERENCE_HITS = "highestHits";

    private Rect pongRect;
    private int deltaTime; // in milliseconds

    private Rect paddleRect;
    private int paddleWidth;
    private int paddleHeight;
    private Point paddleCenter;
    private boolean paddleHit;

    private Point ballCenter;
    private int ballRadius;
    private float ballAngle;
    private float ballSpeedX;
    private float ballSpeedY;
    private boolean ballDropped;

    private int numberHits = 0;

    /*public PongGame(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        setHighestHits(pref.getInt(PREFERENCE_HITS, 0));
    }*/

    /*public PongGame(Rect newPaddleRect, int newBallRadius,
                    float newPaddleSpeed, float newBallSpeed) {
        setPaddleRect(newPaddleRect);
        setPaddleSpeed(newPaddleSpeed);
        setBallRadius(newBallRadius);
        setBallSpeed(newBallSpeed);
        ballDropped = false;
        paddleHit = false;
    }*/

    public PongGame(Rect newPaddleRect, int newBallRadius, float newBallSpeed,
                    float ballAngle) {
        setPaddleRect(newPaddleRect);
        setBallRadius(newBallRadius);
        setBallSpeed(newBallSpeed);
        setBallAngle(ballAngle);
        ballDropped = false;
    }

    // call setPreferences when game ends
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
        }
    }

    public void setPaddlePosition(int left, int right) {
        if (right >= pongRect.right) {
            /*Log.w(MA, "in if in PongGame");
            Log.w(MA, "paddle right: " + paddleRect.right);
            Log.w(MA, "right: " + right);*/
            paddleRect.right = pongRect.right;
            //Log.w(MA, "pong right: " + pongRect.right);
            //Log.w(MA, "right: " + paddleRect.right);
            paddleRect.left = paddleRect.right + paddleWidth;
            /*Log.w(MA, "paddlewidth: " + paddleWidth);
            Log.w(MA, "left pos in first: " + left);
            Log.w(MA, "right in first: " + paddleRect.right);
            Log.w(MA, "left in first: " + paddleRect.left);*/
        }
        else if (left <= pongRect.left) {
            paddleRect.left = pongRect.left;
            paddleRect.right = pongRect.left - paddleWidth;
        }
        else {
            //Log.w(MA, "--------------");
            paddleRect.left = left;
            paddleRect.right = right;
            //Log.w(MA, "end--------------");
        }

    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
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
        //if(newBallSpeed > 0) {
            ballSpeedX = newBallSpeed;
            ballSpeedY = newBallSpeed;
        //}
    }

    public void setBallAngle(float newBallAngle) {
        ballAngle = newBallAngle;
    }

    public void moveBall() {
        // checking the top and bottom
        if (ballCenter.y + ballSpeedY > pongRect.bottom ||
                ballCenter.y + ballSpeedY < pongRect.top) {
            //Log.w(MA, "In moveBall in first if");
            ballSpeedY = -ballSpeedY;
        }

        // checking the right and left
        if (ballCenter.x + ballSpeedX > pongRect.right ||
                ballCenter.x + ballSpeedX < pongRect.left) {
            //Log.w(MA, "In moveBall in second if");
            ballSpeedX = -ballSpeedX;
        }

        ballCenter.x += ballSpeedX * Math.cos(ballAngle) * deltaTime;
        ballCenter.y += ballSpeedY * Math.sin(ballAngle) * deltaTime;
        /*Log.w(MA, "ballCenter.x + paddleWidth: " + (ballCenter.x + ballRadius));
        Log.w(MA, "ballCenter.y + paddleWidth: " + (ballCenter.y + ballRadius));
        Log.w(MA, "ballCenter.x - paddleWidth: " + (ballCenter.x - ballRadius));
        Log.w(MA, "ballCenter.y - paddleWidth: " + (ballCenter.y - ballRadius));
        Log.w(MA, "paddleRect.left: " + paddleRect.left);
        Log.w(MA, "paddleRect.top: " + paddleRect.top);
        Log.w(MA, "paddleRect.right: " + paddleRect.right);
        Log.w(MA, "paddleRect.bottom: " + paddleRect.bottom);
        Log.w(MA, "height: " + paddleHeight);*/
    }

    public void loadBall() {
        ballDropped = false;
    }

    /**
     * Used to check if the ball hits the sides of the screen
     * Checks right, left, and top
     * @return true if the balls hits a side
     */
    public boolean ballHitSide() {
        return (ballCenter.x + ballRadius) == pongRect.right // right >
                || (ballCenter.x - ballRadius) == pongRect.left // left <
                || (ballCenter.y - ballRadius) == pongRect.top; // top <
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

    public void setPaddleHit(boolean newDuckShot) {
        paddleHit = newDuckShot;
    }

    public float getBallSpeedX() {
        return ballSpeedX;
    }

    public float getBallSpeedY() {
        return ballSpeedY;
    }

    public void setBallSpeedX(float newBallSpeedX) {
        ballSpeedX = newBallSpeedX;
    }

    public void setBallSpeedY(float newBallSpeedY) {
        ballSpeedY = newBallSpeedY;
    }

    /**
     * Checks if ball intersects paddle
     * @return
     */
    public boolean paddleTouch() {
        return paddleRect.intersects(
                ballCenter.x - ballRadius, ballCenter.y - ballRadius,
                ballCenter.x + ballRadius, ballCenter.y + ballRadius);
                //ballCenter.x == paddleRect.top && ballCenter.y == paddleRect.top;

                /*paddleRect.intersects(
                ballCenter.x , ballCenter.y,
                ballCenter.x, ballCenter.y);*/

                //(ballCenter.y + ballRadius) >= paddleRect.top;
        /*paddleRect.intersects(
                ballCenter.x - ballRadius, ballCenter.y - ballRadius,
                ballCenter.x + ballRadius, ballCenter.y + ballRadius);*/
    }
}
