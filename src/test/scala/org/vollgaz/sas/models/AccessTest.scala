package org.vollgaz.sas.models

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class AccessTest extends FlatSpec with Matchers {
    val xmlModel: Elem = XML.load("src/test/resources/access-control.xml")
    val collection: Map[String, Access] = Access.buildCollection(xmlModel)

    it should "Verify A5BYC65S.A60001OL existence" in {
        collection.keys should contain("A5BYC65S.A60001OL")
    }

    it should "Verify A5BYC65S.A60001OL permissions" in {
        collection("A5BYC65S.A60001OL").permissions.size should be(9)
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A4000001")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A4000003")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A400000J")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A4000005")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A4000007")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A4000009")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A400000B")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A400000D")
        collection("A5BYC65S.A60001OL").permissions should contain("A5BYC65S.A400000F")
    }

    it should "Verify A5BYC65S.A60001QQ existence" in {
        collection.keys should contain("A5BYC65S.A60001QQ")
    }

    it should "Verify A5BYC65S.A60001QQ permissions" in {
        collection("A5BYC65S.A60001QQ").permissions.size should be(1)
        collection("A5BYC65S.A60001QQ").permissions should contain("A5BYC65S.A400000B")
    }

}

