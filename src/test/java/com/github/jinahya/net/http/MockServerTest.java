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


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.io.IOException;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class MockServerTest {


    @BeforeMethod
    public void before() {

        for (int i = 0; i < 1024; i++) {
            portNumber = current().nextInt(1024, 65536);
            mockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig().port(portNumber));
            try {
                mockServer.start();
            } catch (final RuntimeException re) {
                re.printStackTrace(System.err);
                continue;
            }
            baseUri = "http://localhost:" + portNumber;
            logger.debug("baseUri: {}", baseUri);
            break;
        }
    }


    @AfterMethod
    public void after() throws IOException {

        mockServer.stop();
    }


    private transient final Logger logger = getLogger(lookup().lookupClass());


    private transient int portNumber;


    protected transient WireMockServer mockServer;


    protected transient String baseUri;


}

