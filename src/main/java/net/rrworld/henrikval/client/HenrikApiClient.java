package net.rrworld.henrikval.client;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import net.rrworld.henrikval.gen.model.AccountV1Response;
import net.rrworld.henrikval.gen.model.AccountV2Response;
import net.rrworld.henrikval.gen.model.EsportsV1Response;
import net.rrworld.henrikval.gen.model.EsportsV2EventResponse;
import net.rrworld.henrikval.gen.model.EsportsV2EventsResponse;
import net.rrworld.henrikval.gen.model.EsportsV2MatchesResponse;
import net.rrworld.henrikval.gen.model.EsportsV2PlayerMatchesResponse;
import net.rrworld.henrikval.gen.model.EsportsV2PlayerResponse;
import net.rrworld.henrikval.gen.model.EsportsV2TeamMatchListResponse;
import net.rrworld.henrikval.gen.model.EsportsV2TeamResponse;
import net.rrworld.henrikval.gen.model.EsportsV2TeamTransactionsResponse;
import net.rrworld.henrikval.gen.model.LeaderboardV2Response;
import net.rrworld.henrikval.gen.model.LeaderboardV3Response;
import net.rrworld.henrikval.gen.model.MMRHistoryV1Response;
import net.rrworld.henrikval.gen.model.MMRHistoryV2Response;
import net.rrworld.henrikval.gen.model.MMRV1Response;
import net.rrworld.henrikval.gen.model.MMRV2Response;
import net.rrworld.henrikval.gen.model.MMRV3Response;
import net.rrworld.henrikval.gen.model.MatchesV2Response;
import net.rrworld.henrikval.gen.model.MatchesV3ListResponse;
import net.rrworld.henrikval.gen.model.MatchesV4HistoryResponse;
import net.rrworld.henrikval.gen.model.MatchesV4Response;
import net.rrworld.henrikval.gen.model.PremierSearchResponse;
import net.rrworld.henrikval.gen.model.PremierTeamHistoryV1Response;
import net.rrworld.henrikval.gen.model.PremierTeamV1Response;

/**
 * Simple Valorant client, using HenrikDev API. It provides:
 * <p>
 * Account
 * </p>
 * <ul>
 * <li>/valorant/v1/account/{name}/{tag}</li>
 * <li>/valorant/v1/by-puuid/account/{puuid}</li>
 * <li>/valorant/v2/account/{name}/{tag}</li>
 * <li>/valorant/v2/by-puuid/account/{puuid}</li>
 * </ul>
 *
 * <p>
 * MMR
 * </p>
 * <ul>
 * <li>/valorant/v1/mmr/{region}/{name}/{tag}</li>
 * <li>/valorant/v1/by-puuid/mmr/{region}/{puuid}</li>
 * <li>/valorant/v2/mmr/{region}/{name}/{tag}</li>
 * <li>/valorant/v2/by-puuid/mmr/{region}/{puuid}</li>
 * <li>/valorant/v3/mmr/{region}/{platform}/{name}/{tag}</li>
 * <li>/valorant/v3/by-puuid/mmr/{region}/{platform}/{puuid}</li>
 * </ul>
 *
 * <p>
 * MMR History
 * </p>
 * <ul>
 * <li>/valorant/v1/mmr-history/{region}/{name}/{tag}</li>
 * <li>/valorant/v1/by-puuid/mmr-history/{region}/{puuid}</li>
 * <li>/valorant/v2/mmr-history/{region}/{platform}/{name}/{tag}</li>
 * <li>/valorant/v2/by-puuid/mmr-history/{region}/{platform}/{puuid}</li>
 * </ul>
 *
 * <p>
 * Matches
 * </p>
 * <ul>
 * <li>/valorant/v3/matches/{region}/{name}/{tag}</li>
 * <li>/valorant/v3/by-puuid/matches/{region}/{puuid}</li>
 * <li>/valorant/v4/matches/{region}/{platform}/{name}/{tag}</li>
 * <li>/valorant/v4/by-puuid/matches/{region}/{platform}/{puuid}</li>
 * <li>/valorant/v2/match/{matchid}</li>
 * <li>/valorant/v4/match/{region}/{matchid}</li>
 * </ul>
 *
 * <p>
 * Leaderboard
 * </p>
 * <ul>
 * <li>/valorant/v1/leaderboard/{region}</li>
 * <li>/valorant/v2/leaderboard/{region}</li>
 * <li>/valorant/v3/leaderboard/{region}/{platform}</li>
 * </ul>
 *
 * <p>
 * Premier
 * </p>
 * <ul>
 * <li>/valorant/v1/premier/search</li>
 * <li>/valorant/v1/premier/leaderboard/{region}</li>
 * <li>/valorant/v1/premier/{team_name}/{team_tag}</li>
 * <li>/valorant/v1/premier/{team_id}</li>
 * <li>/valorant/v1/premier/{team_name}/{team_tag}/history</li>
 * <li>/valorant/v1/premier/{team_id}/history</li>
 * </ul>
 *
 * <p>
 * Esports
 * </p>
 * <ul>
 * <li>/valorant/v1/esports/schedule</li>
 * <li>/valorant/v2/esports/vlr/events</li>
 * <li>/valorant/v2/esports/vlr/events/{event_id}/matches</li>
 * <li>/valorant/v2/esports/vlr/matches/{match_id}</li>
 * <li>/valorant/v2/esports/vlr/teams/{team_id}</li>
 * <li>/valorant/v2/esports/vlr/teams/{team_id}/matches</li>
 * <li>/valorant/v2/esports/vlr/teams/{team_id}/transactions</li>
 * <li>/valorant/v2/esports/vlr/players/{player_id}</li>
 * <li>/valorant/v2/esports/vlr/players/{player_id}/matches</li>
 * </ul>
 *
 * <p>
 * Stored Matches / MMR History
 * </p>
 * <ul>
 * <li>/valorant/v1/stored-matches/{region}/{name}/{tag}</li>
 * <li>/valorant/v1/by-puuid/stored-matches/{region}/{puuid}</li>
 * <li>/valorant/v1/stored-mmr-history/{region}/{name}/{tag}</li>
 * <li>/valorant/v1/by-puuid/stored-mmr-history/{region}/{puuid}</li>
 * <li>/valorant/v2/stored-mmr-history/{region}/{platform}/{name}/{tag}</li>
 * <li>/valorant/v2/by-puuid/stored-mmr-history/{region}/{platform}/{puuid}</li>
 * </ul>
 *
 * <p>
 * Misc
 * </p>
 * <ul>
 * <li>/valorant/v1/content</li>
 * <li>/valorant/{version}/store-featured</li>
 * <li>/valorant/{version}/store-offers</li>
 * <li>/valorant/v1/crosshair/generate</li>
 * <li>/valorant/v1/status/{region}</li>
 * <li>/valorant/v1/queue-status/{region}</li>
 * <li>/valorant/v1/version/{region}</li>
 * <li>/valorant/v1/website/{countrycode}</li>
 * <li>/valorant/v1/website/{countrycode}/{db}</li>
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
	
	private RestClient restClient;

	/**
	 * Create a new HenrikDev API client, using given API Key.
	 * <p>
	 * A {@code RestTemplate} instance will be built, with a default configuration.
	 * </p>
	 * 
	 * @param apiKey the HenrikDev API key
	 */
	public HenrikApiClient(final String apiKey) {
		this(apiKey, RestClient.builder());
	}

	/**
	 * Create a new HenrikDev API client, using given API key and a configured ready
	 * to use RestTemplate client.
	 * 
	 * @param apiKey     the HenrikDev API key
	 * @param builder    an existing RestClient builder
	 */
	public HenrikApiClient(final String apiKey, final RestClient.Builder builder) {
		this.restClient = builder
				.baseUrl(HenrikURL.ROOT_URL)
				.defaultHeader(HttpHeaders.AUTHORIZATION, apiKey)
				.defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
					LOGGER.error("Server error {} while calling Henrik API {} {}", response.getStatusCode().value(), request.getMethod(), request.getURI().toString());
			    })
				.defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
					LOGGER.error("Client error {} while calling Henrik API {} {}", response.getStatusCode().value(), request.getMethod(), request.getURI().toString());
			    })
				.defaultStatusHandler(HttpStatusCode::is2xxSuccessful, (request, response) -> {
					LOGGER.info("Henrik API response OK for : {}", request.getURI().toString());
			    })
				.build();
	}

	
	// -------------------------------------------------------------------------
	// Account
	// -------------------------------------------------------------------------
 
	public Optional<AccountV1Response> getAccountV1ByTag(String name, String tag) {
		return executeGet(String.format(HenrikURL.ACCOUNT_V1_BY_TAG_URL, name, tag), AccountV1Response.class,
				"Account for player " + name + "#" + tag);
	}
 
	public Optional<AccountV1Response> getAccountV1ById(String puuid) {
		return executeGet(String.format(HenrikURL.ACCOUNT_V1_BY_ID_URL, puuid), AccountV1Response.class,
				"Account for player " + puuid);
	}
 
	public Optional<AccountV2Response> getAccountV2ByTag(String name, String tag) {
		return executeGet(String.format(HenrikURL.ACCOUNT_V2_BY_TAG_URL, name, tag), AccountV2Response.class,
				"Account V2 for player " + name + "#" + tag);
	}
 
	public Optional<AccountV2Response> getAccountV2ById(String puuid) {
		return executeGet(String.format(HenrikURL.ACCOUNT_V2_BY_ID_URL, puuid), AccountV2Response.class,
				"Account V2 for player " + puuid);
	}
 
	// -------------------------------------------------------------------------
	// MMR
	// -------------------------------------------------------------------------
 
	public Optional<MMRV1Response> getMMRV1ByTag(String region, String name, String tag) {
		return executeGet(String.format(HenrikURL.MMR_V1_BY_TAG_URL, region, name, tag), MMRV1Response.class,
				"MMR V1 for player " + name + "#" + tag + " in region " + region);
	}
 
	public Optional<MMRV1Response> getMMRV1ById(String region, String puuid) {
		return executeGet(String.format(HenrikURL.MMR_V1_BY_ID_URL, region, puuid), MMRV1Response.class,
				"MMR V1 for player " + puuid + " in region " + region);
	}
 
	public Optional<MMRV2Response> getMMRV2ByTag(String region, String name, String tag) {
		return executeGet(String.format(HenrikURL.MMR_V2_BY_TAG_URL, region, name, tag), MMRV2Response.class,
				"MMR V2 for player " + name + "#" + tag + " in region " + region);
	}
 
	public Optional<MMRV2Response> getMMRV2ById(String region, String puuid) {
		return executeGet(String.format(HenrikURL.MMR_V2_BY_ID_URL, region, puuid), MMRV2Response.class,
				"MMR V2 for player " + puuid + " in region " + region);
	}
 
	public Optional<MMRV3Response> getMMRV3ByTag(String region, String platform, String name, String tag) {
		return executeGet(String.format(HenrikURL.MMR_V3_BY_TAG_URL, region, platform, name, tag), MMRV3Response.class,
				"MMR V3 for player " + name + "#" + tag + " on platform " + platform + " in region " + region);
	}
 
	public Optional<MMRV3Response> getMMRV3ById(String region, String platform, String puuid) {
		return executeGet(String.format(HenrikURL.MMR_V3_BY_ID_URL, region, platform, puuid), MMRV3Response.class,
				"MMR V3 for player " + puuid + " on platform " + platform + " in region " + region);
	}
 
	// -------------------------------------------------------------------------
	// MMR History
	// -------------------------------------------------------------------------
 
	public Optional<MMRHistoryV1Response> getPlayerMMRHistoryV1(String region, String puuid) {
		return executeGet(String.format(HenrikURL.MMR_HISTORY_V1_BY_ID_URL, region, puuid), MMRHistoryV1Response.class,
				"MMR history V1 for player " + puuid + " in region " + region);
	}
	
 
	public Optional<MMRHistoryV1Response> getPlayerMMRHistoryV1ByTag(String region, String name, String tag) {
		return executeGet(String.format(HenrikURL.MMR_HISTORY_V1_BY_TAG_URL, region, name, tag), MMRHistoryV1Response.class,
				"MMR history V1 for player " + name + "#" + tag + " in region " + region);
	}
 
	public Optional<MMRHistoryV2Response> getPlayerMMRHistoryV2(String region, String platform, String puuid) {
		return executeGet(String.format(HenrikURL.MMR_HISTORY_V2_BY_ID_URL, region, platform, puuid), MMRHistoryV2Response.class,
				"MMR history V2 for player " + puuid + " on platform " + platform + " in region " + region);
	}
	
	public Optional<MMRHistoryV2Response> getPlayerMMRHistoryV2ByTag(String region, String platform, String name, String tag) {
		return executeGet(String.format(HenrikURL.MMR_HISTORY_V2_BY_TAG_URL, region, platform, name, tag), MMRHistoryV2Response.class,
				"MMR history V2 for player " + name + "#" + tag + " on platform " + platform + " in region " + region);
	}
 
	// -------------------------------------------------------------------------
	// Matches
	// -------------------------------------------------------------------------
 
	public Optional<MatchesV3ListResponse> getMatchesListV3ByTag(String region, String name, String tag) {
		return executeGet(String.format(HenrikURL.MATCHES_LIST_V3_BY_TAG_URL, region, name, tag), MatchesV3ListResponse.class,
				"Matches V3 for player " + name + "#" + tag + " in region " + region);
	}
 
	public Optional<MatchesV3ListResponse> getMatchesListV3ById(String region, String puuid) {
		return executeGet(String.format(HenrikURL.MATCHES_LIST_V3_BY_ID_URL, region, puuid), MatchesV3ListResponse.class,
				"Matches V3 for player " + puuid + " in region " + region);
	}
 
	public Optional<MatchesV4HistoryResponse> getMatchesListV4ByTag(String region, String platform, String name, String tag) {
		return executeGet(String.format(HenrikURL.MATCHES_LIST_V4_BY_TAG_URL, region, platform, name, tag), MatchesV4HistoryResponse.class,
				"Matches V4 for player " + name + "#" + tag + " on platform " + platform + " in region " + region);
	}
 
	public Optional<MatchesV4HistoryResponse> getMatchesListV4ById(String region, String platform, String puuid) {
		return executeGet(String.format(HenrikURL.MATCHES_LIST_V4_BY_ID_URL, region, platform, puuid), MatchesV4HistoryResponse.class,
				"Matches V4 for player " + puuid + " on platform " + platform + " in region " + region);
	}
 
	public Optional<MatchesV2Response> getMatchV2(String matchId) {
		return executeGet(String.format(HenrikURL.MATCH_V2_URL, matchId), MatchesV2Response.class,
				"match V2 " + matchId);
	}
 
	public Optional<MatchesV4Response> getMatchV4(String region, String matchId) {
		return executeGet(String.format(HenrikURL.MATCH_V4_URL, region, matchId), MatchesV4Response.class,
				"match V4 " + matchId + " in region " + region);
	}
 
	// -------------------------------------------------------------------------
	// Leaderboard
	// -------------------------------------------------------------------------
 
	public Optional<String> getLeaderboardV1(String region) {
		return executeGet(String.format(HenrikURL.LEADERBOARD_V1_URL, region), String.class,
				"Leaderboard V1 in region " + region);
	}
 
	public Optional<LeaderboardV2Response> getLeaderboardV2(String region) {
		return executeGet(String.format(HenrikURL.LEADERBOARD_V2_URL, region), LeaderboardV2Response.class,
				"Leaderboard V2 in region " + region);
	}
 
	public Optional<LeaderboardV3Response> getLeaderboardV3(String region, String platform) {
		return executeGet(String.format(HenrikURL.LEADERBOARD_V3_URL, region, platform), LeaderboardV3Response.class,
				"Leaderboard V3 in region " + region + " on platform " + platform);
	}
 
	// -------------------------------------------------------------------------
	// Premier
	// -------------------------------------------------------------------------
 
	public Optional<PremierSearchResponse> searchPremierTeamV1(String name, String tag, String id) {
		//TODO add parameters
		return executeGet(HenrikURL.PREMIER_SEARCH_V1_URL, PremierSearchResponse.class,
				"Premier team search");
	}
 
	public Optional<PremierSearchResponse> getPremierLeaderboardV1(String region) {
		return executeGet(String.format(HenrikURL.PREMIER_LEADERBOARD_V1_URL, region), PremierSearchResponse.class,
				"Premier leaderboard in region " + region);
	}
 
	public Optional<PremierTeamV1Response> getPremierTeamV1(String teamName, String teamTag) {
		return executeGet(String.format(HenrikURL.PREMIER_TEAM_V1_BY_TAG_URL, teamName, teamTag), PremierTeamV1Response.class,
				"Premier team " + teamName + "#" + teamTag);
	}
	
	public Optional<PremierTeamV1Response> getPremierTeamV1ById(String teamId) {
		return executeGet(String.format(HenrikURL.PREMIER_TEAM_V1_BY_ID_URL, teamId), PremierTeamV1Response.class,
				"Premier team " + teamId);
	}
 
	public Optional<PremierTeamHistoryV1Response> getPremierTeamHistoryV1ByTag(String teamName, String teamTag) {
		return executeGet(String.format(HenrikURL.PREMIER_HISTORY_V1_BY_TAG_URL, teamName, teamTag), PremierTeamHistoryV1Response.class,
				"Premier history for team " + teamName + "#" + teamTag);
	}
 
	public Optional<PremierTeamHistoryV1Response> getPremierTeamHistoryV1ById(String teamId) {
		return executeGet(String.format(HenrikURL.PREMIER_HISTORY_V1_BY_ID_URL, teamId), PremierTeamHistoryV1Response.class,
				"Premier history for team " + teamId);
	}
 
	// -------------------------------------------------------------------------
	// Esports
	// -------------------------------------------------------------------------
 
	public Optional<EsportsV1Response> getEsportScheduleV1() {
		return executeGet(HenrikURL.ESPORT_SCHEDULE_V1_URL, EsportsV1Response.class,
				"Esport schedule");
	}
 
	public Optional<EsportsV2EventsResponse> getEsportVlrEventsV2() {
		return executeGet(HenrikURL.ESPORT_VLR_EVENTS_V2_URL, EsportsV2EventsResponse.class,
				"VLR esport events");
	}
 
	public Optional<EsportsV2EventResponse> getEsportVlrEventMatchesV2(String eventId) {
		return executeGet(String.format(HenrikURL.ESPORT_VLR_EVENTS_MATCHES_V2_URL, eventId), EsportsV2EventResponse.class,
				"VLR Matches for event " + eventId);
	}
 
	public Optional<EsportsV2MatchesResponse> getEsportVlrMatchV2(String matchId) {
		return executeGet(String.format(HenrikURL.ESPORT_VLR_EVENTS_MATCH_V2_URL, matchId), EsportsV2MatchesResponse.class,
				"VLR esport match " + matchId);
	}
 
	public Optional<EsportsV2TeamResponse> getEsportVlrTeamV2(String teamId) {
		return executeGet(String.format(HenrikURL.ESPORT_VLR_TEAM_V2_URL, teamId), EsportsV2TeamResponse.class,
				"VLR esport team " + teamId);
	}
 
	public Optional<EsportsV2TeamMatchListResponse> getEsportVlrTeamMatchesV2(String teamId) {
		return executeGet(String.format(HenrikURL.ESPORT_VLR_TEAM_MATCHES_EVENTS_V2_URL, teamId), EsportsV2TeamMatchListResponse.class,
				"VLR Matches for team " + teamId);
	}
 
	public Optional<EsportsV2TeamTransactionsResponse> getEsportVlrTeamTransactionsV2(String teamId) {
		return executeGet(String.format(HenrikURL.ESPORT_VLR_TEAM_TRANSACTIONS_V2_URL, teamId), EsportsV2TeamTransactionsResponse.class,
				"VLR transactions for team " + teamId);
	}
 
	public Optional<EsportsV2PlayerResponse> getEsportVlrPlayerV2(String playerId) {
		return executeGet(String.format(HenrikURL.ESPORT_VLR_PLAYER_V2_URL, playerId), EsportsV2PlayerResponse.class,
				"VLR esport player " + playerId);
	}
 
	public Optional<EsportsV2PlayerMatchesResponse> getEsportVlrPlayerMatchesV2(String playerId) {
		return executeGet(String.format(HenrikURL.ESPORT_VLR_PLAYER_MATCHES_V2_URL, playerId), EsportsV2PlayerMatchesResponse.class,
				"VLR matches for player " + playerId);
	}
 
	// -------------------------------------------------------------------------
	// Stored Matches
	// -------------------------------------------------------------------------
 
	public Optional<?> getStoredMatchesV1ByTag(String region, String name, String tag) {
		return executeGet(String.format(HenrikURL.STORED_MATCHES_V1_BY_TAG_URL, region, name, tag), Object.class,
				"Stored matches V1 for player " + name + "#" + tag + " in region " + region);
	}
 
	public Optional<?> getStoredMatchesV1ById(String region, String puuid) {
		return executeGet(String.format(HenrikURL.STORED_MATCHES_V1_BY_ID_URL, region, puuid), Object.class,
				"Stored matches V1 for player " + puuid + " in region " + region);
	}
 
	// -------------------------------------------------------------------------
	// Stored MMR History
	// -------------------------------------------------------------------------
 
	public Optional<?> getStoredMMRHistoryV1ByTag(String region, String name, String tag) {
		return executeGet(String.format(HenrikURL.STORED_MMR_HISTORY_V1_BY_TAG_URL, region, name, tag), Object.class,
				"Stored MMR history V1 for player " + name + "#" + tag + " in region " + region);
	}
 
	public Optional<?> getStoredMMRHistoryV1ById(String region, String puuid) {
		return executeGet(String.format(HenrikURL.STORED_MMR_HISTORY_V1_BY_ID_URL, region, puuid), Object.class,
				"Stored MMR history V1 for player " + puuid + " in region " + region);
	}
 
	public Optional<?> getStoredMMRHistoryV2ByTag(String region, String platform, String name, String tag) {
		return executeGet(String.format(HenrikURL.STORED_MMR_HISTORY_V2_BY_TAG_URL, region, platform, name, tag), Object.class,
				"Stored MMR history V2 for player " + name + "#" + tag + " on platform " + platform + " in region " + region);
	}
 
	public Optional<?> getStoredMMRHistoryV2ById(String region, String platform, String puuid) {
		return executeGet(String.format(HenrikURL.STORED_MMR_HISTORY_V2_BY_ID_URL, region, platform, puuid), Object.class,
				"Stored MMR history V2 for player " + puuid + " on platform " + platform + " in region " + region);
	}
 
	// -------------------------------------------------------------------------
	// Misc
	// -------------------------------------------------------------------------
 
	public Optional<?> getContentV1() {
		return executeGet(HenrikURL.CONTENT_V1_URL, Object.class,
				"Game content");
	}
 
	public Optional<?> getStoreFeatured(String version) {
		return executeGet(String.format(HenrikURL.STORED_FEATURED_URL, version), Object.class,
				"Featured store items (version " + version + ")");
	}
 
	public Optional<?> getStoreOffers(String version) {
		return executeGet(String.format(HenrikURL.STORE_OFFERS_URL, version), Object.class,
				"Store offers (version " + version + ")");
	}
 
	public Optional<?> generateCrosshairV1() {
		return executeGet(HenrikURL.CROSSHAIR_GENERATE_V1_URL, Object.class,
				"Crosshair generation");
	}
 
	public Optional<?> getStatusV1(String region) {
		return executeGet(String.format(HenrikURL.STATUS_V1_URL, region), Object.class,
				"Server status in region " + region);
	}
 
	public Optional<?> getQueueStatusV1(String region) {
		return executeGet(String.format(HenrikURL.QUEUE_STATUS_V1_URL, region), Object.class,
				"Queue status in region " + region);
	}
 
	public Optional<?> getVersionV1(String region) {
		return executeGet(String.format(HenrikURL.VERSION_V1_URL, region), Object.class,
				"Game version in region " + region);
	}
 
	public Optional<?> getWebsiteV1(String countryCode) {
		return executeGet(String.format(HenrikURL.WEBSITE_V1_URL, countryCode), Object.class,
				"Website content for country " + countryCode);
	}
 
	public Optional<?> getWebsiteV1Db(String countryCode, String db) {
		return executeGet(String.format(HenrikURL.WEBSITE_V1_DB_URL, countryCode, db), Object.class,
				"Website content for country " + countryCode + " (db: " + db + ")");
	}

	// Internal
	private <T> Optional<T> executeGet(String url, Class<T> responseType, String logContext) {
		LOGGER.info("Calling GET {}", logContext);
		T response = restClient
				.get()
				.uri(url)
				.retrieve()
				.body(responseType);
		return Optional.ofNullable(response);
	}
}
