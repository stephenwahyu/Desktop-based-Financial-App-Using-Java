package raven.modal.demo.forms;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;
import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelArusKas;
import raven.modal.demo.model.ModelLaba;
import raven.modal.demo.model.ModelLabaTotal;
import raven.modal.demo.service.ServiceArusKas;
import raven.modal.demo.service.ServiceLaba;
import raven.modal.demo.system.Form;
import raven.modal.demo.utils.SystemForm;
import raven.modal.demo.utils.table.TableHeaderAlignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SystemForm(name = "Arus Kas", description = "Menu untuk melihat laporan Arus Kas", tags = {"arus kas"})
public class FormArusKas extends Form {
    private JTable table;
    ServiceArusKas service = new ServiceArusKas();
    DatePicker datePicker1 = new DatePicker();
    DatePicker datePicker2 = new DatePicker();

    public FormArusKas() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,wrap", "[fill]", "[][fill,grow]push[]"));
        add(createInfo("Arus Kas", "", 1));
        add(createBorder(createTable()), "gapx 7 7");
    }

    private JPanel createInfo(String title, String description, int level) {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "20[fill]20"));
        JLabel lbTitle = new JLabel(title);
        JTextPane text = new JTextPane();
        text.setText(description);
        text.setEditable(false);
        text.setBorder(BorderFactory.createEmptyBorder());
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +" + (4 - level));
        panel.add(lbTitle);
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
        Object columns[] = new Object[]{"KATEGORI", "PEMASUKAN", "PENGELUARAN", "TOTAL"};
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
        table = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // table option
//        table2.getColumnModel().getColumn(0).setMaxWidth(50);

        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer


        // apply checkbox custom to table header
//        table2.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // alignment table header
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table) {
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
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "height:30;" +
                "hoverBackground:null;" +
                "pressedBackground:null;" +
                "separatorColor:$TableHeader.background;");
        table.putClientProperty(FlatClientProperties.STYLE, "" +
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
            DefaultTableModel model2 = (DefaultTableModel) table.getModel();
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            model2.setRowCount(0);
            List<ModelArusKas> list2 = service.getAll(Date.valueOf(LocalDate.now()));
            for (ModelArusKas d : list2) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model2.addRow(d.toTableRow(table.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();

            }
            model.setRowCount(0);
            List<ModelArusKas> list = service.getAll(Date.valueOf(search));
            for (ModelArusKas d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model.addRow(d.toTableRow(table.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showDateInterval(LocalDate[] search) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            model.setRowCount(0);
            List<ModelArusKas> list = service.getAllInterval(Date.valueOf(search[0]), Date.valueOf(search[1]));
            for (ModelArusKas d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                model.addRow(d.toTableRow(table.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
