package com.github.csokol.eleicoes.parser

import org.apache.commons.csv.CSVFormat
import org.scalatest.FlatSpec

class CsvFileTest extends FlatSpec {

  it should "parse candidates csv" in {
    val is = getClass.getResourceAsStream("/candidatos.csv")

    val csv = CsvFile.from(CSVFormat.RFC4180.withFirstRecordAsHeader.withDelimiter(';'))(is)
    val rows = csv.rows.toList

    assert(rows.length === 10)
  }

}
