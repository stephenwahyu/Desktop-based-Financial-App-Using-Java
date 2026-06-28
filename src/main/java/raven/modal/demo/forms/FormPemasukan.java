package raven.modal.demo.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import raven.modal.demo.Demo;
import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.create.CreatePemasukanForm;
import raven.modal.demo.model.ModelTransaksi;
import raven.modal.demo.modelreport.ModelTransaksiReport;
import raven.modal.demo.modelreport.ModelTransaksiTable;
import raven.modal.demo.report.ReportManager;
import raven.modal.demo.service.ServicePemasukan;
import raven.modal.demo.system.Form;
import raven.modal.demo.utils.SystemForm;
import raven.modal.demo.utils.table.CheckBoxTableHeaderRenderer;
import raven.modal.demo.utils.table.TableHeaderAlignment;
import raven.modal.option.Location;
import raven.modal.option.Option;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import raven.modal.demo.auth.Login;

//import static raven.modal.demo.system.FormManager.frame;

@SystemForm(name = "Transaksi Pemasukan", description = "Menu untuk membuat, mengubah, dan menghapus Transaksi Pemasukan", tags = {"transaksi pemasukan"})
public class FormPemasukan extends Form {
    private JTable table;
    ServicePemasukan service = new ServicePemasukan();

    public FormPemasukan() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap", "[fill]", "[][fill,grow]"));
        add(createInfo("Transaksi Pemasukan", "", 1));
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
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[]0[fill,grow]"));

        // create table model
        Object columns[] = new Object[]{"SELECT", "KODE TRANSAKSI", "NAMA AKUN", "TIPE TRANSAKSI", "TANGGAL TRANSAKSI", "JUMLAH", "DESKRIPSI"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // allow cell editable at column 0 for checkbox
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // use boolean type at column 0 for checkbox
                if (columnIndex == 0)
                    return Boolean.class;
                // use profile class

                return super.getColumnClass(columnIndex);
            }
        };

        // create table
        table = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(6).setMinWidth(200);
        
        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer


        // apply checkbox custom to table header
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // alignment table header
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table) {
            @Override
            protected int getAlignment(int column) {
                if (column == 1) {
                    return SwingConstants.CENTER;
                }
                return SwingConstants.LEADING;
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

        // create header
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
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            model.setRowCount(0);
            List<ModelTransaksi> list = service.getAll();
            for (ModelTransaksi d : list) {
                model.addRow(d.toTableRow(table.getRowCount() + 1));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 15 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Cari berdasarkan nama akun...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("raven/modal/demo/icons/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Tambah Transaksi Pemasukan");
        JButton cmdEdit = new JButton("Ubah Transaksi Pemasukan");
        JButton cmdDelete = new JButton("Hapus Transaksi Pemasukan");
        JButton cmdExport = new JButton("Ekspor");
        JButton cmdPrint = new JButton("Print");

        //actionlistener
        cmdCreate.addActionListener(e -> showModalAdd());
        cmdEdit.addActionListener(e -> showModalEdit());
        cmdDelete.addActionListener(e -> showModalDelete());
        cmdExport.addActionListener(e -> showModalExport());
        cmdPrint.addActionListener(e->showModalPrint());
        txtSearch.addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // Optional: Handle keyPressed
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                searchData(txtSearch.getText().trim());
            }

            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                // Optional: Handle keyTyped
            }
        });

        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdDelete);
        panel.add(cmdExport);
        panel.add(cmdPrint);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }

    private void showModalPrint() {
        try {
            List<ModelTransaksi> sData = getSelectedData();
            List<ModelTransaksiTable> list = new ArrayList<>();
            int total = 0;

            DateFormat df = new SimpleDateFormat("dd MMM yyyy");

            if (!sData.isEmpty()) {
                for (ModelTransaksi data : sData) {
                    list.add(new ModelTransaksiTable(data.getNamaAkun().getNamaAkun(),
                            df.format(data.getTanggalTransaksi()), data.getDeskripsi(), data.getJumlah()));
                    total += data.getJumlah();
                }
            } else {
                for (int i = 0; i < table.getRowCount(); i++) {
                    ModelTransaksi data = (ModelTransaksi) table.getValueAt(i, 1);
                    list.add(new ModelTransaksiTable(data.getNamaAkun().getNamaAkun(),
                            df.format(data.getTanggalTransaksi()), data.getDeskripsi(), data.getJumlah()));
                    total += data.getJumlah();
                }
            }

            ModelTransaksiReport dataprint = new ModelTransaksiReport(Demo.DIREKTUR, Login.role, total, list);
            JasperPrint print = ReportManager.getInstance().printMasuk(dataprint);
            print.setOrientation(OrientationEnum.PORTRAIT);
            JasperPrintManager.printReport(print,true);
//            Toast.show(this, Toast.Type.SUCCESS, "Terprint!");


        } catch (JRException | IOException e) {
            e.printStackTrace();
            Toast.show(this, Toast.Type.ERROR, "Gagal print: " + e.getMessage());
        }
    }


    private void showModalExport() {
        JPanel panel = new JPanel();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai:");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Add file filters
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF files", "pdf"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel files", "xlsx"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Word files", "docx"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PPTX files", "pptx"));

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showSaveDialog(panel);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                List<ModelTransaksi> sData = getSelectedData();
                List<ModelTransaksiTable> list = new ArrayList<>();
                int total = 0;
                File selectedFile = fileChooser.getSelectedFile();

                if (selectedFile == null || selectedFile.getName().isEmpty()) {
                    throw new IOException("No file selected or invalid file name!");
                }

                // Get selected file extension
                String fileType = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];
                if (!selectedFile.getName().endsWith("." + fileType)) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + "." + fileType);
                }

                DateFormat df = new SimpleDateFormat("dd MMM yyyy");

                if (!sData.isEmpty()) {
                    for (ModelTransaksi data : sData) {
                        list.add(new ModelTransaksiTable(data.getNamaAkun().getNamaAkun(),
                                df.format(data.getTanggalTransaksi()), data.getDeskripsi(), data.getJumlah()));
                        total += data.getJumlah();
                    }
                } else {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        ModelTransaksi data = (ModelTransaksi) table.getValueAt(i, 1);
                        list.add(new ModelTransaksiTable(data.getNamaAkun().getNamaAkun(),
                                df.format(data.getTanggalTransaksi()), data.getDeskripsi(), data.getJumlah()));
                        total += data.getJumlah();
                    }
                }

                ModelTransaksiReport dataprint = new ModelTransaksiReport(Demo.DIREKTUR, Login.role, total, list);
                JasperPrint print = ReportManager.getInstance().printMasuk(dataprint);

                switch (fileType) {
                    case "pdf":
                        JasperExportManager.exportReportToPdfFile(print, selectedFile.getAbsolutePath());
                        break;
                    case "xlsx":
                        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                        xlsxExporter.setExporterInput(new SimpleExporterInput(print));
                        xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(selectedFile));
                        xlsxExporter.exportReport();
                        break;
                    case "docx":
                        JRDocxExporter docxExporter = new JRDocxExporter();
                        docxExporter.setExporterInput(new SimpleExporterInput(print));
                        docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(selectedFile));
                        docxExporter.exportReport();
                        break;
                    case "pptx":
                        JRPptxExporter pptxExporterExporter = new JRPptxExporter();
                        pptxExporterExporter.setExporterInput(new SimpleExporterInput(print));
                        pptxExporterExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(selectedFile));
                        pptxExporterExporter.exportReport();
                        break;
                    default:
                        throw new IOException("Tidak dapat ekspor mengekspor dengan format ini!");
                }
                Toast.show(this, Toast.Type.SUCCESS, "Tersimpan dengan format " + fileType.toUpperCase() + "!");

            } catch (JRException | IOException e) {
                e.printStackTrace();
                Toast.show(this, Toast.Type.ERROR, "Gagal mengekspor: " + e.getMessage());
            }

        } else if (result == JFileChooser.CANCEL_OPTION) {
            Toast.show(this, Toast.Type.INFO, "Ekspor dibatalkan!");
        }

    }

    private void showModalAdd() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1f, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
                new SimpleModalBorder.Option("Tambah", SimpleModalBorder.OK_OPTION), new SimpleModalBorder.Option("Batal", SimpleModalBorder.NO_OPTION)
        };

        CreatePemasukanForm create = new CreatePemasukanForm();
        create.loadData(service, null);

        ModalDialog.showModal(this, new SimpleModalBorder(create, "Tambah Transaksi", options,
                (controller, action) -> {
                    if (action == SimpleModalBorder.OK_OPTION) {
                        // save
                        try {
                            service.create(create.getData());
                            Toast.show(this, Toast.Type.SUCCESS, "Transaksi Pemasukan dibuat");
                            loadData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (action == SimpleModalBorder.OPENED) {
                        create.init();
                    }
                }), option);
    }

    private void showModalEdit() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-2, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
                new SimpleModalBorder.Option("Simpan", SimpleModalBorder.OK_OPTION), new SimpleModalBorder.Option("Batal", SimpleModalBorder.NO_OPTION)
        };

        List<ModelTransaksi> list = getSelectedData();
        if (!list.isEmpty()) {
            if (list.size() == 1) {
                ModelTransaksi data = list.get(0);
                System.out.println(data);
                CreatePemasukanForm create = new CreatePemasukanForm();
                create.loadData(service, data);
                ModalDialog.showModal(this, new SimpleModalBorder(create, "Ubah Transaksi Pemasukan [" + data.getNamaAkun() + "] ?", options, (controller, action) -> {
                    if (action == SimpleModalBorder.OK_OPTION) {
                        // edit
                        try {
                            ModelTransaksi dataEdit = create.getData();
                            service.edit(dataEdit);
                            Toast.show(this, Toast.Type.SUCCESS, "Transaksi sudah diperbarui");
                            loadData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (action == SimpleModalBorder.OPENED) {
                        create.init();
                    }
                }), option);
            } else {
                Toast.show(this, Toast.Type.WARNING, "Silahkan pilih satu Transaksi saja!");
            }
        } else {
            Toast.show(this, Toast.Type.WARNING, "Silahkan pilih salah satu Transaksi!");
        }
    }

    private void showModalDelete() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setLocation(Location.CENTER, Location.CENTER)
                .setAnimateDistance(0, 0.7f);

        List<ModelTransaksi> list = getSelectedData();
        SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
                new SimpleModalBorder.Option("Hapus", SimpleModalBorder.OK_OPTION), new SimpleModalBorder.Option("Batal", SimpleModalBorder.NO_OPTION)
        };

        if (!list.isEmpty()) {
            JLabel label = new JLabel("Apakah anda yakin hapus " + list.size() + " Transaksi Pemasukan?");
            label.setBorder(new EmptyBorder(5, 25, 5, 25));
            ModalDialog.showModal(this, new SimpleModalBorder(label, "Konfirmasi Hapus Transaksi?", options,
                    (controller, action) -> {
                        if (action == SimpleModalBorder.OK_OPTION) {
                            // delete
                            try {
                                for (ModelTransaksi d : list) {
                                    service.delete(d.getKodeTransaksi());
                                }
                                Toast.show(this, Toast.Type.SUCCESS, "Transaksi Pemasukan sudah dihapus!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            loadData();
                        }
                    }));
        } else {
            Toast.show(this, Toast.Type.WARNING, "Silahkan pilih Transaksi yang akan dihapus!");
        }
    }

    private void searchData(String search) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            model.setRowCount(0);
            List<ModelTransaksi> list = service.search(search);
            for (ModelTransaksi d : list) {
                model.addRow(d.toTableRow(table.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ModelTransaksi> getSelectedData() {
        List<ModelTransaksi> list = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            if ((boolean) table.getValueAt(i, 0)) {
                ModelTransaksi data = (ModelTransaksi) table.getValueAt(i, 1);
                list.add(data);
            }
        }
        return list;
    }

//    private ModelMasukReport getAllData() {
//        List<ModelMasukTable> list = new ArrayList<>();
//        int total = 0;
//        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
//        for (int i = 0; i < table.getRowCount(); i++) {
//            ModelTransaksi data = (ModelTransaksi) table.getValueAt(i, 1);
//            list.add(new ModelMasukTable(data.getNamaAkun().getNamaAkun(), df.format(data.getTanggalTransaksi()), data.getDeskripsi(), data.getJumlah()));
//            total += data.getJumlah();
//        }
//        ModelMasukReport dataprint = new ModelMasukReport("Admin", "Admin 2", String.valueOf(total), list);
//        return dataprint;
//    }
}
