val defaultSettings = Seq(
  scalaVersion := "2.11.8",
  parallelExecution in Test := false,
  logBuffered in Test := false,
  testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD"))

defaultSettings

lazy val model = (project in file("model")).
  settings(
    defaultSettings,
    PB.targets in Compile := Seq(scalapb.gen(flatPackage = true, singleLineToString = true, grpc = false) ->
      (sourceManaged in Compile).value),
    libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.5.42" % "protobuf")

lazy val domain = (project in file("domain")).
  settings(defaultSettings, libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test").
  dependsOn(model)

val chromedriver = file(sys.props.getOrElse("webdriver.chrome.driver", "chromedriver")).getAbsolutePath

lazy val tool = (project in file("tool")).
  enablePlugins(PlayScala).
  settings(
    defaultSettings,
    javaOptions in Test += s"-Dwebdriver.chrome.driver=$chromedriver",
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test").
  dependsOn(domain)
