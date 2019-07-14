package org.vollgaz.sas

import java.io.File

import org.vollgaz.sas.MainEnum.{MainModes, MainServices}
import scopt.{OParser, OParserBuilder}


class MainArgsParser(args: Array[String]) {
    val builder: OParserBuilder[MainConfig] = OParser.builder[MainConfig]
    val parser : OParser[Unit, MainConfig]  = {
        import builder._
        OParser.sequence(
            programName(this.getClass.getPackage.getImplementationTitle),
            head("version", this.getClass.getPackage.getImplementationVersion),
            help("help").text("Print help"),
            opt[File]('f', "src")
                .required()
                .action((folder, config) => config.copy(src = folder))
                .validate(folder =>
                    if (!folder.isDirectory) failure("--src Targeted path is not a folder.")
                    else if (folder.listFiles().isEmpty) failure("--src Targeted folder is empty.")
                    else success
                )
                .text("Source folder containing data [persons, accesses, permissions, groups, trees and libraries].\n"),
            cmd(MainServices.GRAPH)
                .action((_, config) => config.copy(service = Some(MainServices.GRAPH)))
                .text("display relation between object.\n")
                .children(
                    opt[String](MainModes.USER2GROUPS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.USER2GROUPS), target = Some(target))
                        })
                        .text("Display membership hierarchy of the selected user"),
                    opt[String](MainModes.GROUP2MEMBERS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.GROUP2MEMBERS), target = Some(target))
                        })
                        .text("Display membership hierarchy of the selected group"),
                    opt[String](MainModes.GROUP2PARENTS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.GROUP2PARENTS), target = Some(target))
                        })
                        .text("Display inheritance hierarchy of the selected group.\n")
                ),
            cmd(MainServices.LIST)
                .action((_, config) => config.copy(service = Some(MainServices.LIST)))
                .text("display final membership.\n")
                .children(
                    opt[String](MainModes.USER2LIBS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.USER2LIBS), target = Some(target))
                        })
                        .text("Display all libraries where the user as the READMETA permission granted."),
                    opt[String](MainModes.USER2GROUPS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.USER2GROUPS), target = Some(target))
                        })
                        .text("Display all groups where the user is referenced"),
                    opt[String](MainModes.GROUP2MEMBERS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.GROUP2MEMBERS), target = Some(target))
                        })
                        .text("Display all groups who inherite from the selected group"),
                    opt[String](MainModes.GROUP2PARENTS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.GROUP2PARENTS), target = Some(target))
                        })
                        .text("Display all groups from which the selected group inherite"),
                    opt[String](MainModes.GROUP2LIBS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.GROUP2LIBS), target = Some(target))
                        })
                        .text("Display all libraries available from the selected group"),
                    opt[String](MainModes.GROUP2USERS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.GROUP2USERS), target = Some(target))
                        })
                        .text("Display all users who are member of the selected group"),
                    opt[String](MainModes.LIB2GROUPS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.LIB2GROUPS), target = Some(target))
                        })
                        .text("Display all groups accessing the selected library"),
                    opt[String](MainModes.LIB2USERS)
                        .action((target, config) => {
                            config.copy(mode = Some(MainModes.LIB2USERS), target = Some(target))
                        })
                        .text("Display all users accessing the selected library.\n")
                ),
            cmd(MainServices.Matrix)
                .action((_, config) => config.copy(service = Some(MainServices.Matrix)))
                .text("generate file containing a data matrix.\n")
                .children(
                    opt[File]('o', "out")
                        .required()
                        .action((file, config) => config.copy(output = file))
                        .text("Output path for the dataset file"),
                    opt[Unit](MainModes.USER2LIBS)
                        .action((_, config) => {
                            config.copy(mode = Some(MainModes.USER2LIBS))
                        })
                        .text("Generate the matrix of users access (which user access what)."),
                    opt[Unit](MainModes.USER2GROUPS)
                        .action((_, config) => {
                            config.copy(mode = Some(MainModes.USER2GROUPS))
                        })
                        .text("Generate the matrix of membership (which group has which users)."),
                    opt[Unit](MainModes.GROUP2LIBS)
                        .action((_, config) => {
                            config.copy(mode = Some(MainModes.GROUP2LIBS))
                        })
                        .text("Generate the matrix of groups access (which group accesses which library).")
                )
        )
    }

    def getConfig: Option[MainConfig] = {
        OParser.parse(parser, args, MainConfig()) match {
            case Some(config) => Some(config)
            case _            => None
        }
    }
}
