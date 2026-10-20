package otus.simulations;

import io.gatling.javaapi.core.Simulation;
import otus.scenarios.CommonScenario;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static otus.config.Config.STABILITY_DURATION;
import static otus.config.Config.STABILITY_RAMP;
import static otus.config.Config.STABILITY_RPS;
import static otus.steps.Otus.httpProtocol;


public class Stability extends Simulation {
    {
        setUp(
                CommonScenario.infiniteScn.injectOpen(
                        // Шаг 1:  разгон (30 секунд)
                        rampUsersPerSec(0).to(STABILITY_RPS).during(30),
                        // Шаг 2: удержание (1 час). Начнется после завершения ramp
                        constantUsersPerSec(STABILITY_RPS).during(STABILITY_DURATION)
                )
        ).protocols(httpProtocol)
                .maxDuration(STABILITY_RAMP + STABILITY_DURATION)
                .assertions(
                        global().failedRequests().percent().lte(5.0),
                        global().responseTime().percentile3().lt(3000)
                );
    }
}
