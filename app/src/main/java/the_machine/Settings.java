package the_machine;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings
{
    public static final String LOCKDOWN_KEY = "lockdown_enabled";
    public static final String QUIET_MODE_KEY = "quiet_mode_enabled";
    public static final String AUTO_KICK_KEY = "auto_kick_enabled";
    private static final String MACHINE_SETTINGS = "The_Machine_Settings_";

    private SharedPreferences m_preferences;

    public Settings(final Group group)
    {
        m_preferences = Kik.getKikApplication().getSharedPreferences(MACHINE_SETTINGS + group.getDatabaseID(), Context.MODE_PRIVATE);

        if (!m_preferences.contains(LOCKDOWN_KEY))
        {
            createSettings();
        }
    }

    private void createSettings()
    {
        SharedPreferences.Editor editor = m_preferences.edit();

        final String[] keys = new String[]{LOCKDOWN_KEY, QUIET_MODE_KEY, AUTO_KICK_KEY};

        for (int i = 0; i < keys.length; i++)
        {
            editor.putBoolean(keys[i], false);
        }

        editor.apply();
    }

    public boolean getSetting(final String key)
    {
        return m_preferences.getBoolean(key, false);
    }

    public void modifySetting(final String key, final boolean newBoolean)
    {
        SharedPreferences.Editor editor = m_preferences.edit();
        editor.putBoolean(key, newBoolean);
        editor.apply();
    }
}
