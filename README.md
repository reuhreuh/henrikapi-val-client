# henrikapi-val-client
Valorant Java client based on [HenrikDev API](https://docs.henrikdev.xyz/).


## Status

| Type | Status |
| ---- | ------ |
| Build | [![Maven build CI](https://github.com/reuhreuh/henrikapi-val-client/actions/workflows/maven-master.yml/badge.svg)](https://github.com/reuhreuh/henrikapi-val-client/actions/workflows/maven-master.yml)|
| Quality | [![CodeQL](https://github.com/reuhreuh/henrikapi-val-client/actions/workflows/codeql.yml/badge.svg)](https://github.com/reuhreuh/henrikapi-val-client/actions/workflows/codeql.yml)|
| Javadoc | [![javadoc](https://javadoc.io/badge2/net.rr-world/henrikapi-val-client/javadoc.svg)](https://javadoc.io/doc/net.rr-world/henrikapi-val-client)|
| Tests | ![GitHub Workflow Status](https://raw.githubusercontent.com/reuhreuh/henrikapi-val-client/master/.github/badges/jacoco.svg)|
|Licence | [![GitHub license](https://img.shields.io/github/license/reuhreuh/henrikapi-val-client)](https://github.com/reuhreuh/henrikapi-val-client/blob/master/LICENSE)|


## Features
`henrikapi-val-client` provides 1 to 1 SDK get data from [HenrikDev API](https://docs.henrikdev.xyz/valorant/general).
This API is free, however, you'll have to request an API key on [HenrikDev System Discord](https://discord.gg/b5FmTqG)

So far following end-points are implemented:

`Player`
- /valorant/v1/by-puuid/mmr-history/{region}/{puuid}

`Match`
- /valorant/v4/match/{region}/{matchid}

`Premier`
- /valorant/v1/premier/{team_name}/{team_tag}

The SDK also provides a complete API model, generated from the [Swagger definition](https://app.swaggerhub.com/apis-docs/Henrik-3/HenrikDev-API).


## Getting started

### Prerequisites
What you need is :
- A HenrikDev API key
- Java 11 & Maven

### Installation
Import dependency in your `pom.xml` :

```xml
<properties>
  ...
  <!-- Use the latest version whenever possible. -->
  <henrikval.client.version>1.0.0-SNAPSHOT</henrikval.client.version>
  ...
</properties>

<dependencies>
  ...
  <dependency>
    <groupId>net.rr-world</groupId>
    <artifactId>henrikapi-val-client</artifactId>
    <version>${henrikval.client.version}</version>
  </dependency>
  ...
</dependencies>
```

### Usage
Just instanciate the client with API key :

```java
HenrikApiClient client = new HenrikApiClient("HDEV-XXX");
Optional<V1mmrh> res = client.getV1PlayerMMRHistory(Regions.EU.getValue(), "fe067f25-57a5-4f95-81f1-06d96b2290be");
```

If you are in a Spring context, you may wish to initialize a bean using your `RestTemplate` instance :

```java
@Bean
public HenrikApiClient getClient(@Autowired RestTemplate restClient, @Value("${henrikdev.api.key}") String apiKey) {
	return new HenrikApiClient(apiKey, restClient);
}
```

## Documentation
Javadoc is available [here](https://javadoc.io/doc/net.rr-world/henrikapi-val-client/latest/index.html)

## Changelog
### v1.0.0 (TBD)
- First release with just 3 end-points