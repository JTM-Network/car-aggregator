package com.jtm.aggregator.entrypoint.commands

import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StatsCommand @Autowired constructor(private val statsRepository: OperationStatsRepository): ListenerAdapter() {

    private val logger = LoggerFactory.getLogger(StatsCommand::class.java)

    override fun onSlashCommand(event: SlashCommandEvent) {
        if (event.name != "stats") return

        try {
            val builder = StringBuilder()
            statsRepository.findAll()
                    .doOnNext { builder.append(it.toString()).append("\n") }
                    .blockLast()

            event.reply(builder.toString()).setEphemeral(true)
                    .queue()
        } catch (ex: Exception) {
            logger.info("Exception: ${ex.message}")
        }
    }
}