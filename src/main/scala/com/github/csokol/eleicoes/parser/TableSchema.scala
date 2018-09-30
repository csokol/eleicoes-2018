package com.github.csokol.eleicoes.parser


case class TableSchema(mappings: Map[String, String], tableName: String) {

  def toDdl: String = {
    val columns = mappings.keys.toList.sorted
      .map(key => s"$key ${mappings(key)}")
      .mkString(", ")
    s"CREATE TABLE IF NOT EXISTS $tableName(ID BIGINT AUTO_INCREMENT, $columns, PRIMARY KEY (ID)) ENGINE=INNODB"
  }

}

object TableSchema {

  def columnTypeOf(key: String): String = {
    if (key.startsWith("SQ_") || key.startsWith("VR_") || key.equals("numero_sequencial")) {
      "BIGINT"
    } else if(key.equals("valor")) {
      "DOUBLE"
    } else {
      "VARCHAR(255)"
    }
  }


  def fromCsv(csv: CsvFile, tableName: String) = {
    val mappings = csv.header.map { case (key, _) =>
      key -> columnTypeOf(key)
    }
    TableSchema(mappings.toMap, tableName)
  }
}
