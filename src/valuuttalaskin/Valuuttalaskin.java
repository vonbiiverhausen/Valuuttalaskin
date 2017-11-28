package valuuttalaskin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class Valuuttalaskin extends JFrame {
    //Muuntokurssi Kaikki muuntokurssit pohjautuvat niiden arvosta euroon.
    //Esim. 1 eur = 1 eur. 1 eur = 1,163 usd jne.
    private double eurKurssi = 1;
    private double usdKurssi = 1.163635;
    private double cadKurssi = 1.494132;
    private double gbpKurssi = 0.874757522;
    private double jpyKurssi = 132.616551;

    // Ikkuna
    private JFrame ikkuna = new JFrame();

    // Paneelit
    private JPanel pohjaPanel = new JPanel(new GridLayout(2, 1));
    private JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel btPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    // Tekstikentät
    private JTextField tfMuunnettava = new JTextField(6);
    private JTextField tfMuunnettu = new JTextField(6);

    // Tekstiselitteet
    private JLabel nuoli = new JLabel(">>>");

    // Napit
    private JButton btMuunna = new JButton("Muunna");
    private JButton btTyhjenna = new JButton("Tyhjennä");

    // Alasvetovalikot
    private JComboBox cbMuunnettava = new JComboBox();
    private JComboBox cbMuunnettu = new JComboBox();
    
    


    // Muunna -napin funktio:
    // Muunnettava -muuttuja on muunnettavan valuutan arvo ja haetaan fromVal-tekstikentästä
    // cbMuunnettava- ja cbMuunnettu -alasvetovalikoista otetaan indeksit. Näitä käytetään valuuttakurssien haussa
    // 
    // Muunnos -muuttuja on muunnetun valuutan arvo. Tämä lasketaan kertomalla ja jakamalla valuuttakursseilla
    // 
    // Muutoin kopioi virhe konsoliin
    class Muunna implements ActionListener {
        
        // Haetaan valuuttakurssi. Kurssi riippuu alasvetovalikon indeksistä
        public double haeKurssi(int indeksi) {
            switch (indeksi) {
                    case 0: return eurKurssi; // EUR -> EUR
                    case 1: return usdKurssi; // EUR -> USD
                    case 2: return cadKurssi; // EUR -> CAD
                    case 3: return gbpKurssi; // EUR -> GBP
                    case 4: return jpyKurssi; // EUR -> JPY
                    default: return eurKurssi;
                }
        }
        
        public void actionPerformed(ActionEvent tapahtuma) {
            try {
                double muunnettava = Double.parseDouble(tfMuunnettava.getText());
                int convertTo = cbMuunnettava.getSelectedIndex();
                int convertFrom = cbMuunnettu.getSelectedIndex();
                
                double muunnos = muunnettava*haeKurssi(convertFrom)/haeKurssi(convertTo);
                DecimalFormat df = new DecimalFormat("###.##");
                
                tfMuunnettu.setText(String.format("%.2f", muunnos));
                //tfMuunnettu.setText(""+df.format(muunnos));
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // Tyhjenna -napin funktio: Tyhjentää tekstikentät eli korvaa tyhjän tekstin
    class Tyhjenna implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            tfMuunnettava.setText("");
            tfMuunnettu.setText("");
        }
    }
    
    // Konstruktori
    public Valuuttalaskin() {
        this.luoIkkuna();
        this.asetaPaneelit();
    }
    
    // Luodaan ikkuna
    public void luoIkkuna() {
        ikkuna.setSize(400, 200);
        ikkuna.setTitle("Valuuttamuunnin v0.2");
        ikkuna.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ikkuna.setVisible(true);
    }
    
    // Asetetaan paneelit ja niiden sisältö
    public void asetaPaneelit() {
        // Asetetaan paneelit frameen
        ikkuna.add(pohjaPanel);
        pohjaPanel.add(inputPanel);
        pohjaPanel.add(btPanel);

        // Tekstikentät ja selitteet yläpaneeliin
        inputPanel.add(cbMuunnettava);
        cbMuunnettava.addItem("EUR");
        cbMuunnettava.addItem("USD");
        cbMuunnettava.addItem("CAD");
        cbMuunnettava.addItem("GBP");
        cbMuunnettava.addItem("JPY");
        inputPanel.add(tfMuunnettava);
        inputPanel.add(nuoli);
        inputPanel.add(cbMuunnettu);
        cbMuunnettu.addItem("EUR");
        cbMuunnettu.addItem("USD");
        cbMuunnettu.addItem("CAD");
        cbMuunnettu.addItem("GBP");
        cbMuunnettu.addItem("JPY");
        inputPanel.add(tfMuunnettu);

        // Napit alapaneeliin
        btPanel.add(btMuunna);
        btPanel.add(btTyhjenna);

        //Nappien toiminnot
        btMuunna.addActionListener(new Muunna());
        btTyhjenna.addActionListener(new Tyhjenna());
        
        // Muunna -painiketta voi käyttää myös Enterillä
        ikkuna.getRootPane().setDefaultButton(btMuunna);
    }

    public static void main(String[] args) {
        Valuuttalaskin valuuttaLaskin = new Valuuttalaskin();
    }
}
