package org.corgiking.resthighclient.doc;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class DeleteDoc {

	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		deleteReq(client);
		
		client.close();
	}

	public static void deleteReq(RestHighLevelClient client) throws IOException {
		DeleteRequest req = new DeleteRequest("people", "doc", "1");
		DeleteResponse resp = client.delete(req);
		
		System.out.println(resp);
	}

}
