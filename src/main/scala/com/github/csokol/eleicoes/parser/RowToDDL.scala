package com.github.csokol.eleicoes.parser

import org.apache.commons.csv.CSVRecord

object RowToDDL {

  def valueOf(dataType: String, value: String): Any = {
    if (dataType == "BIGINT") {
      if (value.contains(",")) {
        value.substring(0, value.length - 3).toLong
      } else {
        value.toLong
      }
    } else {
      value
    }
  }

  def apply(tableSchema: TableSchema, csvRecord: CSVRecord): RowToDDL = {
    val mappings = tableSchema.mappings.map { case (column, dataType) =>
      column -> valueOf(dataType, csvRecord.get(column))
    }
    new RowToDDL(tableSchema, mappings)
  }

}

class RowToDDL(val tableSchema: TableSchema, val mappings: Map[String, Any]) {
  def toDdl = {
    val columnNames = mappings.keys.toList.sorted.mkString(", ")
    val values = mappings.keys.toList.sorted.map { columnName =>
      asSqlValue(mappings(columnName))
    }.mkString(", ")

    s"INSERT INTO candidatos(${columnNames}) VALUES (${values})"
  }

  private def asSqlValue(value: Any) = {
    value match {
      case x: String => s"'${x}'"
      case x: Any => x.toString
    }
  }

}
