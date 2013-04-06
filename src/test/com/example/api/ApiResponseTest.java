package test.com.example.api;

import org.junit.Test;

import com.example.api.ApiResponse;

import java.io.InputStream;

import static test.com.example.api.TestResponses.GENERIC_XML;
import static com.example.util.Strings.asStream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ApiResponseTest {
    @Test
    public void isSuccess_shouldReturnTrueIfResponseCodeIsInThe200Range() throws Exception {
        assertThat(new TestApiResponse(200, asStream(GENERIC_XML)).isSuccess(), equalTo(true));
        assertThat(new TestApiResponse(201, asStream(GENERIC_XML)).isSuccess(), equalTo(true));
        assertThat(new TestApiResponse(299, asStream(GENERIC_XML)).isSuccess(), equalTo(true));
    }

    @Test
    public void isSuccess_shouldReturnFalseIfResponseCodeIsIn500Range() throws Exception {
        assertThat(new TestApiResponse(500, asStream(GENERIC_XML)).isSuccess(), equalTo(false));
        assertThat(new TestApiResponse(501, asStream(GENERIC_XML)).isSuccess(), equalTo(false));
    }

    @Test
    public void isUnauthorized_shouldReturnTrueIfResponseCodeIs401() throws Exception {
        assertThat(new TestApiResponse(401, asStream("Access Denied")).isUnauthorized(), equalTo(true));
    }

    private class TestApiResponse extends ApiResponse {
        public TestApiResponse(int responseCode, InputStream responseBody) {
            super(responseCode);
        }

        @Override
        public void consumeResponse(InputStream responseBody) throws Exception {
        }
    }
}
