package com.flxrs.dankchat.utils.extensions

import com.flxrs.dankchat.chat.ChatItem
import com.flxrs.dankchat.service.twitch.message.Message

fun List<ChatItem>.replaceWithTimeOuts(name: String): List<ChatItem> = apply {
    forEach { item ->
        if (item.message is Message.TwitchMessage && !item.message.isNotify
            && (name.isBlank() || item.message.name.equals(name, true))
        ) {
            item.message.timedOut = true
        }
    }
}

fun List<ChatItem>.replaceWithTimeOut(id: String): List<ChatItem> = apply {
    forEach {
        if (it.message is Message.TwitchMessage && it.message.id == id) {
            it.message.timedOut = true
            return@apply
        }
    }
}

fun List<ChatItem>.addAndLimit(item: ChatItem): MutableList<ChatItem> = toMutableList().apply {
    add(item)
    if (size > 500) removeAt(0)
}

fun List<ChatItem>.addAndLimit(
    collection: Collection<ChatItem>,
    checkForDuplications: Boolean = false
): MutableList<ChatItem> = toMutableList().apply {
    for (item in collection) {
        if (!checkForDuplications || !this.any { it.message.id == item.message.id })
            add(item)
        if (size > 500) removeAt(0)
    }
}