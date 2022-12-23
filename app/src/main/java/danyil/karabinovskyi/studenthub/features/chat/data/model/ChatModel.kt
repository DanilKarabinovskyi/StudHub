package danyil.karabinovskyi.studenthub.features.chat.data.model

class ChatDefaultModel(
    val data: Any,
    val type: String
)

class ChatModel(
    val data: List<ChatData>,
    val type: String
)

class UpdatedChat(
    val data: ChatData,
    val type: String
)

class MessagesDefaultModel(
    val data: Any,
    val type: String
)

class MessagesModel(
    val data: List<MessageResponse>,
    val type: String
)

class AddMessageModel(
    val data: NewMassageResponse,
    val type: String
)

class NewMassageResponse(
    val message:MessageResponse,
    val chatId : Int
)
