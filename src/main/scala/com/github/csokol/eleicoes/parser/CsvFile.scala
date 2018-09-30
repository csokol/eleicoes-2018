package com.github.csokol.eleicoes.parser

import java.io.{IOException, InputStream, InputStreamReader}

import org.apache.commons.csv.{CSVFormat, CSVParser}

import scala.collection.JavaConverters._


class CsvFile(records: CSVParser) {
  def rows = {
    records.asScala
  }

  def header = {
    records.getHeaderMap.asScala
  }

}

object CsvFile {
  def from(format: CSVFormat)(is: InputStream) = {
    try {
      val reader = new InputStreamReader(is, "ISO-8859-1")
      val records = format.parse(reader)
      new CsvFile(records)
    } catch {
      case e: IOException =>
        throw new RuntimeException(e)
    }
  }

}


