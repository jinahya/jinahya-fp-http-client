/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.net.http.body;


import com.github.jinahya.net.http.HttpClient;
import com.github.jinahya.net.http.HttpResponse;
import com.github.jinahya.net.http.MockServerTest;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import java.io.IOException;
import static java.lang.invoke.MethodHandles.lookup;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class JsonObjectBodyTest extends MockServerTest {


    @Test(enabled = false)
    public void read() throws MalformedURLException, IOException {

        final String expected = "{\"a\":\"b\"}";

        final String path = "/get";
        //final Charset charset = StandardCharsets.ISO_8859_1;
        //final Charset charset = StandardCharsets.US_ASCII;
        //final Charset charset = StandardCharsets.UTF_16;
        //final Charset charset = StandardCharsets.UTF_16BE;
        //final Charset charset = StandardCharsets.UTF_16LE;
        final Charset charset = StandardCharsets.UTF_8;

        final String contentType = "application/json";

        mockServer.stubFor(
            get(urlEqualTo(path))
            .willReturn(
                aResponse()
                .withHeader("Content-Type", contentType)
                .withBody(expected)
            )
        );

        final HttpResponse response = new HttpClient(new URL(baseUri + path))
            .open("GET")
            .send(null);

        final JSONObject actual
            = response.receive(new JsonObjectBody(null)).getValue();
        logger.debug("actual: {}", actual);

        assertEquals(actual.toString(), expected);
    }


    @Test(enabled = true)
    public void update() throws MalformedURLException, IOException {

        final JSONObject expected = new JSONObject().put("a", "b");

        final String path = "/put";
        final URL url = new URL(baseUri + path);

        //final Charset charset = StandardCharsets.ISO_8859_1;
        //final Charset charset = StandardCharsets.US_ASCII;
        //final Charset charset = StandardCharsets.UTF_16;
        //final Charset charset = StandardCharsets.UTF_16BE;
        //final Charset charset = StandardCharsets.UTF_16LE;
        final Charset charset = StandardCharsets.UTF_8;

        final String contentType = "application/json";

        mockServer.stubFor(
            put(urlEqualTo(path))
            .withHeader("content-type", equalTo(contentType))
            .willReturn(
                aResponse()
                .withStatus(204)
            )
        );
        mockServer.addMockServiceRequestListener(
            (final Request request, final Response response) -> {
                logger.debug("request.bodyAsString: {}",
                             request.getBodyAsString());
            });

        final HttpResponse response = new HttpClient(url)
            .open("PUT")
            .contentType(contentType)
            .send(new JsonObjectBody(expected));

        assertEquals(response.statusCode(), 204);

        final List<LoggedRequest> loggedRequests
            = mockServer.findAll(putRequestedFor(urlMatching(path)));
        assertEquals(loggedRequests.size(), 1);
        assertEquals(loggedRequests.get(0).getBodyAsString(), expected.toString());
        loggedRequests.forEach((loggedRequest) -> {
            logger.debug("loggedRequest: {}", loggedRequest);
            logger.debug("loggedRequest.bodyAsString: {}",
                         loggedRequest.getBodyAsString());
        });
    }


    private transient final Logger logger = getLogger(lookup().lookupClass());


}

