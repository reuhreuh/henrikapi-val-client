package net.rrworld.henrikval.client;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withRawStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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
import net.rrworld.henrikval.gen.model.V1PremierTeam;
import net.rrworld.henrikval.gen.model.V1mmrh;
import net.rrworld.henrikval.gen.model.ValorantV4MatchRegionMatchidGet200Response;

public class HenrikApiClientTest {
	
	private RestTemplate restTemplate;
	private HenrikApiClient client;
	private Resource mmrHistoryV1;
	private Resource matchV4;
	private Resource premierTeamV1;

	@BeforeEach
	public void init() throws IOException {
		this.restTemplate = new RestTemplateBuilder().build();
		this.client = new HenrikApiClient("foo-bar-api", restTemplate);
		this.mmrHistoryV1 = new ClassPathResource("v1mmrhistory.json");
		this.matchV4 = new ClassPathResource("v4match.json");
		this.premierTeamV1 = new ClassPathResource("v1PremierTeam.json");
	}
	
	@Test
	public void getV1PlayerMMRHistory_200() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr-history/eu/fe067f25-57a5-4f95-81f1-06d96b2290be"))
			.andRespond(withSuccess(mmrHistoryV1, MediaType.APPLICATION_JSON));
		
		Optional<V1mmrh> res = client.getPlayerMMRHistoryV1(Regions.EU, "fe067f25-57a5-4f95-81f1-06d96b2290be");
		
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
	public void getV1PlayerMMRHistory_400() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr-history/eu/fe067f25-57a5-4f95-81f1-06d96b2290be"))
			.andRespond(withBadRequest());
		
		Optional<V1mmrh> res = client.getPlayerMMRHistoryV1(Regions.EU, "fe067f25-57a5-4f95-81f1-06d96b2290be");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
	
	@Test
	public void getV1PlayerMMRHistory_300() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr-history/eu/fe067f25-57a5-4f95-81f1-06d96b2290be"))
			.andRespond(withRawStatus(302));
		
		Optional<V1mmrh> res = client.getPlayerMMRHistoryV1(Regions.EU, "fe067f25-57a5-4f95-81f1-06d96b2290be");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
	
	@Test
	public void getMatchV4_200() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v4/match/eu/696848f3-f16f-45bf-af13-e2192f81a600"))
			.andRespond(withSuccess(matchV4, MediaType.APPLICATION_JSON));
		
		Optional<ValorantV4MatchRegionMatchidGet200Response> res = client.getMatchV4(Regions.EU, "696848f3-f16f-45bf-af13-e2192f81a600");
		
		// response
		Assertions.assertTrue(res.isPresent(), "Response is null");
		ValorantV4MatchRegionMatchidGet200Response mr = res.get();
		// match
		Assertions.assertNotNull(mr.getData(), "Match data are null");
		Assertions.assertNotNull(mr.getData().getMetadata(), "Match medatada are null");
		Assertions.assertEquals(UUID.fromString("696848f3-f16f-45bf-af13-e2192f81a600"), mr.getData().getMetadata().getMatchId(), "Incorrect match id");
		Assertions.assertFalse(mr.getData().getPlayers().isEmpty(), "Incorrect players count");
		Assertions.assertEquals(10, mr.getData().getPlayers().size(), "Incorrect players count");
	}
	
	@Test
	public void getMatchV4_400() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v4/match/eu/696848f3-f16f-45bf-af13-e2192f81a600"))
			.andRespond(withBadRequest());
		
		Optional<ValorantV4MatchRegionMatchidGet200Response> res = client.getMatchV4(Regions.EU, "696848f3-f16f-45bf-af13-e2192f81a600");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
	
	@Test
	public void getMatchV4_300() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v4/match/eu/696848f3-f16f-45bf-af13-e2192f81a600"))
			.andRespond(withRawStatus(302));
		
		Optional<ValorantV4MatchRegionMatchidGet200Response> res = client.getMatchV4(Regions.EU, "696848f3-f16f-45bf-af13-e2192f81a600");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
	
	@Test
	public void getPremierTeamV1_200() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/premier/GoodGame/gg"))
			.andRespond(withSuccess(premierTeamV1, MediaType.APPLICATION_JSON));
		
		Optional<V1PremierTeam> res = client.getPremierTeamV1("GoodGame","gg");
		
		// response
		Assertions.assertTrue(res.isPresent(), "Response is null");
		V1PremierTeam mr = res.get();
		// Premier Team
		Assertions.assertNotNull(mr.getData(), "Premier Team data are null");
		Assertions.assertEquals(UUID.fromString("58df4050-7792-40e4-94f9-204279ed9c6d"), mr.getData().getId(), "team id is not correct");
		Assertions.assertEquals("GoodGame", mr.getData().getName(), "Wrong name");
		Assertions.assertEquals("gg", mr.getData().getTag(), "Wrong tag");
		
	}
	
	@Test
	public void getPremierTeamV1_400() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/premier/GoodGame/gg"))
			.andRespond(withBadRequest());
		
		Optional<V1PremierTeam> res = client.getPremierTeamV1("GoodGame","gg");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
	
	@Test
	public void getPremierTeamV1_300() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.henrikdev.xyz/valorant/v1/premier/GoodGame/gg"))
			.andRespond(withRawStatus(302));
		
		Optional<V1PremierTeam> res = client.getPremierTeamV1("GoodGame","gg");
		
		Assertions.assertTrue(res.isEmpty(), "Response is not null");
	}
}
