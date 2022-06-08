package ru.m210projects.alphabanktest.service;

import java.time.LocalDate;

public interface UsdToCurrencyIndicatorService {

    byte[] compare(LocalDate date, String currency);
}
