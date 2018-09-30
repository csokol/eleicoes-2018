package com.github.csokol.eleicoes.parser

import org.apache.commons.csv.CSVFormat
import org.scalatest.FlatSpec

class RowToDDLTest extends FlatSpec {

  it should "parse candidate row" in {
    val is = getClass.getResourceAsStream("/candidatos_simple.csv")
    val csv = CsvFile.from(CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';'))(is)
    val schema = TableSchema.fromCsv(csv, "candidatos")
    val row = csv.rows.head

    val ddl = RowToDDL(schema, row)

    assert(ddl.mappings("DT_GERACAO") == "2018")
    assert(ddl.mappings("SQ_CANDIDATO") == 2)
    assert(ddl.mappings("NR_CANDIDATO") == "1")
  }

  it should "parse generate ddl statement" in {
    val is = getClass.getResourceAsStream("/candidatos_simple.csv")
    val csv = CsvFile.from(CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';'))(is)
    val schema = TableSchema.fromCsv(csv, "candidatos")
    val row = csv.rows.head

    val ddl = RowToDDL(schema, row)
    val expectedDdl = "INSERT INTO candidatos(DT_GERACAO, NR_CANDIDATO, SQ_CANDIDATO) " +
      "VALUES ('2018', '1', 2)"
    assert(ddl.toDdl === expectedDdl)
  }

  it should "parse bem_candidato entry" in {
    val is = getClass.getResourceAsStream("/bens_candidato_simple.csv")
    val csv = CsvFile.from(CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';'))(is)
    val schema = TableSchema.fromCsv(csv, "bens_candidato")
    val row = csv.rows.head

    val ddl = RowToDDL(schema, row)

    assert(ddl.mappings("DT_GERACAO") === "29/09/2018")
    assert(ddl.mappings("VR_BEM_CANDIDATO") === 421)
  }


}
