package com.reflex.services;

import com.reflex.core.model.App;
import com.reflex.services.fileSystem.FileSystem;
import com.reflex.services.sms.SmsApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppProvider {

    private HashMap<String, App> repoHashMap;
    private static AppProvider actionProvider;
    public static String SMS = SmsApp.class.getSimpleName();
    public static String FILE_SYSTEM = FileSystem.class.getSimpleName();


    public static AppProvider getInstance() {
        if (actionProvider == null) {
            actionProvider = new AppProvider();
        }
        return actionProvider;
    }

    private AppProvider() {
        repoHashMap = new HashMap<>();
        repoHashMap.put(SMS, SmsApp.getInstance());
        repoHashMap.put(FILE_SYSTEM, FileSystem.getInstance());
    }

    public App getApp(String provider) {
        return repoHashMap.get(provider);
    }

    public List<App> getAllApps() {
        return new ArrayList<>(repoHashMap.values());
    }
}
