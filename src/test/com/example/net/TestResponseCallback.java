package test.com.example.net;

import java.io.IOException;

import com.example.net.NetResponse;
import com.example.net.ResponseCallbacks;

public class TestResponseCallback implements ResponseCallbacks<NetResponse>{

	public NetResponse response;
	
	@Override
	public void onSuccess(NetResponse response) throws IOException {
		this.response = response;
	}

	@Override
	public void onFailure(NetResponse response) {
		this.response = response;
	}

}
