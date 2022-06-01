package ru.m210projects.alphabanktest.service.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.m210projects.alphabanktest.client.OpenExchangeClient;
import ru.m210projects.alphabanktest.service.ExchangeRateService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final OpenExchangeClient client;

    @Value("${exchange-client.appid}")
    private String appid;

    @Value("${exchange-client.base}")
    private String base;

    @Value("${exchange-client.time-format}")
    private String timeFormat;

    public ExchangeRateServiceImpl(OpenExchangeClient client) {
        this.client = client;
    }

    @Override
    public Double getHistoricalRate(LocalDate date, String currency) throws ParseException {
        JSONObject rates = getCurrencyRates(client.historical(date.format(DateTimeFormatter.ofPattern(timeFormat)),
                appid,
                base));

        return (Double) rates.get(currency.toUpperCase());
    }

    private JSONObject getCurrencyRates(String json) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        return (JSONObject) jsonObject.get("rates");
    }
}
