package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Report;
import com.demo.dive.cube.model.Supplier;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Value("${reports.path}")
    private String reportsPath;
    @Value("${export.path}")
    private String exportPath;

	private void getPDF(String filename, HttpServletRequest request, HttpServletResponse response) {

		File pdfFile = new File(filename);
		if (pdfFile.exists())
        {
			Path file = Paths.get(filename);
            response.setContentType("application/pdf");
            response.addHeader("content-disposition", "inline;filename=" + filename);
            try
            {
            	Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
	}

    public void export(List<?> data, String reportName,HttpServletRequest request,HttpServletResponse response) {
		try {
			String exportDest = exportPath+reportName+"-"+System.currentTimeMillis()+".pdf";
			File file = ResourceUtils.getFile(reportsPath+reportName+".jrxml");
			JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);
			Map<String, Object> map = new HashMap<String, Object>();
			JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
			JasperExportManager.exportReportToPdfFile(jp, exportDest);
			getPDF(exportDest,request,response);
			//JasperPrintManager.printReport(jp, false);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
