package com.example.arizz.ponggame_prj3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GameView extends View {
    public static int DELTA_TIME = 100;

    private PongGame game;

    private Paint paint_ball, paint_paddle;
    private int height;
    private int width;

    public GameView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;

        Rect paddleRect = new Rect(0, 0, width/7, height/16);
        game = new PongGame(paddleRect, 5, .03f, .2f);
        game.setPaddleSpeed(width * .00003f);
        game.setBallSpeed(width * .0003f);
        game.setDeltaTime(DELTA_TIME);

        game.setPongRect(new Rect(0, 0, width, height));

        // define style and color for ball
        paint_ball = new Paint();
        paint_ball.setColor(0xFFFFD700);
        paint_ball.setAntiAlias(true);
        paint_ball.setStrokeWidth(10.0f);

        // define style and color for paddle
        paint_paddle = new Paint();
        paint_paddle.setColor(0xFF4682B4);
        paint_paddle.setAntiAlias(true);
        paint_paddle.setStrokeWidth(20.0f);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw ball
        //canvas.drawCircle(width / 2, height / 15, width / 35, paint_ball);
        canvas.drawCircle(game.getBallCenter().x, game.getBallCenter().y,
                game.getBallRadius(), paint_ball);

        // draw paddle
        canvas.drawLine(0 + (3*(width/7)), height - (height / 16),
                width - (3*(width/7)), height - (height / 16), paint_paddle);

        // draw paddle
        /*canvas.drawRect(0 + (3*(width/7)), height - (height / 16),
                width - (3*(width/7)), height - (height / 16), paint_paddle);*/
    }

    public PongGame getGame() {
        return game;
    }
}
