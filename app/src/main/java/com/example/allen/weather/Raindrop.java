package com.example.allen.weather;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Raindrop
 * author: Allen
 * time: 2018/5/12 21:50
 */

public class Raindrop {
    private static final float ANGE_RANGE = 0.1f;
    private static final float HALF_ANGLE_RANGE = ANGE_RANGE / 2f;
    private static final float HALF_PI = (float) Math.PI / 2f;
    private static final float ANGLE_SEED = 25f;
    private static final float ANGLE_DIVISOR = 10000f;
    //    private static final float INCREMENT_LOWER = 2f;
//    private static final float INCREMENT_UPPER = 4f;
    private static final float INCREMENT_LOWER = 300f;
    private static final float INCREMENT_UPPER = 350f;
    private static final float FLAKE_SIZE_LOWER = 7f;
    private static final float FLAKE_SIZE_UPPER = 20f;

    private final Random random;
    private final Point position;
    private float angle;
    private final float increment;
    private final float flakeSize;
    private final Paint paint;

    public static Raindrop create(int width, int height, Paint paint) {
        Random random = new Random();
        int x = random.getRandom(width);
        int y = random.getRandom(height);
        Point position = new Point(x, y);
        float angle = random.getRandom(ANGLE_SEED) / ANGLE_SEED * ANGE_RANGE + HALF_PI - HALF_ANGLE_RANGE;
        float increment = random.getRandom(INCREMENT_LOWER, INCREMENT_UPPER);
        float flakeSize = random.getRandom(FLAKE_SIZE_LOWER, FLAKE_SIZE_UPPER);
        return new Raindrop(random, position, angle, increment, flakeSize, paint);
    }

    Raindrop(Random random, Point position, float angle, float increment, float flakeSize, Paint paint) {
        this.random = random;
        this.position = position;
        this.angle = angle;
        this.increment = increment;
        this.flakeSize = flakeSize;
        this.paint = paint;
    }

    private void move(int width, int height) {
        double x = position.x + (increment * Math.cos(angle));
        double y = position.y + (increment * Math.sin(angle));
//        double x = position.x;
//        double y = position.y + increment;

//        angle += random.getRandom(-ANGLE_SEED, ANGLE_SEED) / ANGLE_DIVISOR;

        position.set((int) x, (int) y);

        if (!isInside(width, height)) {
            reset(width);
        }
    }

    private boolean isInside(int width, int height) {
        int x = position.x;
        int y = position.y;
        return x >= -flakeSize - 1 && x + flakeSize <= width && y >= -flakeSize - 1 && y - flakeSize < height;
    }

    private void reset(int width) {
        position.x = random.getRandom(width);
//        position.y = (int) (-flakeSize - 1);
        position.y = (int) random.getRandom( INCREMENT_UPPER);
        angle = random.getRandom(ANGLE_SEED) / ANGLE_SEED * ANGE_RANGE + HALF_PI - HALF_ANGLE_RANGE;
    }

    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        move(width, height);
//        canvas.drawCircle(position.x, position.y, flakeSize, paint);
        canvas.drawLine(position.x, position.y, position.x, position.y + 40, paint);
    }
}