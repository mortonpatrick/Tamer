package io.patrickmorton.tamer.services

import com.google.inject.Inject
import io.patrickmorton.tamer.repositories.PetRepository
import io.patrickmorton.tamer.repositories.TrainerRepository
import java.util.*

interface RetirementService {
  fun doesPetExist(trainerName: String, petName: String): Boolean
  fun removePet(trainerName: String, petName: String)
}

class RetirementServiceImpl : RetirementService {

  private val trainerRepo: TrainerRepository
  private val petRepo: PetRepository

  @Inject
  constructor(trainerRepo: TrainerRepository, petRepo: PetRepository) {
    this.trainerRepo = trainerRepo
    this.petRepo = petRepo
  }

  override fun doesPetExist(trainerName: String, petName: String): Boolean {
    val trainer = trainerRepo.get(trainerName)
    return trainer?.petNames?.contains(petName) ?: false
  }

  override fun removePet(trainerName: String, petName: String) {
    val trainer = trainerRepo.get(trainerName)
    if (trainer != null) {
      trainer.petNames = trainer.petNames?.minus(petName)
      if (trainer.petNames?.size == 0) {
        trainer.petNames = null
      }
      trainerRepo.save(trainer)
      val pet = petRepo.get(trainerName, petName)
      if (pet != null) {
        pet.withRetirementDate(Date())
        petRepo.save(pet)
      }
    }
  }
}