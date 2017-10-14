package io.patrickmorton.tamer.dao

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.util.*

@DynamoDBTable(tableName = "Pet")
class PetItem {
  @DynamoDBHashKey
  var petId: String = ""
  @DynamoDBAttribute
  var name: String = ""
  @DynamoDBAttribute
  var species: String = ""
  @DynamoDBAttribute
  var experience: Double = 0.0
  @DynamoDBAttribute
  var adoptionDate: Date = Date()
  @DynamoDBAttribute
  var retirementDate: Date? = null
  @DynamoDBAttribute
  var foods: Set<FoodType>? = null
  @DynamoDBAttribute
  var hunger: Int = 0

  fun withId(id: String): PetItem {
    this.petId = id
    return this
  }

  fun withName(name: String): PetItem {
    this.name = name
    return this
  }

  fun withSpecies(species: String): PetItem {
    this.species = species
    return this
  }

  fun withExperience(experience: Double): PetItem {
    this.experience = experience
    return this
  }

  fun withAdoptionDate(adoptionDate: Date): PetItem {
    this.adoptionDate = adoptionDate
    return this
  }

  fun withRetirementDate(retirementDate: Date): PetItem {
    this.retirementDate = retirementDate
    return this
  }

  fun withHunger(hunger: Int): PetItem {
    this.hunger = hunger
    return this
  }
}