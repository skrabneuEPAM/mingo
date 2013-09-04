package com.mingo.exceptions;

/**
 * Copyright 2012-2013 The Mingo Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ConversionException extends RuntimeException {

    /**
     * Default constructor.
     */
    public ConversionException() {
    }

    /**
     * Constructor with parameters.
     *
     * @param message message
     */
    public ConversionException(String message) {
        super(message);
    }

    /**
     * Constructor with parameters.
     *
     * @param cause cause
     */
    public ConversionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with parameters.
     *
     * @param message message
     * @param cause   cause
     */
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

}
