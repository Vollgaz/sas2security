package org.vollgaz.sas.models

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class PermissionTest extends FlatSpec with Matchers {
    val xmlModel: Elem = XML.load("src/test/resources/permission.xml")
    val collection: Map[String, Permission] = Permission.buildCollection(xmlModel)

    it should "Verify all permissions" in {
        collection.size should be(38)
    }
}
