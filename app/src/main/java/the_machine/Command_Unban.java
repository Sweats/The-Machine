package the_machine;

//TODO: Test this code.

public class Command_Unban implements Command
{
    @Override
    public String execute(Message message)
    {
        String reply = "Failed to unban user.";

        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        final String[] args = message.getMessageText().split(" ");

        if (args.length != 2)
        {
            return "Usage: !unban <username>";
        }

        final String username = args[1];
        Database_Bans bansDatabase = Database_Bans.get();
        final User unbanUser = bansDatabase.getUser(username);

        if (unbanUser == null)
        {
            final User[] possibleUsers = bansDatabase.getPossibleUsers(username);
            return Utils.getPossibleUsernames(username, possibleUsers);
        }

        boolean success = bansDatabase.removeUser(unbanUser);

        if (success)
        {
            reply = Utils.createString("Successfully unbanned ? from all chats.", new String[]{unbanUser.getUsernameDisplay()});
            unbanUserFromAllGroups(unbanUser);
        }

        return reply;
    }

    private void unbanUserFromAllGroups(final User user)
    {
        final Group[] groups = Database_Support.get().getSupportedGroups();

        for (int i = 0; i < groups.length; i++)
        {
            Kik.unbanUser(user, groups[i]);
        }
    }
}
