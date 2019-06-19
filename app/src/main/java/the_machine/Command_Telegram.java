package the_machine;

public class Command_Telegram implements Command
{
    @Override
    public String execute(Message message)
    {
        return MachineStrings.RESPONSE_TELEGRAM;
    }
}
