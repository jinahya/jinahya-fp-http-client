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


import com.github.jinahya.util.Objects;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class HttpClient {


    public HttpClient(final URL url) {

        super();

        this.url = Objects.requireNonNull(url, "null url");
    }


    public HttpClient connectTimeout(final int timeout) {

        this.connectTimeout = timeout;

        return this;
    }


    public HttpClient readTimeout(final int timeout) {

        this.readTimeout = timeout;

        return this;
    }


    /**
     * Opens the connection for specified HTTP request method.
     *
     * @param method the HTTP request method
     *
     * @return an HttpRequest
     *
     * @throws IOException if an IO error occurs.
     */
    public HttpRequest open(final String method) throws IOException {

        if (method == null) {
            throw new NullPointerException("null method");
        }

        final HttpURLConnection connection
            = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);

        if (connectTimeout != null) {
            try {
                HttpURLConnection.class.getMethod(
                    "setConnectTimeout", new Class<?>[]{int.class})
                    .invoke(connection, new Object[]{connectTimeout});
            } catch (final NoSuchMethodException nsme) {
            } catch (final IllegalAccessException iae) {
            } catch (final InvocationTargetException ite) {
            }
        }

        if (readTimeout != null) {
            try {
                HttpURLConnection.class.getMethod(
                    "setReadTimeout", new Class<?>[]{int.class})
                    .invoke(connection, new Object[]{readTimeout});
            } catch (final NoSuchMethodException nsme) {
            } catch (final IllegalAccessException iae) {
            } catch (final InvocationTargetException ite) {
            }
        }

        return new HttpRequest(connection);
    }


    public HttpRequest open(final HttpMethod method) throws IOException {

        if (method == null) {
            throw new NullPointerException("null method");
        }

        return open(method.name());
    }


    private final URL url;


    private transient Integer connectTimeout;


    private transient Integer readTimeout;


}

