package the_machine;

//TODO: Figure out how to dynamically grab group hashtags from kik then use it here.
public class Command_Groups implements Command
{
    @Override
    public String execute(Message message)
    {
        final Group[] groups = Database_Support.get().getSupportedGroups();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < groups.length; i++)
        {
            stringBuilder.append(groups[i].getGroupTag() + "\n\n");
        }

        return stringBuilder.toString();
    }
}
