package org.corgiking.resthighclient.doc;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.corgiking.resthighclient.People;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.google.gson.Gson;

public class IndexDoc {

	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		indexSourceParams(client);
		indexContentBuilder(client);
		indexJsonMap(client);
		indexJson(client);

		client.close();
	}

	public static void indexSourceParams(RestHighLevelClient client) throws IOException {
		IndexRequest indexRequest = new IndexRequest("people", "doc", "dba1").source("name", "zhjp", "age", 25, "sex",
				"男", "info", "DBA");
		IndexResponse indexResponse = client.index(indexRequest);
		System.out.println(indexResponse);
	}

	public static void indexContentBuilder(RestHighLevelClient client) throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		builder.field("name", "cyh");
		builder.field("age", 25);
		builder.field("sex", "女");
		builder.field("info", "测试");
		builder.endObject();
		IndexRequest indexRequest = new IndexRequest("people", "doc", "test1").source(builder);
		IndexResponse indexResponse = client.index(indexRequest);
		System.out.println(indexResponse);
	}

	public static void indexJsonMap(RestHighLevelClient client) throws IOException {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("name", "shiyj");
		jsonMap.put("age", 25);
		jsonMap.put("sex", "男");
		jsonMap.put("info", "程序猿");
		IndexRequest indexRequest = new IndexRequest("people", "doc", "dev1").source(jsonMap);
		IndexResponse indexResponse = client.index(indexRequest);
		System.out.println(indexResponse);
	}

	public static void indexJson(RestHighLevelClient client) throws IOException {
		People people = new People("yy", 25, "男", "程序员");
		IndexRequest indexRequest = new IndexRequest("people", "doc", "dev2");
		String info = new Gson().toJson(people);
		indexRequest.source(info, XContentType.JSON);
		IndexResponse indexResponse = client.index(indexRequest);
		System.out.println(indexResponse);
	}

	/**
	 * 
	 */
	public static void getIndexInfo(RestHighLevelClient client) throws IOException {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("video");
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest);
		System.out.println(searchResponse);
	}

	/**
	 * 
	 * 查看基本信息 GET /?pretty=true
	 * 
	 */
	public static void baseInfo(RestClient restClient) throws IOException {
		Response response = restClient.performRequest("GET", "/", Collections.singletonMap("pretty", "true"));
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

}
