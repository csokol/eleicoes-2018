package com.github.csokol.eleicoes

import java.sql.DriverManager
import java.sql.DriverManager.getConnection

import org.apache.commons.csv.CSVFormat

object ImportBrasilIOData {

  def main(args: Array[String]): Unit = {
    CsvImporter.importCsvs(
      "/Users/csokol/workspace/eleicoes-2018/input/brasil-io/bemdeclarado",
      getConnection("jdbc:mysql://127.0.0.1:3306/brasil_io", "root", "passwd"),
      "bens_candidatos",
      CSVFormat.RFC4180.withFirstRecordAsHeader
    )

    CsvImporter.importCsvs(
      "/Users/csokol/workspace/eleicoes-2018/input/brasil-io/candidatura",
      getConnection("jdbc:mysql://127.0.0.1:3306/brasil_io", "root", "passwd"),
      "candidatos",
      CSVFormat.RFC4180.withFirstRecordAsHeader
    )
  }
}
