package io.riddles.javainterface.engine;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by joost on 1/2/17.
 */
public class InputParser {

    /**
     * Parses everything the engine wrapper API sends
     * we need to start the engine, like IDs of the bots
     *
     * @param input Input from engine wrapper
     */
    protected ParseResult parse(String input) {
        String[] split = input.split(" ");
        String command = split[0];
        ParseResult pr = new ParseResult(InputType.EMPTY, null);
        switch (command) {
            case "start":
                return new ParseResult(InputType.START, null);
            case "bot_ids":
                String[] ids = split[1].split(",");

                ArrayList<Integer> bot_ids = new ArrayList<>();
                for(String id : ids) {
                    bot_ids.add(Integer.parseInt(id));
                }

                pr = new ParseResultIntArrayList(InputType.BOT_IDS, bot_ids);
                break;
            case "configuration":
            case "config":
                JSONObject config = new JSONObject(split[1].trim());
                pr = new ParseResult(InputType.CONFIGURATION, config);
        }
        return pr;
    }
}
