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
import ru.m210projects.alphabanktest.service.ExchangeRateService;
import ru.m210projects.alphabanktest.service.GifSearchService;
import ru.m210projects.alphabanktest.service.impl.UsdToCurrencyIndicatorServiceImpl;


@WebMvcTest(controllers = UsdToCurrencyIndicatorController.class)
class AlphaBankTestWebMvcTests {

	private final byte[] BROKE_GIF = getDefaultBrokeGif();
	private final byte[] RICH_GIF = getDefaultRichGif();

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
	public void setup() {
		when(gifSearchService.findGif(eq("broke"))).thenReturn(BROKE_GIF);
		when(gifSearchService.findGif(eq("rich"))).thenReturn(RICH_GIF);
	}

	@Test
	void shouldResponceWelcome() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get("/indicator/welcome")
						.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string("Welcome!"));
	}

	@Test
	void shouldResponceBrokeGif() throws Exception {
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now()), any(String.class))).thenReturn(1.0);
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now().minusDays(1)), any(String.class))).thenReturn(2.0);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/indicator/compareWithYesterdayUSDRate?currency=rub")
						.accept(MediaType.IMAGE_GIF))
				.andExpect(status().isOk())
				.andExpect(content().bytes(BROKE_GIF));

		verify(gifSearchService, never()).findGif("rich");
		verify(gifSearchService, atLeastOnce()).findGif("broke");
	}

	@Test
	void shouldResponceRichGif() throws Exception {
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now()), any(String.class))).thenReturn(4.0);
		when(exchangeRateService.getHistoricalRate(eq(LocalDate.now().minusDays(1)), any(String.class))).thenReturn(3.0);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/indicator/compareWithYesterdayUSDRate?currency=rub")
						.accept(MediaType.IMAGE_GIF))
				.andExpect(status().isOk())
				.andExpect(content().bytes(RICH_GIF));

		verify(gifSearchService, atLeastOnce()).findGif("rich");
		verify(gifSearchService, never()).findGif("broke");
	}

	private byte[] getDefaultBrokeGif() {
		return new byte[] { 1 };
	}

	private byte[] getDefaultRichGif() {
		return new byte[] { 2 };
	}
}
