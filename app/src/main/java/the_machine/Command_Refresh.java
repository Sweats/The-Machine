package the_machine;

// Uses kik database for now. Maybe fix that in the future.

public class Command_Refresh implements Command
{
    @Override
    public String execute(Message message)
    {
        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        final User user = message.getUser();
        Database_Admins database = Database_Admins.get();

        if (database.getAdminlevel(user) < Database_Admins.HIGH_ADMIN)
        {
            return "Sorry " + user.getUsernameDisplay() + ", this command is only available to high admins.";
        }

        Database_Bans bansDatabase = Database_Bans.get();
        Database_Chat chatDatabase = Database_Chat.get(message.getGroup());
        Database_Chat globalDatabase = Database_Chat.get();

        final User[] groupMembers = message.getGroup().getGroupMembers();

        if (groupMembers == null)
        {
            Utils.log(Utils.createString("Refresh failed for group ? because groupMembers is null!", new String[]{message.getGroup().getJID()}));
            return null;
        }

        Integer newBannedMembersCount = 0;
        Integer newMembersCount = 0;
        Integer newGlobalMembersCount = 0;

        for (int i = 0; i < groupMembers.length; i++)
        {
            if (groupMembers[i].isMe())
            {
                continue;
            }

            if (!globalDatabase.isUserInDatabase(groupMembers[i]))
            {
                globalDatabase.addUser(groupMembers[i]);
                newGlobalMembersCount++;
                newMembersCount++;

                if (groupMembers[i].isBanned() && !bansDatabase.isUserInDatabase(groupMembers[i]))
                {
                    bansDatabase.addUser(groupMembers[i]);
                    chatDatabase.addUser(groupMembers[i]);
                    newBannedMembersCount++;
                }

                else
                {
                    chatDatabase.addUser(groupMembers[i], 1);
                }
            }

            else
            {
                if (groupMembers[i].isBanned() && !bansDatabase.isUserInDatabase(groupMembers[i]))
                {
                    bansDatabase.addUser(groupMembers[i]);
                    newBannedMembersCount++;
                    chatDatabase.addUser(groupMembers[i]);
                }

                else
                {
                    chatDatabase.addUser(groupMembers[i], 1);
                    newMembersCount++;
                }
            }
        }

        final String formatResponse =   "Successfully executed refresh command\n\n" +
                            "Total new users added to ban Database: %s\n\n" +
                            "Total new users added to this chats database: %s\n\n" +
                            "Total new users added to the global database: %s";

        final String reponse = String.format(formatResponse, newBannedMembersCount.toString(), newMembersCount.toString(), newGlobalMembersCount.toString());
        return reponse;
    }
}
