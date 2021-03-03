package me.rubataga.everyhunt.commands;

import me.rubataga.everyhunt.roles.Hunter;
import me.rubataga.everyhunt.services.CompassService;
import me.rubataga.everyhunt.services.TargetManager;
import me.rubataga.everyhunt.utils.TrackingCompassUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;

/**
 * Class containing commands relating to {@link TrackingCompassUtils}
 */
public class TrackingCompassCommands {

    private TrackingCompassCommands() {}

    /**
     * Command to give a player a {@link TrackingCompassUtils#trackingCompass()}
     *
     * @return CommandAPICommand
     */
    public static CommandAPICommand compass(){
        return new CommandAPICommand("compass")
                .withArguments(new PlayerArgument("player"))
                .executes((sender, args) -> {
                    Collection<Entity> player = Collections.singletonList((Player) args[0]);
                    CompassService.giveCompass(sender,player);
                });
    }

    /**
     * Command to give self a {@link TrackingCompassUtils#trackingCompass()}
     *
     * @return CommandAPICommand
     */
    public static CommandAPICommand compassSelf(){
        return new CommandAPICommand("compass")
                .executesPlayer((sender, args) -> {
                    Collection<Entity> player = Collections.singletonList(sender);
                    CompassService.giveCompass(sender,player);
                });
    }

    /**
     * Command given by player to track a runner
     *
     * @return CommandAPICommand
     */
    public static CommandAPICommand trackRunner(){
        return new CommandAPICommand("track")
                .withArguments(new PlayerArgument("runner"))
                .executesPlayer((sender, args) -> {
                    Collection<Entity> entityList = Collections.singletonList((Player)args[0]);
                    CompassService.track(sender,entityList);
                });
    }

    /**
     * Command given by player to track an entity / multiple entities
     *
     * @return CommandAPICommand
     */
    @SuppressWarnings("unchecked")
    public static CommandAPICommand trackEntity(){
        return new CommandAPICommand("track")
                .withArguments(new EntitySelectorArgument("entity", EntitySelectorArgument.EntitySelector.MANY_ENTITIES))
                .executesPlayer((sender,args) -> {
                    Collection<Entity> entityList = (Collection<Entity>)args[0];
                    CompassService.track(sender,entityList);
                });
    }


    /**
     * Command to reset a player's compass location
     *
     * @return CommandAPICommand
     */
    public static CommandAPICommand reset(){
        return new CommandAPICommand("reset")
                .withAliases("rs")
                .executesPlayer((sender, args) -> {
                    CompassService.reset(sender);
                });
    }

    public static CommandAPICommand gui(){
        return new CommandAPICommand("gui")
                .executesPlayer((sender, args) -> {
                    Hunter hunter = TargetManager.getHunter(sender);
                    if(hunter!=null){
                        hunter.getGUI().show();
                    } else {
                        sender.sendMessage("Only hunters can do this!");
                    }
                });
    }
}
