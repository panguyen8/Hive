package com.example.hive.Hive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class HiveView extends SurfaceView {

    protected HiveGameState state;

    Paint hexagonalPaint = new Paint();
    Paint hexagonalTargetPaint = new Paint();

    Path Hexagon = new Path();
    Point point1 = new Point();
    Point point2 = new Point();
    Point point3 = new Point();
    Point point4 = new Point();
    Point point5 = new Point();
    Point point6 = new Point();

    public HiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        hexagonalPaint.setColor(Color.WHITE);
        hexagonalTargetPaint.setColor(Color.RED);

        hexagonalPaint.setStyle(Paint.Style.FILL);
        hexagonalTargetPaint.setStyle(Paint.Style.STROKE);


    }

    public void setState(HiveGameState state) {
        this.state = state;
    }

    /*
    public Point mapPixelToSquare(int x, int y) {

        // loop through each square and see if we get a "hit"; if so, return
        // the corresponding Point in "square" coordinates
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                return new Point(i, j);
            }
        }

        return Point;
    }

     */

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
        /*canvas.drawLine(startX, startY+33, startX+50, startY, hexagonalPaint);
        canvas.drawLine(startX+50, startY, startX+100, startY+33, hexagonalPaint);
        canvas.drawLine(startX+100, startY+33, startX+100, startY+66, hexagonalPaint);
        canvas.drawLine(startX+100, startY+66, startX+50, startY+100, hexagonalPaint);
        canvas.drawLine(startX+50, startY+100, startX, startY+66, hexagonalPaint);
        canvas.drawLine(startX, startY+66, startX, startY+33, hexagonalPaint);*/

        canvas.drawPath(drawHexagonLines(startX, startY), hexagonalPaint);
    }

    public void drawTargetHexagon(Canvas canvas, int startX, int startY) {
        /*canvas.drawLine(startX, startY+33, startX+50, startY, hexagonalTargetPaint);
        canvas.drawLine(startX+50, startY, startX+100, startY+33, hexagonalTargetPaint);
        canvas.drawLine(startX+100, startY+33, startX+100, startY+66, hexagonalTargetPaint);
        canvas.drawLine(startX+100, startY+66, startX+50, startY+100, hexagonalTargetPaint);
        canvas.drawLine(startX+50, startY+100, startX, startY+66, hexagonalTargetPaint);
        canvas.drawLine(startX, startY+66, startX, startY+33, hexagonalTargetPaint);*/

        canvas.drawPath(drawHexagonLines(startX, startY), hexagonalTargetPaint);
    }

    private Path drawHexagonLines(int x, int y) {
        point1.set(x, y+33);
        point2.set(x+50, y);
        point3.set(x+100, y+33);
        point4.set(x+100, y+66);
        point5.set(x+50, y+100);
        point6.set(x, y+66);

        Hexagon.moveTo(point1.x, point1.y);
        Hexagon.lineTo(point2.x, point2.y);
        Hexagon.lineTo(point3.x, point3.y);
        Hexagon.lineTo(point4.x, point4.y);
        Hexagon.lineTo(point5.x, point5.y);
        Hexagon.lineTo(point6.x, point6.y);
        Hexagon.close();

        return Hexagon;

    }
}
