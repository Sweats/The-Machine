package the_machine;

public class Command_SupportRemove implements Command
{
    private String m_response = "Failed to remove support for this group.";

    @Override
    public String execute(Message message)
    {
        final User user = message.getUser();
        final Group group = message.getGroup();

        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{user.getUsernameDisplay()});
        }

        Database_Admins adminDatabase = Database_Admins.get();

        if (adminDatabase.getAdminlevel(user) != Database_Admins.ROOT_ADMIN)
        {
            return "Sorry, this command is only available to root admins";
        }

        Database_Support databaseSupport = Database_Support.get();

        if (!databaseSupport.isSupported(group))
        {
            return "This group is not already supported by me";
        }

        if (databaseSupport.removeSupport(group))
        {
            Database.deleteDatabase(group.getDatabaseID());
            m_response = "Successfully removed support for this group.";
        }

        return m_response;
    }
}
