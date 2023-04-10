import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class JsonArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws ParseException {
        String jsonString1 = "{\"user_agent\":\"Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30\",\"platform\":\"Mobile\",\"browser\":\"No\",\"device\":\"Android\"}";
        String jsonString2 = "{\"user_agent\":\"Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1\",\"platform\":\"Mobile\",\"browser\":\"Chrome\",\"device\":\"iOS\"}";
        String jsonString3 = "{\"user_agent\":\"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)\",\"platform\":\"Googlebot\",\"browser\":\"Unknown\",\"device\":\"Unknown\"}";
        String jsonString4 = "{\"user_agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0\",\"platform\":\"Web\",\"browser\":\"Chrome\",\"device\":\"No\"}";
        String jsonString5 = "{\"user_agent\":\"Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1\",\"platform\":\"Mobile\",\"browser\":\"No\",\"device\":\"iPhone\"}";

        JSONObject jo1 = (JSONObject) new JSONParser().parse(jsonString1);
        JSONObject jo2 = (JSONObject) new JSONParser().parse(jsonString2);
        JSONObject jo3 = (JSONObject) new JSONParser().parse(jsonString3);
        JSONObject jo4 = (JSONObject) new JSONParser().parse(jsonString4);
        JSONObject jo5 = (JSONObject) new JSONParser().parse(jsonString5);

        return Stream.of(
                Arguments.of(jo1),
                Arguments.of(jo2),
                Arguments.of(jo3),
                Arguments.of(jo4),
                Arguments.of(jo5)
        );
    }




}


