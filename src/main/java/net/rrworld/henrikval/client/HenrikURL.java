package net.rrworld.henrikval.client;

/**
 * Constants class for all end-points
 */
final class HenrikURL {
	
	public static final String ROOT_URL = "https://api.henrikdev.xyz";
	
	public static final String ACCOUNT_V1_BY_TAG_URL = ROOT_URL + "/valorant/v1/account/%s/%s";
	public static final String ACCOUNT_V1_BY_ID_URL = ROOT_URL + "/valorant/v1/by-puuid/account/%s";
	public static final String ACCOUNT_V2_BY_TAG_URL = ROOT_URL + "/valorant/v2/account/%s/%s";
	public static final String ACCOUNT_V2_BY_ID_URL = ROOT_URL + "/valorant/v2/by-puuid/account/%s";
	public static final String MMR_V1_BY_TAG_URL = ROOT_URL + "/valorant/v1/mmr/%s/%s/%s";
	public static final String MMR_V1_BY_ID_URL = ROOT_URL + "/valorant/v1/by-puuid/mmr/%s/%s";
	public static final String MMR_V2_BY_TAG_URL = ROOT_URL + "/valorant/v2/mmr/%s/%s/%s";
	public static final String MMR_V2_BY_ID_URL = ROOT_URL + "/valorant/v2/by-puuid/mmr/%s/%s";
	public static final String MMR_V3_BY_TAG_URL = ROOT_URL + "/valorant/v3/mmr/%s/%s/%s/%s";
	public static final String MMR_V3_BY_ID_URL = ROOT_URL + "/valorant/v3/by-puuid/mmr/%s/%s/%s";
	public static final String MMR_HISTORY_V1_BY_TAG_URL = ROOT_URL + "/valorant/v1/mmr-history/%s/%s/%s";
	public static final String MMR_HISTORY_V1_BY_ID_URL = ROOT_URL + "/valorant/v1/by-puuid/mmr-history/%s/%s";
	public static final String MMR_HISTORY_V2_BY_TAG_URL = ROOT_URL + "/valorant/v2/mmr-history/%s/%s/%s/%s";
	public static final String MMR_HISTORY_V2_BY_ID_URL = ROOT_URL + "/valorant/v2/by-puuid/mmr-history/%s/%s/%s";
	public static final String MATCHES_LIST_V3_BY_TAG_URL = ROOT_URL + "/valorant/v3/matches/%s/%s/%s";
	public static final String MATCHES_LIST_V3_BY_ID_URL = ROOT_URL + "/valorant/v3/by-puuid/matches/%s/%s";
	public static final String MATCHES_LIST_V4_BY_TAG_URL = ROOT_URL + "/valorant/v4/matches/%s/%s/%s/%s";
	public static final String MATCHES_LIST_V4_BY_ID_URL = ROOT_URL + "/valorant/v4/by-puuid/matches/%s/%s/%s";
	public static final String MATCH_V2_URL = ROOT_URL + "/valorant/v2/match/%s";
	public static final String MATCH_V4_URL = ROOT_URL + "/valorant/v4/match/%s/%s";
	public static final String LEADERBOARD_V1_URL = ROOT_URL + "/valorant/v1/leaderboard/%s";
	public static final String LEADERBOARD_V2_URL = ROOT_URL + "/valorant/v2/leaderboard/%s";
	public static final String LEADERBOARD_V3_URL = ROOT_URL + "/valorant/v3/leaderboard/%s/%s";
	public static final String PREMIER_SEARCH_V1_URL = ROOT_URL + "/valorant/v1/premier/search";
	public static final String PREMIER_LEADERBOARD_V1_URL = ROOT_URL + "/valorant/v1/premier/leaderboard/%s";
	public static final String PREMIER_TEAM_V1_BY_TAG_URL = ROOT_URL + "/valorant/v1/premier/%s/%s";
	public static final String PREMIER_TEAM_V1_BY_ID_URL = ROOT_URL + "/valorant/v1/premier/%s";
	public static final String PREMIER_HISTORY_V1_BY_TAG_URL = ROOT_URL + "/valorant/v1/premier/%s/%s/history";
	public static final String PREMIER_HISTORY_V1_BY_ID_URL = ROOT_URL + "/valorant/v1/premier/%s/history";
	public static final String ESPORT_SCHEDULE_V1_URL = ROOT_URL + "/valorant/v1/esports/schedule";
	public static final String ESPORT_VLR_EVENTS_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/events";
	public static final String ESPORT_VLR_EVENTS_MATCHES_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/events/%s/matches";
	public static final String ESPORT_VLR_EVENTS_MATCH_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/matches/%s";
	public static final String ESPORT_VLR_TEAM_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/teams/%s";
	public static final String ESPORT_VLR_TEAM_MATCHES_EVENTS_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/teams/%s/matches";
	public static final String ESPORT_VLR_TEAM_TRANSACTIONS_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/teams/%s/transactions";
	public static final String ESPORT_VLR_PLAYER_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/players/%s";
	public static final String ESPORT_VLR_PLAYER_MATCHES_V2_URL = ROOT_URL + "/valorant/v2/esports/vlr/players/%s/matches";
	public static final String STORED_MATCHES_V1_BY_TAG_URL = ROOT_URL + "/valorant/v1/stored-matches/%s/%s/%s";
	public static final String STORED_MATCHES_V1_BY_ID_URL = ROOT_URL + "/valorant/v1/by-puuid/stored-matches/%s/%s";
	public static final String STORED_MMR_HISTORY_V1_BY_TAG_URL = ROOT_URL + "/valorant/v1/stored-mmr-history/%s/%s/%s";
	public static final String STORED_MMR_HISTORY_V1_BY_ID_URL = ROOT_URL + "/valorant/v1/by-puuid/stored-mmr-history/%s/%s";
	public static final String STORED_MMR_HISTORY_V2_BY_TAG_URL = ROOT_URL + "/valorant/v2/stored-mmr-history/%s/%s/%s/%s";
	public static final String STORED_MMR_HISTORY_V2_BY_ID_URL = ROOT_URL + "/valorant/v2/by-puuid/stored-mmr-history/%s/%s/%s";
	public static final String CONTENT_V1_URL = ROOT_URL + "/valorant/v1/content";
	public static final String STORED_FEATURED_URL = ROOT_URL + "/valorant/%s/store-featured";
	public static final String STORE_OFFERS_URL = ROOT_URL + "/valorant/%s/store-offers";
	public static final String CROSSHAIR_GENERATE_V1_URL = ROOT_URL + "/valorant/v1/crosshair/generate";
	public static final String STATUS_V1_URL = ROOT_URL + "/valorant/v1/status/%s";
	public static final String QUEUE_STATUS_V1_URL = ROOT_URL + "/valorant/v1/queue-status/%s";
	public static final String VERSION_V1_URL = ROOT_URL + "/valorant/v1/version/%s";
	public static final String WEBSITE_V1_URL = ROOT_URL + "/valorant/v1/website/%s";
	public static final String WEBSITE_V1_DB_URL = ROOT_URL + "/valorant/v1/website/%s/%s";
	public static final String RAW_V1_URL = ROOT_URL + "/valorant/v1/raw";
}
