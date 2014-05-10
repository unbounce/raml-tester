package guru.nidi.ramltester.spring;

import guru.nidi.ramltester.RamlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.*;

/**
 *
 */
public class SpringClientHttpResponseRamlResponse implements ClientHttpResponse, RamlResponse {
    private final ClientHttpResponse response;
    private final String encoding;
    private byte[] body;

    public SpringClientHttpResponseRamlResponse(ClientHttpResponse response, String encoding) {
        this.response = response;
        this.encoding = encoding;
    }

    public SpringClientHttpResponseRamlResponse(ClientHttpResponse response) {
        this(response, "utf-8");
    }

    @Override
    public int getStatus() {
        try {
            return getRawStatusCode();
        } catch (IOException e) {
            throw new RuntimeException("Problem getting status", e);
        }
    }

    @Override
    public String getContentType() {
        final MediaType contentType = getHeaders().getContentType();
        return contentType == null ? null : contentType.toString();
    }

    @Override
    public String getContentAsString() {
        try {
            char[] buf = new char[getBody().available()];
            final InputStreamReader reader = new InputStreamReader(getBody(), encoding);
            final int read = reader.read(buf);
            return new String(buf, 0, read);
        } catch (IOException e) {
            throw new RuntimeException("Problem getting content", e);
        }
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return response.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public InputStream getBody() throws IOException {
        if (body == null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            copy(response.getBody(), out);
            body= out.toByteArray();
        }
        return new ByteArrayInputStream(body);
    }

    @Override
    public HttpHeaders getHeaders() {
        return response.getHeaders();
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1000];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
    }
}
