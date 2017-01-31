package io.riddles.javainterface.game.data;

/**
 * Created by joost on 10/11/16.
 */

/* A Point class that uses integers. */
public class Point {
    public int x;
    public int y;

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setLocation(int x, int y) { this.x = x; this.y = y; }

    public Point clone() {
        return new Point(this.x, this.y);
    }
}