package org.corgiking.resthighclient;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

public class GetDoc {

	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient
				.builder(new HttpHost("192.168.16.21", 9200, "http"), new HttpHost("192.168.16.22", 9200, "http")));

		getAsync(client);
		
		client.close();
	}

	public static void getAsync(RestHighLevelClient client) {
		GetRequest getRequest = new GetRequest("people", "doc", "dev1");
		
		//异步执行
		client.getAsync(getRequest, new ActionListener<GetResponse>() {
			
			@Override
			public void onResponse(GetResponse response) {
				System.out.println("Yes! Get success");
				System.out.println(response);
			}
			
			@Override
			public void onFailure(Exception e) {
				System.out.println("Sorry! Get failure.");
			}
		});
	}
	
	public static void get(RestHighLevelClient client) throws IOException {
		GetRequest getRequest = new GetRequest("people", "doc", "dev1");
		
		//取出的字段
		String[] includes = new String[]{"name", "info"};
		//去除的字段
		String[] excludes = Strings.EMPTY_ARRAY;
		FetchSourceContext sourceContext = new FetchSourceContext(true, includes, excludes);
		
		getRequest.fetchSourceContext(sourceContext);
		
		
		GetResponse response = client.get(getRequest);
		
		System.out.println(getRequest);
		System.out.println(response);
	}

}
