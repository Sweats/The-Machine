package the_machine;

import java.io.IOException;

public class Command_Help implements Command
{
    @Override
    public String execute(final Message message)
    {
        return MachineStrings.RESPONSE_HELP;
    }
}
