package ru.m210projects.alphabanktest.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.m210projects.alphabanktest.client.OpenExchangeClient;
import ru.m210projects.alphabanktest.entity.ExchangeApiResult;
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
    public Double getHistoricalRate(LocalDate date, String currency) {
    	ExchangeApiResult result = client.historical(date.format(DateTimeFormatter.ofPattern(timeFormat)),
                appid,
                base);

        return result.getRates().get(currency.toUpperCase());
    }
}
