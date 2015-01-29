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


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@SuppressWarnings("unchecked")
public final class AliasedBaseUris {


    private static final String NAME = "aliased-base-uris.properties";


    private static final Map<String, URI> MAP;


    static {
        final Map<String, URI> map = new HashMap<String, URI>();
        final URL resource = AliasedBaseUris.class.getResource(NAME);
        if (resource == null) {
            System.err.println(
                "resource not found: "
                + AliasedBaseUris.class.getPackage().getName().replace(".", "/")
                + "/" + NAME);
        }
        if (resource != null) {
            final Properties p = new Properties();
            try {
                final InputStream s = resource.openStream();
                try {
                    p.load(s);
                } finally {
                    s.close();
                }
            } catch (final IOException ioe) {
                ioe.printStackTrace(System.err);
            }
            for (final Enumeration<?> aliases = p.propertyNames();
                 aliases.hasMoreElements();) {
                final String alias
                    = String.valueOf(aliases.nextElement()).trim();
                String value = p.getProperty(String.valueOf(alias)).trim();
                if (value.endsWith("/")) {
                    value = value.substring(0, value.length() - 1);
                }
                final URI uri;
                try {
                    uri = URI.create(value);
                } catch (final IllegalArgumentException iae) {
                    System.err.println("failed to create URI from " + value);
                    continue;
                }
                if (!uri.isAbsolute()) {
                    System.err.println("uri is not absolute: " + value);
                    continue;
                }
                if (map.put(alias, uri) != null) {
                    System.err.println("duplicate alias: " + alias);
                }
            }
        }
        MAP = Collections.unmodifiableMap(map);
    }


    public static URI get(final String alias) {

        if (alias == null) {
            throw new NullPointerException("null alias");
        }

        final URI uri = MAP.get(alias);

        if (uri == null) {
            throw new IllegalArgumentException(
                "no base uri for alias(" + alias + ")");
        }

        return uri;
    }


    /**
     * Returns the first iterated base URI. <b>Use this method with caution. An
     * unexpected result will be returned if two or more properties
     * configured</b>.
     *
     * @return the first iterated URI.
     */
    public static URI get() {

        final URI uri = MAP.values().iterator().next();

        return uri;
    }


    /**
     *
     * @param alias
     *
     * @return a new instance
     *
     * @see #get(java.lang.String)
     */
    public static UriBuilder newInstance(final String alias) {

        return new UriBuilder(get(alias));
    }


    /**
     *
     * @return a new instance.
     *
     * @see #get()
     */
    public static UriBuilder newInstance() {

        return new UriBuilder(get());
    }


    private AliasedBaseUris() {

        super();
    }


}

