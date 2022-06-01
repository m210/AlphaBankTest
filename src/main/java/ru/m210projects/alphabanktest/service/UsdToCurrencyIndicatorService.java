package ru.m210projects.alphabanktest.service;

import org.json.simple.parser.ParseException;
import ru.m210projects.alphabanktest.entity.GifData;

import java.time.LocalDate;

public interface UsdToCurrencyIndicatorService {

    GifData compare(LocalDate date, String currency) throws ParseException;
}
