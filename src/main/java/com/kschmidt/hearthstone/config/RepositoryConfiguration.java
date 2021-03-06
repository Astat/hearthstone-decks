package com.kschmidt.hearthstone.config;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kschmidt.hearthstone.domain.Deck;
import com.kschmidt.hearthstone.repository.impl.ExcelDeckRepository;
import com.kschmidt.hearthstone.repository.impl.HearthpwnRepository;
import com.kschmidt.hearthstone.repository.impl.HearthstoneTopDeckRepository;
import com.kschmidt.hearthstone.repository.impl.IcyVeinsDeckRepository;
import com.kschmidt.hearthstone.repository.impl.JSONCardRepository;
import com.kschmidt.hearthstone.repository.impl.TempoStormDeckRepository;

@Configuration
public class RepositoryConfiguration {

	@Bean
	public JSONCardRepository jsonCardRepository() throws JsonParseException,
			JsonMappingException, IOException {
		return new JSONCardRepository("cards.collectible.json");
	}

	@Bean
	public HearthstoneTopDeckRepository hearthstoneTopDeckRepository()
			throws JsonParseException, JsonMappingException, IOException {
		return new HearthstoneTopDeckRepository(jsonCardRepository());
	}

	@Bean
	public IcyVeinsDeckRepository icyVeinsDeckRepository()
			throws JsonParseException, JsonMappingException, IOException {
		return new IcyVeinsDeckRepository(jsonCardRepository());
	}

	@Bean
	public TempoStormDeckRepository tempoStormDeckRepository()
			throws JsonParseException, JsonMappingException, IOException {
		return new TempoStormDeckRepository(jsonCardRepository());
	}

	@Bean
	public HearthpwnRepository hearthpwnRepository() throws JsonParseException,
			JsonMappingException, IOException {
		return new HearthpwnRepository(jsonCardRepository());
	}

	@Bean
	public Deck userDeck() throws JsonParseException, JsonMappingException,
			IOException, InvalidFormatException {
		ExcelDeckRepository excelDeckRepository = new ExcelDeckRepository(
				jsonCardRepository());
		return excelDeckRepository.getDeck("HearthstoneMasterCollection.xlsx");
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("decks");
	}

}
