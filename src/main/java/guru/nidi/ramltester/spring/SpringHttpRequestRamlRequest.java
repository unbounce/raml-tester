package guru.nidi.ramltester.spring;

import guru.nidi.ramltester.RamlRequest;
import guru.nidi.ramltester.util.UriComponents;
import org.springframework.http.HttpRequest;

import java.util.Map;

/**
 *
 */
public class SpringHttpRequestRamlRequest implements RamlRequest {
    private final HttpRequest request;
    private final byte[] body;
    private final UriComponents uriComponents;

    public SpringHttpRequestRamlRequest(HttpRequest request, byte[] body) {
        this.request = request;
        this.body = body;
        this.uriComponents = UriComponents.fromHttpUrl(request.getURI().toString());
    }

    @Override
    public String getRequestUrl() {
        return uriComponents.getWithoutQuery();
    }

    @Override
    public String getMethod() {
        return request.getMethod().name();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return uriComponents.getQueryParameters().getValues();
    }
}
