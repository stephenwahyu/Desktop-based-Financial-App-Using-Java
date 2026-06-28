package raven.modal.demo.create;

import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;
import raven.modal.demo.model.ModelTransaksi;
import raven.modal.demo.model.ModelAkun;
import raven.modal.demo.service.ServicePengeluaran;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class CreatePengeluaranForm extends JPanel{
    public CreatePengeluaranForm() {initComponents();}

    JTextField txtKodeTransaksi = new JTextField();
    JComboBox comboNamaAkun = new JComboBox();
    JTextField txtTipeTransaksi = new JTextField();
    DatePicker datePicker = new DatePicker();
    JFormattedTextField dateTanggal = new JFormattedTextField();
    JTextField txtJumlah = new JTextField();
    JTextArea txtDeskripsi = new JTextArea();
    JScrollPane scroll = new JScrollPane(txtDeskripsi);




    public void init() {
        txtKodeTransaksi.grabFocus();
    }

    public void loadData(ServicePengeluaran service, ModelTransaksi data) {
        try {
            for (ModelAkun pos : service.getServicePositions().getAll()) {
                comboNamaAkun.addItem(pos);
                if (data != null && data.getNamaAkun().getKodeAkun() == pos.getKodeAkun()) {
                    comboNamaAkun.setSelectedItem(pos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null) {
            txtKodeTransaksi.setText(String.valueOf(data.getKodeTransaksi()));
            comboNamaAkun.setSelectedItem(data.getNamaAkun());
            txtTipeTransaksi.setText(data.getTipeTransaksi());
            if (data.getTanggalTransaksi() != null) {
                datePicker.setSelectedDate(data.getTanggalTransaksi().toLocalDate());
            }
            txtJumlah.setText(String.valueOf(data.getJumlah()));
            txtDeskripsi.setText(data.getDeskripsi());
        }
    }
    public ModelTransaksi getData() {
        int kodeTransaksi = Integer.parseInt(txtKodeTransaksi.getText());
        ModelAkun namaAkun = (ModelAkun) comboNamaAkun.getSelectedItem();
        String tipeTransaksi = txtTipeTransaksi.getText().trim();
        Date tanggal =  datePicker.isDateSelected() ? Date.valueOf(datePicker.getSelectedDate()) : null;
        int jumlah = Integer.parseInt('-'+txtJumlah.getText());
        String deskripsi = txtDeskripsi.getText();

        System.out.println(comboNamaAkun);
        return new ModelTransaksi(kodeTransaksi, namaAkun, tipeTransaksi, tanggal, jumlah, deskripsi);
    }

    private void initComponents() {
        setLayout(new MigLayout("fill,wrap,insets 5 0 5 0,width 350", "[fill]", "[fill]"));
        add(createBorder(createForm()), "gapx 7 7");
    }

    private Component createBorder(Component component) {
        JPanel panel = new JPanel(new MigLayout("fill,wrap", "[fill]", "[]push[fill,grow]push[]"));
        panel.add(new JSeparator(), "height 2!,gapy 0 0");
        panel.add(component);
        panel.add(new JSeparator(), "height 2!,gapy 0 0");
        return panel;
    }

    private Component createForm(){
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));

        datePicker.setUsePanelOption(true);
        datePicker.setCloseAfterSelected(true);
        datePicker.setEditorValidation(true);
        datePicker.setValidationOnNull(true);
        datePicker.setAnimationEnabled(true);

        txtTipeTransaksi.setText("Pengeluaran");
        txtTipeTransaksi.setEditable(false);
        initComboItem(comboNamaAkun);
        datePicker.setEditor(dateTanggal);
        txtDeskripsi.setWrapStyleWord(true);
        txtDeskripsi.setLineWrap(true);

        panel.add(new JLabel("Kode Transaksi"), "gapy 5 0");
        txtKodeTransaksi.validate();
        panel.add(txtKodeTransaksi);
        panel.add(new JLabel("Nama Akun"), "gapy 5 0");
        panel.add(comboNamaAkun);
        panel.add(new JLabel("Tipe Transaksi"), "gapy 5 0");
        panel.add(txtTipeTransaksi);
        panel.add(new JLabel("Tanggal Transaksi"), "gapy 5 0");
        panel.add(dateTanggal);
        panel.add(new JLabel("Jumlah"), "gapy 5 0");
        panel.add(txtJumlah);
        panel.add(new JLabel("Deskripsi"), "gapy 5 0");
        panel.add(scroll, "height 150,grow,pushy");

//        JComboBox comboCountry = new JComboBox();

//        JTextArea txtAddress = new JTextArea();
//        txtAddress.setWrapStyleWord(true);
//        txtAddress.setLineWrap(true);
//        JScrollPane scroll = new JScrollPane(panel);

//        // style
//        txtFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "First");
//        txtLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Last");
//        txtCompany.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. Tesla Motors");

//        panel.add(new JLabel("Address"), "gapy 5 0");
//        panel.add(scroll, "height 150,grow,pushy");

//        txtAddress.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                if (e.isControlDown() && e.getKeyChar() == 10) {
//                    ModalBorderAction modalBorderAction = ModalBorderAction.getModalBorderAction(CreateTBForm.this);
//                    if (modalBorderAction != null) {
//                        modalBorderAction.doAction(SimpleModalBorder.YES_OPTION);
//                    }
//                }
//            }
//        });
//        initComboItem(comboCountry);


        return panel;
    }

//    private void createTitle(String title) {
//        JLabel lb = new JLabel(title);
//        lb.putClientProperty(FlatClientProperties.STYLE, "" +
//                "font:+2");
//        add(lb, "gapy 5 0");
//        add(new JSeparator(), "height 2!,gapy 0 0");
//    }

    private void initComboItem(JComboBox combo) {
//        combo.addItem("Aset");
//        combo.addItem("Kewajiban");
//        combo.addItem("Ekuitas");
//        combo.addItem("Pendapatan");
//        combo.addItem("Beban");
    }
}
