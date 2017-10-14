package io.patrickmorton.tamer.repositories

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.google.inject.Inject
import io.patrickmorton.tamer.dao.TrainerItem

interface TrainerRepository {
  fun get(slackName: String): TrainerItem?
  fun save(trainer: TrainerItem): TrainerItem
  fun delete(trainer: TrainerItem)
}

class TrainerRepositoryImpl : TrainerRepository {

  val db: DynamoDBMapper

  @Inject
  constructor(db: DynamoDBMapper) {
    this.db = db
  }

  override fun get(slackName: String): TrainerItem? {
    return db.load(TrainerItem::class.java, slackName)
  }

  override fun save(trainer: TrainerItem): TrainerItem {
    db.save(trainer)
    return trainer
  }

  override fun delete(trainer: TrainerItem) {
    db.delete(trainer)
  }
}