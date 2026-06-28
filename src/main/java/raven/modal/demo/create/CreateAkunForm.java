package raven.modal.demo.create;

import net.miginfocom.swing.MigLayout;
import raven.modal.demo.model.ModelAkun;
import raven.modal.demo.service.ServiceAkun;

import javax.swing.*;
import java.awt.*;

public class CreateAkunForm extends JPanel{
    public CreateAkunForm() {initComponents();}

    JTextField txtKodeAkun = new JTextField();
    JTextField txtNamaAkun = new JTextField();
    JComboBox comboTipeAkun = new JComboBox();

    public void init() {
        txtKodeAkun.grabFocus();
    }

    public void loadData(ServiceAkun service, ModelAkun data) {
        if (data != null) {
            txtKodeAkun.setText(String.valueOf(data.getKodeAkun()));
            txtNamaAkun.setText(data.getNamaAkun());
            comboTipeAkun.setSelectedItem(data.getTipeAkun());
        }
    }
    public ModelAkun getData() {
        int kodeRekening = Integer.parseInt(txtKodeAkun.getText());
        String namaRekening = txtNamaAkun.getText().trim();
        String tipeRekening = (String) comboTipeAkun.getSelectedItem();
        System.out.println(comboTipeAkun);
        return new ModelAkun(kodeRekening, namaRekening, tipeRekening);
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

        initComboItem(comboTipeAkun);

        panel.add(new JLabel("Kode Rekening"), "gapy 5 0");
        panel.add(txtKodeAkun);
        panel.add(new JLabel("Nama Rekening"), "gapy 5 0");
        panel.add(txtNamaAkun);
        panel.add(new JLabel("Tipe Rekening"), "gapy 5 0");
        panel.add(comboTipeAkun);

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
        combo.addItem("Aset");
        combo.addItem("Kewajiban");
        combo.addItem("Ekuitas");
        combo.addItem("Pendapatan");
        combo.addItem("Beban");
    }
}
