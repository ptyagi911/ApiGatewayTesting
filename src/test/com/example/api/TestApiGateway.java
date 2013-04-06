package test.com.example.api;

import com.example.api.ApiGateway;
import com.example.api.ApiRequest;
import com.example.api.ApiResponseCallbacks;
import com.example.api.XmlApiResponse;
import com.example.util.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.Strings.asStream;

public class TestApiGateway extends ApiGateway {

    List<Pair<ApiRequest, ApiResponseCallbacks>> pendingRequests = new ArrayList<Pair<ApiRequest, ApiResponseCallbacks>>();

    @Override
    public void makeRequest(ApiRequest apiRequest, ApiResponseCallbacks responseCallbacks) {
        pendingRequests.add(Pair.of(apiRequest, responseCallbacks));
    }

    public void simulateResponse(int httpCode, String responseBody) throws IOException, SAXException, ParserConfigurationException {
        ensurePendingRequests();
        XmlApiResponse apiResponse = new XmlApiResponse(httpCode);
        apiResponse.consumeResponse(asStream(responseBody));
        dispatch(apiResponse, unshiftEarliestRequest().b);
    }

    public ApiRequest getLatestRequest() {
        ensurePendingRequests();
        return pendingRequests.get(pendingRequests.size() - 1).a;
    }

    private void ensurePendingRequests() {
        if (pendingRequests.isEmpty()) {
            throw new RuntimeException("No pending requests to simulate response for");
        }
    }

    private Pair<ApiRequest, ApiResponseCallbacks> unshiftEarliestRequest() {
        return pendingRequests.remove(0);
    }
}
