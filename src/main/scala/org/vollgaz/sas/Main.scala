package org.vollgaz.sas

import java.io.File

import org.vollgaz.sas.MainEnum.{MainModes, MainServices}
import org.vollgaz.sas.graph.{GroupsGrapher, PersonsGrapher}
import org.vollgaz.sas.matrix.CSVMaker
import org.vollgaz.sas.models.Sasmeta
import org.vollgaz.sas.utils.{XmlFilesEnum, XmlFinder}

import scala.collection.immutable.HashMap
import scala.xml.XML

object Main {
    var config : MainConfig        = _
    var files  : Map[String, File] = _
    var sasmeta: Sasmeta           = _

    def main(args: Array[String]): Unit = {
        this.config = new MainArgsParser(args).getConfig.get
        this.files = XmlFinder.findRequiredFiles(config.src)
        this.sasmeta = new Sasmeta(XML.loadFile(this.files(XmlFilesEnum.PERSON)),
            XML.loadFile(this.files(XmlFilesEnum.ACCESSCONTROL)),
            XML.loadFile(this.files(XmlFilesEnum.LIBRARY)),
            XML.loadFile(this.files(XmlFilesEnum.PERMISSION)),
            XML.loadFile(this.files(XmlFilesEnum.IDENTITYGROUP)),
            XML.loadFile(this.files(XmlFilesEnum.TREE))
        )
        run()
    }

    private def run(): Unit = {
        config.service match {
            case Some(MainServices.GRAPH)  => runGRAPH()
            case Some(MainServices.LIST)   => runLIST()
            case Some(MainServices.Matrix) => runMATRIX()
            case _                         => throw new Exception("Unknown service : " + config.service)
        }
    }

    private def runGRAPH(): Unit = {
        val groupsGrapher = new GroupsGrapher(sasmeta.groups)
        val personsGrapher = new PersonsGrapher(sasmeta.persons, sasmeta.groups)
        val target = config.target

        config.mode match {
            case Some(MainModes.USER2GROUPS)   => {
                target match {
                    case Some("all")                                 => personsGrapher.fullAscendingHierarchy()
                    case `target` if sasmeta.personExist(target.get) => personsGrapher.personAscendingHierarchy(sasmeta.getPersonByTrigramm(target.get))
                    case _                                           => throw new Exception("Utilisateur inconnu")
                }
            }
            case Some(MainModes.GROUP2PARENTS) => {
                target match {
                    case Some("all")                                => groupsGrapher.fullAscendingHierarchy()
                    case `target` if sasmeta.groupExist(target.get) => groupsGrapher.groupAscendingHierarchy(sasmeta.getGroupByName(target.get))
                    case _                                          => throw new Exception("Groupe inconnu")
                }
            }
            case Some(MainModes.GROUP2MEMBERS) => {
                target match {
                    case Some("all")                                => groupsGrapher.fullDescendingHierarchy()
                    case `target` if sasmeta.groupExist(target.get) => groupsGrapher.groupDescendingHierarchy(sasmeta.getGroupByName(target.get))
                    case _                                          => throw new Exception("Groupe inconnu")
                }
            }
            case _                             => throw new Exception("Mode de functionnement inconnu : " + config.mode)
        }
    }


    def runLIST(): Unit = {
        val target = config.target
        config.mode match {
            case Some(MainModes.USER2LIBS) => target match {
                case Some("all") => printMap(sasmeta.allPersons2LibrariesList())
                case `target` if sasmeta.personExist(target.get) =>
                    printMap(HashMap(target.get -> sasmeta.person2LibrariesList(sasmeta.getPersonByTrigramm(target.get))))
                case _ => throw new Exception("Utilisateur inconnu")
            }
            case Some(MainModes.USER2GROUPS) => target match {
                case Some("all") => printMap(sasmeta.allPersons2GroupsList())
                case `target` if sasmeta.personExist(target.get) =>
                    printMap(HashMap(target.get -> sasmeta.person2GroupsList(sasmeta.getPersonByTrigramm(target.get))))
                case _ => throw new Exception("Utilisateur inconnu")
            }
            case Some(MainModes.GROUP2MEMBERS) => target match {
                case Some("all") => printMap(sasmeta.allGroups2MembersList())
                case `target` if sasmeta.groupExist(target.get) =>
                    printMap(HashMap(target.get -> sasmeta.group2MembersList(sasmeta.getGroupByName(target.get))))
                case _ => throw new Exception("Utilisateur inconnu")
            }
            case Some(MainModes.GROUP2PARENTS) => target match {
                case Some("all") => printMap(sasmeta.allGroups2ParentsList())
                case `target` if sasmeta.groupExist(target.get) =>
                    printMap(HashMap(config.target.get -> sasmeta.group2ParentsList(sasmeta.getGroupByName(target.get))))
                case _ => throw new Exception("Utilisateur inconnu")
            }
            case Some(MainModes.GROUP2LIBS) => target match {
                case Some("all") => printMap(sasmeta.allGroups2LibrariesList())
                case `target` if sasmeta.groupExist(target.get) =>
                    printMap(HashMap(config.target.get -> sasmeta.group2LibrariesList(sasmeta.getGroupByName(target.get))))
                case _ => throw new Exception("Groupe inconnu")
            }
            case Some(MainModes.GROUP2USERS) => target match {
                case Some("all") => printMap(sasmeta.allGroup2UsersList())
                case `target` if sasmeta.groupExist(target.get) =>
                    printMap(HashMap(config.target.get -> sasmeta.group2UsersList(sasmeta.getGroupByName(target.get))))
                case _ => throw new Exception("Groupe inconnu")
            }

            case Some(MainModes.LIB2GROUPS) => target match {
                case Some("all") => printMap(sasmeta.allLibraries2GroupsList())
                case `target` if sasmeta.libraryExist(target.get) =>
                    printMap(HashMap(config.target.get -> sasmeta.library2GroupsList(sasmeta.getLibraryByName(target.get))))
                case _ => throw new Exception("Librairie inconnue")
            }
            case Some(MainModes.LIB2USERS) =>
                target match {
                    case Some("all") => printMap(sasmeta.allLibraries2UsersList())
                    case `target` if sasmeta.libraryExist(target.get) =>
                        printMap(HashMap(config.target.get -> sasmeta.library2UsersList(sasmeta.getLibraryByName(target.get))))
                    case _ => throw new Exception("Librairie inconnue")
                }
            case _ => throw new Exception("Mode de functionnement inconnu : " + config.mode)
        }
    }

    def printMap(data: Map[String, Set[String]]): Unit = {
        data.foreach(x => {
            println(s"${x._1}")
            x._2.toSeq.sorted.foreach(y => println(s"-- $y"))
        })
    }

    def runMATRIX(): Unit = {
        val csvmaker = new CSVMaker(config.output)
        config.mode match {
            case Some(MainModes.USER2LIBS)   => csvmaker.generateMatrixCSV(sasmeta.allPersons2LibrariesList())
            case Some(MainModes.USER2GROUPS) => csvmaker.generateMatrixCSV(sasmeta.allPersons2GroupsList())
            case Some(MainModes.GROUP2LIBS)  => csvmaker.generateMatrixCSV(sasmeta.allGroups2LibrariesList())
            case _                           => throw new Exception("Mode de functionnement inconnu : " + config.mode)
        }
    }

}
