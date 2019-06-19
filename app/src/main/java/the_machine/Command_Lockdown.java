package the_machine;

public class Command_Lockdown implements Command
{
    @Override
    public String execute(final Message message)
    {
        boolean fillFlag = false;
        Settings settings = message.getGroup().getGroupSettings();
        String response;

        final String[] args = message.getMessageText().split(" ");

        if (args.length > 2)
        {
            return "Usage: !lockdown or !lockdown fill";
        }

        if (args.length == 2 && args[1].equals("fill"))
        {
            fillFlag = true;
        }

        if (message.getGroup().getGroupSettings().getSetting(Settings.LOCKDOWN_KEY))
        {
            response = "Lockdown set to false. To revert this change, type in !lockdown again.";
            settings.modifySetting(Settings.LOCKDOWN_KEY, false);
            kickDummyAccounts(message);
        }

        else
        {
            response = "Lockdown set to true. To revert this change, type in !lockdown again.";
            settings.modifySetting(Settings.LOCKDOWN_KEY, true);

            if (fillFlag)
            {
                addDummyAccounts(message);
            }
        }

        return response;
    }

    private void kickDummyAccounts(final Message message)
    {
        final Group group = message.getGroup();
        final User[] groupMembers = group.getGroupMembers();

        for (int i = 0; i < groupMembers.length; i++)
        {
            if (groupMembers[i].getUsername().startsWith("swag.bot"))
            {
                Kik.kickUser(groupMembers[i], group);
            }
        }
    }

    private void addDummyAccounts(final Message message)
    {
        final Group group = message.getGroup();
        final int groupMembersCount = group.getTotalRegularMembersCount();
        final int dummyAccountsToAdd = 50 - groupMembersCount;
        final User[] dummyUsers = Database_Kik.getSwagBots(dummyAccountsToAdd);
        Kik.addUsers(dummyUsers, group);
    }
}
