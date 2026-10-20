package otus.scenarios;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import otus.steps.Actions;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.forever;
import static io.gatling.javaapi.core.CoreDsl.group;
import static io.gatling.javaapi.core.CoreDsl.pace;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static otus.feeders.Feeders.users;


public class CommonScenario {
    public static ScenarioBuilder apply() {
        return scn;
    }

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

    private static final ChainBuilder allSteps =
            exec(openWelcomePage)
                    .feed(users)
                    .exec(login)
                    .exec(openFindFlightPage)
                    .exec(Actions.selectReservations())
                    .exec(Actions.openPaymentDetails())
                    .exec(Actions.returnToWelcomePage());

    //----------Scenarios-----------
    // Обычный сценарий
    public static final ScenarioBuilder scn = scenario("Common scenario")
            .exitBlockOnFail().on(allSteps);

    // Зацикленный сценарий
//    public static final ScenarioBuilder infiniteScn = scenario("Stability scenario")
//            .exitBlockOnFail().on(
//                    forever().on(
//                            pace(4,6).exec(allSteps)
//                    )
//            );
    public static final ScenarioBuilder infiniteScn = scenario("Stability scenario")
            .exitBlockOnFail().on(allSteps);

}


