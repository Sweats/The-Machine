package the_machine;

public class Command_GroupName implements Command
{
    @Override
    public String execute(final Message message)
    {
        if (message.isPrivateMessage())
        {
            return null;
        }

        final String[] args = message.getMessageTextOriginal().split(" ", 2);
        final int argc = args.length;

        if (argc != 2)
        {
            return "Usage: !groupname <name>";
        }

        Database_Admins adminDatabase = Database_Admins.get();
        final User user = message.getUser();

        if (!adminDatabase.isUserInDatabase(user))
        {
            return Utils.createString("Sorry ?, this command is only available to admins.", new String[]{user.getUsernameDisplay()});
        }

        Kik.changeGroupName(args[1], message.getGroup());

        return "Successfully changed the group name.";
    }
}
