package ru.m210projects.alphabanktest.service;

import org.json.simple.parser.ParseException;
import ru.m210projects.alphabanktest.entity.GifData;

public interface GifSearchService {

    GifData findRichGif() throws ParseException;

    GifData findBrokeGif() throws ParseException;

}
