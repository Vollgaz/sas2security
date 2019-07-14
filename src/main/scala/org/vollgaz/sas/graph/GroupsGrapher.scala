package org.vollgaz.sas.graph

import org.vollgaz.sas.models.Group

/**
  * Class used to display inheritance relationship like this :
  * group_A
  * - group_AB
  * - group_BB
  * -- group_CC
  * -- group_DD
  * - group_ZZ
  *
  * @param groups : Object list build from the XML file. It is produced by the companion object of models.Group .
  */
class GroupsGrapher(groups: Map[String, Group]) {

    private var counter       : Int = 0
    private var initialCounter: Int = 0

    def setcounter(offset: Int): Unit = {
        this.counter = offset
        this.initialCounter = offset
    }

    def fullDescendingHierarchy(): Unit = {
        groups.values.foreach(x => {
            this.counter = this.initialCounter
            descendingLineage(x)
        })
    }

    def groupDescendingHierarchy(group: Group): Unit = {
        this.counter = this.initialCounter
        descendingLineage(group)
    }

    def fullAscendingHierarchy(): Unit = {
        groups.values.foreach(x => {
            this.counter = this.initialCounter
            ascendingLineage(x)
        })
    }

    def groupAscendingHierarchy(group: Group): Unit = {
        this.counter = this.initialCounter
        ascendingLineage(group)
    }

    /**
      * Show who are the members of the current group
      *
      * @param group
      */
    private def descendingLineage(group: Group): Unit = {
        println(s"${"-- " * counter}${group.name}")
        counter += 1
        if (group.groupmembers.nonEmpty) group.groupmembers.foreach(x => descendingLineage(groups(x)))
        else counter -= 1
    }

    /**
      * Show from which groups the current group is member.
      *
      * @param group
      */
    private def ascendingLineage(group: Group): Unit = {
        println(s"${"-- " * counter}${group.name}")
        this.counter += 1
        if (group.groupparents.nonEmpty) group.groupparents.foreach(x => ascendingLineage(groups(x)))
        this.counter -= 1
    }

}
