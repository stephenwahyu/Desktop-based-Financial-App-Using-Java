package raven.modal.demo.create;

import net.miginfocom.swing.MigLayout;
import raven.modal.demo.model.ModelAnggaran;
import raven.modal.demo.model.ModelAkun;
import raven.modal.demo.service.ServiceAnggaran;

import javax.swing.*;
import java.awt.*;

public class CreateAnggaranForm extends JPanel{
    public CreateAnggaranForm() {initComponents();}

    JTextField txtKodeAnggaran = new JTextField();
    JComboBox comboNamaAkun = new JComboBox();
    JTextField txtJumlahAnggaran = new JTextField();
    JComboBox comboPeriodeAnggaran = new JComboBox();




    public void init() {
        txtKodeAnggaran.grabFocus();
    }

    public void loadData(ServiceAnggaran service, ModelAnggaran data) {
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
            txtKodeAnggaran.setText(String.valueOf(data.getKodeAnggaran()));
            comboNamaAkun.setSelectedItem(data.getNamaAkun());
            txtJumlahAnggaran.setText(String.valueOf(data.getJumlahAnggaran()));
            comboPeriodeAnggaran.setSelectedItem(data.getPeriodeAnggaran());
        }
    }
    public ModelAnggaran getData() {
        int kodeAnggaran = Integer.parseInt(txtKodeAnggaran.getText());
        ModelAkun namaAkun = (ModelAkun) comboNamaAkun.getSelectedItem();
        int jumlahAnggaran = Integer.parseInt(txtJumlahAnggaran.getText());
        String periodeAnggaran = String.valueOf(comboPeriodeAnggaran.getSelectedItem());

        System.out.println(comboNamaAkun);
        return new ModelAnggaran(kodeAnggaran, namaAkun, jumlahAnggaran, periodeAnggaran);
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


        initComboItem(comboPeriodeAnggaran);

        panel.add(new JLabel("Kode Anggaran"), "gapy 5 0");
        panel.add(txtKodeAnggaran);
        panel.add(new JLabel("Nama Akun"), "gapy 5 0");
        panel.add(comboNamaAkun);
        panel.add(new JLabel("Jumlah Anggaran"), "gapy 5 0");
        panel.add(txtJumlahAnggaran);
        panel.add(new JLabel("Periode Anggaran"), "gapy 5 0");
        panel.add(comboPeriodeAnggaran);

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
        combo.addItem("Bulanan");
        combo.addItem("Tahunan");
    }
}
