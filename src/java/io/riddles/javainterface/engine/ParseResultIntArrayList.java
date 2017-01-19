package io.riddles.javainterface.engine;

import java.util.ArrayList;

/**
 * Created by joost on 1/3/17.
 */
public class ParseResultIntArrayList extends ParseResult<ArrayList<Integer>> {
    public ParseResultIntArrayList(InputType type, ArrayList<Integer> value) {
        super(type, value);
    }

    public ArrayList<Integer> getValue() { return value; }
}
