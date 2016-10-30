val defaultSettings = Seq(scalaVersion := "2.11.8")

lazy val model = (project in file("model")).
  settings(
    defaultSettings,
    PB.targets in Compile := Seq(scalapb.gen(flatPackage = true, singleLineToString = true, grpc = false) ->
      (sourceManaged in Compile).value),
    libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.5.42" % "protobuf")
