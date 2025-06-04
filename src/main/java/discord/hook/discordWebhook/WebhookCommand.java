package discord.hook.discordWebhook;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class WebhookCommand implements CommandExecutor {

    private final DiscordWebhook plugin;

    public WebhookCommand(DiscordWebhook plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("discordwebhook.use")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("webhook")) {

            if (args.length >= 1 && args[0].equalsIgnoreCase("send")) {

                String webhookUrl;
                String message;

                if (args.length >= 3 && isValidDiscordWebhookUrl(args[1])) {
                    webhookUrl = args[1];
                    StringBuilder messageBuilder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        messageBuilder.append(args[i]);
                        if (i < args.length - 1) {
                            messageBuilder.append(" ");
                        }
                    }
                    message = messageBuilder.toString();
                } else if (args.length >= 2) {
                    String defaultWebhook = plugin.getConfig().getString("default-webhook", "");
                    if (defaultWebhook.isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "No default webhook configured! Use /webhook send <webhook_url> <message> or set a default webhook in config.yml");
                        return true;
                    }
                    webhookUrl = defaultWebhook;
                    StringBuilder messageBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        messageBuilder.append(args[i]);
                        if (i < args.length - 1) {
                            messageBuilder.append(" ");
                        }
                    }
                    message = messageBuilder.toString();
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Usage: /webhook send <message> OR /webhook send <webhook_url> <message>");
                    return true;
                }

                if (!isValidDiscordWebhookUrl(webhookUrl)) {
                    sender.sendMessage(ChatColor.RED + "Invalid Discord webhook URL!");
                    return true;
                }

                String finalMessage = message;
                boolean includePlayerName = plugin.getConfig().getBoolean("include-player-name", false);
                if (includePlayerName && sender instanceof Player) {
                    Player player = (Player) sender;
                    finalMessage = "**" + player.getName() + "**: " + message;
                }

                String webhookUsername = plugin.getConfig().getString("webhook-username", "Minecraft Server");

                WebhookSender.sendWebhookMessage(webhookUrl, finalMessage, webhookUsername, sender);

                sender.sendMessage(ChatColor.GREEN + "Sending message to Discord webhook...");
                return true;

            } else if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("discordwebhook.admin")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to reload the config!");
                    return true;
                }
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
                return true;
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Usage: /webhook send <message> OR /webhook send <webhook_url> <message>");
                sender.sendMessage(ChatColor.YELLOW + "Admin: /webhook reload");
                return true;
            }
        }

        return false;
    }

    private boolean isValidDiscordWebhookUrl(String url) {
        return url.startsWith("https://discord.com/api/webhooks/") ||
                url.startsWith("https://discordapp.com/api/webhooks/");
    }
}