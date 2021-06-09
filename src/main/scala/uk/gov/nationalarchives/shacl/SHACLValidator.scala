package uk.gov.nationalarchives.shacl

import org.apache.jena.riot.RDFDataMgr
import org.apache.jena.shacl.ShaclValidator
import org.slf4j.LoggerFactory

import java.net.URL
import scala.jdk.CollectionConverters._

case class SHACLValidator(shapesUrl: URL) {

  private val logger = LoggerFactory.getLogger(classOf[SHACLValidator])

  private val validator = ShaclValidator.get()

  def isValid(dataUrl: URL): Boolean = {
    val dataModel = RDFDataMgr.loadModel(dataUrl.toString)
    val shapesGraph = RDFDataMgr.loadGraph(shapesUrl.toString)
    val dataGraph = dataModel.getGraph
    if (validator.conforms(shapesGraph, dataGraph)) {
      true
    } else {
      val report = validator.validate(shapesGraph, dataGraph)
      report.getEntries.asScala.foreach(entry =>
        logger.info(s"File ${dataUrl.getFile} invalid due to: ${entry.message()}"))
      false
    }
  }
}
