package com.example.hive.Hive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.example.hive.R;

import static android.graphics.Bitmap.createScaledBitmap;

public class HiveView extends SurfaceView {

    protected HiveGameState state = null;
//    private HiveGameState.piece piece;

    Paint piecePaint = new Paint();

    Paint hexagonalPaint = new Paint();
    Paint hexagonalPaintB = new Paint();
    Paint hexagonalTargetPaint = new Paint();
    Paint HexagonalPaintOutline = new Paint();

    Path Hexagon = new Path();
    Point point1 = new Point();
    Point point2 = new Point();
    Point point3 = new Point();
    Point point4 = new Point();
    Point point5 = new Point();
    Point point6 = new Point();

    //Create Bitmaps for decoding
    Bitmap bBeeBitmap;
    Bitmap bAntBitmap;
    Bitmap bBeetleBitmap;
    Bitmap bSpiderBitmap;
    Bitmap bGHopperBitmap;
    Bitmap wBeeBitmap;
    Bitmap wAntBitmap;
    Bitmap wBeetleBitmap;
    Bitmap wSpiderBitmap;
    Bitmap wGHopperBitmap;


    public HiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        hexagonalPaint.setColor(Color.WHITE);
        hexagonalPaint.setStyle(Paint.Style.FILL);
        hexagonalPaintB.setColor(Color.BLACK);
        hexagonalPaintB.setStyle(Paint.Style.FILL);


        hexagonalTargetPaint.setColor(Color.RED);
        HexagonalPaintOutline.setColor(Color.BLACK);


        piecePaint.setColor(Color.RED);

        hexagonalTargetPaint.setStyle(Paint.Style.STROKE);
        HexagonalPaintOutline.setStyle(Paint.Style.STROKE);

        Hexagon.setFillType(Path.FillType.EVEN_ODD);

        //create bitmaps of each png image (not yet fully implemented)
        wBeeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wbeepng);
        wBeeBitmap = scaleBitmap(wBeeBitmap);

        wBeetleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wbeetlepng);
        wBeetleBitmap = scaleBitmap(wBeetleBitmap);

        wSpiderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wspiderpng);
        wSpiderBitmap = scaleBitmap(wSpiderBitmap);

        wAntBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wantpng);
        wAntBitmap = scaleBitmap(wAntBitmap);

        wGHopperBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wgrasshopperpng);
        wGHopperBitmap = scaleBitmap(wGHopperBitmap);

        bBeeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bbeepng);
        bBeeBitmap = scaleBitmap(bBeeBitmap);

        bBeetleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bbeetlepng);
        bBeetleBitmap = scaleBitmap(bBeetleBitmap);

        bSpiderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bspiderpng);
        bSpiderBitmap = scaleBitmap(bSpiderBitmap);

        bAntBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bantpng);
        bAntBitmap = scaleBitmap(bAntBitmap);

        bGHopperBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bgrasshopperpng);
        bGHopperBitmap = scaleBitmap(bGHopperBitmap);
    }

    public void setState(HiveGameState state) {
        this.state = state;
    }

    public void onDraw(Canvas canvas) {
        if(state == null){
            return;
        }

        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < 11; x++) {
                if (y % 2 == 0) {

                    switch (state.getPiece(x, y)) {
                        case BBEE:
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, bBeeBitmap);
                            break;
                        case BANT:
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, bAntBitmap);
                            break;
                        case BBEETLE:
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, bBeetleBitmap);
                            break;
                        case BSPIDER:
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, bSpiderBitmap);
                            break;
                        case BGHOPPER:
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, bGHopperBitmap);
                            break;
                        case WBEE:
                            drawHexagon(canvas,x*100,y*66);
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, wBeeBitmap);
                            break;
                        case WANT:
                            drawHexagon(canvas,x*100,y*66);
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, wAntBitmap);
                            break;
                        case WBEETLE:
                            drawHexagon(canvas,x*100,y*66);
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, wBeetleBitmap);
                            break;
                        case WSPIDER:
                            drawHexagon(canvas,x*100,y*66);
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, wSpiderBitmap);
                            break;
                        case WGHOPPER:
                            drawHexagon(canvas,x*100,y*66);
                            drawPiece(canvas, x * 100 + 5, y * 66 + 15, wGHopperBitmap);
                            break;
                        case EMPTY:
                            drawHexagon(canvas, x * 100, y * 66);
                            break;
                    }
                } else{
                    switch (state.getPiece(x, y)) {
                        case BBEE:
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, bBeeBitmap);
                            break;
                        case BANT:
                            drawPiece(canvas, x * 100 +50 + 5, y * 66 + 15, bAntBitmap);
                            break;
                        case BBEETLE:
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, bBeetleBitmap);
                            break;
                        case BSPIDER:
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, bSpiderBitmap);
                            break;
                        case BGHOPPER:
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, bGHopperBitmap);
                            break;
                        case WBEE:
                            drawHexagon(canvas,x*100 + 50,y*66);
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, wBeeBitmap);
                            break;
                        case WANT:
                            drawHexagon(canvas,x*100 + 50,y*66);
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, wAntBitmap);
                            break;
                        case WBEETLE:
                            drawHexagon(canvas,x*100 + 50,y*66);
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, wBeetleBitmap);
                            break;
                        case WSPIDER:
                            drawHexagon(canvas,x*100 + 50,y*66);
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, wSpiderBitmap);
                            break;
                        case WGHOPPER:
                            drawHexagon(canvas,x*100 + 50,y*66);
                            drawPiece(canvas, x * 100 + 50 + 5, y * 66 + 15, wGHopperBitmap);
                            break;
                        case EMPTY:
                            drawHexagon(canvas, x * 100 + 50, y * 66);
                            break;
                    }
                }
            }
        }
    }

    public Bitmap scaleBitmap(Bitmap bitmap)
    {
        bitmap = createScaledBitmap(bitmap, 90, 70, false);
        return bitmap;
    }

    /**
     * draws empty filled hexagon
     * @param canvas canvas to draw to
     * @param x      x coordinate of hexagon
     * @param y      y coordinate of hexagon
     */
    public void drawHexagon(Canvas canvas, int x, int y) {

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(x, y), hexagonalPaint);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(x, y), HexagonalPaintOutline);

    }

    /**
     * draws hexagon, and white bee bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawPiece(Canvas canvas, int startX, int startY, Bitmap piece) {

        canvas.drawBitmap(piece, startX, startY, piecePaint);

        //draw black outlines
        //canvas.drawPath(drawHexagonLines(startX, startY), HexagonalPaintOutline);


    }


    /**
     * Outlines a hexagon in red
     * @param canvas
     * @param startX
     * @param startY
     */
    public void drawTargetHexagon(Canvas canvas, int startX, int startY) {

        //draw red outlines
        canvas.drawPath(drawHexagonLines(startX, startY), hexagonalTargetPaint);

        //draw black outlines
        //canvas.drawPath(drawHexagonLines(startX, startY), HexagonalPaintOutline);
    }

    /**
     * Modifies the path object by setting six points around target point and connects each point with a line.
     *
     * @param x x coordinate of hexagon origin
     * @param y y coordinate of hexagon origin
     * @return Hexagon  modified path class in the shape of a hexagon
     * <p>
     * *note* this method only draws lines, filling is done by paint
     */
    private Path drawHexagonLines(int x, int y) {
        point1.set(x, y + 33);
        point2.set(x + 50, y);
        point3.set(x + 100, y + 33);
        point4.set(x + 100, y + 66);
        point5.set(x + 50, y + 100);
        point6.set(x, y + 66);

        /*          point5
         *            / \
         *          /     \
         *  point6 /       \  point4
         *        |         |
         *        |         |
         *        |         | point3
         *  point1 \       /
         *          \     /
         *            \ /
         *          point2
         */

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

