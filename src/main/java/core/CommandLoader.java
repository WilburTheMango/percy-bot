package core;
import java.util.Set;

import commands.*;

public class CommandLoader {

    public static void load(CommandRegistry registry) {

        Set<Class<?>> classes = Set.of( //Currently, all commands must be registered here.
        		PingCommand.class
        );

        for (Class<?> clazz : classes) {
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();

                if (instance instanceof BaseCommand cmd) {
                    registry.register(cmd);
                }

            } catch (Exception e) { // i cant remember if this is a valid way to catch the required exception here
                e.printStackTrace();
            }
        }
    }
}