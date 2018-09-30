package com.github.csokol.eleicoes

import java.io.{File, FileOutputStream, PrintWriter}
import java.sql.DriverManager

import com.github.csokol.eleicoes.infra.DB

object NetWorthByCandidateParty {


  def main(args: Array[String]): Unit = {
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/brasil_io", "root", "passwd")

    val db = new DB(connection)
    val l = db.query("select distinct c.sigla_partido from brasil_io.candidatos c")
    l.foreach(println)

    l.map(_.head).foreach { party =>
      val dirName = party.toString.replaceAll(" ", "_")
      new File(s"out/$dirName").mkdirs()
      val os = new FileOutputStream(s"out/$dirName/networth.dat")
      val pw = new PrintWriter(os)
      val sql =
        s"""
           |select c.nome, floor(bens.total_bens)
           |from brasil_io.candidatos c
           |       join (select c.numero_sequencial, sum(bc.valor) as total_bens
           |             from brasil_io.candidatos c
           |                    join brasil_io.bens_candidatos bc on bc.numero_sequencial = c.numero_sequencial
           |             where c.sigla_partido = '$party'
           |             group by c.numero_sequencial) as bens on bens.numero_sequencial = c.numero_sequencial
           |order by bens.total_bens desc;
           |
      """.stripMargin
      val results = db.query(sql)

      results.map(r => r(1).asInstanceOf[Double]).foreach(netWorth => pw.println(Math.log10(netWorth)))

      pw.close()
    }


    connection.close()
  }

}
