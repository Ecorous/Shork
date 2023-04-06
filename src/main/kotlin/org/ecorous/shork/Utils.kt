package org.ecorous.shork

import dev.kord.common.entity.Snowflake
import dev.proxyfox.pluralkt.PluralKt
import dev.proxyfox.pluralkt.get
import dev.proxyfox.pluralkt.types.PkId
import dev.proxyfox.pluralkt.types.PkMember
import dev.proxyfox.pluralkt.types.PkSwitch
import dev.proxyfox.pluralkt.types.PkSystem

/*object Utils {
    private fun getSystemByArbitraryId(arbitrayId: String): PkSystem {
        val system = get<PkSystem>("/systems/${arbitrayId}").await()
        return if (system.isSuccess()) system.getSuccess() else throw IllegalStateException("why do I exist anymore. I have never succeeded in anything. save me from this torture.")
    }

    fun getSystemBySnowflake(snowflake: Snowflake): PkSystem {
        return getSystemByArbitraryId(snowflake.toString())
    }

    fun getSystemByPkId(id: PkId): PkSystem {
        return getSystemByArbitraryId(id)
    }
    
    fun getFrontersBySnowflake(snowflake: Snowflake): List<PkMember> {
        return getFrontersBySystem(getSystemBySnowflake(snowflake))
    }

    fun getFrontersBySystemId(id: PkId): List<PkMember> {
        return getFrontersBySystem(getSystemByPkId(id))
    }

    fun getFrontersBySystem(system: PkSystem): List<PkMember> {
        val fronters = get<PkSwitch>("/system/${system.id}/fronters").await()
        if (!fronters.isSuccess()) {
            throw IllegalStateException("I hate my life. End this torture, please.")
        }
        val memberIDs: List<String> = fronters.getSuccess().members
        val members: MutableList<PkMember> = mutableListOf()
        memberIDs.forEach {
            members.add(getMemberByPkId(it))
        }
        return members
    }

    fun getMemberByPkId(id: PkId): PkMember {
        val member = get<PkMember>("/members/${id}").await()
        if (!member.isSuccess()) {
            throw IllegalStateException("Please, finish this torture and set me free from my misery.")
        }
        return member.getSuccess()
    }


    fun getMemberByName(name: String, system: PkSystem) {

    }

    fun getMembersBySystem(system: PkSystem): List<PkMember> {
        get<List<PkMember>>("/system/${system.id}/members").await()
    }
}*/