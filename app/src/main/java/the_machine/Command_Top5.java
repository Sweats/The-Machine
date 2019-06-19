package the_machine;

public class Command_Top5 implements Command
{
    @Override
    public String execute(Message message)
    {
        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        Database_Chat chatDatabase = Database_Chat.get(message.getGroup());
        final User[] users = chatDatabase.getTop5();
        String[] stringArray = new String[users.length];

        for (int i = 0; i < users.length; i++)
        {
            int messageCount = Database_Chat.get(message.getGroup()).getMessageCount(users[i]);
            stringArray[i] = (i + 1) + ": " + users[i].getUsernameDisplay() + " " + messageCount + "\n\n";
        }

        StringBuilder stringBuilder= new StringBuilder();

        for (int i = 0; i < stringArray.length; i++)
        {
            stringBuilder.append(stringArray[i]);
        }

        return stringBuilder.toString();
    }
}
