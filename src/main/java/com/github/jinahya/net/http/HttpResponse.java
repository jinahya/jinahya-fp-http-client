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


import com.github.jinahya.net.http.body.ReadableBody;
import static com.github.jinahya.util.Optional.ofNullable;
import java.io.IOException;
import java.net.HttpURLConnection;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class HttpResponse {


    protected HttpResponse(final HttpURLConnection connection) {

        super();

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        this.connection = connection;
    }


    /**
     * Returns the connection.
     *
     * @return the connection
     */
    public HttpURLConnection getConnection() {

        return connection;
    }


    /**
     * Returns the HTTP status code returned from the server.
     *
     * @return the HTTP status code
     *
     * @throws IOException if an I/O error occurs.
     *
     * @see HttpURLConnection#getResponseCode()
     */
    public int statusCode() throws IOException {

        return connection.getResponseCode();
    }


    /**
     * Returns the HTTP reason phrase returned from the server.
     *
     * @return the HTTP reason phrase
     *
     * @throws IOException if an I/O error occurs.
     *
     * @see HttpURLConnection#getResponseMessage()
     */
    public String reasonPhrase() throws IOException {

        return connection.getResponseMessage();
    }


    public ReadableBody<?> getBody() {

        return body;
    }


    public void setBody(final ReadableBody<?> body) {

        this.body = body;
    }


    public HttpResponse body(final ReadableBody<?> body) {

        setBody(body);

        return this;
    }


    public HttpResponse receive() throws IOException {

        try {
            if (body != null && statusCode() == HttpURLConnection.HTTP_OK) {
                body.read(connection);
            }
        } finally {
            connection.disconnect();
        }

        return this;
    }


    /**
     * Receives an body.
     *
     * @param <T> body type parameter
     * @param body the body; {@code null} for empty body
     *
     * @return the specified body.
     *
     * @throws IOException if an I/O error occurs.
     * @deprecated
     */
    @Deprecated
    public <T extends ReadableBody<?>> T receive(final T body)
        throws IOException {

        body(body).receive();

        return body;
    }


    private final HttpURLConnection connection;


    private transient ReadableBody<?> body;


}

