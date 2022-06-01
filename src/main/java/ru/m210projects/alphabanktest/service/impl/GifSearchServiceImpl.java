package ru.m210projects.alphabanktest.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.m210projects.alphabanktest.entity.GifData;
import ru.m210projects.alphabanktest.client.GiphyClient;
import ru.m210projects.alphabanktest.service.GifSearchService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

@Service
public class GifSearchServiceImpl implements GifSearchService {

    private final GiphyClient client;

    private final Logger logger = LoggerFactory.getLogger(GifSearchServiceImpl.class);

    private final Random random = new Random();

    @Value("${gif-client.api_key}")
    private String api_key;

    public GifSearchServiceImpl(GiphyClient client) {
        this.client = client;
    }

    @Override
    public GifData findRichGif() throws ParseException {
        int gifNumber = random.nextInt(25);
        return loadGif("rich", gifNumber);
    }

    @Override
    public GifData findBrokeGif() throws ParseException {
        int gifNumber = random.nextInt(25);
        return loadGif("broke", gifNumber);
    }

    private GifData loadGif(String query, int gifNumber) throws ParseException {
        String json = client.search(api_key, query, 1, gifNumber);
        String url = getGifURLFromJsonObject(json);

        byte[] bytes = getGifBytes(url);
        if (bytes == null) {
            logger.error("getGifBytes returns null");
            return null;
        }

        return new GifData()
                .setUrl(url)
                .setData(bytes)
                .setSize(bytes.length);
    }

    private String getGifURLFromJsonObject(String json) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        JSONArray data = (JSONArray) jsonObject.get("data");
        JSONObject gifObject = (JSONObject) data.get(0);
        JSONObject images = (JSONObject) gifObject.get("images");
        JSONObject downsizedImage = (JSONObject) images.get("downsized");

        return (String) downsizedImage.get("url");
    }

    private byte[] getGifBytes(String html) {
        try {
            Document doc = Jsoup.parse(new URL(html), 5000);
            Elements meta = doc.select("meta[property]");

            for (Element e : meta) {
                if (e.attr("property").equalsIgnoreCase("og:url")) {
                    String gifUrl = e.attr("content");

                    URL gifData = new URL(gifUrl);
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                         InputStream in = gifData.openStream()) {
                        in.transferTo(out);
                        return out.toByteArray();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
