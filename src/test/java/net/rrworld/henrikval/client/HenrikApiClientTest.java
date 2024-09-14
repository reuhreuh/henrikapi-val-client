package net.rrworld.henrikval.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withRawStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import net.rrworld.henrikval.gen.model.Regions;
import net.rrworld.henrikval.gen.model.V1mmrh;

public class HenrikApiClientTest {
	
	private RestTemplate restTemplate;
	private HenrikApiClient client;
	private Resource v1MMRHistory;

	@BeforeEach
	public void init() throws IOException {
		this.restTemplate = new RestTemplateBuilder().build();
		this.client = new HenrikApiClient("foo-bar-api", restTemplate);
		this.v1MMRHistory = new ClassPathResource("v1mmrhistory.json");
	}
	
	@Test
	public void getV1PlayerMMRHistory200() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr-history/eu/fe067f25-57a5-4f95-81f1-06d96b2290be"))
			.andRespond(withSuccess(v1MMRHistory, MediaType.APPLICATION_JSON));
		
		Optional<V1mmrh> res = client.getV1PlayerMMRHistory(Regions.EU.getValue(), "fe067f25-57a5-4f95-81f1-06d96b2290be");
		
		
		// response
		Assertions.assertTrue(res.isPresent(), "Response is null");
		
		V1mmrh mmrh = res.get();
		
		// player
		Assertions.assertEquals("Henrik3", mmrh.getName(), "Wrong player name");
		Assertions.assertEquals("EUW3", mmrh.getTag(), "Wrong player tag");
		
		// history
		Assertions.assertEquals(1, mmrh.getData().size(), "Wrong history length");
		Assertions.assertEquals("e5a3301c-c8e5-43bc-be94-a5c0d5275fd4", mmrh.getData().get(0).getMatchId(), "Wrong match id");
	}
	
	@Test
	public void getV1PlayerMMRHistory400() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr-history/eu/fe067f25-57a5-4f95-81f1-06d96b2290be"))
			.andRespond(withBadRequest());
		
		Optional<V1mmrh> res = client.getV1PlayerMMRHistory(Regions.EU.getValue(), "fe067f25-57a5-4f95-81f1-06d96b2290be");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
	
	@Test
	public void getV1PlayerMMRHistory300() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr-history/eu/fe067f25-57a5-4f95-81f1-06d96b2290be"))
			.andRespond(withRawStatus(302));
		
		Optional<V1mmrh> res = client.getV1PlayerMMRHistory(Regions.EU.getValue(), "fe067f25-57a5-4f95-81f1-06d96b2290be");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
}
