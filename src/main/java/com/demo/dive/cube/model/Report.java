package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


public class Report {
    private String reportName;
    private String exportType;

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
