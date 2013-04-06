package test.com.example.net;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.apache.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.org.apache.http.HttpRequestInfo;

import com.example.api.ApiRequest;
import com.example.api.ApiResponse;
import com.example.net.NetApi;
import com.example.net.NetRequest;
import com.example.net.NetResponse;
import com.example.net.ResponseCallbacks;
import com.example.net.Task;
import com.example.net.Util;

@RunWith(RobolectricTestRunner.class)
public class NetApiTest {

	NetApi api;
	private ResponseCallbacks responseCallbacks;
	
	@Before
	public void setUp() {
		api = new NetApi();
		responseCallbacks = new TestResponseCallback();
	}
	
	@Test
	public void testAsyncTasks() {
		Robolectric.getBackgroundScheduler().pause();
		
		TestRequest request = new TestRequest();
		api.makeCall(request, responseCallbacks);
		
		//setting pending http response
		Robolectric.addPendingHttpResponse(200, "Test Response");

		//executing task
		Robolectric.getBackgroundScheduler().runOneTask();
		
		HttpRequestInfo sentHttpRequestData = Robolectric.getSentHttpRequestInfo(0);
		HttpRequest sentHttpRequest = sentHttpRequestData.getHttpRequest();
		
		//Testing URL
		assertThat(sentHttpRequest.getRequestLine().getUri(), 
				equalTo("www.disney.com"));
		
		//Testing Headers
		assertThat(sentHttpRequest.getHeaders("mickey")[0].getValue(),
				equalTo("mouse"));
	}
	
	@Test
	public void shouldCallOnFailureWhenOnSuccessFails() {
		NetResponse response = new NetResponse(200);
		
		TestResponseCallback callbacks = new TestResponseCallback() {
			public void onSuccess(NetResponse success) {
				throw new RuntimeException("Errr....!");
			}
		};
		
		Util.dispatch(response, callbacks);
		assertThat(callbacks.response, sameInstance((NetResponse) response));
	}
	
	private class TestRequest extends NetRequest {

		@Override
		public String getUrl() {
			return "www.disney.com";
		}

		public HashMap<String, String> getHeaders() {
			HashMap<String, String> headers = super.getHeaders();
			headers.put("mickey", "mouse");
			return headers;
		}
		
		@Override
		public NetResponse createResponse(int statusCode) {
			return new NetResponse(statusCode);
		}
	}
	
}
