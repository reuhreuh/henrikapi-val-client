package net.rrworld.henrikval.client;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import net.rrworld.henrikval.gen.model.MMRHistoryV1Response;
import net.rrworld.henrikval.gen.model.MMRHistoryV2Response;
import net.rrworld.henrikval.gen.model.MatchesV4Response;
import net.rrworld.henrikval.gen.model.PremierTeamV1Response;

/**
 * Simple Valorant client, using HenrikDev API. It provides:
 * <p>
 * Player
 * </p>
 * <ul>
 * <li>/valorant/v1/by-puuid/mmr-history/{region}/{puuid}</li>
 * </ul>
 * 
 * <p>
 * Match
 * </p>
 * <ul>
 * <li>/valorant/v4/match/{region}/{matchid}</li>
 * </ul>
 * 
 * <p>
 * Premier
 * </p>
 * <ul>
 * <li>/valorant/v1/premier/{team_name}/{team_tag}</li>
 * </ul>
 * 
 * @see <a href=
 *      "https://app.swaggerhub.com/apis-docs/Henrik-3/HenrikDev-API">HenrikDev-API
 *      Swagger</a>
 * @see <a href="https://docs.henrikdev.xyz/valorant/general">HenrikDev-API
 *      documentation</a>
 * @author reuhreuh
 */
public class HenrikApiClient {

	private final Logger LOGGER = LoggerFactory.getLogger(HenrikApiClient.class);

	private String apiKey;
	private RestTemplate restClient;

	/**
	 * Create a new HenrikDev API client, using given API Key.
	 * <p>
	 * A {@code RestTemplate} instance will be built, with a default configuration.
	 * </p>
	 * 
	 * @param apiKey the HenrikDev API key
	 */
	public HenrikApiClient(final String apiKey) {
		this(apiKey, new RestTemplateBuilder().rootUri(HenrikURL.ROOT_URL).build());
	}

	/**
	 * Create a new HenrikDev API client, using given API key and a configured ready
	 * to use RestTemplate client.
	 * 
	 * @param apiKey     the HenrikDev API key
	 * @param restClient configured http client
	 */
	public HenrikApiClient(final String apiKey, final RestTemplate restClient) {
		this.apiKey = apiKey;
		this.restClient = restClient;
	}

	public Optional<MMRHistoryV1Response> getPlayerMMRHistoryV1(String region, String puuid) {
		return executeGet(String.format(HenrikURL.MMR_HISTORY_V1_BY_ID_URL, region, puuid), MMRHistoryV1Response.class,
				"MMR history for player " + puuid + " in region " + region);
	}

	public Optional<MMRHistoryV2Response> getPlayerMMRHistoryV2(String region, String platform, String puuid) {
		return executeGet(String.format(HenrikURL.MMR_HISTORY_V2_BY_ID_URL, region, platform, puuid), MMRHistoryV2Response.class,
				"MMR history V2 for player " + puuid + " in region " + region);
	}

	public Optional<MatchesV4Response> getMatchV4(String region, String matchId) {
		return executeGet(String.format(HenrikURL.MATCH_V4_URL, region, matchId), MatchesV4Response.class,
				"match " + matchId + " in region " + region);
	}

	public Optional<PremierTeamV1Response> getPremierTeamV1(String teamName, String teamTag) {
		return executeGet(String.format(HenrikURL.PREMIER_TEAM_V1_BY_TAG_URL, teamName, teamTag), PremierTeamV1Response.class,
				"Premier team " + teamName + "#" + teamTag);
	}

	private <T> Optional<T> executeGet(String url, Class<T> responseType, String logContext) {
		LOGGER.info("Calling GET {}", logContext);
		try {
			HttpEntity<String> entity = new HttpEntity<>(buildHeaders());
			ResponseEntity<T> response = restClient.exchange(url, HttpMethod.GET, entity, responseType);
			if (HttpStatus.OK == response.getStatusCode()) {
				LOGGER.info("GET {} succeeded", logContext);
			} else {
				LOGGER.warn("GET {} returned HTTP {}", logContext, response.getStatusCode().value());
			}
			return Optional.ofNullable(response.getBody());
		} catch (RestClientException e) {
			LOGGER.error("GET {} failed : {}", logContext, e.getMessage());
			return Optional.empty();
		}
	}

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, apiKey);
		return headers;
	}
}
