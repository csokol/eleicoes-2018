package com.github.csokol.eleicoes.parser

import org.apache.commons.csv.CSVFormat
import org.scalatest.FlatSpec

class TableSchemaTest extends FlatSpec {

  it should "parse headers into table schema" in {
    val is = getClass.getResourceAsStream("/candidatos.csv")
    val csv = CsvFile.from(CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';'))(is)
    val createTables = TableSchema.fromCsv(csv, "canditados")

    assert(createTables.mappings("SQ_CANDIDATO") === "BIGINT")
  }

  it should "parse headers of bens_candidato into table schema" in {
    val is = getClass.getResourceAsStream("/bens_candidato_simple.csv")
    val csv = CsvFile.from(CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';'))(is)
    val createTables = TableSchema.fromCsv(csv, "bens_canditado")

    assert(createTables.mappings("VR_BEM_CANDIDATO") === "BIGINT")
  }

  it should "creates ddl" in {
    val is = getClass.getResourceAsStream("/candidatos_simple.csv")
    val csv = CsvFile.from(CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';'))(is)
    val createTables = TableSchema.fromCsv(csv, "candidatos")

    val ddl = "" +
      "CREATE TABLE IF NOT EXISTS candidatos(" +
      "ID BIGINT AUTO_INCREMENT, " +
      "DT_GERACAO VARCHAR(255), " +
      "NR_CANDIDATO VARCHAR(255), " +
      "SQ_CANDIDATO BIGINT, " +
      "PRIMARY KEY (ID)" +
      ") ENGINE=INNODB"

    assert(createTables.toDdl === ddl)
  }

}
