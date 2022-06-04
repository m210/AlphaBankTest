package ru.m210projects.alphabanktest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ru.m210projects.alphabanktest.controller.UsdToCurrencyIndicatorController;
import ru.m210projects.alphabanktest.entity.GifData;
import ru.m210projects.alphabanktest.service.ExchangeRateService;
import ru.m210projects.alphabanktest.service.GifSearchService;
import ru.m210projects.alphabanktest.service.impl.UsdToCurrencyIndicatorServiceImpl;


@WebMvcTest(controllers = UsdToCurrencyIndicatorController.class)
class AlphaBankTestWebMvcTests {

	private final GifData BROKE_GIF = getDefaultBrokeGif();
	private final GifData RICH_GIF = getDefaultRichGif();
	
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    private ExchangeRateService exchangeRateService;
	
	@MockBean
    private GifSearchService gifSearchService;
	
	@SpyBean
	private UsdToCurrencyIndicatorServiceImpl service;
	
	@InjectMocks
	private UsdToCurrencyIndicatorController controller;
	
	@BeforeEach
    public void setup() throws ParseException{
		when(gifSearchService.findBrokeGif()).thenReturn(BROKE_GIF);
		when(gifSearchService.findRichGif()).thenReturn(RICH_GIF);
    }

	@Test
	void shouldResponseWelcome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/indicator/welcome")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome!"));
	}
	
	@Test
	void shouldResponseBrokeGif() throws Exception {
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now()), any(String.class))).thenReturn(1.0);
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now().minusDays(1)), any(String.class))).thenReturn(2.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/indicator/compareWithYesterdayUSDRate?currency=rub")
                        .accept(MediaType.IMAGE_GIF))
                .andExpect(status().isOk())
                .andExpect(content().bytes(BROKE_GIF.getData()));
                
       verify(gifSearchService, never()).findRichGif();
       verify(gifSearchService, atLeastOnce()).findBrokeGif();
	}
	
	@Test
	void shouldResponseRichGif() throws Exception {
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now()), any(String.class))).thenReturn(4.0);
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now().minusDays(1)), any(String.class))).thenReturn(3.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/indicator/compareWithYesterdayUSDRate?currency=rub")
                        .accept(MediaType.IMAGE_GIF))
                .andExpect(status().isOk())
                .andExpect(content().bytes(RICH_GIF.getData()));
                
       verify(gifSearchService, atLeastOnce()).findRichGif();
       verify(gifSearchService, never()).findBrokeGif();
	}
	
	private GifData getDefaultBrokeGif() {
		GifData data = new GifData();
		data.setData(new byte[] { 1 });
		data.setSize(1);
		
		return data;
	}
	
	private GifData getDefaultRichGif() {
		GifData data = new GifData();
		data.setData(new byte[] { 2 });
		data.setSize(1);
		
		return data;
	}

}
