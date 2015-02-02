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


package com.github.jinahya.net.http;


import com.github.jinahya.net.http.body.ApplicationOctetStreamBody;
import com.github.jinahya.net.http.body.TextPlainBody;
import java.io.IOException;
import static java.lang.invoke.MethodHandles.lookup;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import static java.util.concurrent.ThreadLocalRandom.current;
import org.bigtesting.fixd.Method;
import org.bigtesting.fixd.ServerFixture;
import org.bigtesting.fixd.request.HttpRequest;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class HttpClientTest {


    private static final int PORT;


    private static final String BASE;


    static {
        PORT = current().nextInt(1014, 65536);
        BASE = "http://localhost:" + PORT;
    }


    @BeforeTest
    public void before() throws IOException {

        server = new ServerFixture(PORT);
        server.start();
    }


    @AfterTest
    public void after() throws IOException {

        server.stop();
    }


    @Test
    public void get() throws IOException {

        final String path = "/get";

        server.handle(Method.GET, path).with(200, "text/plain", "get");

        final HttpResponse response = new HttpClient(new URL(BASE + path))
            .open("GET")
            .send(null);

        final byte[] responseBodyValue
            = response.receive(new ApplicationOctetStreamBody()).getValue();
    }


    @Test
    public void getTextPlain() throws IOException {

        final String path = "/get";
        final Charset charset = StandardCharsets.UTF_8;

        server.handle(Method.GET, path).with((
            HttpRequest request,
            org.bigtesting.fixd.response.HttpResponse response) -> {
                response.setStatusCode(200);
                response.setContentType(
                    "text/plain;charset=" + charset.name().toLowerCase());
                response.setBody("가나다라".getBytes(charset));
            });

        final HttpResponse response = new HttpClient(new URL(BASE + path))
            .open("GET")
            .send(null);
        final String contentType
            = response.getConnection().getHeaderField("content-type");
        logger.debug("contentType: {}", contentType);

        final String body = response.receive(new TextPlainBody()).getValue();
        logger.debug("body: {} <<<<<<<<", body);
    }


    @Test
    public void put() throws IOException {

        final String path = "/put";

        server
            .handle(Method.PUT, path)
            .with(200, "[request#Content-Type]", "[request.body]");

        final HttpResponse response = new HttpClient(new URL(BASE + path))
            .open("PUT")
            .send(new ApplicationOctetStreamBody("hello".getBytes()));

        final byte[] responseBodyValue
            = response.receive(new ApplicationOctetStreamBody()).getValue();
    }


    @Test
    public void daum_get_index_html()
        throws URISyntaxException, MalformedURLException, IOException {

        final HttpResponse response
            = new HttpClient(new UriBuilder(BaseUris.get("daum"))
                .path("index.html")
                .build()
                .toURL())
            .open("GET")
            .send(null);
        logger.debug("code: {}", response.code());
        logger.debug("message: {}", response.message());
        final byte[] body = response.receive(
            new ApplicationOctetStreamBody()).getValue();
        if (body != null) {
            logger.debug("body.length: {}", body.length);
        }
    }


    private transient final Logger logger = getLogger(lookup().lookupClass());


    private ServerFixture server;


}

