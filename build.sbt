import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayJava
//import play.sbt.PlayKeys

name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
//  javaJdbc,
  javaJpa,
  evolutions,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final",
  "org.jadira.usertype" % "usertype.core" % "5.0.0.GA",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.7.1",
  "javax.el" % "javax.el-api" % "3.0.0",
  "org.glassfish.web" % "el-impl" % "2.2"
)

//Running Play in development mode while using JPA will work fine, but in order to deploy the application you will need to add this to your build.sbt file.
//PlayKeys.externalizeResources := false


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
