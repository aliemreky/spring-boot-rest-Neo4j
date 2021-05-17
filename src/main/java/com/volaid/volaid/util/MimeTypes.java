package com.volaid.volaid.util;

import java.util.HashMap;

public class MimeTypes {

    public static final String MIME_IMAGE_JPEG = "image/jpeg";
    public static final String MIME_IMAGE_PNG = "image/png";

    private static HashMap<String, String> mimeTypeMapping;

    static {
        mimeTypeMapping = new HashMap<String, String>(200) {
            private void put1(String key, String value) {
                if (put(key, value) != null) {
                    throw new IllegalArgumentException("Duplicated extension: " + key);
                }
            }

            {
                put1("jpeg", MIME_IMAGE_JPEG);
                put1("jpg", MIME_IMAGE_JPEG);
                put1("jpe", MIME_IMAGE_JPEG);
                put1("png", MIME_IMAGE_PNG);
            }
        };
    }

    /**
     * Simply returns MIME type or <code>null</code> if no type is found.
     */
    public static Boolean lookupMimeType(String ext) {
        return (mimeTypeMapping.get(ext.toLowerCase()) != null);
    }


}
