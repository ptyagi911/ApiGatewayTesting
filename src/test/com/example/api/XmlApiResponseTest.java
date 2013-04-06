package test.com.example.api;

import com.example.api.XmlApiResponse;
import test.com.example.api.TestResponses;
import org.junit.Test;
import org.w3c.dom.Document;

import static com.example.api.Xmls.getTextContentOfChild;
import static com.example.util.Strings.asStream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class XmlApiResponseTest {
    @Test
    public void consumeResponse_shouldAssignAnXmlDocumentFromTheResponseBody() throws Exception {
        XmlApiResponse apiResponse = new XmlApiResponse(200);
        apiResponse.consumeResponse(asStream(TestResponses.AUTH_SUCCESS));
        Document responseDocument = apiResponse.getResponseDocument();
        assertThat(getTextContentOfChild(responseDocument, "guid"), equalTo("c93f12c"));
    }
}
