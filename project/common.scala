import de.johoop.jacoco4sbt.JacocoPlugin.jacoco
import sbt.Keys._
import sbt._

object Config {
  lazy val javaCompilerOptions = Seq(
    "-Xlint"
  )

  lazy val compilerOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8", // yes, this is 2 args
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  )

  lazy val org = "com.solidfire"

  lazy val orgName = "SolidFire"

  // create beautiful scala test report
  lazy val unitTestOptions = Seq(
    Tests.Argument(TestFrameworks.ScalaTest,"-h","target/html-unit-test-report"),
    Tests.Argument(TestFrameworks.ScalaTest,"-u","target/unit-test-reports"),
    Tests.Argument(TestFrameworks.ScalaTest,"-oD")
  )

  lazy val jacocoTestOptions = Seq(
    Tests.Argument(TestFrameworks.ScalaTest,"-h","target/html-unit-test-report"),
    Tests.Argument(TestFrameworks.ScalaTest,"-u","target/unit-test-reports"),
    Tests.Argument(TestFrameworks.ScalaTest,"-oD")
  )

  lazy val settings = Defaults.coreDefaultSettings ++ Seq(
    //populate default set of scalac options for each project
    javacOptions ++= javaCompilerOptions,
    scalacOptions ++= compilerOptions,
    testOptions in (Test, test) ++= unitTestOptions,
    testOptions in jacoco.Config ++= jacocoTestOptions,
    crossPaths in ThisBuild := true,
    crossScalaVersions := Seq( "2.10.5", "2.11.5" ),
    version := Version.jsvcgen,
    organization := org,
    resolvers := repositories,
    libraryDependencies ++= Seq(
      Dependencies.slf4j_simple,
      Dependencies.scalatest      % "test",
      Dependencies.pegdown        % "test",
      Dependencies.scalacheck     % "test",
      Dependencies.mockito        % "test"
    )
  )

  lazy val repositories = List(
    "Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
    "Maven Central" at "http://repo1.maven.org/maven2/"
  )
}

object Version {
  //this project
  val jsvcgen = "0.1.9-SNAPSHOT"

  val gson       = "2.3"
  val json4s     = "3.2.11"
  val scalate    = "1.7.0"
  val scopt      = "3.2.0"
  val logback    = "1.0.13"
  val slf4j      = "1.7.12"
  val junit      = "4.11"
  val scalatest  = "2.2.5"
  val scalacheck = "1.12.2"
  val pegdown    = "1.0.2"
  val mockito    = "1.9.5"
  val wiremock   = "1.56"
  val dispatch   = "0.11.3"
}

object Dependencies {
  lazy val gson          = "com.google.code.gson"     % "gson"            % Version.gson
  lazy val json4sCore    = "org.json4s"               %% "json4s-core"    % Version.json4s
  lazy val json4sJackson = "org.json4s"               %% "json4s-jackson" % Version.json4s
  lazy val scalateCore   = "org.scalatra.scalate"     %% "scalate-core"   % Version.scalate
  lazy val scopt         = "com.github.scopt"         %% "scopt"          % Version.scopt
  lazy val logback       = "ch.qos.logback"           % "logback-classic" % Version.logback
  lazy val slf4j_simple  = "org.slf4j"                % "slf4j-simple"    % Version.slf4j
  lazy val junit         = "junit"                    % "junit"           % Version.junit
  lazy val scalatest     = "org.scalatest"            %% "scalatest"      % Version.scalatest
  lazy val pegdown       = "org.pegdown"              % "pegdown"         % Version.pegdown
  lazy val scalacheck    = "org.scalacheck"           %% "scalacheck"     % Version.scalacheck
  lazy val mockito       = "org.mockito"              % "mockito-all"     % Version.mockito
  lazy val wiremock      = "com.github.tomakehurst"   % "wiremock"        % Version.wiremock
  lazy val dispatch      = "net.databinder.dispatch"  %% "dispatch-core"  % Version.dispatch
}

import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object build extends Build {
  val templateSettings = scalateSettings ++ Seq(
    /**
     * Sets the behavior of recompiling template files.
     * Always template files are recompiled when this setting is true.
     * When you set it to false, they are recompiled only when the modified time of
     * a template file is newer than that of a scala file generated by compilation
     * or a compiled scala file corresponding to a template file doesn't exist yet.
     */
    scalateOverwrite := true,
    scalateTemplateConfig in Compile <<= (baseDirectory) { base =>
      Seq(
        /**
         * A minimal template configuration example.
         * "scalate" is used as a package prefix(the 4th argument of TemplateConfig.apply)
         * if not specified.
         *
         * An example of a scalate usage is as bellow if you have templates/index.ssp.
         *
         * val engine = new TemplateEngine
         * engine.layout("/scalate/index.ssp")
         */
        TemplateConfig(
          base / "src/main/resources/codegen",
          Seq(
            "import com.solidfire.jsvcgen._",
            "import com.solidfire.jsvcgen.codegen._"
          ),
          Nil,
          None
        )
      )
    }
  )

  lazy val root = Project("root", file(".")).settings(templateSettings:_*)
}