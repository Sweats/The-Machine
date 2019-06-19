package the_machine;

public class Command_Announce implements Command
{

    @Override
    public String execute(Message message)
    {
        if (message.isPrivateMessage() || !message.getGroup().getJID().equals(MachineStrings.machineTeamJID))
        {
            return "This command is only supported in the The Machine Team group.";
        }

        final String[] args = message.getMessageText().split(" ", 2);
        final String[] args2 = message.getMessageTextOriginal().split(" ", 2);

        if (args.length != 2)
        {
            return "Usage: !announce <message>";
        }

        final String messageToForward = args2[1];
        final User user = message.getUser();
        final Database_Admins adminDatabase = Database_Admins.get();

        if (!adminDatabase.isUserInDatabase(user))
        {
            return Utils.createString("Sorry ?, This command is only available to admins.", new String[]{user.getUsernameDisplay()});
        }

        if (Database_Admins.get().getAdminlevel(message.getUser()) < Database_Admins.HIGH_ADMIN)
        {
            return "This command is only available to ROOT and HIGH admins.";
        }

        final Group[] groups = Database_Support.get().getSupportedGroups();

        try
        {

            for (int i = 0; i < groups.length; i++)
            {
                Kik.messageGroup(messageToForward, groups[i]);
                Thread.sleep(500);
            }
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return Utils.createString("Forwarded message to ? groups", new String[]{Integer.toString(groups.length)});
    }
}
