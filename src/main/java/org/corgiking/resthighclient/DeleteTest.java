package org.corgiking.resthighclient;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class DeleteTest {

	public static void main(String[] args) throws IOException {

		RestClient restClient = RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http"))
				.build();
		RestHighLevelClient client = new RestHighLevelClient(restClient);

		
		
		restClient.close();
	}

}
