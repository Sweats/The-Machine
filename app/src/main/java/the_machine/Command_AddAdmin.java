package the_machine;

/**
 * This command makes a user an admin of The Machine.
 */

public class Command_AddAdmin implements Command
{
    @Override
    public String execute(final Message message)
    {
        final String[] args = message.getMessageText().split(" ");

        if (args.length != 3)
        {
            return "Usage: !add_admin <username> <adminLevel>";
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

        if (!Utils.isValidNumberString(args[2]))
        {
            return Utils.createString("Invalid argument \"?\" because it is not a valid number.", new String[]{args[2]});
        }

        final String username = args[1];
        final Integer adminLevel = Integer.valueOf(args[2]);
        Database_Chat globalDatabase = Database_Chat.get();
        final User newAdminUser = globalDatabase.getUser(username);

        if (newAdminUser == null)
        {
            final User[] possibleUsers = globalDatabase.getPossibleUsers(username);
            return Utils.getPossibleUsernames(username, possibleUsers);
        }

        if (adminDatabase.isUserInDatabase(newAdminUser))
        {
            return Utils.createString("? is already an admin.", new String[]{newAdminUser.getUsernameDisplay()});
        }


        if (!adminDatabase.addUser(newAdminUser, adminLevel))
        {
            return Utils.createString("Failed to add admin ? because the admin level is not valid. Valid arguments are:\n\n" +
                                            "0 (?)\n\n" +
                                            "1 (?)\n\n" +
                                            "2 (?)\n\n" +
                                            "3 (?)\n\n",
                    new String[]{newAdminUser.getUsernameDisplay(), Database_Admins.LOW_ADMIN_NAME, Database_Admins.MEDIUM_ADMIN_NAME, Database_Admins.HIGH_ADMIN_NAME, Database_Admins.ROOT_ADMIN_NAME});
        }

        return Utils.createString("Successfully added ? to the admin database.", new String[]{newAdminUser.getUsernameDisplay()});
    }
}
