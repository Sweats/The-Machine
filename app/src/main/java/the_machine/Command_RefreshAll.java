package the_machine;

public class Command_RefreshAll implements Command
{
    @Override
    public String execute(final Message message)
    {
        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        final User user = message.getUser();
        final Database_Admins databaseAdmins = Database_Admins.get();

        if (databaseAdmins.getAdminlevel(user) != Database_Admins.ROOT_ADMIN)
        {
            return "Sorry, this command is only available to root admins.";
        }

        final Group[] groups = Database_Support.get().getSupportedGroups();

        Database_Bans bansDatabase = Database_Bans.get();
        Database_Chat globalDatabase = Database_Chat.get();

        Integer newMembersCount = 0;
        Integer newBannedMembersCount = 0;
        Integer newGlobalMembersCount = 0;

        for (int i = 0; i < groups.length; i++)
        {
            Database_Chat chatDatabase = Database_Chat.get(groups[i]);
            final User[] groupMembers = Kik.getGroupMembers(groups[i]);

            if (groupMembers == null)
            {
                Utils.log(Utils.createString("Refresh all failed for group ? because groupMembers is null!", new String[]{message.getGroup().getJID()}));
                return null;
            }

            for (int j = 0; j < groupMembers.length; j++)
            {
                if (groupMembers[j].isMe())
                {
                    continue;
                }

                if (!globalDatabase.isUserInDatabase(groupMembers[j]))
                {
                    globalDatabase.addUser(groupMembers[j]);
                    newGlobalMembersCount++;
                    newMembersCount++;

                    if (groupMembers[j].isBanned() && !bansDatabase.isUserInDatabase(groupMembers[j]))
                    {
                        bansDatabase.addUser(groupMembers[j]);
                        chatDatabase.addUser(groupMembers[j]);
                        newBannedMembersCount++;
                    }

                    else
                    {
                        chatDatabase.addUser(groupMembers[j], 1);
                    }
                }

                else
                {
                    if (groupMembers[j].isBanned() && !bansDatabase.isUserInDatabase(groupMembers[j]))
                    {
                        bansDatabase.addUser(groupMembers[j]);
                        newBannedMembersCount++;
                        chatDatabase.addUser(groupMembers[j]);
                    }

                    else
                    {
                        chatDatabase.addUser(groupMembers[j], 1);
                        newMembersCount++;
                    }
                }
            }
        }

        final String formatResponse =   "Successfully executed refresh command\n\n" +
                "Total new users added to ban Database: %s\n\n" +
                "Total new users added to the global database: %s\n\n" +
                "Total new users added to all chat databases: %s";

        final String response = String.format(formatResponse, newBannedMembersCount.toString(), newGlobalMembersCount.toString(), newMembersCount.toString());
        return response;
    }
}
