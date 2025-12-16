package grup10.projecte_aea2_pgm.Classes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Requester {

    String method;
    String endpoint;
    String body;
    String[][] headers;
    String responseBody = null;



    public Requester(String method, String endpoint, String body, String[][] headers) {
        this.method = method;
        this.endpoint = endpoint;
        this.body = body;
        this.headers = headers;
    }

    public Requester(String method, String endpoint, String[][] headers) {
        this.method = method;
        this.endpoint = endpoint;
        this.headers = headers;
    }

    public Requester(String method, String endpoint) {
        this.method = method;
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getBody() {
        return body;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public String getResponseBody() {
        if (responseBody == null) makeRequest();
        return responseBody;
    }

    public void makeRequest() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder RequestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint));

            for (String[] header : headers) {
                RequestBuilder.header(header[0], header[1]);
            }

            switch(method.toUpperCase()) {
                case "POST":
                    RequestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
                    break;
                case "GET":
                    RequestBuilder = RequestBuilder.GET();
                    break;
                case "PUT":
                    RequestBuilder = RequestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body));
                    break;
                case "DELETE":
                    RequestBuilder = RequestBuilder.DELETE();
                    break;
                default:
                    System.err.println("Invalid HTTP method: " + method);
            }

            RequestBuilder
                    .build();

            HttpRequest request = RequestBuilder.build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            this.responseBody = response.body().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
