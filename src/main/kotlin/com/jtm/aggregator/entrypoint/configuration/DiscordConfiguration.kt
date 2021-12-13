package com.jtm.aggregator.entrypoint.configuration

import com.jtm.aggregator.entrypoint.commands.PingCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DiscordConfiguration {

    @Value("\${discord.token:token}")
    lateinit var token: String

    @Bean
    open fun discordBot(): JDA {
        val builder = JDABuilder.createDefault(token)
        builder.addEventListeners(PingCommand())
        builder.setActivity(Activity.watching("JTM Network"))
        val jda = builder.build()
        jda.upsertCommand("ping", "Calculate ping of the bot").queue()
        return jda
    }
}