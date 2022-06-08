package ru.m210projects.alphabanktest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.m210projects.alphabanktest.service.ExchangeRateService;
import ru.m210projects.alphabanktest.service.GifSearchService;
import ru.m210projects.alphabanktest.service.UsdToCurrencyIndicatorService;

import java.time.LocalDate;

@Service
public class UsdToCurrencyIndicatorServiceImpl implements UsdToCurrencyIndicatorService {

    private final GifSearchService gifSearchService;
    private final ExchangeRateService exchangeService;
    private final Logger logger = LoggerFactory.getLogger(UsdToCurrencyIndicatorServiceImpl.class);

    public UsdToCurrencyIndicatorServiceImpl(GifSearchService gifSearchService, ExchangeRateService exchangeService) {
        this.gifSearchService = gifSearchService;
        this.exchangeService = exchangeService;
    }

    @Override
    public byte[] compare(LocalDate date, String currency) {
        logger.info("Compare USD with {} on {}", currency.toUpperCase(), date);
        double todayRate = exchangeService.getHistoricalRate(LocalDate.now(), currency);
        double targetRate = exchangeService.getHistoricalRate(date, currency);

        logger.debug("Today rate is {}", todayRate);
        logger.debug("Target rate is {}", targetRate);

        if (todayRate > targetRate) {
            return gifSearchService.findGif("rich");
        }

        return gifSearchService.findGif("broke");
    }

}
