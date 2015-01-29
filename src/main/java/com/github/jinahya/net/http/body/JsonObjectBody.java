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


package com.github.jinahya.net.http.body;


import com.github.jinahya.net.http.HeaderUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @see <a href="http://tools.ietf.org/html/rfc7159">Request for Comment: 7159 -
 * The JavaScript Object Notation (JSON) Data Interchange Format</a>
 */
public class JsonObjectBody extends BidirectionalBody<JSONObject> {


    public JsonObjectBody(final JSONObject value) {

        super();

        setValue(value);
    }


    public JsonObjectBody() {

        this(null);
    }


    @Override
    public void read(final HttpURLConnection connection) throws IOException {

        String contentTypeCharset
            = HeaderUtilities.getResponseContentTypeCharset(connection);
        if (contentTypeCharset == null) {
            contentTypeCharset = "utf-8"; // rfc7159
        }

        final InputStream input = connection.getInputStream();
        try {
            setValue(new JSONObject(new JSONTokener(
                new InputStreamReader(input, contentTypeCharset))));
        } finally {
            input.close();
        }
    }


    @Override
    public void write(final HttpURLConnection connection) throws IOException {

        String contentTypeCharset
            = HeaderUtilities.getRequestContentTypeCharset(connection);
        if (contentTypeCharset == null) {
            contentTypeCharset = "utf-8"; // rf7159
        }
        System.out.println("request$content-type;charset: "
                           + contentTypeCharset);

        final OutputStream output = connection.getOutputStream();
        try {
            final Writer writer
                = new OutputStreamWriter(output, contentTypeCharset);
            try {
                getValue().write(writer).flush();
            } finally {
                writer.close();
            }
        } finally {
            output.close();
        }
    }


}

