package the_machine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Command_AddMe implements Command
{
    public String execute(final Message message)
    {
        if (message.isPrivateMessage())
        {
            return "This command is only supported in groups";
        }

        final User user = message.getUser();
        final String[] args = message.getMessageText().split(" ");
        final int argc = args.length;
        final Database_Admins adminDatabase = Database_Admins.get();

        if (!adminDatabase.isUserInDatabase(user))
        {
            return Utils.createString(MachineStrings.SORRY_ADMIN_ONLY, new String[]{user.getUsernameDisplay()});
        }

        if (argc != 2)
        {
            return "Usage: !addme <all> OR <supportedGroup>. Type in !groups to see supported groups";
        }

        final String arg = args[1];

        if (arg.equals("all"))
        {
            addUserToAllGroups(user);
            return "Successfully added you to all supported groups";
        }

        final Group[] supportedGroups = Database_Support.get().getSupportedGroups();

        for (int i = 0; i < supportedGroups.length; i++)
        {
            final String groupTag = supportedGroups[i].getGroupTag();

            if (arg.equals(groupTag))
            {
                return "Successfully added you to the group " + groupTag;
            }
        }

        final String[] possibleGroups = getPossibleGroups(arg, supportedGroups);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < possibleGroups.length; i++)
        {
            stringBuilder.append((i + 1) + ": " + possibleGroups[i] + "\n\n");
        }

        return Utils.createString("Possible groups matching \"?\" found. Which one of these groups did you mean?\n\n?", new String[]{arg, stringBuilder.toString()});
    }

    private void addUserToAllGroups(final User user)
    {
        Database_Support supportDatabase = Database_Support.get();
        final Group[] groups = supportDatabase.getSupportedGroups();

        for (int i = 0; i < groups.length; i++)
        {
            Kik.addUser(user, groups[i]);
        }
    }

    private String[] getPossibleGroups(final String requestedGroup, final Group[] groups)
    {
        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0; i < groups.length; i++)
        {
            final String groupTag = groups[i].getGroupTag();

            if (groupTag.startsWith(requestedGroup))
            {
                arrayList.add(groupTag);
            }
        }

        return arrayList.toArray(new String[0]);
    }
}
