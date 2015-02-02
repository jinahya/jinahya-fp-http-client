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


import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class QueryBuilder {


    public QueryBuilder() {

        super();

        this.params = new HashMap<String, Object[]>();
    }


    public QueryBuilder put(final String name, final Object... values) {

        if (name == null) {
            throw new NullPointerException("null name");
        }

        if (values == null) {
            throw new NullPointerException("null values");
        }

        for (final Object value : values) {
            if (value == null) {
                params.remove(name);
                return this;
            }
        }

        params.put(name, values);

        return this;
    }


    public QueryBuilder remove(final String name) {

        if (name == null) {
            throw new NullPointerException("null name");
        }

        return put(name, new Object[]{null});
    }


    public String build() {

        final StringBuilder builder = new StringBuilder();

        for (final Iterator<Map.Entry<String, Object[]>> entries
            = params.entrySet().iterator();
             entries.hasNext();) {
            final Map.Entry<String, Object[]> entry = entries.next();
            for (final Object value : entry.getValue()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }
                builder
                    .append(entry.getKey())
                    .append("=")
                    .append(String.valueOf(value));
            }
        }

        return builder.toString();
    }


    public URI build(final String uri) throws URISyntaxException {

        if (uri == null) {
            throw new NullPointerException("null uri");
        }

        final String built = build();

        return new URI(uri + (built.isEmpty() ? "" : "?" + built));
    }


    public URI build(final URI uri) throws URISyntaxException {

        if (uri == null) {
            throw new NullPointerException("null uri");
        }

        return build(uri.toASCIIString());
    }


    private final Map<String, Object[]> params;


}

