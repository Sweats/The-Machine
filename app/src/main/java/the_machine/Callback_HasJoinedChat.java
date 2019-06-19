package the_machine;

public class Callback_HasJoinedChat implements Callback
{
    @Override
    public String execute(final Message message)
    {
        final User user = message.getUser();

        Database_Chat globalDatabase = Database_Chat.get();
        Database_Chat chatDatabase = Database_Chat.get(message.getGroup());

        if (!globalDatabase.isUserInDatabase(user))
        {
            globalDatabase.addUser(user);
            chatDatabase.addUser(user, 1);
        }

        if (message.getGroup().getGroupSettings().getSetting(Settings.LOCKDOWN_KEY))
        {
            Kik.kickUser(user, message.getGroup());
            return Utils.createString("Kicked ? because this group is currently on lockdown.", new String[]{user.getUsernameDisplay()});
        }

        Database_Bans bansDatabase = Database_Bans.get();

        if (!bansDatabase.isUserInDatabase(user))
        {
            return Utils.createString(MachineStrings.WELCOME, new String[]{user.getUsernameDisplay()});
        }

        if (bansDatabase.isBanTimeExpired(user))
        {
            bansDatabase.removeUser(user);
            return Utils.createString(MachineStrings.WELCOME_UNBANNED, new String[]{user.getUsernameDisplay()});
        }

        if (message.getGroup().getBannedMembers().length >= 99)
        {
            final User oldestBannedUser = bansDatabase.getOldestBannedUser(message.getGroup());
            Kik.unbanUser(oldestBannedUser, message.getGroup());
        }

        Kik.kickUser(user, message.getGroup());
        Kik.banUser(user, message.getGroup());

        return Utils.createString("Banned ? because they are already banned from the other chats.", new String[]{user.getUsernameDisplay()});
    }
}
