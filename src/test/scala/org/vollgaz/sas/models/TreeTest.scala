package org.vollgaz.sas.models

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class TreeTest extends FlatSpec with Matchers {

    val xmlModel: Elem = XML.load("src/test/resources/tree.xml")
    val collection: Map[String, Tree] = Tree.buildCollection(xmlModel)

    it should "Verify AntenneQualite folder" in {
        collection.keys should contain("A5BYC65S.AA0001J2")
    }
}
