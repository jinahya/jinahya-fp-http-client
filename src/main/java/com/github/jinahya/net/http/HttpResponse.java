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


    public HttpURLConnection getConnection() {

        return connection;
    }


    /**
     *
     * @return @throws IOException
     *
     * @see HttpURLConnection#getResponseCode()
     */
    public int code() throws IOException {

        return connection.getResponseCode();
    }


    /**
     *
     * @return @throws IOException
     *
     * @see HttpURLConnection#getResponseMessage()
     */
    public String message() throws IOException {

        return connection.getResponseMessage();
    }


    public <T extends ReadableBody<?>> T receive(final T body)
        throws IOException {

        try {
            if (code() == HttpURLConnection.HTTP_OK) {
                body.read(connection);
            }
        } finally {
            connection.disconnect();
        }

        return body;
    }


    private final HttpURLConnection connection;


}

