package org.corgiking.resthighclient;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class BulkRequestDemo {

	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		BulkRequest req = new BulkRequest();
		
		req.add(new IndexRequest("bulk-demo", "doc", "doc1").source(XContentType.JSON, "title", "doc-test"));

		client.close();
		
	}

}
