package io.patrickmorton.tamer.configuration

enum class Conversation(val phrases: List<String>) {

  GENDER_QUESTION(listOf(
      "Would you prefer a boy or a girl?",
      "Are you interested in a male or female?",
      "I have both male and female {petType}s. Which do you prefer?"
  )),
  NAME_QUESTION(listOf(
      "What would you like to call {pronoun}?",
      "What are you going to name {pronoun}?"
  )),
  NAME_ALREADY_TAKEN(listOf(
      "You already have a pet named {name}. Pick another name.",
      "Don't tell me you forgot about {name}. Pick a different name.",
      "How many {name}s do you think you need? Pick a different name.",
      "You're confused enough without duplicate pet names. Pick another name."
  )),
  TOO_MANY_PETS(listOf(
      "You've got too many pets already. You'll have to retire an active pet before you can get a new pet.",
      "Come on! People are going to start calling you the cat lady if you get another pet. Retire one of your active pets first",
      "You can barely care for the pets you've got. You need to send one of your active pets to pasture first."
  )),
  ADOPTION_CANCELLED(listOf(
      "Sure. Let's start over.",
      "Fine by me. What now?",
      "It is a big choice. Maybe later."
  )),
  ADOPTION_FINALIZED(listOf(
      "Perfect! I'll move {name} to your training ground.",
      "{name} will be so excited.",
      "That's a great choice. I'll go get {name} ready."
  )),
  GENERIC_ERROR(listOf(
      "This is kind of embarrassing, but I have no idea what's going on. Maybe we can take it from the top.",
      "<Snort> Eh!? Sorry, I must've dozed off. Can you remind me what we were talking about?"
  )),
  ADOPTION_CONFIRMATION(listOf(
      "Before I put this in my logs, you want a {gender} {petType} named {name}?",
      "Just to double check, you're looking for a {gender} {petType} named {name}?",
      "Everything seems in order. I just need your confirmation that you're adopting a {gender} {petType} named {name}?"
  )),
  RETIREMENT_CONFIRMATION(listOf(
      "Are you sure you want to retire {name}? A retired pet cannot be returned to you.",
      "That's a shame. Are you sure you want to retire {name}?",
      "That's a shame. Are you sure you want to retire {name}?"
  )),
  RETIREMENT_CANCELLED(listOf(
      "Phew. That was close.",
      "I'm glad you're giving {name} another shot."
  )),
  RETIREMENT_FINALIZED(listOf(
      "I'll find a good home for {name}.",
      "I'm sure {name} will be happy in their new home."
  )),
  RETIREMENT_PET_DOES_NOT_EXIST(listOf(
      "You don't have a pet with that name.",
      "{name}? You don't have a pet named {name}."
  ));

  fun getRandom(replacements: Map<String, String?>?): String {
    var result = phrases[(Math.random() * phrases.size).toInt()]
    replacements?.forEach { key, value -> result = replace(result, key, value) }
    return result
  }

  private fun replace(base: String, key: String, value: String?): String {
    return base.replace(key, (value ?: ""), true)
  }
}