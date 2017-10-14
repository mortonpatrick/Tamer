package io.patrickmorton.tamer.dto

class LexResponse {
  var sessionAttributes: Map<String, String>? = null
  var dialogAction: DialogAction = NullAction()

  fun withSessionAttributes(sessionAttributes: Map<String, String>): LexResponse {
    this.sessionAttributes = sessionAttributes
    return this
  }

  fun withDialogAction(dialogAction: DialogAction): LexResponse {
    this.dialogAction = dialogAction
    return this
  }

  fun withAttribute(attribute: Pair<String, String>): LexResponse {
    if (this.sessionAttributes == null) {
      this.sessionAttributes = HashMap()
    }
    this.sessionAttributes = this.sessionAttributes?.plus(attribute)
    return this
  }
}

interface DialogAction

class NullAction : DialogAction {}

enum class FulfillmentState(val text: String) {
  FULFILLED("Fulfilled"), FAILED("Failed");
}

class CloseAction : DialogAction {
  val type: String = "Close"
  var fulfillmentState: String = ""
  var message: Message? = null
  var responseCard: ResponseCard? = null

  fun withFulfillmentState(state: FulfillmentState): CloseAction {
    this.fulfillmentState = state.text
    return this
  }

  fun withMessage(message: Message): CloseAction {
    this.message = message
    return this
  }

  fun withPlaintextMessage(text: String): CloseAction {
    this.message = Message()
        .withContentType("PlainText")
        .withContent(text)
    return this
  }

  fun withResponseCard(responseCard: ResponseCard): CloseAction {
    this.responseCard = responseCard
    return this
  }
}

class ConfirmIntentAction : DialogAction {
  val type: String = "ConfirmIntent"
  var intentName: String = ""
  var message: Message? = null
  var responseCard: ResponseCard? = null
  var slots: Map<String, String> = HashMap<String, String>()

  fun withIntentName(intentName: String): ConfirmIntentAction {
    this.intentName = intentName
    return this
  }

  fun withMessage(message: Message): ConfirmIntentAction {
    this.message = message
    return this
  }

  fun withPlaintextMessage(text: String): ConfirmIntentAction {
    this.message = Message()
        .withContentType("PlainText")
        .withContent(text)
    return this
  }

  fun withResponseCard(responseCard: ResponseCard): ConfirmIntentAction {
    this.responseCard = responseCard
    return this
  }

  fun withSlots(slots: Map<String, String>): ConfirmIntentAction {
    this.slots = slots
    return this
  }
}

class ElicitSlotAction : DialogAction {
  val type: String = "ElicitSlot"
  var message: Message? = null
  var responseCard: ResponseCard? = null
  var slots: Map<String, String> = HashMap()
  var slotToElicit: String = ""
  var intentName: String = ""

  fun withMessage(message: Message): ElicitSlotAction {
    this.message = message
    return this
  }

  fun withPlaintextMessage(text: String): ElicitSlotAction {
    this.message = Message()
        .withContentType("PlainText")
        .withContent(text)
    return this
  }

  fun withResponseCard(responseCard: ResponseCard): ElicitSlotAction {
    this.responseCard = responseCard
    return this
  }

  fun withSlots(slots: Map<String, String>): ElicitSlotAction {
    this.slots = slots
    return this
  }

  fun withSlotToElicit(slotToElicit: String): ElicitSlotAction {
    this.slotToElicit = slotToElicit
    return this
  }

  fun withIntentName(intentName: String): ElicitSlotAction {
    this.intentName = intentName
    return this
  }
}

class Message {
  var contentType: String = "PlainText"
  var content: String = ""

  fun withContentType(contentType: String): Message {
    this.contentType = contentType
    return this
  }

  fun withContent(content: String): Message {
    this.content = content
    return this
  }
}

class ResponseCard {
  var version: Int = 1
  val contentType: String = "application/vnd.amazonaws.card.generic"
  var genericAttachments: Set<Attachment> = HashSet()

  fun withVersion(version: Int): ResponseCard {
    this.version = version
    return this
  }

  fun withAttachments(attachments: Set<Attachment>): ResponseCard {
    this.genericAttachments = attachments
    return this
  }
}

class Attachment {
  var title: String = ""
  var subtitle: String = ""
  var imageUrl: String = ""
  var attachmentLinkUrl: String = ""
  var buttons: Set<Button> = HashSet()

  fun withTitle(title: String): Attachment {
    this.title = title
    return this
  }

  fun withSubtitle(subtitle: String): Attachment {
    this.subtitle = subtitle
    return this
  }

  fun withImageUrl(imageUrl: String): Attachment {
    this.imageUrl = imageUrl
    return this
  }

  fun withAttachmentLinkUrl(attachmentLinkUrl: String): Attachment {
    this.attachmentLinkUrl = attachmentLinkUrl
    return this
  }

  fun withButtons(buttons: Set<Button>): Attachment {
    this.buttons = buttons
    return this
  }
}

class Button {
  var text: String = ""
  var value: String = ""

  fun withText(text: String): Button {
    this.text = text
    return this
  }

  fun withValue(value: String): Button {
    this.value = value
    return this
  }
}