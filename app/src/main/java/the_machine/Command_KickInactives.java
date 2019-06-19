package the_machine;

public class Command_KickInactives implements Command
{
    private static final Long ONE_DAY = 86400000L;
    private static final Long ONE_WEEK = 604800000L;
    private static final Long ONE_MONTH = 2629746000L;

    private static final String DAY = "day";
    private static final String WEEK = "week";
    private static final String MONTH = "month";

    @Override
    public String execute(Message message)
    {
        final String messageText = message.getMessageText();

        if (message.isPrivateMessage())
        {
            return Utils.createString(MachineStrings.SORRY_GROUP_ONLY, new String[]{message.getUser().getUsernameDisplay()});
        }

        final String[] args = messageText.split(" ");

        if (args.length != 2)
        {
            return "Usage: !kick_inactives <day/week/month>";
        }

        final User user = message.getUser();
        final Group group = message.getGroup();
        Database_Admins adminDatabase = Database_Admins.get();

        if (!isValidArg(args[1]))
        {
            return "Invalid argument: \" " + args[1] + " \". Valid arguments are: day, week, or month.";
        }

        if (adminDatabase.getAdminlevel(user) < Database_Admins.HIGH_ADMIN)
        {
            return "Sorry, this command is only available to ROOT and HIGH admins.";
        }

        final Long argTimestamp = getRequestedArg(args[1]);
        final User[] users = Database_Chat.get(group).getInactives(argTimestamp);

        for (int i = 0; i < users.length; i++)
        {
            Kik.kickUser(users[i], group);
        }

        return Utils.createString(MachineStrings.RESPONSE_KICK_INACTIVES, new String[]{Utils.createDate(argTimestamp)});
    }

    private Long getRequestedArg(final String arg)
    {
        Long timestamp = 0L;

        switch (arg)
        {
            case DAY:
                timestamp = ONE_DAY;
                break;
            case WEEK:
                timestamp = ONE_WEEK;
                break;
            case MONTH:
                timestamp = ONE_MONTH;
                break;
            default:
                break;
        }

        return timestamp;
    }

    private boolean isValidArg(final String arg)
    {
        boolean result = false;
        final String[] args = new String[]{DAY, WEEK, MONTH};

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals(arg))
            {
               result = true;
               break;
            }
        }

        return result;
    }
}
