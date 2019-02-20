package com.reflex.services.sms;
import com.reflex.core.providers.App;
import com.reflex.core.providers.TriggerProvider;

public class SmsApp extends App {

    public static SmsApp instance;

    public static SmsApp getInstance() {
        if (instance == null) {
            instance = new SmsApp();
        }
        return instance;
    }


    private SmsApp() {
        super(new TriggerProvider() {
        }, SmsReflexes.getInstance());
        triggerProvider.register(new SmsReceivedTrigger(this));
    }


}
