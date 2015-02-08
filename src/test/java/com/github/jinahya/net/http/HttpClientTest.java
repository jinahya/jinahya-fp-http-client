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
import java.io.IOException;
import static java.lang.invoke.MethodHandles.lookup;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class HttpClientTest extends MockServerTest {


    @Test
    public void get_daum_net_index_html()
        throws URISyntaxException, MalformedURLException, IOException {

        final HttpResponse response
            = new HttpClient(new UriBuilder(BaseUris.get("daum"))
                .path("index.html")
                .build()
                .toURL())
            .open("GET")
            .send(null);
        logger.debug("code: {}", response.statusCode());
        logger.debug("message: {}", response.reasonPhrase());
        final byte[] body = response.receive(
            new ApplicationOctetStreamBody()).getValue();
        if (body != null) {
            logger.debug("body.length: {}", body.length);
        }
    }


    private transient final Logger logger = getLogger(lookup().lookupClass());


}

