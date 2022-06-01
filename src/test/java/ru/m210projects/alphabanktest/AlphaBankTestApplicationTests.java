package ru.m210projects.alphabanktest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.m210projects.alphabanktest.controller.UsdToCurrencyIndicatorController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlphaBankTestApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private UsdToCurrencyIndicatorController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    public void testWelcome() {
        Assertions
                .assertThat(restTemplate.getForObject("http://localhost:" + port + "/indicator/welcome", String.class))
                .isEqualTo("Welcome!");
    }

    @Test
    public void testCompareWithYesterDay() {
        Assertions
                .assertThat(restTemplate.getForObject("http://localhost:" + port + "/indicator/compareWithYesterdayUSDRate?currency=rub", String.class))
                .isNotNull();
    }

    @Test
    public void testCompareByDate() {
        Assertions
                .assertThat(restTemplate.getForObject("http://localhost:" + port + "/indicator/compareUSDRateByDate?date=01.01.2013&currency=EUR", String.class))
                .isNotNull();
    }

    @Test
    public void shouldResponseBadRequest() {
        Assertions
                .assertThat(restTemplate.getForObject("http://localhost:" + port + "/indicator/compareWithYesterdayUSDRate", String.class))
                .contains("Bad Request");
    }

}
