package org.vollgaz.sas.models

import scala.xml.{Elem, Node}

/**
  * The simpliest representation of a person
  *
  * @param id       Unique ID generated by the SASmeta server
  * @param name     The display name (LASTNAME firstname)
  * @param groups   The list of all [[Group]] where the person is a direct member
  * @param accesses The list of [[Access]] specific to the person. It is the case when some specific rights are assigned for a specific user.
  */
case class Person(id: String, name: String, groups: Seq[String], accesses: Seq[String])

object Person extends Factory {
    def buildCollection(xmlModel: Elem): Map[String, Person] = {
        val nodeSeq = xmlModel \\ "Objects" \ "Person"
        nodeSeq.map(buildElement).toMap
    }

    def buildElement(node: Node): (String, Person) = {
        getId(node) -> Person(getId(node),
            getName(node),
            getIdentityGroups(node),
            getAccessControlEntries(node))
    }
}