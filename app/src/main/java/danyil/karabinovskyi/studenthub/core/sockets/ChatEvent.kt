package danyil.karabinovskyi.studenthub.core.sockets

import java.util.Date

public sealed class ChatEvent {
    public abstract val type: String
    public abstract val createdAt: Date
}