package com.reflex.core.providers;

import com.reflex.core.model.Trigger;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public abstract class TriggerProvider implements Serializable {

    private HashMap<String, Trigger> map;

    public TriggerProvider() {
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

    public Collection<Trigger> getAll() {
        return map.values();
    }
}
