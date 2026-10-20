package otus.simulations;

import io.gatling.javaapi.core.Simulation;
import otus.scenarios.CommonScenario;

import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.incrementUsersPerSec;

import static otus.config.Config.LEVEL_DURATION;
import static otus.config.Config.RAMP_DURATION;
import static otus.config.Config.STEP;
import static otus.config.Config.STEPS;
import static otus.steps.Otus.httpProtocol;

public class FMax extends Simulation {

    {
        setUp(
                CommonScenario.apply()
                        .injectOpen(
                                incrementUsersPerSec(STEP)
                                        .times(STEPS)
                                        .eachLevelLasting(LEVEL_DURATION)
                                        .startingFrom(STEP)
                                        .separatedByRampsLasting(RAMP_DURATION)
                        )
        ).protocols(httpProtocol);
    }
}
