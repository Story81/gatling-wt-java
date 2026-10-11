package otus.simulations;

import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.Simulation;
import otus.scenarios.CommonScenario;

import static otus.steps.Protocols.httpProtocol;


public class Debug extends Simulation {
    {
        setUp(
                CommonScenario.apply()
                        .injectOpen(OpenInjectionStep.atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}
