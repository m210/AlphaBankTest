package ru.m210projects.alphabanktest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.m210projects.alphabanktest.entity.ExchangeApiResult;

@FeignClient(name = "exchange-client", url = "${exchange-client.url}")
public interface OpenExchangeClient {

    @GetMapping("/historical/{date}.json")
    ExchangeApiResult historical(@PathVariable("date") String date, @RequestParam("app_id") String app_id, @RequestParam("base") String base);

}
