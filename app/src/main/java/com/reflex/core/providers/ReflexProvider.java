package com.reflex.core.providers;

import java.util.HashMap;

public abstract class ReflexProvider {

    protected HashMap<String, Reflex> map;

    public static final String DELETE_IMPORTANT_FILE = "delete_important_files";
    public static final String DELETE_FILE_OR_Directory = "delete_file";
    public static final String READ_SMS_FROM_PROVIDER = "read_sms_from_intent";
    public static final String READ_JSON_STREAM = "read_json_asset";
    public static final String FILTER_SMS_FROM_PROVIDER = "filter_sms_from_intent";
    public static final String SEND_SMS_TEXT_MESSAGE = "SEND_SMS_TEXT";

    public Reflex getAction(String action) {
        return map.get(action);
    }


    public void registerAction(String action,Reflex reflex) {
        if (map != null) {
            map.put(action, reflex);
        }
    }
}