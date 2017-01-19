package io.riddles.javainterface.engine;

/**
 * Created by joost on 1/2/17.
 */
public class ParseResult<T> {

    private InputType type;
    protected T value;

    public ParseResult(InputType type, T value) {
        this.type = type;
        this.value = value;
    }

    public InputType getType() { return type; }
    public T getValue() { return value; }
}
