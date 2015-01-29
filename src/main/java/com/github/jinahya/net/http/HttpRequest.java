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


import com.github.jinahya.net.http.body.WritableBody;
import java.io.IOException;
import java.net.HttpURLConnection;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class HttpRequest {


    protected HttpRequest(final HttpURLConnection connection) {

        super();

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        this.connection = connection;
    }


    public HttpURLConnection getConnection() {

        return connection;
    }


    public HttpRequest header(final String key, final String value) {

        connection.setRequestProperty(key, value);

        return this;
    }


    public HttpRequest contentType(final String value) {

        return header("Content-Type", value);
    }


    public HttpRequest accept(final String value) {

        return header("Accept", value);
    }


    public HttpRequest acceptXml() {

        return accept("application/xml");
    }


    public HttpRequest acceptJson() {

        return accept("application/json");
    }


    public HttpRequest acceptPng() {

        return accept("image/png");
    }


    public <T extends WritableBody<?>> HttpResponse send(final T body)
        throws IOException {

        if (body != null) {
            connection.setDoOutput(true);
            body.write(connection);
        } else {
            connection.connect();
        }

        return new HttpResponse(connection);
    }


    private final HttpURLConnection connection;


}

