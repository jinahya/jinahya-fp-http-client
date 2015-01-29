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


import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public final class HeaderUtilities {


    public static List<String> getHeaderParameterValues(
        final Map<String, List<String>> headerFields,
        final String fieldName, final String parameterName) {

        final List<String> parameterValues = new ArrayList<String>();

        for (final Entry<String, List<String>> entry
             : headerFields.entrySet()) {
            final String key = entry.getKey();
            if (key == null && fieldName != null) {
                continue;
            }
            if (key != null && !key.equalsIgnoreCase(fieldName)) {
                continue;
            }
            for (final String fieldValue : entry.getValue()) {
                final int semicolonIndex = fieldValue.indexOf(';');
                if (semicolonIndex == -1) {
                    continue;
                }
                final StringTokenizer tokenizer = new StringTokenizer(
                    fieldValue.substring(semicolonIndex + 1), ";");
                while (tokenizer.hasMoreTokens()) {
                    final String token = tokenizer.nextToken();
                    final int equalIndex = token.indexOf('=');
                    if (equalIndex == -1) {
                        continue;
                    }
                    if (!token.substring(0, equalIndex)
                        .equalsIgnoreCase(parameterName)) {
                        continue;
                    }
                    parameterValues.add(token.substring(equalIndex + 1));
                }
            }
        }

        return parameterValues;
    }


    public static Long getRequestContentLength(
        final HttpURLConnection connection) {

        for (final Entry<String, List<String>> entry
             : connection.getRequestProperties().entrySet()) {
            if (!"content-length".equalsIgnoreCase(entry.getKey())) {
                continue;
            }
            try {
                return Long.parseLong(entry.getValue().get(0));
            } catch (final IndexOutOfBoundsException ioobe) {
            }
        }

        return null;
    }


    public static Long getResponseContentLength(
        final HttpURLConnection connection) {

        for (final Entry<String, List<String>> entry
             : connection.getHeaderFields().entrySet()) {
            if (!"content-length".equalsIgnoreCase(entry.getKey())) {
                continue;
            }
            try {
                return Long.parseLong(entry.getValue().get(0));
            } catch (final IndexOutOfBoundsException ioobe) {
            }
        }

        return null;
    }


    public static List<String> getRequestContentTypeCharsets(
        final HttpURLConnection connection) {

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        return getHeaderParameterValues(connection.getRequestProperties(),
                                        "Content-Type", "charset");
    }


    public static String getRequestContentTypeCharset(
        final HttpURLConnection connection) {

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        try {
            return getRequestContentTypeCharsets(connection).get(0);
        } catch (final IndexOutOfBoundsException ioobe) {
            return null;
        }
    }


    public static List<String> getResponseContentTypeCharsets(
        final HttpURLConnection connection) {

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        return getHeaderParameterValues(connection.getHeaderFields(),
                                        "Content-Type", "charset");
    }


    public static String getResponseContentTypeCharset(
        final HttpURLConnection connection) {

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        try {
            return getResponseContentTypeCharsets(connection).get(0);
        } catch (final IndexOutOfBoundsException ioobe) {
            return null;
        }
    }


    private HeaderUtilities() {

        super();
    }


}

