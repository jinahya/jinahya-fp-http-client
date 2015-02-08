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
import java.net.URLConnection;


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


    /**
     * Returns the connection.
     *
     * @return the connection.
     */
    public HttpURLConnection getConnection() {

        return connection;
    }


    /**
     * Sets a HTTP request header.
     *
     * @param key the header field name
     * @param value the header field value.
     *
     * @return self
     *
     * @see URLConnection#setRequestProperty(java.lang.String, java.lang.String)
     */
    public HttpRequest header(final String key, final String value) {

        connection.setRequestProperty(key, value);

        return this;
    }


    /**
     * Sets {@code Content-Type} header.
     *
     * @param value the header field value.
     *
     * @return self.
     *
     * @see #header(java.lang.String, java.lang.String)
     */
    public HttpRequest contentType(final String value) {

        return header("Content-Type", value);
    }


    /**
     * Sets {@code Accept} header.
     *
     * @param value the header field value.
     *
     * @return self.
     *
     * @see #header(java.lang.String, java.lang.String)
     */
    public HttpRequest accept(final String value) {

        return header("Accept", value);
    }


    /**
     * Sets {@code Accept} header value with {@code application/xml}.
     *
     * @return self
     *
     * @see #accept(java.lang.String)
     */
    public HttpRequest acceptXml() {

        return accept("application/xml");
    }


    /**
     * Sets {@code Accept} header value with {@code application/xml}.
     *
     * @return self
     *
     * @see #accept(java.lang.String)
     */
    public HttpRequest acceptJson() {

        return accept("application/json");
    }


    /**
     * Sets {@code Accept} header value with {@code image/png}.
     *
     * @return self
     *
     * @see #accept(java.lang.String)
     */
    public HttpRequest acceptPng() {

        return accept("image/png");
    }


    /**
     * Sets {@code Accept} header value with {@code image/jpeg}.
     *
     * @return self
     *
     * @see #accept(java.lang.String)
     */
    public HttpRequest acceptJpeg() {

        return accept("image/jpeg");
    }


    public WritableBody<?> getBody() {

        return body;
    }


    public void setBody(final WritableBody<?> body) {

        this.body = body;
    }


    public HttpRequest body(final WritableBody<?> body) {

        setBody(body);

        return this;
    }


    public <T extends WritableBody<?>> HttpResponse send()
        throws IOException {

        if (body != null) {
            connection.setDoOutput(true);
            body.write(connection); // implicitly connect
        } else {
            connection.connect(); // explicitly connect without sending
        }

        return new HttpResponse(connection);
    }


    /**
     * Sends the request.
     *
     * @param <T> body type parameter
     * @param body the body; {@code null} for empty request body.
     *
     * @return an HTTPResponse instance.
     *
     * @throws IOException if an I/O error occurs.
     * @deprecated
     */
    @Deprecated
    public <T extends WritableBody<?>> HttpResponse send(final T body)
        throws IOException {

        return body(body).send();
    }


    private final HttpURLConnection connection;


    private transient WritableBody<?> body;


}

