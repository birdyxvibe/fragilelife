package com.birdy.fragileLife.managers;

import com.birdy.fragileLife.schemas.Profile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class ProfileManager {

    private final File file;
    private final FileConfiguration config;
    private final Map<UUID, Profile> cache = new HashMap<>();

    public ProfileManager(File dataFolder) {
        this.file = new File(dataFolder, "users.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                getLogger().severe("Could not create users.yml!");
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        loadAllProfiles();
    }

    public Profile getProfile(UUID uuid) {
        if (cache.containsKey(uuid)) return cache.get(uuid);

        ConfigurationSection section = config.getConfigurationSection("users." + uuid.toString());
        Profile profile = new Profile(uuid);

        if (section != null) {
            loadFromSection(profile, section);
        }
        cache.put(uuid, profile);
        return profile;
    }

    public void saveProfile(Profile profile) {
        ConfigurationSection section = config.getConfigurationSection("users." + profile.getUuid().toString());
        if (section == null) {
            section = config.createSection("users." + profile.getUuid().toString());
        }

        saveToSection(profile, section);

        try {
            config.save(file);
        } catch (IOException e) {
            getLogger().severe("Failed to save users.yml!");
        }
    }

    public void saveAll() {
        for (Profile profile : cache.values()) {
            saveProfile(profile);
        }
    }

    private void loadAllProfiles() {
        ConfigurationSection usersSection = config.getConfigurationSection("users");
        if (usersSection == null) return;

        for (String uuidStr : usersSection.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(uuidStr);
                getProfile(uuid); // loads into cache
            } catch (IllegalArgumentException e) {
                getLogger().warning("Invalid UUID in users.yml: " + uuidStr);
            }
        }
    }

    private void loadFromSection(Profile profile, ConfigurationSection section) {
        Field[] fields = Profile.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            String key = field.getName();
            if (section.contains(key)) {
                try {
                    Object value = section.get(key);

                    // Special case UUID field (skip because it's constructor-set)
                    if (field.getType() == UUID.class) continue;

                    // Handle primitives and String only for simplicity
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        field.set(profile, section.getInt(key));
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        field.set(profile, section.getDouble(key));
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        field.set(profile, section.getBoolean(key));
                    } else if (field.getType() == String.class) {
                        field.set(profile, (String) value);
                    } else if (key.equals("missionProgress")) {
                        ConfigurationSection mapSection = section.getConfigurationSection(key);
                        if (mapSection != null) {
                            Map<String, Integer> map = new HashMap<>();
                            for (String mapKey : mapSection.getKeys(false)) {
                                map.put(mapKey, mapSection.getInt(mapKey));
                            }
                            field.set(profile, map);
                        }
                    } else if (key.equals("missionCooldowns")) {
                        ConfigurationSection mapSection = section.getConfigurationSection(key);
                        if (mapSection != null) {
                            Map<String, String> map = new HashMap<>();
                            for (String mapKey : mapSection.getKeys(false)) {
                                map.put(mapKey, mapSection.getString(mapKey));
                            }
                            field.set(profile, map);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveToSection(Profile profile, ConfigurationSection section) {
        Field[] fields = Profile.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();

            try {
                Object value = field.get(profile);

                if (value == null) return;

                // Skip UUID - it’s the key
                if (field.getType() == UUID.class) continue;

                if (key.equals("missionProgress") && value instanceof Map) {
                    ConfigurationSection mapSection = section.createSection(key);
                    Map<?, ?> map = (Map<?, ?>) value;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        mapSection.set(entry.getKey().toString(), ((Number) entry.getValue()).intValue());
                    }
                } else if (key.equals("missionCooldowns") && value instanceof Map) {
                    ConfigurationSection mapSection = section.createSection(key);
                    Map<?, ?> map = (Map<?, ?>) value;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        mapSection.set(entry.getKey().toString(), entry.getValue().toString());
                    }
                } else {
                    section.set(key, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
