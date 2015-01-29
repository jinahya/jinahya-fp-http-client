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


import com.github.jinahya.net.http.UriBuilder;
import static java.lang.invoke.MethodHandles.lookup;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class UriBuilderTest {


    @Test
    public void local() throws URISyntaxException {

        final URI uri = new UriBuilder(new URI("http://localhost"))
            .path("a")
            .path("b/c")
            .queryParam("d", "e")
            .queryParam("f", new Object[]{"g", "h"})
            .build();
        logger.debug("url: {}", uri);
    }


    @Test
    public void slash() throws URISyntaxException {

        final URI uri = new UriBuilder(URI.create("http://localhost"))
            .path("/a")
            .path("b/c/")
            .queryParam("d", "e")
            .queryParam("d", (Object) null)
            .queryParam("f", new Object[]{"g", "h"})
            .build();
        logger.debug("url: {}", uri);
        assertTrue(uri.isAbsolute());
    }


    private transient final Logger logger = getLogger(lookup().lookupClass());


}

