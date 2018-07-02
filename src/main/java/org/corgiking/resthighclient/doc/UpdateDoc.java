package org.corgiking.resthighclient.doc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
/**
 * 
 * req.doc 方法更新文档
 * 		若更新内容与原文档相同，则不更新
 * 		若文档不存在，则抛出异常
 * 
 * req.upsert 方法
 * 		若文档不存在，则插入文档
 * 
 * @author corgiking
 *
 */
public class UpdateDoc {

	public static void main(String[] args) throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		UpdateRequest req = new UpdateRequest("people", "doc", "dev1");

		req.doc("age", 18);
		
		String[] includes = Strings.EMPTY_ARRAY;
		String[] excludes = new String[]{"info"};
		req.fetchSource(new FetchSourceContext(true, includes, excludes));

		UpdateResponse resp = client.update(req);
		
		GetResult res = resp.getGetResult();
		System.out.println(res.sourceAsString());
		System.out.println(res.sourceAsMap().get("name"));
		System.out.println(resp);

		client.close();
	}

	public static void sourceParams(RestHighLevelClient client) throws IOException {
		UpdateRequest req = new UpdateRequest("people", "doc", "test1");

		req.doc("age", 18);

		UpdateResponse resp = client.update(req);
		System.out.println(resp);
	}

	public static void JsonXContentBuilder(RestHighLevelClient client) throws IOException {
		UpdateRequest req = new UpdateRequest("people", "doc", "test1");

		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		builder.field("age", 25);
		builder.endObject();

		req.doc(builder);

		UpdateResponse resp = client.update(req);
		System.out.println(resp);
	}

	public static void jsonString(RestHighLevelClient client) throws IOException {
		UpdateRequest req = new UpdateRequest("people", "doc", "test1");

		req.doc("{\"age\":18}", XContentType.JSON);

		UpdateResponse resp = client.update(req);
		System.out.println(resp);
	}

	public static void jsonMap(RestHighLevelClient client) throws IOException {
		UpdateRequest req = new UpdateRequest("people", "doc", "test1");

		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("age", "18");

		req.doc(jsonMap);

		UpdateResponse resp = client.update(req);
		System.out.println(resp);
	}

}
