package com.jtm.aggregator.entrypoint.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class PingCommand: ListenerAdapter() {

    override fun onSlashCommand(event: SlashCommandEvent) {
        if (event.name != "ping") return  // make sure we handle the right command

        val time = System.currentTimeMillis()
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                .flatMap { v -> event.hook.editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) } // then edit original
                .queue()
    }
}