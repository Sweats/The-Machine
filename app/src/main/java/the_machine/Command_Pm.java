package the_machine;

//TODO: Test.

public class Command_Pm implements Command
{
    @Override
    public String execute(Message message)
    {
        if (!message.isPrivateMessage())
        {
            return null;
        }

        Database_Admins adminDatabase = Database_Admins.get();
        final User user = message.getUser();

        if (!adminDatabase.isUserInDatabase(user))
        {
            return null;
        }

        if (adminDatabase.getAdminlevel(user) < Database_Admins.HIGH_ADMIN)
        {
            return null;
        }

        final String[] args = message.getMessageText().split(" ", 3);

        if (args.length != 3)
        {
            return "Usage: !pm <group> <message>";
        }

        Database_Support supportDatabase = Database_Support.get();
        final String groupName = args[1];
        final String messageString = args[2];

        final Group group = supportDatabase.getGroup(groupName);

        if (group == null)
        {
            final Group[] supportedGroups = supportDatabase.getSupportedGroups();
            final String groupsString = buildGroupNamesString(supportedGroups);
            return Utils.createString("?\n\nInvalid group tag. Please use one of the ones above.", new String[]{groupsString});
        }

        Kik.messageGroup(messageString, group);
        return null;
    }

    private String buildGroupNamesString(final Group[] groups)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++)
        {
            stringBuilder.append(groups[i].getGroupTag() + "\n\n");
        }

        return stringBuilder.toString();
    }

}
