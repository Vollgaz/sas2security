package org.vollgaz.sas.matrix

import java.io.File

import org.scalatest.{FlatSpec, Matchers}
import org.vollgaz.sas.models.Sasmeta

import scala.xml.XML

class CSVMakerTest extends FlatSpec with Matchers {

    val sasmeta = new Sasmeta(
        XML.loadFile(new File("src/test/resources/person.xml")),
        XML.loadFile(new File("src/test/resources/access-control.xml")),
        XML.loadFile(new File("src/test/resources/library.xml")),
        XML.loadFile(new File("src/test/resources/permission.xml")),
        XML.loadFile(new File("src/test/resources/identity-group.xml")),
        XML.loadFile(new File("src/test/resources/tree.xml")),
    )

    it should "Test persons2libraries matrix" in {
        val maker = new CSVMaker(new File("target/dataset_person2libs.csv"))
        val data = sasmeta.allPersons2LibrariesList()
        ///maker.generateCSV(data)
    }

    it should "Test group2libraries matrix" in {
        val maker = new CSVMaker(new File("target/dataset_group2libs.csv"))
        val data = sasmeta.allGroups2LibrariesList()
        //maker.generateCSV(data)
    }
}
