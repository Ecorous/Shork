package org.ecorous.shork.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingBoolean
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.user
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ephemeralSlashCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.types.respondPublic
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.channel.createMessage
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.create.embed
import dev.kord.rest.json.request.EmbedRequest
import dev.proxyfox.pluralkt.types.*
import org.ecorous.shork.Emojis
import org.ecorous.shork.MAIN_SERVER_ID

@OptIn(KordPreview::class)
class MainExtension : Extension() {
    override val name = "test"
    override suspend fun setup() {
        publicSlashCommand(::SlapSlashArgs) {
            name = "slap"
            description = "Slap another user"

            guild(MAIN_SERVER_ID)

            action {
                // Because of the DslMarker annotation KordEx uses, we need to grab Kord explicitly
                val kord = this@MainExtension.kord

                // Don't slap ourselves on request, slap the requester!
                val realTarget = if (arguments.target.id == kord.selfId) {
                    member
                } else {
                    arguments.target
                }

                respond {
                    embed {
                        title = "${user.mention} slaps ${realTarget?.mention} with their ${arguments.weapon}"
                        image = Emojis.Blobfox.PAT
                    }
                }
            }
        }
        ephemeralSlashCommand(::CutieArgs) {
            name = "cute"
            description = "someone here is a cutie"
            guild(MAIN_SERVER_ID)
            action {
                val cutieEmbed: EmbedBuilder.() -> Unit = {
                    title = "Aww, you absolute cutie. You're cuter though."
                    description = if (arguments.cutie.id == Snowflake(/* applejuice id */ 316638846207131648)) "YOU ARE AN ABSOLUTE CUTIE AND YOU CANNOT DENY IT. WHOEVER SENT THIS MESSAGE, THEY ARE VERY VERY VERY CORRECT. YOU CANNOT DENY YOUR CUTENESS.\n\n*This is an applejuice-exclusive provided to you by the Ecorous System, now available at your local Shork bot*\n" else ""
                    image = Emojis.Blobfox.SNUGGLE
                }
                var responded = false
                val kord = this@MainExtension.kord
                if (arguments.cutie.id == kord.selfId) {
                    if (arguments.dm) {
                        val dmChannel = member?.getDmChannelOrNull();
                        if (dmChannel == null) {
                            if (!responded) {
                                respond {
                                    content =
                                        "Sorry, there was an error while processing this command. Please forward the following to <@604653220341743618>:\n```\nmember.getDmChannelOrNull() was null. MainExtension.kt:50"
                                    responded = true
                                }
                            }
                        } else {
                            dmChannel.createEmbed(cutieEmbed)
                        }
                    } else {
                        if (!responded) respondPublic {
                            channel.createMessage {
                                content = member?.mention
                                embed(cutieEmbed)
                            }
                            responded = true
                        }
                    }
                } else {
                    val cutieEmbed: EmbedBuilder.() -> Unit = {
                        title = "Someone thinks you are a cutie."
                        description = "${if (arguments.cutie.id == Snowflake(/* applejuice id */ 316638846207131648)) "AND THEY'RE CORRECT. YOU ARE AN ABSOLUTE CUTIE AND YOU CANNOT DENY IT. WHOEVER SENT THIS MESSAGE, THEY ARE VERY VERY VERY CORRECT. YOU CANNOT DENY YOUR CUTENESS.\n\n*This is an applejuice-exclusive provided to you by the Ecorous System, now available at your local Shork bot*\n" else ""}*psst, it's ||${member?.mention}||*"
                        image = Emojis.Blobfox.SNUGGLE
                    }

                    if (arguments.dm) {
                        val dmChannel = arguments.cutie.getDmChannelOrNull()
                        if (dmChannel == null) {
                            if (!responded) {
                                respond {
                                    content =
                                        "Sorry, there was an error while processing this command. Please forward the following to <@604653220341743618>:\n```\nmember.getDmChannelOrNull() was null. MainExtension.kt:86\n```"
                                    responded = true
                                }
                            }
                        } else {
                            dmChannel.createEmbed(cutieEmbed)
                        }
                    } else {
                        channel.createMessage {
                            content = arguments.cutie.mention
                            embed(cutieEmbed)
                        }
                    }
                }
                if (!responded) {
                    respond {
                        content = "The cutie has been cutiefied."
                    }
                }
            }
        }
    }

    inner class CutieArgs : Arguments() {
        val cutie by user {
            name = "cutie"
            description = "the cutie"
        }
        val dm by defaultingBoolean {
            name = "dm"
            defaultValue = false
            description = "whether to cutiefy in DMs or in this channel"
        }
    }

    inner class SlapSlashArgs : Arguments() {
        val target by user {
            name = "target"
            description = "Person you want to slap"
        }

        // Coalesced strings are not currently supported by slash commands
        val weapon by defaultingString {
            name = "weapon"

            defaultValue = "large, smelly trout"
            description = "What you want to slap with"
        }
    }
}
