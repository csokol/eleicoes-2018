package com.github.csokol.eleicoes

import java.sql.DriverManager

object ImportBensCandidato {

  def main(args: Array[String]): Unit = {
    val path = "/Users/csokol/workspace/eleicoes-2018/input/2018/bem_candidato"
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/eleicoes", "root", "passwd")

    CsvImporter.importCsvs(path, connection, "bens_candidatos")
  }
}
