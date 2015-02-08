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
import static com.github.jinahya.util.Optional.ofNullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class TextPlainBody extends BidirectionalBody<String> {


    public TextPlainBody(final String value) {

        super();

        setValue(value);
    }


    @Override
    public void read(final HttpURLConnection connection) throws IOException {

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        contentTypeCharset = ofNullable(contentTypeCharset)
            .orElse(HeaderUtilities.getResponseContentTypeCharset(connection)
                .orElse("iso-8859-1"));

        final InputStream input = connection.getInputStream();
        try {
            final ByteArrayOutputStream baos;
            final Long contentLength
                = HeaderUtilities.getResponseContentLength(connection);
            if (contentLength != null) {
                baos = new ByteArrayOutputStream(contentLength.intValue());
            } else {
                baos = new ByteArrayOutputStream();
            }
            final byte[] buffer = new byte[1024];
            for (int read = -1; (read = input.read(buffer)) != -1;
                 baos.write(buffer, 0, read)) {
            }
            baos.flush();
            setValue(new String(baos.toByteArray(), contentTypeCharset));
        } finally {
            input.close();
        }
    }


    //protected abstract void read(final InputStream input) throws IOException;
    @Override
    public void write(final HttpURLConnection connection) throws IOException {

        if (connection == null) {
            throw new NullPointerException("null connection");
        }

        contentTypeCharset = ofNullable(contentTypeCharset)
            .orElse(HeaderUtilities.getRequestContentTypeCharset(connection)
                .orElse("iso-8859-1"));

        final OutputStream output = connection.getOutputStream();
        try {
            output.write(getValue().getBytes(contentTypeCharset));
            output.flush();
        } finally {
            output.close();
        }
    }


    @Override
    public TextPlainBody value(final String value) {

        return (TextPlainBody) super.value(value);
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
    public TextPlainBody contentTypeCharset(final String contentTypeCharset) {

        setContentTypeCharset(contentTypeCharset);

        return this;
    }


    private String contentTypeCharset;


}

