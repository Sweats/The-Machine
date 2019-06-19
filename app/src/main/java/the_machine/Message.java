package the_machine;

import android.content.ContentValues;

public class Message
{

    private User m_user;
    private Group m_group;
    private String m_message;
    private String m_originalMessage;
    private Long m_timestamp;

    private boolean m_isImage;
    private boolean m_isPrivateMessage;
    private boolean m_isCommand;
    private boolean m_isStatMessage;
    private boolean m_isSystemMessage;

    private static final String PARTNER_JID_KEY = "partner_jid";
    private static final String BIN_ID_KEY = "bin_id";
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String BODY_KEY = "body";
    private static final String STAT_MSG_KEY = "stat_msg";
    private static final String SYS_MSG_KEY = "sys_msg";
    private static final String STAT_JID_KEY = "stat_user_jid";

    /**
     * This constructor is called after every message. A message can be just a regular message or system like messages like someone being removed, banned, etc.
     *
     * @param contentValues The ContentValues object that Kik creates to easily update its databases
     */

    public Message(final ContentValues contentValues)
    {
        String body = contentValues.getAsString(BODY_KEY);
        String sysMsg = contentValues.getAsString(SYS_MSG_KEY);
        String statMsg = contentValues.getAsString(STAT_MSG_KEY);
        String bin_id = contentValues.getAsString(BIN_ID_KEY);
        String partner_jid = contentValues.getAsString(PARTNER_JID_KEY);
        String stat_jid = contentValues.getAsString(STAT_JID_KEY);
        m_timestamp = contentValues.getAsLong(TIMESTAMP_KEY);

        if (body != null)
        {
            m_isImage = false;
            m_isStatMessage = false;
            m_isSystemMessage = false;
            m_message = body.toLowerCase();
            m_originalMessage = body;
            m_isCommand = m_message.startsWith("!");
            m_isPrivateMessage = bin_id.contains("@talk.kik.com") && partner_jid.contains("@talk.kik.com");
            m_group = new Group(bin_id);
            m_user = new User(partner_jid);
        }

        if (sysMsg != null)
        {
            m_isSystemMessage = true;
            m_message = sysMsg.toLowerCase();
            m_originalMessage = sysMsg;
            m_isStatMessage = false;
            m_isImage = false;
            m_isCommand = false;
            m_group = new Group(bin_id);
            m_user = new User(partner_jid); // Not really a user here.
            parseSystemMessage();
        }

        if (statMsg != null)
        {
            m_message = statMsg.toLowerCase();
            m_originalMessage = statMsg;
            m_isSystemMessage = false;
            m_isStatMessage = true;
            m_isPrivateMessage = false;
            m_isImage = false;
            m_isCommand = false;
            m_user = new User(stat_jid);
            m_group = new Group(bin_id);
            parseStatMessage();

        }
    }

    /**
     * Do not use this inside handleSystemMessage()
     *
     * @return The user that has kicked, banned, etc, or the user that sent the message..
     */
    public User getUser()
    {
        return m_user;
    }

    /**
     * Do not use this if the message is a private message.
     * @return The group where the message took place.
     */

    public Group getGroup()
    {
        return m_group;
    }

    /**
     *
     * @return If the message is a system message or stat message, this will simply return it.
     *         If the message is a regular message, this will return a lower cased version of the original message.
     */

    public String getMessageText()
    {
        return m_message;
    }

    /**
     *
     * @return The original, non lower cased version of the message we received.
     */
    public String getMessageTextOriginal()
    {
        return m_originalMessage;
    }


    /**
     * This should not be used because modded Kiks can easily change the timestamp on their message.
     *
     * @return The timestamp inside the ContentValues object.
     */
    public Long getTimestamp()
    {
        return m_timestamp;
    }

    public boolean isCommand()
    {
        return m_isCommand;
    }

    /**
     * @return True if the message was not sent in a group. False otherwise.
     */
    public boolean isPrivateMessage()
    {
        return m_isPrivateMessage;
    }

    public boolean isImage()
    {
        return m_isImage;
    }

    /**
     * A system message is a message when The Machine itself has done one of the following:
     *
     * 1. Banning someone.
     * 2. Kicking someone.
     * 3. Leaving the group.
     * 4. Unbanning someone.
     *
     * @return True if it's a system message. False otherwise.
     */
    public boolean isSystemMessage()
    {
        return m_isSystemMessage;
    }

    /**
     * A stat message is a message when someone else has done one of the following:
     *
     * 1. Banning someone.
     * 2. Kicking someone.
     * 3. Leaving the group.
     * 4. Unbanning someone.
     *
     * @return True if it's a stat message. False otherwise.
     */

    public boolean isStatMessage()
    {
        return m_isStatMessage;
    }

    /**
     * A regular message is a message when someone or The Machine sends a plain text message.
     *
     * @return true if it's a regular text message, false otherwise.
     */
    public boolean isRegularMessage()
    {
        return !m_isStatMessage && !m_isSystemMessage;
    }

    // Parses the system message if there is one. Used for the CallbackList hashmap.
    private void parseStatMessage()
    {
        final String[] statMessages = new String[]{MachineStrings.CALLBACK_HAS_BANNED, MachineStrings.CALLBACK_HAS_REMOVED,
                MachineStrings.CALLBACK_HAS_UNBANNED, MachineStrings.CALLBACK_JOINED_CHAT};

        for (int i = 0; i < statMessages.length; i++)
        {
            if (m_message.contains(statMessages[i]))
            {
                StringBuilder stringBuilder = new StringBuilder(m_message);
                stringBuilder.delete(0, stringBuilder.indexOf(statMessages[i]));
                m_message = stringBuilder.toString();
                m_message = m_message.substring(0, statMessages[i].length());
                break;
            }
        }
    }

    private void parseSystemMessage()
    {
        final String[] systemMessages = new String[]{MachineStrings.CALLBACK_YOU_HAVE_ADDED, MachineStrings.CALLBACK_ADDED_YOU};

        for (int i = 0; i < systemMessages.length; i++)
        {
            if (m_message.contains(systemMessages[i]))
            {
                StringBuilder stringBuilder = new StringBuilder(m_message);
                stringBuilder.delete(0, stringBuilder.indexOf(systemMessages[i]));
                m_message = stringBuilder.toString();
                m_message = m_message.substring(0, systemMessages[i].length());
                break;
            }
        }
    }
}
