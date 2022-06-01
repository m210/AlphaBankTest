package ru.m210projects.alphabanktest.controller;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.m210projects.alphabanktest.entity.GifData;
import ru.m210projects.alphabanktest.service.UsdToCurrencyIndicatorService;

import java.time.LocalDate;

@RestController
@RequestMapping("/indicator")
public class UsdToCurrencyIndicatorController {

	private final UsdToCurrencyIndicatorService service;

	public UsdToCurrencyIndicatorController(UsdToCurrencyIndicatorService service) {
		this.service = service;
	}

	@GetMapping("/welcome")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Welcome!");
	}

    @GetMapping("/compareUSDRateByDate")
    public ResponseEntity<byte[]> checkUSDRateByDate(@RequestParam LocalDate date, @RequestParam String currency) throws ParseException {
		return buildResponse(service.compare(date, currency));
    }

	@GetMapping("/compareWithYesterdayUSDRate")
	public ResponseEntity<byte[]> checkYesterdayUSDRate(@RequestParam String currency) throws ParseException {
		return buildResponse(service.compare(LocalDate.now().minusDays(1), currency));
	}

	private ResponseEntity<byte[]> buildResponse(GifData gif) {
		if(gif == null) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_GIF);
		headers.setContentLength(gif.getSize());

		return ResponseEntity.ok().headers(headers).body(gif.getData());
	}

}
