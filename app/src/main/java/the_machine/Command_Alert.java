package the_machine;


//TODO: Get group name dynamically.

public class Command_Alert implements Command
{
    @Override
    public String execute(Message message)
    {
        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        final String[] args = message.getMessageTextOriginal().split(" ", 2);

        String result = "";

        if (args.length > 1)
        {
            result = "? has alerted all admins to look into the chat. Reason: ?";
            result = Utils.createString(result, new String[]{message.getUser().getUsernameDisplay(), args[1]});
        }

        else
        {
            result = "? has alerted all admins to look into the chat.";
            result = Utils.createString(result, new String[]{message.getUser().getUsernameDisplay()});
        }

        Kik.messageGroup(result, Group.createFromJID(MachineStrings.machineTeamJID));
        return "Alerted admins to look into this group.";
    }
}
