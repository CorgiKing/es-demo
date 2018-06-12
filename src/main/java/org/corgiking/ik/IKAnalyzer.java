package org.corgiking.ik;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class IKAnalyzer {

	public static void main(String[] args) throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		// 创建索引
		createIndex(client);
		
		//添加文档
		indexDoc(client, "好好学习，天天向上");
		indexDoc(client, "学和习，有什么区别");
		indexDoc(client, "es的分词该怎么学的");
		
		//查询文档
		searchDoc(client, "title", "学习");
		searchDoc(client, "title.title_ik_smart", "学习");
		searchDoc(client, "title.title_ik_max_word", "学习");
		
		//删除索引
		deleteIndex(client);

		client.close();
	}

	public static void deleteIndex(RestHighLevelClient client) throws IOException {
		DeleteIndexRequest req = new DeleteIndexRequest("ik_java");
		client.indices().delete(req);
	}

	public static void searchDoc(RestHighLevelClient client, String field, String value) throws IOException {
		SearchRequest req = new SearchRequest("ik_java");
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		ssb.query(QueryBuilders.matchQuery(field, value));
		req.source(ssb);
		SearchResponse resp = client.search(req);
		System.out.println(resp);
	}

	public static void indexDoc(RestHighLevelClient client, String title) throws IOException {
		IndexRequest req = new IndexRequest("ik_java", "doc");
		XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject()
				.field("title", title)
			.endObject();
		req.source(contentBuilder);
		client.index(req);
	}

	public static void createIndex(RestHighLevelClient client) throws IOException {
		CreateIndexRequest req = new CreateIndexRequest("ik_java");
		req.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));
		
		XContentBuilder mappings = JsonXContent.contentBuilder().startObject()
				.startObject("properties")
					.startObject("title")
						.field("type", "text")
						.startObject("fields")
							.startObject("title_ik_smart")
								.field("type", "text")
								.field("analyzer", "ik_smart")
							.endObject()
							.startObject("title_ik_max_word")
								.field("type", "text")
								.field("analyzer", "ik_max_word")
							.endObject()
						.endObject()
					.endObject()
				.endObject().endObject();

		req.mapping("doc", mappings);
		client.indices().create(req);
	}

}
