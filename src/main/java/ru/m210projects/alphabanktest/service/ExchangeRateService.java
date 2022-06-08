package ru.m210projects.alphabanktest.service;

import java.time.LocalDate;

public interface ExchangeRateService {

    Double getHistoricalRate(LocalDate date, String currency);

}
