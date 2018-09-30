package com.github.csokol.eleicoes

import java.io.{File, FileInputStream}
import java.sql.{Connection, DriverManager}

import com.github.csokol.eleicoes.infra.DB
import com.github.csokol.eleicoes.parser.{CsvFile, RowToDDL, TableSchema}

object ImportCandidatos {

  def main(args: Array[String]): Unit = {
    val path = "/Users/csokol/workspace/eleicoes/input/2018/candidatos/"
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/eleicoes", "root", "passwd")
    val tableName = "candidatos"

    CsvImporter.importCsvs(path, connection, tableName)
  }
}
