package io.patrickmorton.tamer.dto

class LexRequest {
  val currentIntent: Intent = Intent()
  val bot: Bot = Bot()
  val userId: String = ""
  val inputTranscript: String = ""
  val invocationSource: String = ""
  val outputDialogMode: String = ""
  val messageVersion: String = ""
  val sessionAttributes: Map<String, String> = HashMap<String, String>()
}

class Intent {
  val name: String = ""
  val slots: Map<String, String> = HashMap<String, String>()
  val slotDetails: SlotDetails? = null
  val confirmationStatus: String = ""
}

class Bot {
  val name: String = ""
  val alias: String = ""
  val version: String = ""
}

class SlotDetails {
  val details: Map<String, SlotDetail> = HashMap<String, SlotDetail>()
}

class SlotDetail {
  val originalValue: String = ""
  val resolutions: List<Resolution> = ArrayList<Resolution>()
}

class Resolution {
  val value: String = ""
}