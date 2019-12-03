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

public class HiveView extends SurfaceView {

    protected HiveGameState state = null;

    Paint hexagonalPaint = new Paint();
    Paint hexagonalPaintB = new Paint();
    Paint hexagonalTargetPaint = new Paint();
    Paint HexagonalPaintOutline = new Paint();
    Paint HexagonalPaintOutlineB = new Paint();
    Paint HexagonalSelectedPaint = new Paint();

    Path Hexagon = new Path();
    Path HexagonHighlights = new Path();
    Path HexagonSelected = new Path();
    Point point1 = new Point();
    Point point2 = new Point();
    Point point3 = new Point();
    Point point4 = new Point();
    Point point5 = new Point();
    Point point6 = new Point();

    private int selectedX = -1;
    private int selectedY = -1;

    public HiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        //piece paints
        hexagonalPaint.setColor(Color.WHITE);
        hexagonalPaint.setStyle(Paint.Style.FILL);
        hexagonalPaintB.setColor(Color.BLACK);
        hexagonalPaintB.setStyle(Paint.Style.FILL);

        //hexagon paint colors
        hexagonalTargetPaint.setColor(Color.RED);
        HexagonalPaintOutline.setColor(Color.BLACK);
        HexagonalPaintOutlineB.setColor(Color.WHITE);
        HexagonalSelectedPaint.setColor(Color.BLUE);

        //hexagon paint styles
        hexagonalTargetPaint.setStyle(Paint.Style.STROKE);
        HexagonalPaintOutline.setStyle(Paint.Style.STROKE);
        HexagonalSelectedPaint.setStyle(Paint.Style.STROKE);

        //sets path fill type (for black pieces)
        Hexagon.setFillType(Path.FillType.EVEN_ODD);

        //sets hexagon line thickness (red is is 4 thick, black is 2 thick)
        hexagonalTargetPaint.setStrokeWidth(4);
        HexagonalPaintOutline.setStrokeWidth(2);
        HexagonalSelectedPaint.setStrokeWidth(8);
    }

    public void setState(HiveGameState state) {
        this.state = state;
    }

    public void onDraw(Canvas canvas) {
        if(state == null){
            return;
        }

        Hexagon.reset();
        HexagonHighlights.reset();
        HexagonSelected.reset();
        setBackgroundColor(Color.WHITE);
        drawHexagon(canvas, HexagonHighlights);
        drawHexagon(canvas, Hexagon);
        drawHexagon(canvas, HexagonSelected);
    }

    /**
     * draws piece hexagons and target hexagons on canvas
     * @param canvas canvas to draw to
     * @param path   which path object to draw on (pieces, target, or selected)
     */
    public void drawHexagon(Canvas canvas, Path path) {

        if(path == Hexagon) {
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 11; x++) {
                    if (y % 2 == 0) {

                        switch (state.getPiece(x, y)) {
                            case BBEE:
                                drawBBee(canvas, x * 100, y * 66);
                                break;
                            case BANT:
                                drawBAnt(canvas, x * 100, y * 66);
                                break;
                            case BBEETLE:
                                drawBBeetle(canvas, x * 100, y * 66);
                                break;
                            case BSPIDER:
                                drawBSpider(canvas, x * 100, y * 66);
                                break;
                            case BGHOPPER:
                                drawBGhopper(canvas, x * 100, y * 66);
                                break;
                            case WBEE:
                                drawWBee(canvas, x * 100, y * 66);
                                break;
                            case WANT:
                                drawWAnt(canvas, x * 100, y * 66);
                                break;
                            case WBEETLE:
                                drawWBeetle(canvas, x * 100, y * 66);
                                break;
                            case WSPIDER:
                                drawWSpider(canvas, x * 100, y * 66);
                                break;
                            case WGHOPPER:
                                drawWGhopper(canvas, x * 100, y * 66);
                                break;
                        }
                    } else {
                        switch (state.getPiece(x, y)) {
                            case BBEE:
                                drawBBee(canvas, x * 100 + 50, y * 66);
                                break;
                            case BANT:
                                drawBAnt(canvas, x * 100 + 50, y * 66);
                                break;
                            case BBEETLE:
                                drawBBeetle(canvas, x * 100 + 50, y * 66);
                                break;
                            case BSPIDER:
                                drawBSpider(canvas, x * 100 + 50, y * 66);
                                break;
                            case BGHOPPER:
                                drawBGhopper(canvas, x * 100 + 50, y * 66);
                                break;
                            case WBEE:
                                drawWBee(canvas, x * 100 + 50, y * 66);
                                break;
                            case WANT:
                                drawWAnt(canvas, x * 100 + 50, y * 66);
                                break;
                            case WBEETLE:
                                drawWBeetle(canvas, x * 100 + 50, y * 66);
                                break;
                            case WSPIDER:
                                drawWSpider(canvas, x * 100 + 50, y * 66);
                                break;
                            case WGHOPPER:
                                drawWGhopper(canvas, x * 100 + 50, y * 66);
                                break;
                        }
                    }
                }
            }
        }
        else if(path == HexagonHighlights)
        {
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 11; x++) {
                    if(state.getPiece(x, y) == HiveGameState.piece.TARGET)
                    if (y % 2 == 0) {
                        drawTargetHexagon(canvas, x * 100, y * 66);
                    } else {
                        drawTargetHexagon(canvas, x * 100 + 50, y * 66);
                    }
                }
            }
        }
        else if(path == HexagonSelected){
            if(getSelectedX() != -1 && getSelectedY() != -1){
                if (getSelectedY() % 2 == 0) {
                    drawSelectedHexagon(canvas, getSelectedX() * 100, getSelectedY() * 66);
                } else {
                    drawSelectedHexagon(canvas, getSelectedX() * 100 + 50, getSelectedY() * 66);
                }
            }
        }
    }

    /**
     * Draws blue outline when player has selected hexagon
     * @param canvas: Canvas to draw on
     * @param startX: X coordinate of canvas
     * @param startY: Y coordinate
     */
    public void drawSelectedHexagon(Canvas canvas, int startX, int startY)
    {
        if(startX == -1 || startY == -1){
            //do nothing
        }
        else{
            canvas.drawPath(drawHexagonLines(startX, startY, HexagonSelected), HexagonalSelectedPaint);
        }

    }

    /**
     * Draws highlighted outlines for legal spots upon
     * piece selection
     * @param c: Canvas to draw on
     * @param startX: X coordinate of canvas
     * @param startY: Y coordinate
     */
    public void drawHighlights(Canvas c, int startX, int startY)
    {
        c.drawPath(drawHexagonLines(startX, startY, HexagonHighlights), hexagonalTargetPaint);
    }

    /**
     * draws hexagon, and white bee bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawWBee(Canvas canvas, int startX, int startY) {

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wbeepng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaint);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaint);
    }

    /**
     * draws hexagon, and white beetle bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawWBeetle(Canvas canvas, int startX, int startY) {

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wbeetlepng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaint);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);


        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaint);
    }

    /**
     * draws hexagon, and white spider bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawWSpider(Canvas canvas, int startX, int startY) {

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wspiderpng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaint);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaint);
    }

    /**
     * draws hexagon, and white ant bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawWAnt(Canvas canvas, int startX, int startY) {

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wantpng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75,80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaint);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaint);
    }

    /**
     * draws hexagon, and white grasshopper bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawWGhopper(Canvas canvas, int startX, int startY) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wgrasshopperpng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75,80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaint);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaint);

    }

    /**
     * draws hexagon, and black bee bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawBBee(Canvas canvas, int startX, int startY) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bbeepng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);


        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaintB);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaintB);
    }

    /**
     * draws hexagon, and black beetle bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawBBeetle(Canvas canvas, int startX, int startY) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bbeetlepng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaintB);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaintB);
    }

    /**
     * draws hexagon, and black spider bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawBSpider(Canvas canvas, int startX, int startY) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bspiderpng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaintB);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaintB);
    }

    /**
     * draws hexagon, and black ant bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawBAnt(Canvas canvas, int startX, int startY) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bantpng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaintB);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaintB);
    }

    /**
     * draws hexagon, and black grasshopper bitmap on canvas
     * @param canvas  the canvas to draw to
     * @param startX  x coordinate on canvas
     * @param startY  y coordinate on canvas
     */
    public void drawBGhopper(Canvas canvas, int startX, int startY) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bgrasshopperpng);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, 75, 80, false);

        //draw filled hexagon
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), hexagonalPaintB);

        //draw black outlines
        canvas.drawPath(drawHexagonLines(startX, startY, Hexagon), HexagonalPaintOutline);

        canvas.drawBitmap(resizedBitmap, startX+13, startY+12, hexagonalPaintB);
    }

    /**
     * draws red hexagons as the targets.
     * @param canvas canvas to draw on
     * @param startX x coordinate of hexagon
     * @param startY y coordinate of hexagon
     */
    public void drawTargetHexagon(Canvas canvas, int startX, int startY) {

        //draw red outlines
        canvas.drawPath(drawHexagonLines(startX, startY, HexagonHighlights), hexagonalTargetPaint);
    }

    public void setSelectedCoords(int x, int y)
    {
        selectedX = x;
        selectedY = y;
    }

    public int getSelectedX(){
        return selectedX;
    }

    public int getSelectedY(){
        return selectedY;
    }

    /**
     *  used to set selected hexagon to not draw
     */
    public void deselectPiece(){
        selectedX = -1;
        selectedY = -1;
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
    private Path drawHexagonLines(int x, int y, Path hex) {
        point1.set(x, y + 33);
        point2.set(x + 50, y);
        point3.set(x + 100, y + 33);
        point4.set(x + 100, y + 66);
        point5.set(x + 50, y + 100);
        point6.set(x, y + 66);

        /*          point2
         *            / \
         *          /     \
         *  point6 /       \  point4
         *        |         |
         *        |         |
         *        |         | point3
         *  point1 \       /
         *          \     /
         *            \ /
         *          point5
         */

        hex.moveTo(point1.x, point1.y);
        hex.lineTo(point2.x, point2.y);
        hex.lineTo(point3.x, point3.y);
        hex.lineTo(point4.x, point4.y);
        hex.lineTo(point5.x, point5.y);
        hex.lineTo(point6.x, point6.y);
        hex.close();

        return hex;
    }
}

