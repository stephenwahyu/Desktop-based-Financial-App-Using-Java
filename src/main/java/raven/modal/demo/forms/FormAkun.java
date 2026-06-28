package raven.modal.demo.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.create.CreateAkunForm;
import raven.modal.demo.model.ModelAkun;
import raven.modal.demo.service.ServiceAkun;
import raven.modal.demo.system.Form;
import raven.modal.demo.utils.SystemForm;

import java.util.ArrayList;
import java.util.List;
import raven.modal.demo.utils.table.CheckBoxTableHeaderRenderer;
import raven.modal.demo.utils.table.TableHeaderAlignment;
import raven.modal.option.Location;
import raven.modal.option.Option;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@SystemForm(name = "Akun", description = "Menu untuk membuat, mengubah dan menghapus Akun", tags = {"akun"})
public class FormAkun extends Form {
    private javax.swing.JTable table;
    ServiceAkun service = new ServiceAkun();

    public FormAkun() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap", "[fill]", "[][fill,grow]"));
        add(createInfo("Akun Keuangan", "", 1));
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
        Object columns[] = new Object[]{"SELECT", "KODE AKUN", "NAMA AKUN", "TIPE AKUN"};
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
            List<ModelAkun> list = service.getAll();
            for (ModelAkun d : list) {
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
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("raven/modal/demo/icons/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Tambah Akun");
        JButton cmdEdit = new JButton("Ubah Akun");
        JButton cmdDelete = new JButton("Hapus Akun");
        JButton cmdExport = new JButton("Ekspor");

        //actionlistener
        cmdCreate.addActionListener(e -> showModalAdd());
        cmdEdit.addActionListener(e -> showModalEdit());
        cmdDelete.addActionListener(e -> showModalDelete());
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
//        panel.add(cmdExport);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }

    private void showModalAdd() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1f, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
                new SimpleModalBorder.Option("Tambah",SimpleModalBorder.OK_OPTION),new SimpleModalBorder.Option("Batal",SimpleModalBorder.NO_OPTION)
        };

        CreateAkunForm create = new CreateAkunForm();
        create.loadData(service, null);

        ModalDialog.showModal(this, new SimpleModalBorder(create, "Tambah Akun", options,
                (controller, action) -> {
                    if (action == SimpleModalBorder.OK_OPTION) {
                        // save
                        try {
                            service.create(create.getData());

                            Toast.show(this, Toast.Type.SUCCESS, "Akun dibuat");
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
                new SimpleModalBorder.Option("Simpan",SimpleModalBorder.OK_OPTION),new SimpleModalBorder.Option("Batal",SimpleModalBorder.NO_OPTION)
        };

        List<ModelAkun> list = getSelectedData();
        if (!list.isEmpty()) {
            if (list.size() == 1) {
                ModelAkun data = list.get(0);
                System.out.println(data);
                CreateAkunForm create = new CreateAkunForm();
                create.loadData(service, data);
                ModalDialog.showModal(this, new SimpleModalBorder(create, "Ubah Akun [" + data.getNamaAkun() + "] ?", options, (controller, action) -> {
                    if (action == SimpleModalBorder.OK_OPTION) {
                        // edit
                        try {
                            ModelAkun dataEdit = create.getData();
                            service.edit(dataEdit);
                            Toast.show(this, Toast.Type.SUCCESS, "Akun sudah diperbarui");
                            loadData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (action == SimpleModalBorder.OPENED) {
                        create.init();
                    }
                }), option);
            } else {
                Toast.show(this, Toast.Type.WARNING, "Silahkan pilih satu Akun saja!");
            }
        } else {
            Toast.show(this, Toast.Type.WARNING, "Silahkan pilih salah satu Akun!");
        }
    }

    private void showModalDelete() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setLocation(Location.CENTER, Location.CENTER)
                .setAnimateDistance(0, 0.7f);

        List<ModelAkun> list = getSelectedData();
        SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
                new SimpleModalBorder.Option("Hapus",SimpleModalBorder.OK_OPTION),new SimpleModalBorder.Option("Batal",SimpleModalBorder.NO_OPTION)
        };

        if (!list.isEmpty()) {
            JLabel label = new JLabel("Apakah anda yakin hapus " + list.size() + " Akun?");
            label.setBorder(new EmptyBorder(5, 25, 5, 25));
            ModalDialog.showModal(this, new SimpleModalBorder(label , "Konfirmasi Hapus Akun?", options, (controller, action) -> {
                if (action == SimpleModalBorder.OK_OPTION) {
                    // delete
                    try {
                        for (ModelAkun d : list) {
                            service.delete(d.getKodeAkun());
                        }
                        Toast.show(this, Toast.Type.SUCCESS, "Akun sudah dihapus!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loadData();
                }
            }));
        } else {
            Toast.show(this, Toast.Type.WARNING, "Silahkan pilih Akun yang akan dihapus!");
        }
    }
    private void searchData(String search) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            model.setRowCount(0);
            List<ModelAkun> list = service.search(search);
            for (ModelAkun d : list) {
                model.addRow(d.toTableRow(table.getRowCount() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ModelAkun> getSelectedData() {
        List<ModelAkun> list = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            if ((boolean) table.getValueAt(i, 0)) {
                ModelAkun data = (ModelAkun) table.getValueAt(i, 2);
                list.add(data);
            }
        }
        return list;
    }
}
