# Discord Webhook Plugin

A simple and efficient Minecraft Paper plugin that allows you to send messages to Discord webhooks directly from your server.

## Features

- ✅ Send messages to Discord webhooks from Minecraft
- ✅ Configurable default webhook for easy use
- ✅ Clean Discord messages without command clutter
- ✅ Custom webhook username
- ✅ Permission-based access control
- ✅ Async message sending (no server lag)
- ✅ Support for both default and custom webhooks
- ✅ Config reload command

## Requirements

- **Minecraft Server**: Paper 1.21+ (or compatible forks)
- **Java**: Version 17 or higher
- **Discord**: A Discord webhook URL

## Installation

1. Download the latest `DiscordWebhook-1.0.0.jar` from the [Releases](https://github.com/mukulx/discord-webhook-plugin/releases) page
2. Place the JAR file in your server's `plugins/` folder
3. Restart your server
4. Configure the plugin (see Configuration section)

## Configuration

After first startup, edit `plugins/DiscordWebhook/config.yml`:

```yaml
default-webhook: "https://discord.com/api/webhooks/YOUR_WEBHOOK_ID/YOUR_WEBHOOK_TOKEN"
include-player-name: false
webhook-username: "Minecraft Server"
```

### Configuration Options

- `default-webhook`: Your Discord webhook URL (leave empty if not using default webhook)
- `include-player-name`: Set to `true` to include player names in messages, `false` for clean messages
- `webhook-username`: The username that appears in Discord messages

## Commands

| Command | Description | Permission | Usage |
|---------|-------------|------------|-------|
| `/webhook send <message>` | Send message using default webhook | `discordwebhook.use` | `/webhook send Hello World!` |
| `/webhook send <webhook_url> <message>` | Send message to specific webhook | `discordwebhook.use` | `/webhook send https://discord.com/... Hello!` |
| `/webhook reload` | Reload plugin configuration | `discordwebhook.admin` | `/webhook reload` |

## Permissions

- `discordwebhook.use` - Allows players to send webhook messages (default: op)
- `discordwebhook.admin` - Allows config reload and admin functions (default: op)

## How to Get a Discord Webhook

1. Go to your Discord server settings
2. Navigate to **Integrations** → **Webhooks**
3. Click **New Webhook**
4. Choose a channel and customize the webhook
5. Copy the **Webhook URL**
6. Paste it in your `config.yml` or use it directly in commands

## Usage Examples

### Using Default Webhook
```
/webhook send Server is starting up!
/webhook send Welcome to our Minecraft server!
```

### Using Custom Webhook
```
/webhook send https://discord.com/api/webhooks/123/abc Important announcement!
```

### Admin Commands
```
/webhook reload
```

## Building from Source

### Prerequisites
- Java 17+
- Maven

### Build Steps
```bash
git clone https://github.com/mukulx/discord-webhook-plugin.git
cd discord-webhook-plugin
mvn clean package
```

The compiled JAR will be in `target/DiscordWebhook-1.0.0.jar`

## Project Structure
```
discord-webhook-plugin/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── discord/
        │       └── hook/
        │           └── discordWebhook/
        │               ├── DiscordWebhook.java
        │               ├── WebhookCommand.java
        │               └── WebhookSender.java
        └── resources/
            ├── config.yml
            └── plugin.yml
```

## Discord Message Format

Messages appear in Discord as clean text without command clutter:

**Before:**
```
Captain Hook APP - Today at 6:36 PM
mukul - yo/webhook send https://discord.com/api/webhooks/...
```

**After:**
```
Minecraft Server - Today at 6:36 PM
Hello everyone!
```

## Troubleshooting

### Common Issues

**"No default webhook configured" error:**
- Make sure you've set the `default-webhook` in your config.yml
- Use `/webhook reload` after editing the config

**"Invalid Discord webhook URL" error:**
- Ensure your webhook URL starts with `https://discord.com/api/webhooks/` or `https://discordapp.com/api/webhooks/`
- Check that your webhook URL is complete and valid

**Messages not appearing in Discord:**
- Verify your webhook URL is correct
- Check that the Discord channel still exists
- Ensure the webhook hasn't been deleted

**Permission errors:**
- Make sure players have the `discordwebhook.use` permission
- Admins need `discordwebhook.admin` for reload commands

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

If you encounter any issues or have questions:

- Open an [Issue](https://github.com/mukulx/discord-webhook-plugin/issues) on GitHub
- Check the [Wiki](https://github.com/mukulx/discord-webhook-plugin/wiki) for additional documentation

## Changelog

### v1.0.0
- Initial release
- Basic webhook functionality
- Configuration system
- Permission-based access control
- Async message sending

---

**Made with ❤️ for the Minecraft community**
