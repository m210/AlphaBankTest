package ru.m210projects.alphabanktest.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.m210projects.alphabanktest.entity.GifMetadata;
import ru.m210projects.alphabanktest.entity.GiphyApiResult;
import ru.m210projects.alphabanktest.client.GifDataClient;
import ru.m210projects.alphabanktest.client.GiphyClient;
import ru.m210projects.alphabanktest.service.GifSearchService;

@Service
public class GifSearchServiceImpl implements GifSearchService {

    private final GiphyClient client;
    private final GifDataClient dataClient;
    
    private final Logger logger = LoggerFactory.getLogger(GifSearchServiceImpl.class);
    
    @Value("${gif-client.api_key}")
    private String api_key;

    public GifSearchServiceImpl(GiphyClient client, GifDataClient dataClient) {
    	this.client = client;
    	this.dataClient = dataClient;
    }

	@Override
	public byte[] findGif(String query) {
		GiphyApiResult res = client.search(api_key, query, 100);
    	List<GifMetadata> list = res.getData();
    	
    	logger.debug("Calling findGif({})", query);
        return dataClient.getGifData(list.stream().skip((int) (list.size() * Math.random())).findAny().get().getId());
    }
}
