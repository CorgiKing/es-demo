package org.corgiking.location.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.corgiking.location.demo.model.GeoPoint;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class LocationDemo {

	static String city = "浙江杭州";

	public static void main(String[] args) throws IOException {
		// createIndex();

		// saveData();

		search("杭州市拱墅区祥园路28号", 2);

	}

	public static void saveData() throws IOException {
		String[] names = { "小明", "小红", "小胖" };
		String[] addresses = { "杭州市拱墅区祥园路28号", "杭州市西湖区国风美域6号楼", "杭州市火车东站" };

		saveToEs(names, addresses);
	}

	/**
	 * 
	 * @param addr
	 * @param distance
	 *            距离(千米)
	 * @throws IOException
	 */
	public static void search(String addr, int distance) throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		GeoPoint point = GDGeoUtil.getGeoPoint(city, addr);

		SearchRequest req = new SearchRequest("location-demo");
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		geoDistanceQueryBuilder.point(point.getLatitude(), point.getLongitude()).distance(distance,
				DistanceUnit.KILOMETERS);
		ssb.query(geoDistanceQueryBuilder);
		req.source(ssb);

		SearchResponse resp = client.search(req);
		System.out.println(resp);

		System.out.println("以“" + addr + "”为中心，周围" + distance + "公里的用户有：");
		SearchHits hits = resp.getHits();
		for (SearchHit hit : hits) {
			System.out.println(hit.getSourceAsString());
		}

		client.close();
	}

	/**
	 * 保存到es
	 * 
	 * @param names
	 * @param addresses
	 * @throws IOException
	 */
	public static void saveToEs(String[] names, String[] addresses) throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		for (int i = 0; i < names.length; i++) {

			GeoPoint point = GDGeoUtil.getGeoPoint(city, addresses[i]);

			indexDoc(client, names[i], addresses[i], point);
		}

		client.close();
	}

	/**
	 * 索引文档
	 * 
	 * @param name
	 * @param point
	 * @throws IOException
	 */
	public static void indexDoc(RestHighLevelClient client, String name, String address, GeoPoint point)
			throws IOException {
		IndexRequest req = new IndexRequest("location-demo", "doc");

		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("name", name);
		jsonMap.put("address", address);
		String lat_lon = point.getLatitude() + "," + point.getLongitude();
		jsonMap.put("location", lat_lon);

		req.source(jsonMap);

		IndexResponse resp = client.index(req);
		System.out.println(resp);
	}

	/**
	 * 创建索引
	 * 
	 * @param client
	 * @throws IOException
	 */
	public static void createIndex() throws IOException {

		RestHighLevelClient clientLocal = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		CreateIndexRequest req = new CreateIndexRequest("location-demo");
		XContentBuilder builder = XContentFactory.jsonBuilder();

		builder.startObject();
		{
			builder.startObject("doc");
			{
				builder.startObject("properties");
				{
					builder.startObject("name");
					{
						builder.field("type", "text");
					}
					builder.endObject();
					builder.startObject("address");
					{
						builder.field("type", "text");
					}
					builder.endObject();
					builder.startObject("location");
					{
						builder.field("type", "geo_point");// 坐标点类型
					}
					builder.endObject();
				}
				builder.endObject();
			}
			builder.endObject();
		}
		builder.endObject();

		req.mapping("doc", builder);

		clientLocal.indices().create(req);
		clientLocal.close();
	}
}
