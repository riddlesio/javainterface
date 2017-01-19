package io.riddles.go

import io.riddles.javainterface.engine.AbstractEngine
import io.riddles.javainterface.game.AbstractGameSerializer
import io.riddles.javainterface.game.player.AbstractPlayer
import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.processor.AbstractProcessor
import io.riddles.javainterface.game.state.AbstractState
import io.riddles.javainterface.io.BotIOHandler
import io.riddles.javainterface.io.FileIOHandler
import org.json.JSONObject
import spock.lang.Ignore
import spock.lang.Specification

class javainterfaceTests extends Specification {
/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */
/*

    public class TestEngine extends AbstractEngine {

        TestEngine(PlayerProvider<RockPaperScissorsPlayer> playerProvider, String wrapperInput) {
            super(playerProvider, null);
            this.ioHandler = new FileIOHandler(wrapperInput);
        }

    }

    public class TestSerializer extends AbstractGameSerializer<AbstractProcessor,AbstractState> {

        TestSerializer(PlayerProvider playerProvider) {
            super(playerProvider);
        }

        public JSONObject getDefaultJSON(AbstractProcessor processor, AbstractState initialState) {
            JSONObject game = new JSONObject();
            game = addDefaultJSON(initialState, game, processor);
        }

        @Override
        public String traverseToString(AbstractProcessor processor, AbstractState initialState) {
            JSONObject game = new JSONObject();

            game = addDefaultJSON(initialState, game, processor);
            return game.toString();
        }
    }

    public class TestPlayer extends AbstractPlayer {

        public TestPlayer(int id) {
            super(id);
        }
    }

    public class TestIOHandler extends BotIOHandler {

        public TestIOHandler(int botId) {
            super(botId);
        }
        @Override
        void sendMessage(String s) {
            System.out.println(s);
        }
    }
    */

    //@Ignore
    /*
    def "Test JSON addDefaultJSON output"() {
        println("addDefaultJSON")

        setup:
        PlayerProvider p = new PlayerProvider<AbstractPlayer>();
        TestPlayer testPlayer1 = new TestPlayer(1);
        TestPlayer testPlayer2 = new TestPlayer(2);
        p.add(testPlayer1);
        p.add(testPlayer2);
        TestSerializer serializer = new TestSerializer(p);
        String j = serializer.traverseToString();

        expect:

        j.get("settings") instanceof JSONObject;
        j.get("settings").get('players') instanceof JSONObject;
        j.get("settings").get('players').get('names') instanceof JSONArray;
        j.get("settings").get('players').get('count') instanceof Integer;
        j.get("score") instanceof Integer;
        if (j.get("winner") != null) {
            j.get("winner") instanceof Integer;
        }
    }
    */
/*

    def "Test Player"() {

        setup:
        TestPlayer testPlayer1 = new TestPlayer(1);
        testPlayer1.setName("FooPlayer");
        testPlayer1.setIoHandler(new FileIOHandler("./test/resources/wrapper_input.txt"))

        expect:
        testPlayer1.getName() == "FooPlayer";
        testPlayer1.getIoHandler() instanceof FileIOHandler;
    }

    def "Test PlayerProvider"() {

        setup:
        PlayerProvider p = new PlayerProvider<AbstractPlayer>();


        TestPlayer testPlayer1 = new TestPlayer(1);
        TestPlayer testPlayer2 = new TestPlayer(2);
        p.add(testPlayer1);
        p.add(testPlayer2);

        expect:
        p.getPlayerById(1).getId() == 1;
        p.getPlayerById(2).getId() == 2;

    }

    def "Test IOHandler"() {

        setup:
        TestPlayer testPlayer1 = new TestPlayer(1);
        testPlayer1.setName("FooPlayer");

        TestIOHandler testIOHandler = Mock();
        testPlayer1.setIoHandler(testIOHandler);

        when:
        testPlayer1.sendSetting("test", 1);
        then:
        1 * testIOHandler.sendMessage("settings test 1")

        when:
        testPlayer1.sendSetting("test2", "value");
        then:
        1 * testIOHandler.sendMessage("settings test2 value")
    }
*/
}