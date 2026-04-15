# percyBot, a Fun/Music Bot Written in JDA.
Currently all music features remain to be implemented. The bot currently consists of a command framework to add slash commands and some wacky stuff in the chat.
This bot is meant for personal use among friends. It has/will have features which would disrupt the normal flow of a moderated discord server and therefore should not be used on large servers that have many people in them. The bot comes with no warranty and use it at your own risk.

## Installation:
Currently no releases are provided. Therefore you will need to compile it yourself.
Maven is the ideal way to handle it and what JDA wants. Java 17 is the normal target but anything later *should* work. (not tested.)
You can also try importing the project into eclipse with the provided .classpath I left in there for myself.
1. After you have your jarfile make sure you have a data folder with the jarfile, and a config.env file containing the needed tokens. 
2. Currently you only need a discord bot token. However soon a spotify and youtube token will be required for music features.
3. The bot only works if you have it in less than 100 guilds. Ideally you should only run it in a couple guilds.

## Feature Roadmap
- Nicknaming users.
- Fetching youtube videos and spotify songs and playing their audio in discord channels.
- Config toggle for "malicious"/annoying features to turn the bot into just a music bot.
- Custom command responses.
- "Malicious" role perms. (The bot is currently set to have admin, therefore I thought it would be funny if you posted a gif that asks for mod and the bot gives it to you.)