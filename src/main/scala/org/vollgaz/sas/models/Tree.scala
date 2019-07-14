package org.vollgaz.sas.models

import scala.collection.immutable
import scala.xml.{Elem, Node}

/**
  * Represents a virtual folder.
  * A 'tree' is a node in the virtual filesytem of the sasmeta server.
  * So tree can have as members : libraries, reports, real file, ...
  *
  * @param id        Unique ID generated by SASmeta server.
  * @param name      The display name
  * @param accesses  The list of [[Access]] IDs targeting the tree node.
  * @param libraries The list of [[Library]] IDs contained in the node.
  * @param tree      The ID of the parent node.
  */
case class Tree(id: String, name: String, accesses: Seq[String], libraries: Seq[String], tree: String)

object Tree extends Factory {
    override def buildCollection(xmlModel: Elem): Map[String, Tree] = {
        val nodeSeq = xmlModel \\ "Objects" \ "Tree"
        nodeSeq.map(buildElement).toMap
    }

    override def buildElement(node: Node): (String, Tree) = {
        getId(node) -> Tree(getId(node), getName(node), getAccessControls(node), getLibraries(node), getParentTree(node))
    }

    def getLibraries(node: Node): immutable.Seq[String] = (node \\ "Members" \ "SASLibrary").map(_ \@ "Id")
}
