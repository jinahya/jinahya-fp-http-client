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


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @param <T> value type parameter
 *
 * @see <a href="http://tools.ietf.org/html/rfc7159">Request for Comment: 7159 -
 * The JavaScript Object Notation (JSON) Data Interchange Format</a>
 */
public interface ApplicationJsonBody<T> {


    /**
     * The default value for {@code Content-Type}.
     */
    public static final String DEFAULT_CONENT_TYPE = "application/json";


    /**
     * The default value for {@code charset} of {@code Content-Type}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7159">Request for Comment:
     * 7159 - The JavaScript Object Notation (JSON) Data Interchange Format</a>
     */
    public static final String DEFAULT_CONENT_TYPE_CHARSET = "utf-8";


}

