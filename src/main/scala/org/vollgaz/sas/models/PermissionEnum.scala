package org.vollgaz.sas.models

/**
  * Enum for the detail of all the permission type
  * If a madman want to make more checks.
  */
object PermissionEnum extends Enumeration {
    val READMETADATA = Value("ReadMetadata")
    val WRITEMETADATA = Value("WriteMetadata")
    val CHECKINMETADATA = Value("CheckInMetadata")
    val READ = Value("Read")
    val WRITE = Value("Write")
    val ADMINISTER = Value("Administer")
    val CREATE = Value("Create")
    val DELETE = Value("Delete")
    val EXECUTE = Value("Execute")
    val WRITEMEMBERMETADATA = Value("WriteMemberMetadata")
    val CREATETABLE = Value("Create Table")
    val DROPTABLE = Value("Drop Table")
    val ALTERTABLE = Value("Alter Table")
    val SELECT = Value("Select")
    val INSERT = Value("Insert")
    val UPDATE = Value("Update")
    val REFERENCES = Value("References")
    val MANAGECREDENTIALSMETADATA = Value("ManageCredentialsMetadata")
    val MANAGEMEMBERMETADATA = Value("ManageMemberMetadata")
}
