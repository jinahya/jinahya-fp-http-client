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


import com.github.jinahya.net.http.body.ApplicationJsonBody;
import com.github.jinahya.net.http.body.ApplicationXmlBody;
import com.github.jinahya.net.http.body.ReadableBody;
import com.github.jinahya.net.http.body.WritableBody;
import static com.github.jinahya.util.Optional.ofNullable;
import java.io.IOException;
import java.net.URL;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class RestClient extends HttpClient {


    public RestClient(final URL url) {

        super(url);
    }


    @Override
    public RestClient connectTimeout(final int timeout) {

        return (RestClient) super.connectTimeout(timeout);
    }


    @Override
    public RestClient readTimeout(final int timeout) {

        return (RestClient) super.readTimeout(timeout);
    }


    public HttpResponse delete() throws IOException {

        return open(HttpMethod.DELETE)
            .body(null)
            .send()
            .body(null)
            .receive();
    }


    public <T, U extends ApplicationJsonBody<T> & WritableBody<T>> HttpResponse createJson(
        final String contentType, final U body)
        throws IOException {

        return open(HttpMethod.POST)
            .contentType(
                ofNullable(contentType)
                .orElse(ApplicationJsonBody.DEFAULT_CONENT_TYPE))
            .body(body)
            .send()
            .body(null)
            .receive();
    }


    public <T, U extends ApplicationJsonBody<T> & ReadableBody<T>> HttpResponse readJson(
        final String accept, final U body)
        throws IOException {

        return open(HttpMethod.GET)
            .accept(
                ofNullable(accept)
                .orElse(ApplicationJsonBody.DEFAULT_CONENT_TYPE))
            .body(null)
            .send()
            .body(body)
            .receive();
    }


    public <T, U extends ApplicationJsonBody<T> & WritableBody<T>> HttpResponse updateJson(
        final String contentType, final U body)
        throws IOException {

        if (body == null) {
            throw new NullPointerException("null body");
        }

        return open(HttpMethod.PUT)
            .contentType(
                ofNullable(contentType)
                .orElse(ApplicationJsonBody.DEFAULT_CONENT_TYPE))
            .body(body)
            .send()
            .body(null)
            .receive();
    }


    public <T, U extends ApplicationXmlBody<T> & ReadableBody<T>> HttpResponse readXml(
        final String accept, final U body)
        throws IOException {

        return open(HttpMethod.GET)
            .accept(
                ofNullable(accept)
                .orElse(ApplicationXmlBody.DEFAULT_CONENT_TYPE))
            .body(null)
            .send()
            .body(body)
            .receive();
    }


    public <T, U extends ApplicationXmlBody<T> & ReadableBody<T>> HttpResponse readXml(
        final U body)
        throws IOException {

        return readXml(null, body);
    }


}

