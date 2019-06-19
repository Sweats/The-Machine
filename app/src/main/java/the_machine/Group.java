package the_machine;

import java.util.ArrayList;

public class Group
{
    private String m_jid;
    private String m_groupName; // TODO. See if we can somehow reverse a function from kik to grab the groupname for us automatically
    private String m_groupTag; // TODO. Same as above. This is the group hashtag.
    private String m_databaseID;
    private boolean m_validGroup;
    private Settings m_Settings;

    private User[] m_groupMembers;
    private int m_bannedMembersCount;
    private int m_regularMembersCount;
    private int m_adminMembersCount;
    private int m_totalMembersCount;

    /**
     * This constructor is only used after reading from Kiks ContentValues object.
     * If the message is a private message, do not use the constructed group object. Use the constructed User object instead.
     *
     * @param jid The jid of the group where the message took place.
     */

    public Group(final String jid)
    {
        this(jid, jid);
    }

    /**
     * This constructor is used after reading from our databases.
     *
     * @param groupTag The group hash tag.
     * @param jid The jid of the group.
     */

    public Group(final String groupTag, final String jid)
    {
        m_groupTag = groupTag;
        m_jid = jid;
        m_validGroup = Database_Support.get().isSupported(this);

        if (m_validGroup)
        {
            m_databaseID = parseJID();
            m_groupMembers = Database_Kik.getUsersInGroup(this);
            m_Settings = new Settings(this);
        }
    }

    /**
     * If the message is a private message, use the User object instead.
     *
     * @return The jid of the group.
     */
    public String getJID()
    {
        return m_jid;
    }

    /**
     * Don't use this. Haven't found a reliable way to get the group name yet.
     *
     * @return The group name.
     */

    public String getGroupName()
    {
        return m_groupName;
    }

    /**
     * If the message is a private message, don't use this.
     *
     * @return The group hash tag.
     */

    public String getGroupTag()
    {
        return m_groupTag;
    }

    /**
     *
     * @return True if the group where the message took place is supported by The Machine. False otherwise.
     */

    public boolean isValidGroup()
    {
        return m_validGroup;
    }

    public static Group createFromJID(final String jid)
    {
        return new Group(jid);
    }

    /**
     * The return value is used to keep track of chat databases.
     *
     * @return The parsed numbers in a group jid if the group is supported. Null otherwise.
     */

    public String getDatabaseID()
    {
        return m_databaseID;
    }

    /**
     *
     * @return The settings object for the group where the message took place if the group is supported by The Machine. Null otherwise.
     */

    public Settings getGroupSettings()
    {
        return m_Settings;
    }

    private String parseJID()
    {
        StringBuilder stringBuilder = new StringBuilder(m_jid);
        stringBuilder.delete(stringBuilder.indexOf("@") - 2, m_jid.length());
        return stringBuilder.toString();
    }

    /**
     *
     * @return An array of all users in a group including banned users if the group is supported by The Machine. Null otherwise.
     */

    public User[] getGroupMembers()
    {
        return m_groupMembers;
    }

    public User[] getBannedMembers()
    {
        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < m_groupMembers.length; i++)
        {
            if (m_groupMembers[i].isMe() || !m_groupMembers[i].isBanned())
            {
                continue;
            }

            users.add(m_groupMembers[i]);
        }

        return users.toArray(new User[0]);
    }


    /**
     *
     * @return An array of all admins in a group if the group is supported by The Machine. Null otherwise.
     */
    public User[] getAdmins()
    {
        if (m_groupMembers == null)
        {
            return null;
        }

        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < m_groupMembers.length; i++)
        {
            if (m_groupMembers[i].isMe() || !m_groupMembers[i].isAdmin())
            {
                continue;
            }

            users.add(m_groupMembers[i]);
        }

        return users.toArray(new User[0]);
    }

    /**
     * Used only once in getOldestBannedUser(Group) in Database_Bans.java
     *
     * @return a string array of banned user names in a group to use as selection args in a sqlite query if the group is supported by The Machine. Null otherwise.
     */

    public String[] getBannedUsernames()
    {

        if (m_groupMembers == null)
        {
            return null;
        }

        ArrayList<String> usernames = new ArrayList<>();

        for (int i = 0; i < m_groupMembers.length; i++)
        {
            if (m_groupMembers[i].isMe() || !m_groupMembers[i].isBanned())
            {
                continue;
            }

            usernames.add(m_groupMembers[i].getUsername());
        }

        return usernames.toArray(new String[0]);
    }

    public int getTotalAdminsCount()
    {
        return m_adminMembersCount;
    }

    public int getTotalMembersCount()
    {
        return m_totalMembersCount;
    }

    public int getTotalRegularMembersCount()
    {
        return m_regularMembersCount;
    }

    public int getBannedMembersCount()
    {
        return m_bannedMembersCount;
    }

    private void fillVariables()
    {
        m_totalMembersCount = m_groupMembers.length;

        for (int i = 0; i < m_totalMembersCount; i++)
        {
            if (m_groupMembers[i].isMe())
            {
                continue;
            }

            if (m_groupMembers[i].isAdmin())
            {
                m_adminMembersCount++;
                m_regularMembersCount++;
            }

            else if (m_groupMembers[i].isBanned())
            {
                m_regularMembersCount++;
                m_bannedMembersCount++;
            }

            else
            {
                m_regularMembersCount++;
            }
        }
    }
}
