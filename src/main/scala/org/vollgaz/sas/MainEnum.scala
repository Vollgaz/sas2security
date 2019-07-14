package org.vollgaz.sas

object MainEnum {

    object MainServices {
        val GRAPH  = "graph"
        val LIST   = "list"
        val Matrix = "matrix"
    }

    object MainModes {
        val USER2GROUPS = "user2groups"
        val USER2LIBS = "user2libs"

        val GROUP2MEMBERS = "group2members" // members which are groups
        val GROUP2PARENTS = "group2parents" // parents which are groups
        val GROUP2LIBS = "group2libs"
        val GROUP2USERS = "group2users"
        val GROUP2GROUPS = "group2groups"

        val LIB2GROUPS = "lib2groups"
        val LIB2USERS = "lib2users"
    }

}
