package the_machine;

public class Command_Stats implements Command
{
    @Override
    public String execute(final Message message)
    {
        return MachineStrings.RESPONSE_STATS;
    }
}
