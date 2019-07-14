package org.vollgaz.sas.models

import scala.xml.Elem

/**
  * Here is the MAGIC
  *
  * @param personsXML     Object representation of the file containing users information
  * @param accessesXML    Object representation of the file containing accesses definition
  * @param librariesXML   Object representation of the file containing libraries information
  * @param permissionsXML Object representation of the file containing permissions information
  * @param groupsXML      Object representation of the file containing groups information
  * @param treeXML        Object representation of the file containing the sas virtual files hierarchy information
  */
class Sasmeta(personsXML: Elem, accessesXML: Elem, librariesXML: Elem, permissionsXML: Elem, groupsXML: Elem, treeXML: Elem) {

    val accesses   : Map[String, Access]     = Access.buildCollection(accessesXML)
    val permissions: Map[String, Permission] = Permission.buildCollection(permissionsXML)
    val libraries  : Map[String, Library]    = Library.buildCollection(librariesXML)
    val groups     : Map[String, Group]      = Group.buildCollection(groupsXML)
    val persons    : Map[String, Person]     = Person.buildCollection(personsXML)
    val trees      : Map[String, Tree]       = Tree.buildCollection(treeXML)

    val libraries2Accesses: Map[String, Set[String]] = {
        this.libraries.values.map(lib => lib.id -> {
            lib.accesses.toSet ++ tree2accesses(this.trees(lib.tree))
        }).toMap
    }

    val accesses2Libraries: Map[String, Set[String]] = {
        val accessesList = libraries2Accesses.flatMap(_._2).toSet
        accessesList.map(accessID => accessID -> libraries2Accesses.filter(_._2.contains(accessID)).keys.toSet).toMap
    }

    def isAccessAllowed(accessesID: Set[String], permission: PermissionEnum.Value): Boolean = {
        val permissionGranted = getPermissionID(PermissionEnum.READMETADATA, isAuthorized = true)
        val permissionDenied = getPermissionID(PermissionEnum.READMETADATA, isAuthorized = false)
        val accessesObjects = accessesID.map(accessID => accesses(accessID))
        val allpermss = accessesObjects.flatMap(access => access.permissions)
        if (allpermss.contains(permissionGranted) && !allpermss.contains(permissionDenied)) true
        else false
    }

    def getPermissionID(permission: PermissionEnum.Value, isAuthorized: Boolean): String = {
        val perm = permission.toString
        permissions.values.filter(permission => permission.name == perm && permission.isGranted == isAuthorized).head.id
    }

    def allPersons2GroupsList(): Map[String, Set[String]] = this.persons.values.map(groupID => groupID.name -> person2GroupsList(groupID)).toMap

    def person2GroupsList(person: Person): Set[String] = {
        person.groups.map(groupID => this.groups(groupID).name).toSet ++ person.groups.flatMap(groupID => group2ParentsList(this.groups(groupID))).toSet
    }

    def allGroups2MembersList(): Map[String, Set[String]] = this.groups.values.map(groupID => groupID.name -> group2MembersList(groupID)).toMap

    def group2MembersList(group: Group): Set[String] = {
        group.groupmembers.map(groupID => this.groups(groupID).name).toSet ++ group.groupmembers.flatMap(groupID => group2MembersList(this.groups(groupID)))
    }

    def allGroups2ParentsList(): Map[String, Set[String]] = this.groups.values.map(groupID => groupID.name -> group2ParentsList(groupID)).toMap

    def group2ParentsList(group: Group): Set[String] = {
        group.groupparents.map(x => this.groups(x).name).toSet ++ group.groupparents.flatMap(groupID => group2ParentsList(this.groups(groupID)))
    }

    def allPersons2LibrariesList(): Map[String, Set[String]] = this.persons.values.map(person => person.name -> person2LibrariesList(person)).toMap

    def person2LibrariesList(person: Person): Set[String] = {
        val allAccesses: Set[String] = (person.accesses ++ person.groups.flatMap(groupID => group2accesses(groups(groupID)))).toSet
        val linkedLibs: Set[String] = allAccesses.flatMap(accesID => this.accesses2Libraries.getOrElse(accesID, Set.empty[String]))
        linkedLibs.filter(libID => {
            val commonAccesses = this.libraries2Accesses(libID).intersect(allAccesses)
            isAccessAllowed(commonAccesses, PermissionEnum.READMETADATA)
        }).map(libID => this.libraries(libID).name)
    }

    def allGroups2LibrariesList(): Map[String, Set[String]] = {
        this.groups.values.map(group => group.name -> group2LibrariesList(group)).toMap
    }

    def group2LibrariesList(group: Group): Set[String] = {
        val allAccesses: Set[String] = (group.accesses ++ group2accesses(group)).toSet
        val linkedLibs: Set[String] = allAccesses.flatMap(accesID => this.accesses2Libraries.getOrElse(accesID, Set.empty[String]))
        linkedLibs.filter(libID => {
            val commonAccesses = this.libraries2Accesses(libID).intersect(allAccesses)
            isAccessAllowed(commonAccesses, PermissionEnum.READMETADATA)
        }).map(libID => this.libraries(libID).name)
    }

    def allGroup2UsersList(): Map[String, Set[String]] = {
        this.groups.values.map(group => group.name -> group2UsersList(group)).toMap
    }

    def group2UsersList(group: Group): Set[String] = {
        group.usermembers.toSet ++ group.groupmembers.flatMap(groupID => group2UsersList(this.groups(groupID))).map(personID => this.persons(personID).name)
    }

    def allLibraries2GroupsList(): Map[String, Set[String]] = {
        val groups2libs = allGroups2LibrariesList()
        this.libraries.values.map(lib => lib.name -> groups2libs.filter(_._2.contains(lib.name)).keySet).toMap
    }

    def library2GroupsList(library: Library): Set[String] = allGroups2LibrariesList().filter(_._2.contains(library.name)).keySet

    def allLibraries2UsersList(): Map[String, Set[String]] = {
        val users2libs = allPersons2LibrariesList()
        this.libraries.values.map(lib => lib.name -> users2libs.filter(_._2.contains(lib.name)).keySet).toMap
    }

    def library2UsersList(library: Library): Set[String] = allPersons2LibrariesList().filter(_._2.contains(library.name)).keySet

    def groupExist(groupName: String): Boolean = this.groups.values.exists(_.name == groupName)

    def personExist(personTrigramm: String): Boolean = this.persons.values.exists(_.name == personTrigramm)

    def libraryExist(libName: String): Boolean = this.libraries.values.exists(_.name == libName)

    def getPersonByTrigramm(personTrigramm: String): Person = this.persons.values.filter(_.name == personTrigramm).head

    def getGroupByName(groupName: String): Group = this.groups.values.filter(_.name == groupName).head

    def getLibraryByName(libName: String): Library = this.libraries.values.filter(_.name == libName).head

    def allGroups2accesses(): Map[String, Set[String]] = groups.values.map(group => group.id -> group2accesses(group)).toMap

    def group2accesses(group: Group): Set[String] = group.accesses.toSet ++ group.groupparents.flatMap(groupID => group2accesses(this.groups(groupID))).toSet

    def person2accesses(person: Person): Set[String] = person.accesses.toSet ++ person.groups.flatMap(groupID => group2accesses(this.groups(groupID)))

    def tree2accesses(tree: Tree): Set[String] = {
        val parent = tree.tree
        if (parent.nonEmpty) tree.accesses.toSet ++ tree2accesses(this.trees(parent))
        else tree.accesses.toSet
    }

}