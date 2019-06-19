package the_machine;

public class User
{
    private String m_username;
    private String m_usernameDisplay; // username with @ infront of it. Example: @sweats.
    private String m_jid;
    private boolean m_isMe;
    private int m_isBlacklisted;
    private int m_isBanned;
    private int m_isAdmin;

    /**
     * This constructor is called after parsing the ContentValues object that Kik creates for us.
     * It will parse the jid and make a valid username and display username.
     * It will also set isBanned and isAdmin to 0.
     * If this constructor is created after the machine has done something like banning or kicking someone, do not use it.
     *
     * @param jid The jid of the user from the ContentValues object.
     */
    public User(final String jid)
    {
        m_jid = jid;
        m_isBanned = 0;
        m_isAdmin = 0;
        m_isMe = jid.contains("the_machine") || jid.contains("@groups.kik.com");
        parse();
        m_isBlacklisted = Database_Chat.get().getBlacklisted(this);
    }

    /**
     * This constructor is used for reading information out of our databases.
     * It will also set isBanned and isAdmin to 0.
     *
     * @param username The username that we have found in the database.
     * @param jid The jid of the person we have found in the database.
     */
    public User(final String username, final String jid)
    {
        m_username = username;
        m_usernameDisplay = new StringBuilder(m_username).insert(0, "@").toString();
        m_jid = jid;
        m_isBanned = 0;
        m_isAdmin = 0;
        m_isMe = jid.contains("the_machine") || jid.contains("@groups.kik.com");
        m_isBlacklisted = Database_Chat.get().getBlacklisted(this);
    }

    /**
     * This constructor is used after reading information out of Kiks database (kikDatabase.db).
     *
     * @param jid The jid we have found in the messagesTable or memberTable.
     * @param isBanned The value of is_banned in the memberTable.
     * @param isAdmin The value of is_admin in the memberTable.
     */
    public User(final String jid, final int isBanned, final int isAdmin)
    {
        m_jid = jid;
        m_isBanned = isBanned;
        m_isAdmin = isAdmin;
        // TODO: Change the .contains("The_Machine") part. Someone can easily create an account with that text somewhere in username and completely be undetected by the machine.
        // We use the @groups.kik.com because if the jid comes from the user itself, it'll use the group jid instead.
        m_isMe = jid.contains("the_machine") || jid.contains("@groups.kik.com");
        parse();
        m_isBlacklisted = Database_Chat.get().getBlacklisted(this);

    }

    private void parse()
    {
        StringBuilder stringBuilder = new StringBuilder(m_jid);
        int Index = stringBuilder.indexOf("@") - 4;
        stringBuilder.delete(Index, stringBuilder.length());
        m_username = stringBuilder.toString();
        stringBuilder = new StringBuilder(m_username);
        stringBuilder.insert(0, "@");
        m_usernameDisplay = stringBuilder.toString();
    }

    /**
     * Do not use this inside handleSystemMessage()
     *
     * @return The username of the user. Example: sweats.525
     */
    public String getUsername()
    {
        return m_username;
    }


    /**
     * Do not use this inside handleSystemMessage()
     *
     * @return The display username of the user. Example: @sweats.525
     */
    public String getUsernameDisplay()
    {
        return m_usernameDisplay;
    }

    public boolean isMe()
    {
        return m_isMe;
    }

    public boolean isBanned()
    {
        return m_isBanned == 1;
    }

    public boolean isAdmin()
    {
        return m_isAdmin == 1;
    }

    public boolean isBlacklisted()
    {
        return m_isBlacklisted == 1;
    }

    /**
     *
     * @return the jid of the user or the group jid if this is used inside handleSystemMessage()
     */

    public String getJID()
    {
        return m_jid;
    }

    public void setUsername(final String username)
    {
        m_username = username;
        m_usernameDisplay = "@" + m_username;
    }
}
