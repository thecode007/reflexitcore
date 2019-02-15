package com.reflex.services.sms;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.services.providers.ActionRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;


public class SmsActions extends ActionRepository {

    private static final String TAG =
            SmsActions.class.getSimpleName();
    private static final String pdu_type = "pdus";

    protected static ActionRepository instance;

    public static ActionRepository getInstance() {
        if (instance == null) {
            instance = new SmsActions();
        }
        return instance;
    }

    private SmsActions() {
        map = new HashMap<>();

        map.put(READ_SMS_FROM_PROVIDER, args -> {
            Intent intent = (Intent) args[0];
            JSONObject callback = (JSONObject) args[1];
            readSMSFromIntent(intent, callback);
        });

        map.put(FILTER_SMS_FROM_PROVIDER, args -> {
            Intent intent = (Intent) args[0];
            ObjectNode filters = (ObjectNode) args[1];
            ObjectNode callback = (ObjectNode) args[2];
            filterSMSIntent(intent, filters, callback);
        });
    }


    void readSMSFromIntent(Intent intent, JSONObject resultCallBack) {

        // Get the SMS message.
        Log.wtf("Function SMS Read","executing");
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);

        try {
            JSONArray resultArray = new JSONArray();

            if (pdus != null) {
                Log.wtf(TAG, "onReceive: Recieved");
                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }

                    // Build the message to show.
                    strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                    strMessage += " :" + msgs[i].getMessageBody() + "\n";
                    // Log and display the SMS message.
                    Log.wtf(TAG, "onReceive: " + strMessage);
                    JSONObject messageJson = new JSONObject();

                    messageJson.put("number",msgs[i].getOriginatingAddress());
                    messageJson.put("message",msgs[i].getOriginatingAddress());
                    resultArray.put(messageJson);
                }
                resultCallBack.put("results",resultArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void filterSMSIntent(Intent intent, ObjectNode filterArgs, ObjectNode resultCallBack) {
        // Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        String phoneNumber = filterArgs.get("number").asText();
        String filterMessage = filterArgs.get("message").asText();
            if (pdus != null) {
                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    if (phoneNumber != null && !msgs[i].getOriginatingAddress().equalsIgnoreCase(phoneNumber)) {
                        return;
                    }
                    // Build the message to show.
                        strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                        strMessage += " :" + msgs[i].getMessageBody() + "\n";
                        // Log and display the SMS message.
                        Log.wtf(TAG, "onReceive: " + strMessage);
                }

                if (filterMessage != null && filterMessage.equals(strMessage)) {
                    resultCallBack.put("matched",true);
                    return;
                }
            }
        resultCallBack.put("matched",false);
    }


}