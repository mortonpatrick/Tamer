package io.patrickmorton.tamer.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.LexEvent
import com.google.inject.Guice
import io.patrickmorton.tamer.configuration.Conversation
import io.patrickmorton.tamer.configuration.TamerModule
import io.patrickmorton.tamer.dto.CloseAction
import io.patrickmorton.tamer.dto.ConfirmIntentAction
import io.patrickmorton.tamer.dto.FulfillmentState
import io.patrickmorton.tamer.dto.LexResponse
import io.patrickmorton.tamer.services.RetirementService

class RetirementHandler : RequestHandler<LexEvent, LexResponse> {

  private val retirementService: RetirementService

  constructor() {
    val injector = Guice.createInjector(TamerModule())
    retirementService = injector.getInstance(RetirementService::class.java)
  }

  override fun handleRequest(input: LexEvent, context: Context?): LexResponse {
    return when (true) {
      isConfirmationMessage(input) -> {
        when (getConfirmationMessage(input)) {
          "Denied" -> {
            LexResponse().withDialogAction(
                CloseAction()
                    .withFulfillmentState(FulfillmentState.FAILED)
                    .withPlaintextMessage(Conversation.RETIREMENT_CANCELLED.getRandom(
                        mapOf("{name}" to getName(input))))
            )
          }
          "Confirmed" -> {
            removePet(input)
            LexResponse().withDialogAction(
                CloseAction()
                    .withFulfillmentState(FulfillmentState.FULFILLED)
                    .withPlaintextMessage(Conversation.RETIREMENT_FINALIZED.getRandom(
                        mapOf("{name}" to getName(input))
                    ))
            )
          }
          else -> {
            LexResponse().withDialogAction(
                CloseAction()
                    .withFulfillmentState(FulfillmentState.FAILED)
                    .withPlaintextMessage(Conversation.GENERIC_ERROR.getRandom(null)))
          }
        }
      }
      doesPetExist(input) -> {
        LexResponse().withDialogAction(
            ConfirmIntentAction()
                .withPlaintextMessage(Conversation.RETIREMENT_CONFIRMATION.getRandom(
                    mapOf("{name}" to getName(input))))
                .withIntentName(input.currentIntent.name)
                .withSlots(input.currentIntent.slots)
        )
      }
      else -> {
        LexResponse().withDialogAction(
            CloseAction()
                .withFulfillmentState(FulfillmentState.FAILED)
                .withPlaintextMessage(Conversation.RETIREMENT_PET_DOES_NOT_EXIST.getRandom(
                    mapOf("{name}" to getName(input)))))
      }
    }
  }

  private fun doesPetExist(input: LexEvent): Boolean {
    val petName = input.currentIntent.slots["petName"]
    return when (petName != null) {
      true -> {
        retirementService.doesPetExist(input.userId, petName!!)
      }
      else -> false
    }
  }

  private fun removePet(input: LexEvent) {
    val petName = input.currentIntent.slots["petName"]
    if (petName != null) {
      retirementService.removePet(input.userId, petName)
    }
  }

  private fun isConfirmationMessage(input: LexEvent): Boolean {
    return input.currentIntent.confirmationStatus != "None"
  }

  private fun getConfirmationMessage(input: LexEvent): String {
    return input.currentIntent.confirmationStatus
  }

  private fun getName(input: LexEvent): String? {
    return input.currentIntent.slots["name"]
  }
}