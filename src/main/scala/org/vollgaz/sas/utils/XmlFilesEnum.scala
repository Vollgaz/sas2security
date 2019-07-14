package org.vollgaz.sas.utils

/**
  * Define necessary files for the treatment
  * Each enum value is used to detect the correct file, because the datatype is contained in the file.
  */
object XmlFilesEnum {
    val ACCESSCONTROL = "AccessControlEntry"
    val IDENTITYGROUP = "IdentityGroup"
    val LIBRARY = "SASLibrary"
    val PERMISSION = "Permission"
    val PERSON = "Person"
    val TREE = "Tree"
}
