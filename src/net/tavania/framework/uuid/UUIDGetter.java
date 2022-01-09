package net.tavania.framework.uuid;

import com.github.f4b6a3.uuid.UuidCreator;

/**
 * Helper class for UUID-Generator-Framework by f4b6a3
 */
public class UUIDGetter {

    /**
     * Gets a single random UUID
     *
     * @return the uuid as string
     */
    public static String getRandomUUID() {
        return UuidCreator.getRandomBased().toString();
    }

    /**
     * Gets random UUIDs
     *
     * @param number is the number how many uuids should get returned
     * @return provides the uuids as strings
     */
    public static String[] getRandomUUIDs(int number) {
        String[] returnString = new String[number];
        for (int i = 0; i < number; i++) {
            returnString[i] = UuidCreator.getRandomBased().toString();
        }
        return returnString;
    }

}
