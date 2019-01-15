package org.elemental.rules

import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JsonDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import java.io.ByteArrayOutputStream
import java.sql.Connection


class Jasper(val conn: Connection? = null) {
    fun createOnDisk(params: HashMap<String, Any>, jasperFile: String, destFileName: String) {
        val resourceFile = "/jasper/build/${jasperFile}.jasper"
        val inputStream = this.javaClass.getResourceAsStream(resourceFile)
        val jp = JasperFillManager.fillReport(inputStream, params, conn)
        JasperExportManager.exportReportToPdfFile(jp, destFileName)
    }

    fun create(params: HashMap<String, Any>, jasperFile: String): ByteArray? {
        val resourceFile = "/jasper/build/${jasperFile}.jasper"
        val inputStream = this.javaClass.getResourceAsStream(resourceFile)
        val jp = JasperFillManager.fillReport(inputStream, params, conn)
        return generatePDF(jp)
    }

    fun fromJSON(params: HashMap<String, Any>? = java.util.HashMap(), json: String, jasperFile: String): ByteArray? {
        val dataSource = JsonDataSource(json.byteInputStream(), "data")
        val layout = this.javaClass.getResourceAsStream("/jasper/build/${jasperFile}.jasper")
        val print = JasperFillManager.fillReport(layout, params, dataSource)
        return generatePDF(print)
    }

    private fun generatePDF(jp: JasperPrint?): ByteArray? {
        val pdfExporter = JRPdfExporter()
        pdfExporter.setExporterInput(SimpleExporterInput(jp))
        val pdfReportStream = ByteArrayOutputStream()
        pdfExporter.setExporterOutput(SimpleOutputStreamExporterOutput(pdfReportStream))
        pdfExporter.exportReport()
        val byteArray = pdfReportStream.toByteArray()
        pdfReportStream.close()
        return byteArray
    }

}