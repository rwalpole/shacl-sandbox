package uk.gov.nationalarchives.shacl

import org.apache.jena.riot.RDFDataMgr
import org.apache.jena.shacl.ShaclValidator
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._
import java.net.URL

case class SHACLValidator(shapesUrl: URL) {

  private val logger = LoggerFactory.getLogger(classOf[SHACLValidator])

  private val validator = ShaclValidator.get()

  def isValid(dataUrl: URL): Boolean = {
    val shapesGraph = RDFDataMgr.loadGraph(shapesUrl.toString)
    val dataGraph = RDFDataMgr.loadGraph(dataUrl.toString)
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
