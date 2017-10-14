package io.patrickmorton.tamer.dao

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute

class FoodType {
  @DynamoDBAttribute
  var name: String = ""

  @DynamoDBAttribute
  var affinity: Int = 0
}