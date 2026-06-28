package raven.modal.demo.report;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.type.OrientationEnum;
import raven.modal.demo.modelreport.ModelLabaRugiReport;
import raven.modal.demo.modelreport.ModelNeracaReport;
import raven.modal.demo.modelreport.ModelTransaksiReport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {
    public static ReportManager instance;

    private JasperReport transaksiMasuk;
    private JasperReport transaksiKeluar;
    private JasperReport neraca;
    private JasperReport labaRugi;

    public static ReportManager getInstance(){
        if (instance== null)
            instance = new ReportManager();
        return instance;
    }

    private ReportManager(){
    }

    public void compile() throws JRException {
        transaksiMasuk = JasperCompileManager.compileReport(getClass().getResourceAsStream("/raven/modal/demo/report/Pemasukan.jrxml"));
        transaksiKeluar = JasperCompileManager.compileReport(getClass().getResourceAsStream("/raven/modal/demo/report/Pengeluaran.jrxml"));
        neraca = JasperCompileManager.compileReport(getClass().getResourceAsStream("/raven/modal/demo/report/Neraca.jrxml"));
        labaRugi = JasperCompileManager.compileReport(getClass().getResourceAsStream("/raven/modal/demo/report/LabaRugi.jrxml"));
    }

    public JasperPrint printMasuk(ModelTransaksiReport data) throws JRException, IOException {
        Map para = new HashMap();
        para.put("total", data.getTotal());
        para.put("disetujui", data.getDisetujui());
        para.put("diterima", data.getDiterima());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getTable());
        JasperPrint print = JasperFillManager.fillReport(transaksiMasuk, para, dataSource);
        print.setOrientation(OrientationEnum.PORTRAIT);
        return print;
    }

    public JasperPrint printKeluar(ModelTransaksiReport data) throws JRException, IOException {
        Map para = new HashMap();
        para.put("total", data.getTotal());
        para.put("disetujui", data.getDisetujui());
        para.put("diterima", data.getDiterima());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getTable());
        JasperPrint print = JasperFillManager.fillReport(transaksiKeluar, para, dataSource);
        print.setOrientation(OrientationEnum.PORTRAIT);
        return print;
    }

    public JasperPrint printNeraca(ModelNeracaReport data) throws JRException, IOException {
        Map para = new HashMap();
        para.put("tanggal", data.getTanggal());
        para.put("saldoAset", data.getSaldoAset());
        para.put("saldoKewajiban", data.getSaldoKewajiban());
        para.put("saldoEkuitas", data.getSaldoEkuitas());
        para.put("saldoKewajibanEkuitas", data.getSaldoKewajibanEkuitas());
        para.put("disetujui", data.getDisetujui());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getTable());
        JasperPrint print = JasperFillManager.fillReport(neraca, para, dataSource);
        print.setOrientation(OrientationEnum.PORTRAIT);
        return print;
    }
    public JasperPrint printLabaRugi(ModelLabaRugiReport data) throws JRException, IOException {
        Map para = new HashMap();
        para.put("tanggal", data.getTanggal());
        para.put("saldoPendapatan", data.getSaldoPendapatan());
        para.put("saldoBeban", data.getSaldoBeban());
        para.put("labaRugi", data.getLabaRugi());
        para.put("disetujui", data.getDisetujui());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getTable());
        JasperPrint print = JasperFillManager.fillReport(labaRugi, para, dataSource);
        print.setOrientation(OrientationEnum.PORTRAIT);
        return print;
    }

//    private void view(JasperPrint print) throws JRException, IOException {
////        JasperViewer.viewReport(print, false);
////        JasperExportManager.exportReportToPdfFile(print, "Jurnal.pdf");
////        JRPdfExporter exporter = new JRPdfExporter();
////        exporter.setExporterInput(new SimpleExporterInput(print));
////        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
////        exporter.exportReport();
//    }
}
