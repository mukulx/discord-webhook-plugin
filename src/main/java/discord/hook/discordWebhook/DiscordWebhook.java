package discord.hook.discordWebhook;

import org.bukkit.plugin.java.JavaPlugin;

public class DiscordWebhook extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();

        // Register the command
        this.getCommand("webhook").setExecutor(new WebhookCommand(this));

        // Log plugin enabled
        getLogger().info("DiscordWebhook plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DiscordWebhook plugin has been disabled!");
    }
}