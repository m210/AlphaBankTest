package ru.m210projects.alphabanktest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.m210projects.alphabanktest.entity.GiphyApiResult;

@FeignClient(name = "gif-client", url = "${gif-client.url}")
public interface GiphyClient {
	
	@GetMapping("/search")
	GiphyApiResult search(@RequestParam("api_key") String api_key,
                  @RequestParam("q") String quote,
                  @RequestParam("limit") int limit);
}
