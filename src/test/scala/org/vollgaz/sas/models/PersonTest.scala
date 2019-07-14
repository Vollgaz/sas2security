package org.vollgaz.sas.models

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class PersonTest extends FlatSpec with Matchers {
    val xmlModel: Elem = XML.load("src/test/resources/person.xml")
    val collection: Map[String, Person] = Person.buildCollection(xmlModel)

    it should "Verify HELMAN Bernard existence" in {
        collection.keys should contain("A5BYC65S.AP00001M")
        collection("A5BYC65S.AP00001M").name should be("BHN")
    }

    it should "Verify HELMAN Bernard accesses" in {
        collection("A5BYC65S.AP00001M").accesses.size should be(2)
        collection("A5BYC65S.AP00001M").accesses should contain("A5BYC65S.A60001SH")
        collection("A5BYC65S.AP00001M").accesses should contain("A5BYC65S.A60001SI")
    }

    it should "Verify HELMAN Bernard groups" in {
        collection("A5BYC65S.AP00001M").groups.size should be(29)
        collection("A5BYC65S.AP00001M").groups should contain("A5BYC65S.A5000016")
        collection("A5BYC65S.AP00001M").groups should contain("A5BYC65S.A500002K")
        collection("A5BYC65S.AP00001M").groups should contain("A5BYC65S.A5000030")
        collection("A5BYC65S.AP00001M").groups should contain("A5BYC65S.A500002E")
    }

    it should "Verify AMBROISE Valérie existence" in {
        collection.keys should contain("A5BYC65S.AP000025")
        collection("A5BYC65S.AP000025").name should be("AMV")
    }

    it should "Verify AMBROISE Valérie accesses" in {
        collection("A5BYC65S.AP000025").accesses.size should be(2)
        collection("A5BYC65S.AP000025").accesses should contain("A5BYC65S.A60001QI")
        collection("A5BYC65S.AP000025").accesses should contain("A5BYC65S.A60001QJ")
    }

    it should "Verify AMBROISE Valérie groups" in {
        collection("A5BYC65S.AP000025").groups.size should be(12)
        collection("A5BYC65S.AP000025").groups should contain("A5BYC65S.A500001V")
        collection("A5BYC65S.AP000025").groups should contain("A5BYC65S.A500002A")
        collection("A5BYC65S.AP000025").groups should contain("A5BYC65S.A500002N")
        collection("A5BYC65S.AP000025").groups should contain("A5BYC65S.A5000024")
    }
}