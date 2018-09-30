package com.github.csokol.eleicoes

import java.io.{File, FileInputStream}
import java.sql.{Connection, DriverManager}

import com.github.csokol.eleicoes.infra.DB
import com.github.csokol.eleicoes.parser.{CsvFile, RowToDDL, TableSchema}
import org.apache.commons.csv.CSVFormat

object CsvImporter {
  val defaultFormat = CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';')
  def importCsvs(path: String, connection: Connection, tableName: String, format: CSVFormat = defaultFormat) = {
    val files = new File(path).list()
      .filter(_.endsWith(".csv"))

    val parsedCsvs = files
      .map(new File(path, _))
      .map(new FileInputStream(_))
      .map(CsvFile.from(format))

    val tableSchema = TableSchema.fromCsv(parsedCsvs.head, tableName)
    try {
      val db = new DB(connection)
      db.execute(tableSchema)

      parsedCsvs.foreach { file =>
        file.rows.sliding(5000, 5000).foreach { entries =>
          val rows = entries.map(RowToDDL(tableSchema, _))
          db.insertBatch(rows)
        }
      }
    } finally {
      connection.close()
    }
  }
}
