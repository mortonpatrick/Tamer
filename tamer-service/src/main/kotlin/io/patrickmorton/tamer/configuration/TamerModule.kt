package io.patrickmorton.tamer.configuration

import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.google.inject.AbstractModule
import com.google.inject.Provides
import io.patrickmorton.tamer.repositories.PetRepository
import io.patrickmorton.tamer.repositories.PetRepositoryImpl
import io.patrickmorton.tamer.repositories.TrainerRepository
import io.patrickmorton.tamer.repositories.TrainerRepositoryImpl
import io.patrickmorton.tamer.services.*

class TamerModule : AbstractModule() {

  override fun configure() {
    bind(TrainerRepository::class.java).to(TrainerRepositoryImpl::class.java)
    bind(PetRepository::class.java).to(PetRepositoryImpl::class.java)
    bind(AdoptionService::class.java).to(AdoptionServiceImpl::class.java)
    bind(RetirementService::class.java).to(RetirementServiceImpl::class.java)
    bind(FoodService::class.java).to(FoodServiceImpl::class.java)
  }

  @Provides
  fun provideDynamoDbMapper(): DynamoDBMapper {
    val client = AmazonDynamoDBClient()
    client.setRegion(Region.getRegion(Regions.US_EAST_1))
    return DynamoDBMapper(client)
  }
}