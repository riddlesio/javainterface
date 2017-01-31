package io.riddles.javainterface.configuration;

import java.util.ArrayList;

/**
 * CheckedConfiguration is used to create and check the desired settings
 * and check if all needed values are present for your app.
 *
 * Created by joost on 1/3/17.
 */
public class CheckedConfiguration extends Configuration {
    ArrayList<String> requiredKeys = new ArrayList<>();

    public CheckedConfiguration() {
        this.requiredKeys = new ArrayList<String>();
    }

    public CheckedConfiguration(String[] keys) {
        this.requiredKeys = new ArrayList<String>();
        for (String key : keys) {
            this.requiredKeys.add(key);
        }
    }

    public void addRequiredKey(String key) {
        this.requiredKeys.add(key);
    }


    public boolean isComplete() {
        for (String requiredKey : requiredKeys) {
            ConfigurationItem item = this.values.get(requiredKey);
            if (item == null) {
                return false;
            }
        }
        return true;
    }
}
