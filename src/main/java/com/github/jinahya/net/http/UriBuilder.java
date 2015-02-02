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


import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class UriBuilder {


    public UriBuilder(final URI baseUri) {

        super();

        if (baseUri == null) {
            throw new NullPointerException("null baseUri");
        }

        if (!baseUri.isAbsolute()) {
            throw new IllegalArgumentException("relative baseUri");
        }

        this.baseUri = baseUri;
        pathBuilder = new StringBuilder();
        queryBuilder = new QueryBuilder();
    }


    public UriBuilder path(final String path) {

        if (path == null) {
            throw new NullPointerException("null path");
        }

        if (path.isEmpty()) {
            throw new IllegalArgumentException("empty path");
        }

        if (path.endsWith("/")) {
            return path(path.substring(0, path.length() - 1));
        }

        if (!path.startsWith("/")) {
            pathBuilder.append("/");
        }

        pathBuilder.append(path);

        return this;
    }


    /**
     *
     * @param name
     * @param values
     *
     * @return self
     *
     * @see QueryStringBuilder#put(java.lang.String, java.lang.Object...)
     */
    public UriBuilder queryParam(final String name, final Object... values) {

        queryBuilder.put(name, values);

        return this;
    }


    public UriBuilder queryParam(final String name, final Object value) {

        return queryParam(name, new Object[]{value});
    }


    public URI build() throws URISyntaxException {

        return queryBuilder.build(
            baseUri.toASCIIString() + pathBuilder.toString());
    }


    /**
     * Prints build output to specified printer.
     *
     * @param out the output to print
     *
     * @return self.
     */
    public UriBuilder print(final PrintStream out) {

        final String queryString = queryBuilder.build();
        out.println(baseUri.toASCIIString() + pathBuilder.toString()
                    + (queryString.isEmpty() ? "" : "?" + queryString));

        return this;
    }


    private final URI baseUri;


    private final StringBuilder pathBuilder;


    private final QueryBuilder queryBuilder;


}

