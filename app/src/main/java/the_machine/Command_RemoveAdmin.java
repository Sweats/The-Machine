package the_machine;

/**
 * This command removes an admin from the admin database.
 */

public class Command_RemoveAdmin implements Command
{
    @Override
    public String execute(final Message message)
    {
        final String[] args = message.getMessageText().split(" ");

        if (args.length != 2)
        {
            return "Usage: !remove_admin <username>";
        }

        final User user = message.getUser();
        Database_Admins adminDatabase = Database_Admins.get();

        if (!adminDatabase.isUserInDatabase(user))
        {
            return Utils.createString(MachineStrings.SORRY_ADMIN_ONLY, new String[]{user.getUsernameDisplay()});
        }

        if (adminDatabase.getAdminlevel(user) != Database_Admins.ROOT_ADMIN)
        {
            return Utils.createString("Sorry ?, This command is only available to root admins.", new String[]{user.getUsernameDisplay()});
        }

        final String username = args[1];
        final User removedAdminUser = adminDatabase.getUser(username);

        if (removedAdminUser == null)
        {
            final User[] possibleUsers = adminDatabase.getPossibleUsers(username);
            return Utils.getPossibleUsernames(username, possibleUsers);
        }

        if (!adminDatabase.isUserInDatabase(removedAdminUser))
        {
            return Utils.createString("? is already not an admin.", new String[]{removedAdminUser.getUsernameDisplay()});
        }

        if (!adminDatabase.removeUser(removedAdminUser))
        {
            return Utils.createString("Failed to remove ? from the admin database", new String[]{removedAdminUser.getUsernameDisplay()});
        }

        return Utils.createString("Successfully removed ? from the admin database", new String[]{removedAdminUser.getUsernameDisplay()});
    }
}
