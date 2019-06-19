package the_machine;


public class Command_Kick implements Command
{
    @Override
    public String execute(Message message)
    {
        final String[] args = message.getMessageText().split(" ");

        if (args.length != 2)
        {
            return "Usage: !kick <username>";
        }

        final String username = args[1];
        final User user = message.getUser();

        if (!Database_Admins.get().isUserInDatabase(user))
        {
            return Utils.createString(MachineStrings.SORRY_ADMIN_ONLY, new String[]{user.getUsernameDisplay()});
        }

        final Group group = message.getGroup();
        Database_Chat chatDatabase = Database_Chat.get(group);
        final User userToKick = chatDatabase.getUser(username);

        if (userToKick == null)
        {
            final User[] possibleUsers = chatDatabase.getPossibleUsers(username);
            return Utils.getPossibleUsernames(username, possibleUsers);
        }

        if (!chatDatabase.updateinChatValue(userToKick, 0))
        {
            return "Failed to kick user.";
        }

        Kik.kickUser(userToKick, group);
        return Utils.createString("Kicked ? successfully", new String[]{userToKick.getUsernameDisplay()});
    }
}
