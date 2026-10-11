package otus.scenarios;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import otus.steps.Actions;

import static io.gatling.javaapi.core.CoreDsl.csv;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.group;
import static io.gatling.javaapi.core.CoreDsl.scenario;

public class CommonScenario {
    public static ScenarioBuilder apply() {
        return scn;
    }

    public static final FeederBuilder<String> users = csv("users.csv").circular();

    //----------Chains-----------
    private static final ChainBuilder openWelcomePage =
            group("openWelcomePage").on(
                    exec(Actions.openWelcomePage())
                            .exec(Actions.openWelcomePageNav()));

    private static final ChainBuilder login =
            group("login").on(
                    exec(Actions.login())
                            .exec(Actions.loginNav())
                            .exec(Actions.loginIntro()));

    private static final ChainBuilder openFindFlightPage =
            group("openFindFlightPage").on(
                    exec(Actions.openPageFindFlight())
                            .exec(Actions.openPageFindFlightNav())
                            .exec(Actions.getReservations()));

    //----------Scenarios-----------
    public static final ScenarioBuilder scn = scenario("Common scenario")
            .exec(openWelcomePage)
            .feed(users)
            .exec(login)
            .exec(openFindFlightPage)
            .exec(Actions.selectReservations())
            .exec(Actions.openPaymentDetails())
            .exec(Actions.openInvoicePage())
            .exec(Actions.returnToWelcomePage());
}

