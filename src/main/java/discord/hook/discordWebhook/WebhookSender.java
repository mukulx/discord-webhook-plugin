package discord.hook.discordWebhook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebhookSender {

    public static void sendWebhookMessage(String webhookUrl, String message, String username, CommandSender sender) {
        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("DiscordWebhook"), () -> {
            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "DiscordWebhook-Plugin/1.0");
                connection.setDoOutput(true);

                String jsonPayload = createJsonPayload(message, username);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();

                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("DiscordWebhook"), () -> {
                    if (responseCode >= 200 && responseCode < 300) {
                        sender.sendMessage(ChatColor.GREEN + "Message sent successfully to Discord!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Failed to send message. Response code: " + responseCode);
                    }
                });

                connection.disconnect();

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("DiscordWebhook"), () -> {
                    sender.sendMessage(ChatColor.RED + "Error sending webhook: " + e.getMessage());
                });

                Bukkit.getLogger().severe("Discord webhook error: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private static String createJsonPayload(String message, String username) {
        String escapedMessage = message.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

        String escapedUsername = username.replace("\\", "\\\\")
                .replace("\"", "\\\"");

        return "{\"content\":\"" + escapedMessage + "\",\"username\":\"" + escapedUsername + "\"}";
    }
}