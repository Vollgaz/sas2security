package org.vollgaz.sas.models

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

class LibraryTest extends FlatSpec with Matchers {

    val xmlModel = XML.load("src/test/resources/library.xml")
    val collection = Library.buildCollection(xmlModel)

    it should "Verify FICHIWI existence" in {
        collection.keys should contain("A5BYC65S.B500001V")
        collection("A5BYC65S.B500001V").name should be("EG - Domaines - FICHIWI")
    }

    it should "Verify FICHIWI access controls" in {
        collection("A5BYC65S.B500001V").accesses.size should be(2)
        collection("A5BYC65S.B500001V").accesses should contain("A5BYC65S.A6000192")
        collection("A5BYC65S.B500001V").accesses should contain("A5BYC65S.A60001D7")
    }

    it should "Verify FICHEXPLOIT existence" in {
        collection.keys should contain("A5BYC65S.B5000025")
        collection("A5BYC65S.B5000025").name should be("EG - Domaines - FICHEXPLOIT")
    }

    it should "Verify FICHEXPLOIT access controls" in {
        collection("A5BYC65S.B5000025").accesses.size should be(3)
        collection("A5BYC65S.B5000025").accesses should contain("A5BYC65S.A600019C")
        collection("A5BYC65S.B5000025").accesses should contain("A5BYC65S.A60001D2")
        collection("A5BYC65S.B5000025").accesses should contain("A5BYC65S.A60001GF")
    }
}

