package the_machine;

public class Callback_HasBanned implements Callback
{
    @Override
    public String execute(final Message message)
    {
        Database_Chat globalDatabase = Database_Chat.get();
        Database_Chat chatDatabase = Database_Chat.get(message.getGroup());
        Database_Bans bansDatabase = Database_Bans.get();
        final User user = message.getUser();

        if (!globalDatabase.isUserInDatabase(user))
        {
            globalDatabase.addUser(user);
            chatDatabase.addUser(user, 0);
        }

        if (!bansDatabase.isUserInDatabase(user))
        {
            bansDatabase.addUser(user);
        }

        banUserFromAllChats(user);
        return "Banned " + user.getUsernameDisplay() + " from all chats.";
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
