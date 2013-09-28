import sbt._
import Keys._
import sbt.Reference._

object build extends Build {

  import DependencyManagement._

  // Settings shared by all sub-projects.
  val standardSettings: Seq[Project.Setting[_]] =
    Seq[Project.Setting[_]](
      ivyXML := DependencyManagement.ivyExclusionsAndOverrides,
      scalaVersion := "2.10.2",
      resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
        "releases" at "http://scala-tools.org/repo-releases")
    )

  //
  // PROJECTS
  //

  // Parent Project, it aggregates all others.
  lazy val nearlyCompleteBinaryTree = Project(
    id = "nearly-complete-binary-tree",
    base = file("."),
    settings = Defaults.defaultSettings ++ standardSettings,
    aggregate = Seq[ProjectReference](nearlyCompleteBinaryTreeCore)
  )

  lazy val nearlyCompleteBinaryTreeCore = Project(
    id = "nearly-complete-binary-tree-core",
    base = file("nearly-complete-binary-tree-core"),
    settings = Defaults.defaultSettings ++ standardSettings ++ Seq(
      libraryDependencies ++= Seq(ScalazCore, ScalazConcurrent, Specs, JUnit, Scalacheck, MockitoAll, CommonsIo,
        TreeHugger, JodaConvert, JodaTime, ScalaMeter, CommonsLang, Fastutil),
      testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
      logBuffered := false
    )
  )

}
