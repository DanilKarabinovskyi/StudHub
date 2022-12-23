package danyil.karabinovskyi.studenthub.core.sockets

import danyil.karabinovskyi.studenthub.common.model.Result

public sealed class DisconnectCause {

    public object NetworkNotAvailable : DisconnectCause()

    public class Error(public val error: String?) : DisconnectCause()

    public class UnrecoverableError(public val error: String?) : DisconnectCause()

    public object ConnectionReleased : DisconnectCause()
}
