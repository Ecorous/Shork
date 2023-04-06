package org.ecorous.shork

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.utils.env
import dev.kord.common.entity.Snowflake
import org.ecorous.shork.extensions.MainExtension

val MAIN_SERVER_ID = Snowflake(1033951784462462976)

private val TOKEN = env("TOKEN")   // Get the bot' token from the env vars or a .env file

suspend fun main() {
    val bot = ExtensibleBot(TOKEN) {
        
        extensions {
            add(::MainExtension)
        }
    }

    bot.start()
}
