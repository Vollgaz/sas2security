package org.vollgaz.sas.models

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class GroupTest extends FlatSpec with Matchers {

    def collection: Map[String, Group] = Group.buildCollection(xmlModel)

    def xmlModel: Elem = XML.load("src/test/resources/identity-group.xml")

    it must "Verify SAS_DSAdmins" in {
        collection.keySet should contain("A5BYC65S.A5000014")
        collection("A5BYC65S.A5000014").name should be("SAS_DSAdmins")


    }
    it must "Verify SAS_DSAdmins inheritances" in {
        collection("A5BYC65S.A5000014").groupparents should contain("A5BYC65S.A50005ZA")
        collection("A5BYC65S.A5000014").groupparents should contain("A5BYC65S.A500000F")
        collection("A5BYC65S.A5000014").groupparents should contain("A5BYC65S.A500000B")
        collection("A5BYC65S.A5000014").groupparents should contain("A5BYC65S.A500000D")
        collection("A5BYC65S.A5000014").groupparents should contain("A5BYC65S.A500000C")
        collection("A5BYC65S.A5000014").groupparents should contain("A5BYC65S.A5000009")
    }

    it must "Verify SAS_DSAdmins accesses" in {
        collection("A5BYC65S.A5000014").accesses should contain("A5BYC65S.A600013X")
        collection("A5BYC65S.A5000014").accesses should contain("A5BYC65S.A600018S")
        collection("A5BYC65S.A5000014").accesses should contain("A5BYC65S.A600019A")
        collection("A5BYC65S.A5000014").accesses should contain("A5BYC65S.A60001BV")
        collection("A5BYC65S.A5000014").accesses.size should be(122)
    }


    it must "Verify SAS_Dpt_BET" in {
        collection.keySet should contain("A5BYC65S.A500001S")
        collection("A5BYC65S.A500001S").name should be("SAS_Dpt_BET")


    }
    it must "Verify SAS_Dpt_BET inheritances" in {
        collection("A5BYC65S.A500001S").groupparents should be(Seq.empty[String])
    }
    it must "Verify SAS_Dpt_BET accesses" in {
        collection("A5BYC65S.A500001S").accesses should contain("A5BYC65S.A60001DS")
        collection("A5BYC65S.A500001S").accesses should contain("A5BYC65S.A600015D")
        collection("A5BYC65S.A500001S").accesses should contain("A5BYC65S.A60001E4")
        collection("A5BYC65S.A500001S").accesses should contain("A5BYC65S.A60001LL")
        collection("A5BYC65S.A500001S").accesses.size should be(5)
    }


    it must "Verify AccessTemplate of SAS System Services" in {
        collection("A5BYC65S.A5000007").accesstemplate should contain("A5BYC65S.AO000001")
    }
}

