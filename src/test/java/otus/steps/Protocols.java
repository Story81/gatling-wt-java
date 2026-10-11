package otus.steps;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;


public class Protocols {
    public static final HttpProtocolBuilder httpProtocol =
            http
                    .baseUrl("http://webtours.load-test.ru:1090/")
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptEncodingHeader("gzip, deflate")
                    .acceptLanguageHeader("ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:156.0) Gecko/20100101 Firefox/156.0");
}

