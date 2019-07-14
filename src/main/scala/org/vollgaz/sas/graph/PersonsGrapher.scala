package org.vollgaz.sas.graph

import org.vollgaz.sas.models.{Group, Person}

/**
  * Class used to display group membership of user like this :
  * user_ID1
  * - group_A
  * - group_B
  * -- group_C
  * -- group_D
  * - group_Z
  *
  * @param persons Object list build from the XML file. It is produced by the companion object of models.Person .
  * @param groups  Object list build form the XML file. It is produced by the companion object of models.Group .
  */
class PersonsGrapher(persons: Map[String, Person], groups: Map[String, Group]) {

    val groupsGrapher = new GroupsGrapher(groups)

    def fullAscendingHierarchy(): Unit = {
        persons.values.foreach(x => {
            personAscendingHierarchy(x)
            groupsGrapher.setcounter(1)
        })
    }

    def personAscendingHierarchy(person: Person): Unit = {
        println(s"${person.name}")
        groupsGrapher.setcounter(1)
        person.groups.foreach(x => groupsGrapher.groupAscendingHierarchy(groups(x)))
    }

}
