package the_machine;

public class MachineStrings
{
    private MachineStrings(){}

    // Commands.
    public static final String COMMAND_TEST = "!test";
    public static final String COMMAND_TODO = "!todo";
    public static final String COMMAND_HELP = "!help";
    public static final String COMMAND_HELP2 = "!help2";
    public static final String COMMAND_HELP3 = "!help3";
    public static final String COMMAND_VERSION = "!version";
    public static final String COMMAND_SUPPORT_ADD = "!support_add";
    public static final String COMMAND_SUPPORT_REMOVE = "!support_remove";
    public static final String COMMAND_TELEGRAM = "!telegram";
    public static final String COMMAND_GROUPS = "!groups";
    public static final String COMMAND_TOP5 = "!top5";
    public static final String COMMAND_TOP5_GLOBAL = "!top5_global";
    public static final String COMMAND_KICK_INACTIVES = "!kick_inactives";
    public static final String COMMAND_ALERT = "!alert";
    public static final String COMMAND_REFRESH = "!refresh";
    public static final String COMMAND_REFRESH_ALL = "!refresh_global";
    public static final String COMMAND_PURGE = "!purge";
    public static final String COMMAND_LOCKDOWN = "!lockdown";
    public static final String COMMAND_BAN = "!ban";
    public static final String COMMAND_UNBAN = "!unban";
    public static final String COMMAND_KICK = "!kick";
    public static final String COMMAND_BANINFO = "!baninfo";
    public static final String COMMAND_PM = "!pm";
    public static final String COMMAND_MUZZYNUKE = "!muznuke";
    public static final String COMMAND_ADD_ADMIN = "!add_admin";
    public static final String COMMAND_REMOVE_ADMIN = "!remove_admin";
    public static final String COMMAND_ANNOUNCE = "!announce";
    public static final String COMMAND_GROUPNAME = "!groupname";
    public static final String COMMAND_INACTIVE = "!inactive";
    public static final String COMMAND_ADDME = "!addme";
    public static final String COMMAND_STATS = "!stats";
    // Responses.

    public static final String RESPONSE_TEST = "Test.";

    public static final String RESPONSE_TODO =  "TODO list:\n\n" +
                                                "1. Integrate Telegram bot API inside Kik to further unite us programmers.\n\n" +
                                                "2. Implement a new banning system.";

    public static final String RESPONSE_HELP =  "List of commands (page 1):\n\n" +
                                                "1. !telegram - Prints out our Telegram group information.\n\n" +
                                                "2. !groups - Prints out a list of our groups on Kik.\n\n" +
                                                "3. !alert - Messages all admins via PM to look into this chat.\n\n" +
                                                "4. !suggest - Prints out how to suggest new ideas or changes to the creator(s) of me.\n\n" +
                                                "5. !inactive - Prints out inactivity checking commands.\n\n" +
                                                "See !help2 for more commands.";


    public static final String RESPONSE_HELP2 = "List of commands (page 2):\n\n" +
                                                "1. !addme - Prints out how to make me add you to our programming groups.\n\n" +
                                                "2. !stats - Prints out chat statistic commands.\n\n" +
                                                "3. !baninfo - Prints out how to get ban information for a Kik username.\n\n" +
                                                "4. !dump_bans - Dumps all currently banned Kik usernames.\n\n" +
                                                "5. !faq - Prints out answers to frequently asked questions.\n\n" +
                                                "See !help3 for more commands.";


    public static final String RESPONSE_HELP3 = "List of commands (page 3):\n\n" +
                                                "1. !settings - Prints out chat setting commands.\n\n" +
                                                "2. !todo - Prints out a to do list for me.\n\n" +
                                                "3. !version - Prints out current version for me.\n\n" +
                                                "4. !test - Prints out \"Test\" if I am working.\n\n" +
                                                "5. !welcome - Prints the welcome message\n\n" +
                                                "See !help4 for more commands.";

    public static final String RESPONSE_STATS = "List of stats commands:\n\n" +
                                                "1. !top5 - Prints out the top 5 active users in this group sorted by message count.\n\n" +
                                                "2. !top5_global - Prints out the top 5 active users in all groups supported by me sorted by message count.\n\n";

    public static final String WELCOME = "Welcome ?!\n\n" +
                                         "I'm just a bot that helps manage these groups. See !help for commands.\n\n" +
                                         "Join our Telegram group. See !telegram for details";

    public static final String WELCOME_UNBANNED = "Welcome back ?. You have been unbanned from all chats because your ban time has expired";

    public static final String RESPONSE_VERSION = "Version 1.0";

    public static final String RESPONSE_TELEGRAM =  "Link:\nhttps://t.me/programmers_unite\n\n" +
                                                    "@programmers_unite\n\nFor latest updates on this bot, join this Telegram channel:\n" +
                                                    "https://t.me/The_Machine0";

    public static final String RESPONSE_KICK_INACTIVES = "Kicked all inactive users since:\n\n?";

    public static final String ERROR_HELP_FILE_NOT_FOUND = "ERROR: Owner of The Machine, make sure you add the help text files to the bot! Otherwise, I won't be able to print out help commands.";

    public static final String SORRY_GROUP_ONLY = "Sorry ?, this command is only supported in groups.";
    public static final String SORRY_PM_ONLY = "Sorry ?, this command is only supported in PM.";
    public static final String SORRY_ADMIN_ONLY = "Sorry ?, this command is only available to admins.";



    //Passive commands

    public static final String CALLBACK_JOINED_CHAT = "has joined the chat";
    public static final String CALLBACK_LEFT_CHAT = "has left the chat";
    public static final String CALLBACK_ADDED_YOU = "has added you";
    public static final String CALLBACK_YOU_HAVE_ADDED = "You have added";
    public static final String CALLBACK_HAS_BANNED = "has banned";
    public static final String CALLBACK_HAS_UNBANNED = "has unbanned";
    public static final String CALLBACK_HAS_REMOVED = "has removed";



    //JIDS
    public static final String machineTeamJID = "1100207838123_g@groups.kik.com";
    /*
    public static final String codingJID = "1099909546401_g@groups.kik.com";
    public static final String coding2JID = "1099959311413_g@groups.kik.com";
    public static final String programJID = "1099910149291_g@groups.kik.com";
    public static final String codin2JID = "1100031118933_g@groups.kik.com";
    public static final String LearnCodingJID = "1099988708641_g@groups.kik.com";
    public static final String codinJID = "1099959312259_g@groups.kik.com";
    public static final String machineTeamJID = "1100207838123_g@groups.kik.com";
    public static final String programmerChatJID = "1100196947725_g@groups.kik.com";
    public static final String scriptingJID = "1099971236717_g@groups.kik.com";
    public static final String programmingJID = "1099909533505_g@groups.kik.com";
    public static final String programmingSquadJID = "1100203869977_g@groups.kik.com";
    public static final String gameDevelopmentJID = "1099967822615_g@groups.kik.com";;
    public static final String javaScript2JID = "1100048676899_g@groups.kik.com";
    public static final String programmersUniteJID = "1100210486329_g@groups.kik.com";
    public static final String codingLearnJID = "1100217651861_g@groups.kik.com";
    public static final String programmers_UniteJID = "1100218410815_g@groups.kik.com";

    public static final String TAG_CODING = "#coding";
    public static final String TAG_CODING2 = "#coding2";
    public static final String TAG_CODIN2 = "#codin2";
    public static final String TAG_PROGRAMMINGSQUAD = "#programmingSquad";
    public static final String TAG_PROGRAM = "#program";
    public static final String TAG_LEARNCODING = "#LearnCoding";
    public static final String TAG_CODIN = "#codin";
    public static final String TAG_MACHINE_TEAN = "The Machine Team";
    public static final String TAG_PROGRAMMER_CHAT = "#programmerChat";
    public static final String TAG_SCRIPTING = "#scripting";
    public static final String TAG_PROGRAMMING = "#programming";
    public static final String TAG_GAME_DEVELOPMENT = "#gamedevelopment";
    public static final String TAG_JAVASCRIPT_2 = "#javaScript2";
    public static final String TAG_PROGRAMMERSUNITE = "#programmersUnite";
    public static final String TAG_CODING_LEARN = "#codingLearn";
    public static final String TAG_PROGRAMMERS_UNITE = "#programmers_unite";
    */
}
