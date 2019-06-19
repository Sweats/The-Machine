package the_machine;


public class Command_BanInfo implements Command
{
    @Override
    public String execute(final Message message)
    {
        String response;

        final String[] args = message.getMessageText().split(" ");

        if (args.length != 2)
        {
            return "Usage: !baninfo <username>";
        }

        Database_Bans bansDatabase = Database_Bans.get();
        final String username = args[1];

        final User user = bansDatabase.getUser(username);

        if (user == null)
        {
            final User[] possibleUsers = bansDatabase.getPossibleUsers(username);
            return Utils.getPossibleUsernames(username, possibleUsers);
        }

        response = bansDatabase.getBanInformation(user);

        if (response == null)
        {
            return Utils.createString("Failed to get ban info for \"?\". Please check code.", new String[]{username});
        }

        return response;
    }
}
