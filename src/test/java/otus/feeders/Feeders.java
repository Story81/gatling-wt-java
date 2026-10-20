package otus.feeders;

import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.csv;

public class Feeders {
    public static final FeederBuilder<String> users = csv("users.csv").circular();
}
