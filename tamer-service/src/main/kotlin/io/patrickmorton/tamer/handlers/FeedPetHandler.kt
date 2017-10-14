package io.patrickmorton.tamer.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import io.patrickmorton.tamer.dto.CloseAction
import io.patrickmorton.tamer.dto.FulfillmentState
import io.patrickmorton.tamer.dto.LexRequest
import io.patrickmorton.tamer.dto.LexResponse
import org.apache.log4j.Logger

class FeedPetHandler : RequestHandler<LexRequest, LexResponse> {

  override fun handleRequest(input: LexRequest, context: Context): LexResponse {

    return LexResponse().withDialogAction(



        CloseAction()
            .withFulfillmentState(FulfillmentState.FULFILLED)
            .withPlaintextMessage("It works with {petName}!"))
  }

  companion object {
    private val LOG = Logger.getLogger(FeedPetHandler::class.java)
  }
}