package the_machine;

public class Command_SupportAdd implements Command
{
    private String m_reply = "Failed to add bot support for this group.";

    @Override
    public String execute(final Message message)
    {
        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        final String[] args = message.getMessageText().split(" ");

        if (args.length != 2)
        {
            return "Usage: !support_add <groupTagName>";
        }

        final User user = message.getUser();
        final Group group = message.getGroup();
        final String groupTag = args[1];
        Database_Admins adminDatabase = Database_Admins.get();

        if (adminDatabase.getAdminlevel(user) != Database_Admins.ROOT_ADMIN)
        {
            return Utils.createString("Sorry ?, this command is only available to root admins", new String[]{message.getUser().getUsernameDisplay()});
        }

        Database_Support databaseSupport = Database_Support.get();

        if (databaseSupport.isSupported(group))
        {
            return "This group is already supported by me.";
        }

        if (databaseSupport.addSupport(group, groupTag))
        {
            final String parseString =  "Successfully added bot support for this group.\n\n" +
                                        "Group JID: ?\n\n" +
                                        "Group Tag: ?\n\n" +
                                        "Date added: ?\n\n" +
                                        "Please call !refresh to update this groups database otherwise I won't work properly.";

            m_reply = Utils.createString(parseString, new String[]{group.getJID(), groupTag, Utils.createDate(System.currentTimeMillis())});
        }

        return m_reply;
    }
}
