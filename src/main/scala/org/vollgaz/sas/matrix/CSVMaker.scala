package org.vollgaz.sas.matrix

import java.io.{File, FileWriter}

import scala.collection.SortedSet

class CSVMaker(output: File) {

    private val ACCESS_GRANTED = "true"
    private val ACCESS_DENIED  = "false"


    def generateMatrixCSV(data: Map[String, Set[String]]): Unit = {
        val fw = new FileWriter(output, false)
        fw.write(createDataMatrix(data))
        fw.close()
    }

    /**
      *
      * @param data Contains all data in a structure like this
      *             user_ID1 -> [group1, group2, group5]
      *             user_ID2 -> [group1, group3, group4, group5]
      * @return The CSV (semi-colon separated) string with all the data
      */
    def createDataMatrix(data        : Map[String, Set[String]]): String = {
        // Retrive all possible values in the value part of the Map.
        val allcolumns: SortedSet[String] = SortedSet.empty[String] ++ data.flatMap(_._2).toSet
        val strbuilder = new StringBuilder(s"user;${allcolumns.mkString(";")}\n")
        data.keys.toSeq.sorted.foreach(key => {
            strbuilder.append(s"$key")
            val keyCols = data(key)
            allcolumns.foreach(column => {
                if (keyCols.contains(column)) strbuilder.append(s";$ACCESS_GRANTED")
                else strbuilder.append(s";$ACCESS_DENIED")
            })
            strbuilder.append(s"\n")
        })
        strbuilder.mkString
    }


}
