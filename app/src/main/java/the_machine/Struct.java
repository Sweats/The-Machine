package the_machine;


public class Struct
{
    public Command m_command;
    public String m_string;
    public Callback m_callback;

    public Struct(final Command command, final String commandString)
    {
        m_command = command;
        m_string = commandString;
    }

    public Struct(final Callback callback, final String callbackString)
    {
        m_callback = callback;
        m_string = callbackString;
    }
}
