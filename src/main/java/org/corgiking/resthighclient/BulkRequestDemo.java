package org.corgiking.resthighclient;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class BulkRequestDemo {

	public static void main(String[] args) throws IOException, InterruptedException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		BulkProcessor.Listener bulkListener = new BulkProcessor.Listener() {
			
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				System.out.println("before request!");
			}
			
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("request failure!");
			}
			
			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				System.out.println("after request!");
			}
		};
		
		BulkProcessor bulkProcessor = BulkProcessor.builder(client::bulkAsync, bulkListener).build();
		
		bulkProcessor.add(new IndexRequest("bulk-demo", "doc", "do-test").source(XContentType.JSON, "title", "doc-test"));
		bulkProcessor.add(new UpdateRequest("bulk-demo", "doc", "do-test").doc(XContentType.JSON, "title", "doc-title"));
		bulkProcessor.add(new DeleteRequest("bulk-demo", "doc", "do-test"));

		bulkProcessor.close();
		
		client.close();

	}

	public static void bulkRequest(RestHighLevelClient client) throws IOException {
		BulkRequest req = new BulkRequest();

		req.add(new IndexRequest("bulk-demo", "doc", "do-test").source(XContentType.JSON, "title", "doc-test"));
		req.add(new UpdateRequest("bulk-demo", "doc", "do-test").doc(XContentType.JSON, "title", "doc-title"));
		req.add(new DeleteRequest("bulk-demo", "doc", "do-test"));

		BulkResponse resps = client.bulk(req);

		System.out.println(resps.hasFailures());

		for (BulkItemResponse resp : resps) {
			DocWriteResponse itemResponse = resp.getResponse();

			if (resp.getOpType() == DocWriteRequest.OpType.INDEX || resp.getOpType() == DocWriteRequest.OpType.CREATE) {
				IndexResponse indexResponse = (IndexResponse) itemResponse;
				System.out.println("Index:" + indexResponse);

			} else if (resp.getOpType() == DocWriteRequest.OpType.UPDATE) {
				UpdateResponse updateResponse = (UpdateResponse) itemResponse;
				System.out.println("Update:" + updateResponse);

			} else if (resp.getOpType() == DocWriteRequest.OpType.DELETE) {
				DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
				System.out.println("Delete:" + deleteResponse);
			}
		}
	}

}
