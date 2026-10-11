package otus.steps;

import io.gatling.javaapi.http.HttpRequestActionBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.gatling.javaapi.core.CoreDsl.ElFileBody;
import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class Actions {
    public static HttpRequestActionBuilder openWelcomePage() {
        return http("UC01_GET_welcomePage")
                .get("/cgi-bin/welcome.pl")
                .queryParam("signOff", "true")
                .check(status().is(200));
    }

    public static HttpRequestActionBuilder openWelcomePageNav() {
        return http("UC02_GET_welcomePageNav")
                .get("/cgi-bin/nav.pl")
                .queryParam("in", "home")
                .check(
                        status().is(200),
                        css("input[name='userSession']", "value").saveAs("userSession")
                );
    }

    public static HttpRequestActionBuilder login() {
        HttpRequestActionBuilder login =
                http("UC03_POST_login by user=#{username}")
                        .post("/cgi-bin/login.pl")
                        .formParam("userSession", "#{userSession}")
                        .formParam("username", "#{username}")
                        .formParam("password", "#{password}")
                        .formParam("login.x", "66")
                        .formParam("login.y", "14")
                        .formParam("JSFormSubmit", "off")
                        .check(status().is(200));
        return login;
    }

    public static HttpRequestActionBuilder loginNav() {
        HttpRequestActionBuilder loginNav =
                http("UC04_GET_loginNav")
                        .get("/cgi-bin/nav.pl")
                        .queryParam("page", "menu")
                        .queryParam("in", "home")
                        .check(status().is(200));
        return loginNav;
    }

    public static HttpRequestActionBuilder loginIntro() {
        HttpRequestActionBuilder loginIntro =
                http("UC05_GET_loginIntro")
                        .get("/cgi-bin/login.pl")
                        .queryParam("intro", "true")
                        .check(status().is(200));
        return loginIntro;
    }

    public static HttpRequestActionBuilder openPageFindFlight() {
        return http("UC06_GET_PageFindFlight")
                .get("/cgi-bin/welcome.pl")
                .queryParam("page", "search")
                .check(status().is(200));
    }

    public static HttpRequestActionBuilder openPageFindFlightNav() {
        return http("UC07_GET_pageFindFlightNav")
                .get("/cgi-bin/nav.pl")
                .queryParam("page", "menu")
                .queryParam("in", "flights")
                .check(status().is(200));
    }

    public static HttpRequestActionBuilder getReservations() {
        HttpRequestActionBuilder getReservations =
                http("UC08_GET_reservations")
                        .get("/cgi-bin/reservations.pl")
                        .queryParam("page", "welcome")
                        .check(
                                status().is(200),
                                css("select[name='depart'] option", "value").findRandom().saveAs("departCity"),
                                css("select[name='arrive'] option", "value").findRandom().saveAs("arriveCity")
                        );
        return getReservations;
    }

    public static HttpRequestActionBuilder selectReservations() {
        HttpRequestActionBuilder selectReservations =
                http("UC09_POST_selectReservations from #{departCity} to #{arriveCity}")
                        .post("/cgi-bin/reservations.pl")
                        .formParam("advanceDiscount", "0")
                        .formParam("depart", "#{departCity}")
                        .formParam("departDate", session -> {
                            LocalDate date = LocalDate.now().plusDays(1);
                            return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                        })
                        .formParam("arrive", "#{arriveCity}")
                        .formParam("returnDate", session -> {
                            LocalDate date = LocalDate.now().plusDays(3);
                            return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                        })
                        .formParam("numPassengers", "1")
                        .formParam("seatPref", "None")
                        .formParam("seatType", "Coach")
                        .formParam("findFlights.x", "50")
                        .formParam("findFlights.y", "15")
                        .formParam(".cgifields", "roundtrip")
                        .formParam(".cgifields", "seatType")
                        .formParam(".cgifields", "seatPref")
                        .check(
                                status().is(200),
                                css("input[name='outboundFlight']", "value").findRandom().saveAs("outboundFlight")
                        );
        return selectReservations;
    }

    public static HttpRequestActionBuilder openPaymentDetails() {
        HttpRequestActionBuilder openPaymentDetails =
                http("UC10_POST_openPaymentDetails on flight #{outboundFlight}")
                        .post("/cgi-bin/reservations.pl")
                        .formParam("outboundFlight", "#{outboundFlight}")
                        .formParam("numPassengers", "1")
                        .formParam("advanceDiscount", "0")
                        .formParam("seatType", "Coach")
                        .formParam("seatPref", "None")
                        .formParam("reserveFlights.x", "53")
                        .formParam("reserveFlights.y", "11")
                        .check(status().is(200));
        return openPaymentDetails;
    }

    public static HttpRequestActionBuilder openInvoicePage() {
        HttpRequestActionBuilder openInvoicePage =
                http("UC11_POST_openInvoicePage")
                        .post("/cgi-bin/reservations.pl")
                        .body(ElFileBody("paymentDetails.txt"))
                        .check(status().is(200));
        return openInvoicePage;
    }

    public static HttpRequestActionBuilder returnToWelcomePage() {
        return http("UC12_GET_returnToWelcomePage")
                .get("/cgi-bin/welcome.pl")
                .queryParam("page", "menus")
                .check(status().is(200));
    }
}
