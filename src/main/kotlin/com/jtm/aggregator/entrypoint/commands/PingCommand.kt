package com.jtm.aggregator.entrypoint.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.exceptions.ContextException
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.concurrent.RejectedExecutionException

class PingCommand: ListenerAdapter() {

    private val logger = LoggerFactory.getLogger(PingCommand::class.java)

    override fun onSlashCommand(event: SlashCommandEvent) {
        try {
            if (event.name != "ping") return  // make sure we handle the right command

            val time = System.currentTimeMillis()
            event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                    .flatMap { v -> event.hook.editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) } // then edit original
                    .queue()
        } catch (ex: RejectedExecutionException) {
            logger.info("Failed context: ${ex.message}")
        }
    }
}