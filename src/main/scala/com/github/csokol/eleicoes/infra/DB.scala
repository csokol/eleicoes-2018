package com.github.csokol.eleicoes.infra

import java.sql.{Connection, ResultSet}

import com.github.csokol.eleicoes.parser.{RowToDDL, TableSchema}

import scala.util.Try

class DB(connection: Connection) {


  def query(sql: String): Seq[Seq[Object]] = {
    val rs = connection.prepareStatement(sql)
      .executeQuery()

    assembleRows(List(), rs)

  }

  private def assembleRows(s: List[Seq[Object]], rs: ResultSet): List[Seq[Object]] = {
    if (rs.next()) {
      val meta = rs.getMetaData
      val count = meta.getColumnCount
      val row = 1.to(count).map(rs.getObject)
      assembleRows(row :: s, rs)
    } else {
      s
    }
  }

  def execute(ddl: String): Unit = {
    println(ddl + ";")
  }

  def execute(tableSchema: TableSchema) = {
    val ddl = tableSchema.toDdl
    println(ddl + ";")
    val stmt = connection.prepareStatement(ddl)
    try {
      stmt.execute()
    } finally {
      stmt.close()
    }
  }

  def execute(rowToDDL: RowToDDL): Unit = {
    println(rowToDDL.toDdl + ";")
  }

  def insertBatch(rows: Iterable[RowToDDL]) = {
    val first = rows.head
    val sortedColumnNames = first.mappings.keys.toList.sorted
    val columns = sortedColumnNames.mkString(", ")
    val valuesPlaceholder = (0 until first.mappings.keys.size).map(_ => "?").mkString(", ")
    val ddl = s"INSERT INTO ${first.tableSchema.tableName}($columns) VALUES ($valuesPlaceholder)"
    val stmt = connection.prepareStatement(ddl)

    try {
      rows.foreach { row =>
        sortedColumnNames.zipWithIndex.foreach { case (column, index) =>
          val value = row.mappings.get(column).get
          stmt.setObject(index + 1, value)
        }
        stmt.addBatch()
      }
      stmt.executeBatch
    } finally {
      stmt.close()
    }
  }

}
