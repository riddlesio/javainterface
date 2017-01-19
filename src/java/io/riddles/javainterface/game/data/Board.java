package io.riddles.javainterface.game.data;
import io.riddles.javainterface.game.data.Point;


/**
 * Created by joost on 10/7/16.
 */

public abstract class Board<T> {
    protected T fields[][];

    protected int width;
    protected int height;


    public Board() {

    }

    public abstract void clear();

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }


    public T getFieldAt(Point point) {
        if(point.x < 0 || point.x >= width || point.y < 0 || point.y >= height)
            return null;
        return fields[point.getX()][point.getY()];
    }

    public void setFieldAt(Point point, T c) {
        fields[point.getX()][point.getY()] = c;
    }

    /**
     * Creates comma separated String
     * @param :
     * @return : String
     */
    public String toString() {
        String r = "";
        int counter = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (counter > 0) {
                    r += ",";
                }
                r += fields[x][y];
                counter++;
            }
        }
        return r;
    }
}
