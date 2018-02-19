name := "percategory"

version := "0.2"

scalaVersion := "2.11.8"

val sparkVersion = "2.0.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
libraryDependencies += "commons-net" % "commons-net" % "3.6" % "provided"