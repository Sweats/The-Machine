package the_machine;

public class Callback_HasLeftChat implements Callback
{
    @Override
    public String execute(final Message message)
    {
        Database_Chat globalDatabase = Database_Chat.get();
        Database_Chat chatDatabase = Database_Chat.get(message.getGroup());

        final User removedUser = message.getUser();

        if (!globalDatabase.isUserInDatabase(removedUser))
        {
            globalDatabase.addUser(removedUser);
            chatDatabase.addUser(removedUser, 0);
        }

        else
        {
            chatDatabase.updateinChatValue(removedUser, 0);
        }

        return "RIP";
    }
}
