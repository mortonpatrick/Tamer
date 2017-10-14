package io.patrickmorton.tamer.services

import com.google.inject.Inject
import io.patrickmorton.tamer.dao.PetItem
import io.patrickmorton.tamer.dao.TrainerItem
import io.patrickmorton.tamer.repositories.PetRepository
import io.patrickmorton.tamer.repositories.TrainerRepository
import java.security.SecureRandom
import java.util.*
import kotlin.collections.HashSet

interface AdoptionService {
  fun getPetCount(trainerName: String): Int
  fun getPetNames(trainerName: String): Set<String>?
  fun addPet(trainerName: String, petName: String, species: String)
}

class AdoptionServiceImpl : AdoptionService {
  private val trainerRepo: TrainerRepository
  private val petRepo: PetRepository
  private val random: SecureRandom

  @Inject
  constructor(trainerRepo: TrainerRepository, petRepo: PetRepository) {
    this.trainerRepo = trainerRepo
    this.petRepo = petRepo
    this.random = SecureRandom()
  }

  override fun getPetCount(trainerName: String): Int {
    return trainerRepo.get(trainerName)?.petNames?.size ?: 0
  }

  override fun getPetNames(trainerName: String): Set<String>? {
    return trainerRepo.get(trainerName)?.petNames
  }

  override fun addPet(trainerName: String, petName: String, species: String) {
    var trainer = trainerRepo.get(trainerName)
    if (trainer == null) {
      trainerRepo.save(TrainerItem()
          .withSlackName(trainerName)
          .withExperience(0.0)
          .withNewPet(petName))
    } else {
      if (trainer.petNames == null) {
        trainer.petNames = HashSet()
      }
      trainer.petNames = trainer.petNames?.plus(petName)
      trainerRepo.save(trainer)
    }
    petRepo.save(PetItem()
        .withId(trainerName + ":" + petName)
        .withName(petName)
        .withSpecies(species)
        .withExperience(0.0)
        .withAdoptionDate(Date())
        .withHunger(getRandom(100)))
  }

  private fun getRandom(max: Int): Int {
    return random.nextInt(max)
  }
}