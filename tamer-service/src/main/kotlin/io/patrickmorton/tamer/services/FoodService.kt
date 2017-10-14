package io.patrickmorton.tamer.services

import com.google.inject.Inject
import io.patrickmorton.tamer.repositories.PetRepository
import io.patrickmorton.tamer.repositories.TrainerRepository
import java.security.SecureRandom

interface FoodService {
  fun doesPetExist(trainerName: String, petName: String): Boolean
  fun feedPet(trainerName: String, petName: String, food: String)
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

  override fun doesPetExist(trainerName: String, petName: String): Boolean {
    val trainer = trainerRepo.get(trainerName)
    return trainer?.petNames?.contains(petName) ?: false
  }

  override fun feedPet(trainerName: String, petName: String, food: String) {
    var petItem = petRepo.get(trainerName, petName)
    if (petItem.foods == null) {
      petItem.foods = HashSet()
    }
    if (petItem.foods?.count { element -> element.name == food } ?: 0 > 0) {
      val foodItem = petItem.foods?.associateBy { element -> element.name }!![food]
      
    }
  }

  private fun getRandom(max: Int): Int {
    return random.nextInt(max)
  }
}