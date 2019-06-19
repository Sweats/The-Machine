package the_machine;
import java.util.Random;

public class Command_MuzzNuke implements Command
{
    @Override
    public String execute(final Message message)
    {
        String[] countries = new String[]{"The USA", "The United Kingdom", "Sweden", "Niger", "Mexico", "Finland", "Russia", "Muzzyland", "Saudi Arabia"};
        Random rnd = new Random();
        int countryTN = rnd.nextInt(countries.length);
        return countries[countryTN] + " was nuked! They must have left their IP open.";
    }
}
