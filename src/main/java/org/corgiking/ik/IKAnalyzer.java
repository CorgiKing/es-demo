package org.corgiking.ik;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

public class IKAnalyzer {

	public static void main(String[] args) throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		// 创建索引
		CreateIndexRequest req = new CreateIndexRequest("ik_java");
		req.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));

		XContentBuilder mappings = JsonXContent.contentBuilder()
				.startObject("properties")
					.startObject("title")
						.field("type", "text")
						.field("analyzer", "ik_max_word")
					.endObject()
				.endObject();

		req.mapping("ik_type", mappings);

		client.indices().create(req);

		client.close();
	}

}
