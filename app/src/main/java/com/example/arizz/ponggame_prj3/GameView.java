package com.example.arizz.ponggame_prj3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GameView extends View {
    public static int DELTA_TIME = 100;

    private PongGame game;

    private Rect paddleRect;
    private Paint paint_ball, paint_paddle, paint_text, paint_text2;
    private int height;
    private int width;

    public GameView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;

        paddleRect = new Rect(width - (3 * width/7),  height - (2 * height/22),
                3 * width/7, height - (height/16));

        // width
        game = new PongGame(paddleRect, 20, .4f, (float) 45);
        game.setDeltaTime(DELTA_TIME);
        game.setBallCenter(width/2, 2*game.getBallRadius());
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
        paint_paddle.setStrokeWidth(10.0f);

        paint_text = new Paint();
        paint_text.setColor(Color.BLACK);
        paint_text.setTextSize(70);
        paint_text.setTextAlign(Paint.Align.CENTER);

        paint_text2 = new Paint();
        paint_text2.setColor(Color.BLACK);
        paint_text2.setTextSize(50);
        paint_text2.setTextAlign(Paint.Align.CENTER);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw ball
        if (!game.ballOffScreen()) {
            canvas.drawCircle(game.getBallCenter().x, game.getBallCenter().y,
                    game.getBallRadius(), paint_ball);
        }
        else {
            canvas.drawText("GAME OVER!", width/2, height/2, paint_text);
            canvas.drawText("Score: " + game.getHits(), width/2,
                    height/2 + 90, paint_text);
            canvas.drawText("(Tap to play again.)", width/2,
                    height/2 + 160, paint_text2);
        }
        // draw paddle
        canvas.drawRect(game.getPaddleRect().left, game.getPaddleRect().top,
                game.getPaddleRect().right, game.getPaddleRect().bottom, paint_paddle);

    }

    public PongGame getGame() {
        return game;
    }
}
