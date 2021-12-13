package com.jtm.aggregator.entrypoint.configuration

import com.jtm.aggregator.entrypoint.commands.PingCommand
import com.jtm.aggregator.entrypoint.commands.StatsCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DiscordConfiguration @Autowired constructor(val context: ApplicationContext) {

    @Value("\${discord.token:token}")
    lateinit var token: String

    @Bean
    open fun discordBot(): JDA {
        val builder = JDABuilder.createDefault(token)
        builder.addEventListeners(PingCommand())
        builder.addEventListeners(context.getBean(StatsCommand::class.java))
        builder.setActivity(Activity.watching("JTM Network"))
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB)
        val jda = builder.build()
        jda.upsertCommand("ping", "Calculate ping of the bot").queue()
        jda.upsertCommand("stats", "Give stats on operations being processed").queue()
        return jda
    }
}