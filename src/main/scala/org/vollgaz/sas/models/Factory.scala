package org.vollgaz.sas.models

import scala.xml.{Elem, Node}

/**
  * Contains common methods for every metadata type files.
  */
trait Factory {

    def buildCollection(xmlModel: Elem): Map[String, Any]

    def buildElement(node: Node): (String, Any)

    def getId(node: Node): String = node \@ "Id"

    def getName(node: Node): String = node \@ "Name"

    def getAccessControls(node: Node): Seq[String] = (node \\ "AccessControls" \ "AccessControlEntry").map(_ \@ "Id")

    def getAccessControlEntries(node: Node): Seq[String] = (node \\ "AccessControlEntries" \ "AccessControlEntry").map(_ \@ "Id")

    def getIdentityGroups(node: Node): Seq[String] = (node \\ "IdentityGroups" \ "IdentityGroup").map(_ \@ "Id")

    def getParentTree(node: Node): String = (node \\ "Trees" \ "Tree").map(_ \@ "Id").headOption.getOrElse("")

}
