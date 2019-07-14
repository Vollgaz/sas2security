package org.vollgaz.sas

import java.io.File

case class MainConfig(src    : File = new File("."),
                      service: Option[String] = None,
                      mode   : Option[String] = None,
                      target : Option[String] = None,
                      output : File = new File("./output.css"))


