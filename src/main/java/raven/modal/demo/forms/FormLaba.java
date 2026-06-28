package raven.modal.demo.forms;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.type.OrientationEnum;
import raven.datetime.DatePicker;
import raven.modal.Toast;
import raven.modal.demo.Demo;
import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelLaba;
import raven.modal.demo.model.ModelLabaTotal;
import raven.modal.demo.modelreport.ModelLabaRugiReport;
import raven.modal.demo.modelreport.ModelLabaRugiTable;
import raven.modal.demo.report.ReportManager;
import raven.modal.demo.service.ServiceLaba;
import raven.modal.demo.system.Form;
import raven.modal.demo.utils.SystemForm;
import raven.modal.demo.utils.table.TableHeaderAlignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SystemForm(name = "Laba / Rugi", description = "Menu untuk melihat laporan Laba", tags = {"laba rugi"})
public class FormLaba extends Form {
    private JTable table1;
    private JTable table2;
    ServiceLaba service = new ServiceLaba();
    DatePicker datePicker1 = new DatePicker();
    DatePicker datePicker2 = new DatePicker();
    String saldoPendapatan = "", saldoBeban = "", labaRugi = "";
    LocalDate[] tanggalInterval = new LocalDate[]{LocalDate.now()};

    public FormLaba() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,wrap", "[fill]", "[][fill,grow]push[]"));
        add(createInfo("Laba / Rugi", "", 1));
        add(createBorder(createTable()), "gapx 7 7");
        add(createBorder(createTableTotal()), "gapx 7 7");
    }

    private JPanel createInfo(String title, String description, int level) {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "20[fill,grow]push[fill]20"));
        JLabel lbTitle = new JLabel(title);
        JTextPane text = new JTextPane();
        text.setText(description);
        text.setEditable(false);
        text.setBorder(BorderFactory.createEmptyBorder());
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +" + (4 - level));
        panel.add(lbTitle);
        JButton cmdExport = new JButton("Ekspor");
        cmdExport.addActionListener(e->showModalPrint());
        panel.add(cmdExport);
//        panel.add(text, "width 500");
        return panel;
    }

//    private Component createTab() {
//        JTabbedPane tabb = new JTabbedPane();
//        tabb.putClientProperty(FlatClientProperties.STYLE, "" +
//                "tabType:card");
//        tabb.addTab("Basic table", createBorder(createBasicTable()));
//        tabb.addTab("Custom table", createBorder(createCustomTable()));
//        return tabb;
//    }

    private Component createBorder(Component component) {
        JPanel panel = new JPanel(new MigLayout("fill,insets 7 0 7 0", "[fill]", "[fill]"));
        panel.add(component);
        return panel;
    }

    private Component createTable() {
        JPanel panel = new JPanel(new MigLayout("fill,wrap,insets 10 0 10 0", "[fill]", "[]push[fill,grow]"));

        // create table model
        Object columns[] = new Object[]{"TIPE AKUN", "NAMA AKUN", "SALDO"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                // allow cell editable at column 0 for checkbox
//                return column == 0;
//            }
//
//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//                // use boolean type at column 0 for checkbox
//                if (columnIndex == 0)
//                    return Boolean.class;
//                // use profile class
//
//                return super.getColumnClass(columnIndex);
//            }
        };

        // create table
        table2 = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table2);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // table option
//        table2.getColumnModel().getColumn(0).setMaxWidth(50);

        // disable reordering table column
        table2.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer


        // apply checkbox custom to table header
//        table2.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // alignment table header
        table2.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table2) {
            @Override
            protected int getAlignment(int column) {
//                if (column == 1) {
//                    return SwingConstants.CENTER;
//                }
                return SwingConstants.CENTER;
            }
        });

        // style
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:$Table.background;");
        table2.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "height:30;" +
                "hoverBackground:null;" +
                "pressedBackground:null;" +
                "separatorColor:$TableHeader.background;");
        table2.putClientProperty(FlatClientProperties.STYLE, "" +
                "rowHeight:70;" +
                "showHorizontalLines:true;" +
                "intercellSpacing:0,1;" +
                "cellFocusColor:$TableHeader.hoverBackground;" +
                "selectionBackground:$TableHeader.hoverBackground;" +
                "selectionInactiveBackground:$TableHeader.hoverBackground;" +
                "selectionForeground:$Table.foreground;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "trackInsets:3,3,3,3;" +
                "thumbInsets:3,3,3,3;" +
                "background:$Table.background;");

        // create title
//        JLabel title = new JLabel("Custom table");
//        title.putClientProperty(FlatClientProperties.STYLE, "" +
//                "font:bold +2");
//        panel.add(title, "gapx 20");

//        // create header
//        panel.add(createHeaderAction());
        panel.add(createHeaderAction());
        panel.add(scrollPane);


        try {
            DatabaseConnection.getInstance().connectToDatabase();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // sample data


        return panel;
    }
    private void loadData() {
        try {
            DefaultTableModel model2 = (DefaultTableModel) table2.getModel();
            if (table2.isEditing()) {
                table2.getCellEditor().stopCellEditing();
            }
            model2.setRowCount(0);
            List<ModelLaba> list2 = service.getAll(Date.valueOf(LocalDate.now()));
            for (ModelLaba d : list2) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model2.addRow(d.toTableRow(table2.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showModalPrint() {
        try {
            List<ModelLabaRugiTable> list = new ArrayList<>();

            TableModel model = table2.getModel();
            for (int row = 0; row < model.getRowCount(); row++) {
                String tipeAkun = model.getValueAt(row, 0).toString();
                String namaAkun = model.getValueAt(row, 1).toString();
                String saldo = model.getValueAt(row, 2).toString();;

                if (tipeAkun == null){
                    continue;
                }

                list.add(new ModelLabaRugiTable(tipeAkun, namaAkun, saldo));
            }

            TableModel model1 = table1.getModel();
                saldoPendapatan = model1.getValueAt(0, 0).toString();
                saldoBeban = model1.getValueAt(0, 1).toString();;
                labaRugi = model1.getValueAt(0, 2).toString();;


            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            LocalDate[] tanggalLocal =tanggalInterval.length == 2? new LocalDate[]{tanggalInterval[0], tanggalInterval[1]} : tanggalInterval.length == 1? new LocalDate[]{tanggalInterval[0]} : new LocalDate[]{LocalDate.now()};
            String tanggal = tanggalLocal.length == 2? df.format(Date.valueOf(tanggalLocal[0])) +" - "+ df.format(Date.valueOf(tanggalLocal[1])) : df.format(Date.valueOf(tanggalLocal[0]));

            ModelLabaRugiReport dataprint = new ModelLabaRugiReport(tanggal,saldoPendapatan, saldoBeban, labaRugi, Demo.DIREKTUR, list);
            JasperPrint print = ReportManager.getInstance().printLabaRugi(dataprint);
            print.setOrientation(OrientationEnum.PORTRAIT);
            JasperPrintManager.printReport(print,true);
//            Toast.show(this, Toast.Type.SUCCESS, "Terprint!");


        } catch (JRException | IOException e){
            e.printStackTrace();
            Toast.show(this, Toast.Type.ERROR, "Gagal print: " + e.getMessage());
        }
    }

    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx, insets 5 20 15 20", "[fill,230][fill]push[fill,230][fill]"));

        JFormattedTextField dateTanggal1 = new JFormattedTextField();
        datePicker1.setEditor(dateTanggal1);

        datePicker1.setUsePanelOption(true);
        datePicker1.setCloseAfterSelected(true);
        datePicker1.setEditorValidation(true);
        datePicker1.setSelectedDate(LocalDate.now());
        datePicker1.setValidationOnNull(true);
        datePicker1.setAnimationEnabled(true);

        JFormattedTextField dateTanggal2 = new JFormattedTextField();
        datePicker2.setEditor(dateTanggal2);

        datePicker2.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker2.setSelectedDateRange(LocalDate.now(),LocalDate.now());

        datePicker2.setUsePanelOption(true);
        datePicker2.setCloseAfterSelected(true);
        datePicker2.setEditorValidation(true);
        datePicker2.setValidationOnNull(true);
        datePicker2.setAnimationEnabled(true);

        JButton cmdDate = new JButton("Urutkan berdasarkan Tanggal");
        cmdDate.setHorizontalAlignment(JButton.TRAILING);
        JButton cmdDateInterval = new JButton("Urutkan berdasarkan Interval Tanggal");

        //actionlistener
        cmdDate.addActionListener(e -> showDate(datePicker1.getSelectedDate()));
        cmdDateInterval.addActionListener(e -> showDateInterval(datePicker2.getSelectedDateRange()));

        panel.add(dateTanggal1);
        panel.add(cmdDate);
        panel.add(dateTanggal2);
        panel.add(cmdDateInterval);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }

    private void showDate(LocalDate search) {
        try {
            tanggalInterval = new LocalDate[]{search};
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();
            DefaultTableModel model2 = (DefaultTableModel) table2.getModel();
            if (table1.isEditing() && table2.isEditing()) {
                table1.getCellEditor().stopCellEditing();
                table2.getCellEditor().stopCellEditing();

            }
            model1.setRowCount(0);
            model2.setRowCount(0);
            List<ModelLabaTotal> list1 = service.getTotal(Date.valueOf(search));
            List<ModelLaba> list2 = service.getAll(Date.valueOf(search));
            for (ModelLabaTotal d : list1) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model1.addRow(d.toTableRow(table1.getRowCount() + 1));
            }
            for (ModelLaba d : list2) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model2.addRow(d.toTableRow(table2.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showDateInterval(LocalDate[] search) {
        try {
            tanggalInterval = search;
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();
            DefaultTableModel model2 = (DefaultTableModel) table2.getModel();
            if (table1.isEditing() && table2.isEditing()) {
                table1.getCellEditor().stopCellEditing();
                table2.getCellEditor().stopCellEditing();

            }
            model1.setRowCount(0);
            model2.setRowCount(0);

            List<ModelLabaTotal> list1 = service.getTotalInterval(Date.valueOf(search[0]), Date.valueOf(search[1]));
            List<ModelLaba> list2 = service.getAllInterval(Date.valueOf(search[0]), Date.valueOf(search[1]));
            for (ModelLabaTotal d : list1) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model1.addRow(d.toTableRow(table1.getRowCount() + 1));
            }
            for (ModelLaba d : list2) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model2.addRow(d.toTableRow(table2.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Component createTableTotal() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[fill]"));

        // create table model
        Object columns[] = new Object[]{"TOTAL PENDAPATAN", "TOTAL BEBAN", "LABA / RUGI BERSIH"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {

        };

        // create table
        table1 = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setBorder(BorderFactory.createCompoundBorder());
        scrollPane.setPreferredSize(new Dimension(500, 80));

        // table option
//        table.getColumnModel().getColumn(0).setMaxWidth(50);

        // disable reordering table column
        table1.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer


        // apply checkbox custom to table header
//        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // alignment table header
        table1.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table1) {
            @Override
            protected int getAlignment(int column) {
                return SwingConstants.CENTER;
            }
        });

        // style
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:$Table.background;");
        table1.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "height:30;" +
                "hoverBackground:null;" +
                "pressedBackground:null;" +
                "separatorColor:$TableHeader.background;");
        table1.putClientProperty(FlatClientProperties.STYLE, "" +
                "rowHeight:40;" +
                "showHorizontalLines:true;" +
                "intercellSpacing:0,1;" +
                "cellFocusColor:$TableHeader.hoverBackground;" +
                "selectionBackground:$TableHeader.hoverBackground;" +
                "selectionInactiveBackground:$TableHeader.hoverBackground;" +
                "selectionForeground:$Table.foreground;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "trackInsets:3,3,3,3;" +
                "thumbInsets:3,3,3,3;" +
                "background:$Table.background;");

        // create header
        panel.add(scrollPane);


        try {
            DatabaseConnection.getInstance().connectToDatabase();
            loadDataTotal();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        return panel;
    }

    private void loadDataTotal() {
        try {
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();
            if (table1.isEditing()) {
                table1.getCellEditor().stopCellEditing();
            }
            model1.setRowCount(0);
            List<ModelLabaTotal> list2 = service.getTotal(Date.valueOf(LocalDate.now()));
            for (ModelLabaTotal d : list2) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model1.addRow(d.toTableRow(table1.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
