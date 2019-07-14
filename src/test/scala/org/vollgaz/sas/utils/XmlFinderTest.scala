package org.vollgaz.sas.utils

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

class XmlFinderTest extends FlatSpec with Matchers {

    it should "Verify test/resources folder" in {
        val finder = XmlFinder.findRequiredFiles(new File("src/test/resources"))

        finder.size should be(6)
        finder.keys should contain("AccessControlEntry")
        finder("AccessControlEntry").getName should be("access-control.xml")

        finder.keys should contain("IdentityGroup")
        finder("IdentityGroup").getName should be("identity-group.xml")

        finder.keys should contain("SASLibrary")
        finder("SASLibrary").getName should be("library.xml")

        finder.keys should contain("Permission")
        finder("Permission").getName should be("permission.xml")

        finder.keys should contain("Person")
        finder("Person").getName should be("person.xml")

        finder.keys should contain("Tree")
        finder("Tree").getName should be("tree.xml")

    }
}
