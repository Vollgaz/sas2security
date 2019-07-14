package org.vollgaz.sas.graph

import org.vollgaz.sas.models.{Group, Person}
import org.scalatest.{FlatSpec, Matchers}
import org.vollgaz.sas.models.{Group, Person}

import scala.xml.{Elem, XML}

class GroupsGrapherTest extends FlatSpec with Matchers {
    def groups: Map[String, Group] = Group.buildCollection(xmlGroups)

    def xmlGroups: Elem = XML.load("src/test/resources/identity-group.xml")

    def persons: Map[String, Person] = Person.buildCollection(xmlPersons)

    def xmlPersons: Elem = XML.load("src/test/resources/person.xml")


    /*    it should "Check SAS_DSAdmins descending lineage" in {
            new GroupsGrapher(groups).groupDescendingHierarchy(groups("A5BYC65S.A5000014"))
        }

        it should "Check SAS_DSAdmins ascending lineage" in {
            new GroupsGrapher(groups).groupAscendingHierarchy(groups("A5BYC65S.A5000014"))
        }


        it should "Display SAS_DSAdmins parents" in {
            groups("A5BYC65S.A5000014").groupparents.foreach(x => {
                println(s"$x        ${groups(x).name}")
            })
        }

        it should "Display SCHAFER Eric membership" in {
            val grapher = new GroupsGrapher(groups)
            val myGroups = persons("A5BYC65S.AP00005A").groups.map(groups)
            println("SCHAFER Eric")
            myGroups.foreach(grapher.groupAscendingHierarchy)
        }*/
}
