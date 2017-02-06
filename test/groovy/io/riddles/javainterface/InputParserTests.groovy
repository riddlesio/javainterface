package io.riddles.javainterface

import io.riddles.javainterface.engine.InputParser
import io.riddles.javainterface.engine.InputType
import io.riddles.javainterface.engine.ParseResult
import spock.lang.Specification

/**
 * Created by joost on 1/30/17.
 */
class InputParserTests extends Specification {
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

    def "InputParser"() {

        setup:
        InputParser parser = new InputParser();

        when:
        ParseResult parseResult = parser.parse("bot_ids 0,1")
        parseResult = parser.parse('configuration {\"maxRounds\":{\"type\":\"integer\",\"value\":14}}')
        parseResult = parser.parse('config {\"maxRounds\":{\"type\":\"integer\",\"value\":14}}')

        parseResult = parser.parse("start")

        then:
        parseResult != null;
        parseResult.getType() == InputType.START;
        parseResult.getValue() == null;
    }
}
