package ru.m210projects.alphabanktest.service;

import org.json.simple.parser.ParseException;

import java.time.LocalDate;

public interface ExchangeRateService {

    Double getHistoricalRate(LocalDate date, String currency) throws ParseException;

}
