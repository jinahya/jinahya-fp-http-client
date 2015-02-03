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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.kxml2.kdom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @see <a href="http://tools.ietf.org/html/rfc7159">Request for Comment: 7159 -
 * The JavaScript Object Notation (JSON) Data Interchange Format</a>
 */
public class KXml2DocumentBody extends BidirectionalBody<Document> {


    public KXml2DocumentBody(final Document value) {

        super();

        setValue(value);
    }


    public KXml2DocumentBody() {

        this(null);
    }


    @Override
    public void read(final HttpURLConnection connection) throws IOException {

        if (contentTypeCharset == null) {
            contentTypeCharset
                = HeaderUtilities.getResponseContentTypeCharset(connection);
        }

        final Document value = new Document();

        final InputStream input = connection.getInputStream();
        try {
            final XmlPullParser parser;
            try {
                parser = XmlPullParserFactory.newInstance().newPullParser();
                parser.setInput(input, contentTypeCharset);
                value.parse(parser);
            } catch (final XmlPullParserException xmlppe) {
                xmlppe.printStackTrace(System.err);
                return;
            }
        } finally {
            input.close();
        }

        setValue(value);
    }


    @Override
    public void write(final HttpURLConnection connection) throws IOException {

        if (contentTypeCharset == null) {
            contentTypeCharset
                = HeaderUtilities.getRequestContentTypeCharset(connection);
        }

        final OutputStream output = connection.getOutputStream();
        try {
            final XmlSerializer serializer;
            try {
                serializer = XmlPullParserFactory.newInstance().newSerializer();
                serializer.setOutput(output, contentTypeCharset);
                getValue().write(serializer);
            } catch (final XmlPullParserException xmlppe) {
                xmlppe.printStackTrace(System.err);
            }
        } finally {
            output.close();
        }
    }


    @Override
    public KXml2DocumentBody value(final Document value) {

        return (KXml2DocumentBody) super.value(value);
    }


    public String getContentTypeCharset() {

        return contentTypeCharset;
    }


    public void setContentTypeCharset(final String contentTypeCharset) {

        this.contentTypeCharset = contentTypeCharset;
    }


    /**
     * Replaces {@code contentTypeCharset} with given and returns self.
     *
     * @param contentTypeCharset contentTypeCharset
     *
     * @return self.
     */
    public KXml2DocumentBody contentTypeCharset(
        final String contentTypeCharset) {

        setContentTypeCharset(contentTypeCharset);

        return this;
    }


    private String contentTypeCharset;


}

