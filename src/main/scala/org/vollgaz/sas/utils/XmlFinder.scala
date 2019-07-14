package org.vollgaz.sas.utils

import java.io.File

import scala.collection.immutable.HashMap
import scala.xml.{SAXParseException, XML}

object XmlFinder {

    /**
      * Scan folder for finding all XML metadata files
      * @param path Folder path
      * @return Mapping between the medata type and object File targeting the right file.
      */
    def findRequiredFiles(path: File): Map[String, File] = {
        path.listFiles()
            .filter(_.isFile)
            .filter(_.getName.split("\\.").last.toLowerCase == "xml")
            .flatMap(x => try {
                analyseXML(x)
            } catch {
                // Ignore fake or malformed xml files
                case exception: SAXParseException => None
                case exception: Exception => throw exception
            })
            .toMap
    }

    /**
      *
      * @param file
      * @throws SAXParseException In case the xml file is malformed
      * @return
      */
    @throws(classOf[SAXParseException])
    def analyseXML(file: File): HashMap[String, File] = {
        val xml = XML.loadFile(file)
        (xml \ "Type").text match {
            case XmlFilesEnum.ACCESSCONTROL => HashMap(XmlFilesEnum.ACCESSCONTROL -> file)
            case XmlFilesEnum.IDENTITYGROUP => HashMap(XmlFilesEnum.IDENTITYGROUP -> file)
            case XmlFilesEnum.LIBRARY => HashMap(XmlFilesEnum.LIBRARY -> file)
            case XmlFilesEnum.PERMISSION => HashMap(XmlFilesEnum.PERMISSION -> file)
            case XmlFilesEnum.PERSON => HashMap(XmlFilesEnum.PERSON -> file)
            case XmlFilesEnum.TREE => HashMap(XmlFilesEnum.TREE -> file)
        }
    }
}
