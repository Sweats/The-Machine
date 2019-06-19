package the_machine;

//TODO: Implement byte code for Kik.getGroupMembers(final Group group)

public class Command_Purge implements Command
{
    @Override
    public String execute(Message message)
    {
        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        Database_Admins database = Database_Admins.get();
        final User user = message.getUser();

        if (database.getAdminlevel(user) != Database_Admins.ROOT_ADMIN)
        {
            return "Sorry "  + user.getUsernameDisplay() + ", this command is only available to root admins.";
        }

        final User[] users = message.getGroup().getGroupMembers();

        for (int i = 0; i < users.length; i++)
        {
            Kik.kickUser(users[i], message.getGroup());
        }

        return "Successfully purged the group!";
    }
}
