package io.patrickmorton.tamer.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.LexEvent
import com.google.inject.Guice
import io.patrickmorton.tamer.configuration.Conversation
import io.patrickmorton.tamer.configuration.TamerModule
import io.patrickmorton.tamer.dto.*
import io.patrickmorton.tamer.services.AdoptionService
import org.apache.log4j.Logger

class AdoptPetHandler : RequestHandler<LexEvent, LexResponse> {

  private val adoptionService: AdoptionService

  constructor() {
    val injector = Guice.createInjector(TamerModule())
    adoptionService = injector.getInstance(AdoptionService::class.java)
  }

  override fun handleRequest(input: LexEvent, context: Context): LexResponse {

    return when (true) {
      isTooManyPets(input) -> {
        LexResponse().withDialogAction(
            CloseAction()
                .withFulfillmentState(FulfillmentState.FAILED)
                .withPlaintextMessage(Conversation.TOO_MANY_PETS.getRandom(null)))
      }
      isGenderNeeded(input) -> {
        LexResponse().withDialogAction(
            ElicitSlotAction()
                .withPlaintextMessage(Conversation.GENDER_QUESTION.getRandom(
                    mapOf("{petType}" to getPetType(input))))
                .withIntentName(input.currentIntent.name)
                .withSlots(input.currentIntent.slots)
                .withSlotToElicit("gender")

        )
      }
      isNameNeeded(input) -> {
        LexResponse().withDialogAction(
            ElicitSlotAction()
                .withPlaintextMessage(Conversation.NAME_QUESTION.getRandom(
                    mapOf("{pronoun}" to getPronoun(input))))
                .withIntentName(input.currentIntent.name)
                .withSlots(input.currentIntent.slots)
                .withSlotToElicit("name")
        )
      }
      isPetAlreadyHaveName(input) -> {
        LexResponse().withDialogAction(
            ElicitSlotAction()
                .withPlaintextMessage(Conversation.NAME_ALREADY_TAKEN.getRandom(
                    mapOf("{name}" to getName(input))))
                .withIntentName(input.currentIntent.name)
                .withSlots(input.currentIntent.slots)
                .withSlotToElicit("name")
        )
      }
      isConfirmationMessage(input) -> {
        when (getConfirmationMessage(input)) {
          "Denied" -> {
            LexResponse().withDialogAction(
                CloseAction()
                    .withFulfillmentState(FulfillmentState.FAILED)
                    .withPlaintextMessage(Conversation.ADOPTION_CANCELLED.getRandom(null)))
          }
          "Confirmed" -> {
            adoptionService.addPet(input.userId,
                (getName(input) ?: ""),
                (getPetType(input) ?: ""))

            LexResponse().withDialogAction(
                CloseAction()
                    .withFulfillmentState(FulfillmentState.FULFILLED)
                    .withPlaintextMessage(Conversation.ADOPTION_FINALIZED.getRandom(
                        mapOf("{name}" to getName(input)))))
          }
          else -> {
            LexResponse().withDialogAction(
                CloseAction()
                    .withFulfillmentState(FulfillmentState.FAILED)
                    .withPlaintextMessage(Conversation.GENERIC_ERROR.getRandom(null)))
          }
        }
      }
      else -> {
        LexResponse().withDialogAction(
            ConfirmIntentAction()
                .withPlaintextMessage(Conversation.ADOPTION_CONFIRMATION.getRandom(
                    mapOf("{gender}" to getGender(input),
                        "{petType}" to getPetType(input),
                        "{name}" to getName(input))))
                .withIntentName(input.currentIntent.name)
                .withSlots(input.currentIntent.slots)
        )
      }
    }
  }

  private fun getGender(input: LexEvent): String? {
    return input.currentIntent.slots["gender"]
  }

  private fun getName(input: LexEvent): String? {
    return input.currentIntent.slots["name"]
  }

  private fun getPetType(input: LexEvent): String? {
    return input.currentIntent.slots["animalType"]
  }

  private fun getPetCount(input: LexEvent): Int {
    return adoptionService.getPetCount(input.userId)
  }

  private fun getConfirmationMessage(input: LexEvent): String {
    return input.currentIntent.confirmationStatus
  }

  private fun isPetAlreadyHaveName(input: LexEvent): Boolean {
    return adoptionService.getPetNames(input.userId)?.contains(input.currentIntent.slots["name"]) == true
  }

  private fun isTooManyPets(input: LexEvent): Boolean {
    return getPetCount(input) >= 3
  }

  private fun isGenderNeeded(input: LexEvent): Boolean {
    return getGender(input) == null
  }

  private fun isNameNeeded(input: LexEvent): Boolean {
    return getName(input) == null
  }

  private fun isConfirmationMessage(input: LexEvent): Boolean {
    return input.currentIntent.confirmationStatus != "None"
  }

  private fun getPronoun(input: LexEvent): String {
    return when (getGender(input)) {
      "Male" -> "him"
      else -> "her"
    }
  }

  companion object {
    private val LOG = Logger.getLogger(AdoptPetHandler::class.java)
  }
}