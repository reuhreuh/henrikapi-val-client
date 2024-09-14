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

import net.rrworld.henrikval.gen.model.V1mmrh;

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

	private static final String ROOT_URL = "https://api.henrikdev.xyz";
	private static final String PLAYER_MMR_HISTORY_URL = ROOT_URL + "/valorant/v1/by-puuid/mmr-history/%s/%s";

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
		this(apiKey, new RestTemplateBuilder().build());
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

	public Optional<V1mmrh> getV1PlayerMMRHistory(final String region, final String puuid) {
		LOGGER.info("Retrieving MMR history for player {} in region {}", puuid, region);
		String url = String.format(PLAYER_MMR_HISTORY_URL, region, puuid);
		Optional<V1mmrh> res = null;
		try {
			HttpEntity<String> entity = new HttpEntity<>(buildHeaders());
			ResponseEntity<V1mmrh> response = restClient.exchange(url, HttpMethod.GET, entity, V1mmrh.class);
			if (HttpStatus.OK == response.getStatusCode()) {
				LOGGER.info("MMR history for player {} found", puuid);
			} else {
				LOGGER.warn("MMR history for player {} not found. HTTP response code : {}", puuid,
					response.getStatusCode().value());
			}
			res = Optional.ofNullable(response.getBody());
		} catch (RestClientException e) {
			LOGGER.error("Error while calling HenrikDev API for getV1PlayerMMRHistory({},{}) : {}", region, puuid, e.getMessage());
			res = Optional.ofNullable(null);
		}
		return res;

	}

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, apiKey);
		return headers;
	}
}
