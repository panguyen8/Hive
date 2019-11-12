package com.example.hive.Hive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class HiveView extends SurfaceView {

    protected HiveGameState state;

    Paint hexagonalPaint = new Paint();
    Paint hexagonalTargetPaint = new Paint();

    public HiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        hexagonalPaint.setColor(Color.WHITE);
        hexagonalTargetPaint.setColor(Color.RED);
    }

    public void setState(HiveGameState state) {
        this.state = state;
    }

    public void onDraw(Canvas canvas)  {
        for(int i = 0; i < 12; i++) {
            for (int x = 0; x < 12; x++) {
                if (i%2 == 0) {
                    drawHexagon(canvas, x * 100, i * 66);
                } else {
                    drawHexagon(canvas, x * 100 + 50, i * 66);
                }
            }
        }
    }

    public void drawHexagon(Canvas canvas, int startX, int startY) {
        canvas.drawLine(startX, startY+33, startX+50, startY, hexagonalPaint);
        canvas.drawLine(startX+50, startY, startX+100, startY+33, hexagonalPaint);
        canvas.drawLine(startX+100, startY+33, startX+100, startY+66, hexagonalPaint);
        canvas.drawLine(startX+100, startY+66, startX+50, startY+100, hexagonalPaint);
        canvas.drawLine(startX+50, startY+100, startX, startY+66, hexagonalPaint);
        canvas.drawLine(startX, startY+66, startX, startY+33, hexagonalPaint);
    }

    public void drawTargetHexagon(Canvas canvas, int startX, int startY) {
        canvas.drawLine(startX, startY+33, startX+50, startY, hexagonalTargetPaint);
        canvas.drawLine(startX+50, startY, startX+100, startY+33, hexagonalTargetPaint);
        canvas.drawLine(startX+100, startY+33, startX+100, startY+66, hexagonalTargetPaint);
        canvas.drawLine(startX+100, startY+66, startX+50, startY+100, hexagonalTargetPaint);
        canvas.drawLine(startX+50, startY+100, startX, startY+66, hexagonalTargetPaint);
        canvas.drawLine(startX, startY+66, startX, startY+33, hexagonalTargetPaint);
    }
    }
