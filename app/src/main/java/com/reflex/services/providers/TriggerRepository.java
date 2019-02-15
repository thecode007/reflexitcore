package com.reflex.services.providers;

import com.reflex.services.Trigger;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class TriggerRepository {

    private HashMap<String, Trigger> map;

    public TriggerRepository() {
        map = new HashMap<>();
    }

   public void register(Trigger trigger) {
        map.put(trigger.getTriggerString(), trigger);
   }

    public void unRegister(String triggerString) {
        if (map.get(triggerString) != null) {
            map.remove(triggerString);
        }
    }

    public ArrayList<Trigger> getAll() {
        return new ArrayList<>(map.values());
    }
}