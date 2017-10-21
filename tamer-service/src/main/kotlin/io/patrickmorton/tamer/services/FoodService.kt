package io.patrickmorton.tamer.services

import com.google.inject.Inject
import io.patrickmorton.tamer.repositories.PetRepository
import io.patrickmorton.tamer.repositories.TrainerRepository
import java.security.SecureRandom

interface FoodService {

}

class FoodServiceImpl: FoodService {
  private val trainerRepo: TrainerRepository
  private val petRepo: PetRepository
  private val random: SecureRandom

  @Inject
  constructor(trainerRepo: TrainerRepository, petRepo: PetRepository) {
    this.trainerRepo = trainerRepo
    this.petRepo = petRepo
    this.random = SecureRandom()
  }

  fun feedPet(trainerName: String, petName: String) {
    
  }
}