package the_machine;

//TODO: TEST the argument stuff and clean up this code.

public class Command_Ban implements Command
{
    @Override
    public String execute(Message message)
    {
        String reply = "Failed to ban user.";

        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        final String[] args = message.getMessageText().split(" ");

        if (args.length != 2 && args.length != 3 && args.length != 4)
        {
            return "Usage: !ban <username> or !ban <username> <hours> or !ban <username> <hours> <reason>";
        }

        Database_Chat chatDatabase = Database_Chat.get(message.getGroup());
        final String username = args[1];
        boolean success = false;
        final User user = chatDatabase.getUser(username);

        if (user == null)
        {
            final User[] possibleUsers = chatDatabase.getPossibleUsers(username);
            return Utils.getPossibleUsernames(username, possibleUsers);
        }

        Database_Bans bansDatabase = Database_Bans.get();

        if (bansDatabase.isUserInDatabase(user))
        {
            return Utils.createString("? is already banned.", new String[]{user.getUsernameDisplay()});
        }

        if (args.length == 2)
        {
            success = bansDatabase.addUser(user);

            if (success)
            {
                banUserFromAllChats(user);
                reply = Utils.createString("Successfully banned ? from all chats.", new String[]{user.getUsernameDisplay()});
            }
        }

        else if (args.length == 3)
        {
            if (Utils.isValidNumberString(args[2]))
            {
                return Utils.createString("Invalid argument \"?\" because it is not a valid number.", new String[]{args[2]});
            }

            final Long unbanTimestamp = System.currentTimeMillis() + (Utils.ONE_HOUR * Long.valueOf(args[2]));
            success = bansDatabase.addUser(user, unbanTimestamp);

            if (success)
            {
                banUserFromAllChats(user);
                reply = Utils.createString("Successfully banned ? from all chats.\n\n Ban expires on: ?", new String[]{user.getUsernameDisplay(), Utils.createDate(unbanTimestamp)});
            }
        }

        else if (args.length == 4)
        {
            if (Utils.isValidNumberString(args[2]))
            {
                return Utils.createString("Invalid argument \"?\" because it is not a valid number.", new String[]{args[2]});
            }

            final Long unbanTimestamp = System.currentTimeMillis() + (Utils.ONE_HOUR * Long.valueOf(args[2]));
            final String banReason = args[3];
            success = bansDatabase.addUser(user, unbanTimestamp, banReason);

            if (success)
            {
                banUserFromAllChats(user);
                reply = Utils.createString("Successfully banned ? from all chats.\n\nBan expires on: ?\n\nReason: ?", new String[]{user.getUsernameDisplay(), Utils.createDate(unbanTimestamp), banReason});
            }
        }

        return reply;
    }

    private void banUserFromAllChats(final User user)
    {
        final Group[] groups = Database_Support.get().getSupportedGroups();

        for (int i = 0; i < groups.length; i++)
        {
            Kik.banUser(user, groups[i]);
        }
    }
}
