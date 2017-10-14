package io.patrickmorton.tamer.repositories

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.google.inject.Inject
import io.patrickmorton.tamer.dao.PetItem

interface PetRepository {
  fun get(trainerName: String, petName: String): PetItem
  fun save(pet: PetItem): PetItem
  fun delete(pet: PetItem)
}

class PetRepositoryImpl : PetRepository {

  val db: DynamoDBMapper

  @Inject
  constructor(db: DynamoDBMapper) {
    this.db = db;
  }

  override fun get(trainerName: String, petName: String): PetItem {
    return db.load(PetItem::class.java, trainerName + ":" + petName)
  }

  override fun save(pet: PetItem): PetItem {
    db.save(pet)
    return pet
  }

  override fun delete(pet: PetItem) {
    db.delete(pet)
  }
}