package raven.modal.demo.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import raven.modal.demo.component.ToolBarSelection;
import raven.modal.demo.component.chart.BarChart;
import raven.modal.demo.component.chart.PieChart;
import raven.modal.demo.component.chart.SpiderChart;
import raven.modal.demo.component.chart.TimeSeriesChart;
import raven.modal.demo.component.chart.renderer.ChartXYLineRenderer;
import raven.modal.demo.component.chart.themes.ColorThemes;
import raven.modal.demo.component.chart.themes.DefaultChartTheme;
import raven.modal.demo.component.chart.utils.ToolBarCategoryOrientation;
import raven.modal.demo.component.dashboard.CardBox;
import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelRingkasanBulanan;
import raven.modal.demo.model.ModelRingkasanTahunan;
import raven.modal.demo.model.ModelTSBulanan;
import raven.modal.demo.model.ModelTSHarian;
import raven.modal.demo.sample.SampleData;
import raven.modal.demo.service.ServiceDashboard;
import raven.modal.demo.system.Form;
import raven.modal.demo.utils.SystemForm;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

@SystemForm(name = "Dashboard", description = "dashboard form display some details")
public class FormDashboard extends Form {

    private ServiceDashboard service = new ServiceDashboard();

    public FormDashboard() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill", "[fill]", "[grow 0][fill]"));
        createTitle();
        createPanelLayout();
        createCard();
        createChart();
        createOtherChart();
    }

    @Override
    public void formInit() {
        loadData();
    }

    @Override
    public void formRefresh() {
        loadData();
    }

    private void loadData() {
        // load data card
//        cardBox.setValueAt(0, "1,205", "+305 new registered", "+25%", true);
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            List<ModelRingkasanBulanan> list = service.getBulanan();
            NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
            DecimalFormat df = new DecimalFormat("#,##0.##%");
            for (ModelRingkasanBulanan d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                cardBox.setValueAt(0, nf.format(d.getPemasukanBulanIni()), d.getPemasukanBulanIni()>d.getPemasukanBulanLalu()? "more then previous month" : "less then previous month", df.format(d.getPersentasePemasukan()), d.getPemasukanBulanIni()>d.getPemasukanBulanLalu()? true : false);
                cardBox.setValueAt(1, nf.format(d.getPengeluaranBulanIni()), d.getPengeluaranBulanIni()>d.getPengeluaranBulanLalu()? "more then previous month" : "less then previous month", df.format(d.getPersentasePengeluaran()), d.getPengeluaranBulanIni()>d.getPengeluaranBulanLalu()? true : false);
                cardBox.setValueAt(2, nf.format(d.getSaldoBersihBulanIni()), d.getSaldoBersihBulanIni()>d.getSaldoBersihBulanLalu()? "more then previous month" : "less then previous month", df.format(d.getPersentaseSaldoBersih()), d.getSaldoBersihBulanIni()>d.getSaldoBersihBulanLalu()? true : false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // load data chart

        try {
            DatabaseConnection.getInstance().connectToDatabase();
            List<ModelTSHarian> list = service.getTSHarian();
            TimeSeries s1 = new TimeSeries("Pemasukan");
            TimeSeries s2 = new TimeSeries("Pengeluaran");
            for (ModelTSHarian d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                s1.add(new Day(d.getDay(), d.getMonth(), d.getYear()), d.getPemasukan());
                s2.add(new Day(d.getDay(), d.getMonth(), d.getYear()), d.getPengeluaran());
            }
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            dataset.addSeries(s2);
            dataset.addSeries(s1);
            timeSeriesChart.setDataset(dataset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        barChart.setDataset(SampleData.getCategoryDataset());
        spiderChart.setDataset(SampleData.getCategoryDataset());
        pieChart.setDataset(SampleData.getPieDataset());
    }

    private void createTitle() {
        JPanel panel = new JPanel(new MigLayout("fillx", "[]push[]"));
        JLabel title = new JLabel("Dashboard");

        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +3");

        DefaultChartTheme.setChartColors(ColorThemes.DEFAULT);

            if (DefaultChartTheme.setChartColors(ColorThemes.DEFAULT)) {
                DefaultChartTheme.applyTheme(timeSeriesChart.getFreeChart());
                DefaultChartTheme.applyTheme(barChart.getFreeChart());
                DefaultChartTheme.applyTheme(pieChart.getFreeChart());
                DefaultChartTheme.applyTheme(spiderChart.getFreeChart());
                cardBox.setCardIconColor(0, DefaultChartTheme.getColor(0));
                cardBox.setCardIconColor(1, DefaultChartTheme.getColor(1));
                cardBox.setCardIconColor(2, DefaultChartTheme.getColor(2));
                cardBox.setCardIconColor(3, DefaultChartTheme.getColor(3));
            }
        String[] data = new String[]{"Bulanan", "Tahunan"};
        ToolBarSelection<String> toolBarSelection = new ToolBarSelection<>(data , action -> {
            if ("Bulanan".equals(action)){
                try {
                    DatabaseConnection.getInstance().connectToDatabase();
                    List<ModelRingkasanBulanan> list = service.getBulanan();
                    NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
                    DecimalFormat df = new DecimalFormat("#,##0.##%");
                    for (ModelRingkasanBulanan d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                        cardBox.setValueAt(0, nf.format(d.getPemasukanBulanIni()), d.getPemasukanBulanIni()>d.getPemasukanBulanLalu()? "more then previous month" : "less then previous month", df.format(d.getPersentasePemasukan()), d.getPemasukanBulanIni()>d.getPemasukanBulanLalu()? true : false);
                        cardBox.setValueAt(1, nf.format(d.getPengeluaranBulanIni()), d.getPengeluaranBulanIni()>d.getPengeluaranBulanLalu()? "more then previous month" : "less then previous month", df.format(d.getPersentasePengeluaran()), d.getPengeluaranBulanIni()>d.getPengeluaranBulanLalu()? true : false);
                        cardBox.setValueAt(2, nf.format(d.getSaldoBersihBulanIni()), d.getSaldoBersihBulanIni()>d.getSaldoBersihBulanLalu()? "more then previous month" : "less then previous month", df.format(d.getPersentaseSaldoBersih()), d.getSaldoBersihBulanIni()>d.getSaldoBersihBulanLalu()? true : false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            else if ("Tahunan".equals(action)) {
                try {
                    DatabaseConnection.getInstance().connectToDatabase();
                    List<ModelRingkasanTahunan> list = service.getTahunan();
                    NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
                    DecimalFormat df = new DecimalFormat("#,##0.##%");
                    for (ModelRingkasanTahunan d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                        cardBox.setValueAt(0, nf.format(d.getPemasukanTahunIni()), d.getPemasukanTahunIni()>d.getPemasukanTahunLalu()? "more than previous year" : "less than previous year", df.format(d.getPersentasePemasukan()), d.getPemasukanTahunIni()>d.getPemasukanTahunLalu()? true : false);
                        cardBox.setValueAt(1, nf.format(d.getPengeluaranTahunIni()), d.getPengeluaranTahunIni()>d.getPengeluaranTahunLalu()? "more than previous year" : "less than previous year", df.format(d.getPersentasePengeluaran()), d.getPengeluaranTahunIni()>d.getPengeluaranTahunLalu()? true : false);
                        cardBox.setValueAt(2, nf.format(d.getSaldoBersihTahunIni()), d.getSaldoBersihTahunIni()>d.getSaldoBersihTahunLalu()? "more than previous year" : "less than previous year", df.format(d.getPersentaseSaldoBersih()), d.getSaldoBersihTahunIni()>d.getSaldoBersihTahunLalu()? true : false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        panel.add(title);
        panel.add(toolBarSelection);
        add(panel);
    }

    private void createPanelLayout() {
        panelLayout = new JPanel(new DashboardLayout());
        JScrollPane scrollPane = new JScrollPane(panelLayout);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "width:5;" +
                "trackArc:$ScrollBar.thumbArc;" +
                "trackInsets:0,0,0,0;" +
                "thumbInsets:0,0,0,0;");
        add(scrollPane);
    }

    private void createCard() {
        JPanel panel = new JPanel(new MigLayout("fillx", "[fill]"));
        cardBox = new CardBox();
//        cardBox.addCardItem(createIcon("raven/modal/demo/icons/dashboard/customer.svg", DefaultChartTheme.getColor(0)), "Total Customer");
        cardBox.addCardItem(createIcon("raven/modal/demo/icons/dashboard/income.svg", DefaultChartTheme.getColor(1)), "Total Pemasukan");
        cardBox.addCardItem(createIcon("raven/modal/demo/icons/dashboard/expense.svg", DefaultChartTheme.getColor(2)), "Total Pengeluaran");
        cardBox.addCardItem(createIcon("raven/modal/demo/icons/dashboard/profit.svg", DefaultChartTheme.getColor(3)), "Total Saldo");
        panel.add(cardBox);
        panelLayout.add(panel);
    }

    private void createChart() {
        JPanel panel = new JPanel(new MigLayout("gap 14,wrap,fillx", "[fill]", "[350]"));
        timeSeriesChart = new TimeSeriesChart();
        timeSeriesChart.setRenderer(new ChartXYLineRenderer());

        String[] data = new String[]{"Harian", "Bulanan"};
        ToolBarSelection<String> toolBarSelection = new ToolBarSelection<>(data , action -> {
            if ("Bulanan".equals(action)){
                try {
                    DatabaseConnection.getInstance().connectToDatabase();
                    List<ModelTSBulanan> list = service.getTSBulanan();
                    TimeSeries s1 = new TimeSeries("Pemasukan");
                    TimeSeries s2 = new TimeSeries("Pengeluaran");
                    for (ModelTSBulanan d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                        s1.add(new Month(d.getMonth(), d.getYear()), d.getPemasukan());
                        s2.add(new Month(d.getMonth(), d.getYear()), d.getPengeluaran());
                    }
                    TimeSeriesCollection dataset = new TimeSeriesCollection();
                    dataset.addSeries(s2);
                    dataset.addSeries(s1);
                    timeSeriesChart.setDataset(dataset);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            else if ("Harian".equals(action)) {
                try {
                    DatabaseConnection.getInstance().connectToDatabase();
                    List<ModelTSHarian> list = service.getTSHarian();
                    TimeSeries s1 = new TimeSeries("Pemasukan");
                    TimeSeries s2 = new TimeSeries("Pengeluaran");
                    for (ModelTSHarian d : list) {
//                if (d.getKategori() == null){
//                    continue;
//                }
                        s1.add(new Day(d.getDay(), d.getMonth(), d.getYear()), d.getPemasukan());
                        s2.add(new Day(d.getDay(), d.getMonth(), d.getYear()), d.getPengeluaran());
                    }
                    TimeSeriesCollection dataset = new TimeSeriesCollection();
                    dataset.addSeries(s2);
                    dataset.addSeries(s1);
                    timeSeriesChart.setDataset(dataset);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        barChart = new BarChart();
        timeSeriesChart.add(toolBarSelection, "al trailing,grow 0", 0);
        barChart.add(new ToolBarCategoryOrientation(barChart.getFreeChart()), "al trailing,grow 0", 0);
        panel.add(timeSeriesChart);
        panel.add(barChart);
        panelLayout.add(panel);
    }

    private void createOtherChart() {
        JPanel panel = new JPanel(new MigLayout("fillx,gap 14", "[fill,300::]", "[300]"));
        spiderChart = new SpiderChart();
        pieChart = new PieChart();
        panel.add(spiderChart);
        panel.add(pieChart);
        panelLayout.add(panel);
    }

    private Icon createIcon(String icon, Color color) {
        return new FlatSVGIcon(icon, 0.4f).setColorFilter(new FlatSVGIcon.ColorFilter(color1 -> color));
    }

    private JPanel panelLayout;
    private CardBox cardBox;

    private TimeSeriesChart timeSeriesChart;
    private BarChart barChart;
    private SpiderChart spiderChart;
    private PieChart pieChart;

    private class DashboardLayout implements LayoutManager {

        private int gap = 0;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int width = (insets.left + insets.right);
                int height = insets.top + insets.bottom;
                int g = UIScale.scale(gap);
                int count = parent.getComponentCount();
                for (int i = 0; i < count; i++) {
                    Component com = parent.getComponent(i);
                    Dimension size = com.getPreferredSize();
                    height += size.height;
                }
                if (count > 1) {
                    height += (count - 1) * g;
                }
                return new Dimension(width, height);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(10, 10);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int g = UIScale.scale(gap);
                int count = parent.getComponentCount();
                for (int i = 0; i < count; i++) {
                    Component com = parent.getComponent(i);
                    Dimension size = com.getPreferredSize();
                    com.setBounds(x, y, width, size.height);
                    y += size.height + g;
                }
            }
        }
    }
}
