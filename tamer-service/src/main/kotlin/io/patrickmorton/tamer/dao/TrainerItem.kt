package io.patrickmorton.tamer.dao

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "Trainer")
class TrainerItem {
  @DynamoDBHashKey
  var slackName: String = ""

  @DynamoDBAttribute
  var nickname: String = ""

  @DynamoDBAttribute
  var experience: Double = 0.0

  @DynamoDBAttribute
  var petNames: Set<String>? = HashSet()

  fun withSlackName(name: String): TrainerItem {
    this.slackName = name
    return this
  }

  fun withNickname(name: String): TrainerItem {
    this.nickname = name
    return this
  }

  fun withExperience(experience: Double): TrainerItem {
    this.experience = experience
    return this
  }

  fun withPetNames(petNames: Set<String>): TrainerItem {
    this.petNames = petNames
    return this
  }

  fun withNewPet(petName: String): TrainerItem {
    this.petNames = this.petNames?.plus(petName)
    return this
  }
}
