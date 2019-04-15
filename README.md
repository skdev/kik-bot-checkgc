# kik-bot-checkgc
CheckGC is a bot written for the Kik messenger application. It allows group chats to see active and inactive members so that they know who is currently active. The bot is a great tool for analytics.

[https://www.kik.com/bots/checkgc/](https://www.kik.com/bots/checkgc/)

## Getting Started

### Prerequisites
1. CheckGC uses [Gradle](https://gradle.org) so this must be installed on your machine.
2. A bot must be created through Kik's website to hook this project [https://dev.kik.com](https://dev.kik.com)
3. Java 8 or higher
4. Port 80 open (this can be configured in config.json)
5. Access to a MySQL database

### Installing
1. Run `gradle build` in the kik-bot-checkgc directory to download dependencies and build the project.
2. Open the `config.json` and set the name to your bots name and key to the API key generated through the Kik developer dashboard.
3. Copy the `hibernate.cfg.xml` file into build/classes/java/main directory. Gradle can sometimes be a little difficult with file locations.
4. Update the `hibernate.cfg.xml` with details to your database.
5. Run the `checkgc.sql` on your database to create tables required for the bot.

CheckGC should now be ready and setup. You can run the project using the `gradle run` command.

## Contributing
We are happy to have contributions whether it is for small bug fixes or new pieces of major functionality. To contribute changes, you should first fork the upstream repository to your own GitHub account. You can then add a new remove for upstream and rebase any changes to
make keep up to date with upstream.

`git remote add upstream https://github.com/skdev/kik-bot-checkgc.git`

The style guides the project uses is based on the [Google style guide](https://google.github.io/styleguide/javaguide.html)

## Authors
**Suraj Kumar**

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
