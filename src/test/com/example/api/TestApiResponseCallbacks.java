package test.com.example.api;

import com.example.api.ApiResponse;
import com.example.api.ApiResponseCallbacks;
import com.example.api.XmlApiResponse;

public class TestApiResponseCallbacks implements ApiResponseCallbacks<XmlApiResponse> {

    public boolean onCompleteWasCalled;
    public XmlApiResponse successResponse;
    public ApiResponse failureResponse;

    @Override
    public void onSuccess(XmlApiResponse successResponse) {
        this.successResponse = successResponse;
    }

    @Override
    public void onFailure(ApiResponse failureResponse) {
        this.failureResponse = failureResponse;
    }

    @Override
    public void onComplete() {
        onCompleteWasCalled = true;
    }
}
