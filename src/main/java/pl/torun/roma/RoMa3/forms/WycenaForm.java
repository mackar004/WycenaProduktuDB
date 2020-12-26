/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.model.Materialy;
import pl.torun.roma.RoMa3.model.Wycena;
import static pl.torun.roma.RoMa3.model.dane.TypMaterialu.*;
import pl.torun.roma.RoMa3.model.dane.TypPrzekrycia;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;
import pl.torun.roma.RoMa3.repository.MaterialyRepository;
import pl.torun.roma.RoMa3.repository.MaterialyUzyteRepository;
import pl.torun.roma.RoMa3.repository.WycenaRepository;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class WycenaForm extends VerticalLayout implements KeyNotifier {

    private final WycenaRepository wycenaRepository;
    private final InwestycjaRepository inwestycjaRepository;
    private final MaterialyRepository materialyRepository;
    private final MaterialyUzyteRepository materialyUzyteRepository;

    private Inwestycja inwestycja;
    private Wycena wycena;

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button edit = new Button("Edytuj");

    private final Button zywicaDodaj = new Button("Dodaj");
    private final Button mataDodaj = new Button("Dodaj");
    private final Button zelkotDodaj = new Button("Dodaj");
    private final Button topkotDodaj = new Button("Dodaj");
    private final Button piankaDodaj = new Button("Dodaj");
    private final Button rbhDodaj = new Button("Dodaj");
    private final Button podstawoweDodaj = new Button("Dodaj");
    private final Button pomocniczeDodaj = new Button("Dodaj");

    ComboBox<TypPrzekrycia> typPrzekrycia = new ComboBox<>("Typ przekrycia", TypPrzekrycia.values());

    //wyszukać wartości dla poniższych pól
    private final TextField nazwaInwestycji = new TextField("Inwestycja");
    private final TextField miastoInwestycji = new TextField("Miasto");

    private final TextField dlugosc = new TextField("Długość [mm]");
    private final TextField szerokosc = new TextField("Szerokość [mm]");
    private final TextField srednica = new TextField("Średnica [mm]");

    private final TextField laminat = new TextField("Laminat");
    private final TextField sandwich = new TextField("Sandwich");
    private final TextField laminatSztuki = new TextField("Elementy laminat");
    private final TextField sandwichSztuki = new TextField("Elementy sandwich");

    private final TextField marza = new TextField("Marża");
    private final TextField cenaKoncowa = new TextField("Cena końcowa");

    private final Button sLewo = new Button(VaadinIcon.CHEVRON_CIRCLE_LEFT.create());
    private final Button sPrawo = new Button(VaadinIcon.CHEVRON_CIRCLE_RIGHT.create());

    private final Map<String, Double> materialyTemp = new HashMap<String, Double>();

    List<Materialy> listaZywic;
    List<Materialy> listaMat;
    List<Materialy> listaZelkotow;
    List<Materialy> listaTopkotow;
    List<Materialy> listaPianek;
    List<Materialy> listaRBH;
    List<Materialy> listaPodstawowych;
    List<Materialy> listaPomocniczych;

    ComboBox<Materialy> zywicaPole;
    ComboBox<Materialy> mataPole;
    ComboBox<Materialy> zelkotPole;
    ComboBox<Materialy> topkotPole;
    ComboBox<Materialy> piankaPole;
    ComboBox<Materialy> rbhPole;
    ComboBox<Materialy> podstawowePole;
    ComboBox<Materialy> pomocniczePole;

    private final TextField zywicaIlosc = new TextField("Ilość");
    private final TextField mataIlosc = new TextField("Ilość");
    private final TextField zelkotIlosc = new TextField("Ilość");
    private final TextField topkotIlosc = new TextField("Ilość");
    private final TextField piankaIlosc = new TextField("Ilość");
    private final TextField rbhIlosc = new TextField("Ilość");
    private final TextField podstawoweIlosc = new TextField("Ilość");
    private final TextField pomocniczeIlosc = new TextField("Ilość");

    //private final TextField zywicaPole = new TextField("Zywica");
    private final HorizontalLayout daneInwestycji = new HorizontalLayout(miastoInwestycji, nazwaInwestycji);
    private final HorizontalLayout typWymiary = new HorizontalLayout(typPrzekrycia, srednica, dlugosc, szerokosc);
    private final HorizontalLayout laminatIlosc = new HorizontalLayout(laminat, laminatSztuki);
    private final HorizontalLayout sandwichIlosc = new HorizontalLayout(sandwich, sandwichSztuki);
    private final HorizontalLayout marzaCena = new HorizontalLayout(marza, cenaKoncowa);

    private final HorizontalLayout zywica = new HorizontalLayout();
    private final HorizontalLayout mata = new HorizontalLayout();
    private final HorizontalLayout zelkot = new HorizontalLayout();
    private final HorizontalLayout topkot = new HorizontalLayout();
    private final HorizontalLayout pianka = new HorizontalLayout();
    private final HorizontalLayout rbh = new HorizontalLayout();
    private final HorizontalLayout podstawowe = new HorizontalLayout();
    private final HorizontalLayout pomocnicze = new HorizontalLayout();

    private final VerticalLayout pola1 = new VerticalLayout();
    private final VerticalLayout pola2 = new VerticalLayout();
    private final HorizontalLayout strzalki = new HorizontalLayout(sLewo, sPrawo);

    Binder<Wycena> binderWycena = new Binder<>(Wycena.class);
    //private final Grid materialy;

    private WycenaForm.ChangeHandler changeHandler;

    @Autowired
    public WycenaForm(WycenaRepository wycenaRepository, InwestycjaRepository inwestycjaRepository,
            MaterialyRepository materialyRepository, MaterialyUzyteRepository materialyUzyteRepository) {

        this.wycenaRepository = wycenaRepository;
        this.inwestycjaRepository = inwestycjaRepository;
        this.materialyRepository = materialyRepository;
        this.materialyUzyteRepository = materialyUzyteRepository;

        this.listaZywic = this.materialyRepository.findByTypMaterialu(Żywica);
        this.listaMat = this.materialyRepository.findByTypMaterialuOrTypMaterialu(Mata, Matotkanina);
        this.listaZelkotow = this.materialyRepository.findByTypMaterialu(Żelkot);
        this.listaTopkotow = this.materialyRepository.findByTypMaterialu(Topkot);
        this.listaPianek = this.materialyRepository.findByTypMaterialu(Pianka);
        this.listaRBH = this.materialyRepository.findByTypMaterialu(RBH);
        this.listaPodstawowych = this.materialyRepository.findByTypMaterialu(Podstawowe);
        this.listaPomocniczych = this.materialyRepository.findByTypMaterialu(Pomocnicze);

        zywicaDodaj.addClickListener(e -> {
            if ((zywicaPole.getValue() != null) || (zywicaIlosc.getValue() != null)) {
                materialyTemp.put(zywicaPole.getValue().toString(), Double.parseDouble(zywicaIlosc.getValue()));
                zywicaPole.clear();
                zywicaPole.setPlaceholder("Dodano!");
                zywicaIlosc.clear();
            } else {
                zywicaPole.setPlaceholder("Wybierz");
                zywicaIlosc.setPlaceholder("Podaj ilość");
            }
        });
        mataDodaj.addClickListener(e -> {
            if ((mataPole.getValue() != null) || (mataIlosc.getValue() != null)) {
                materialyTemp.put(mataPole.getValue().toString(), Double.parseDouble(mataIlosc.getValue()));
                mataPole.clear();
                mataPole.setPlaceholder("Dodano!");
                mataIlosc.clear();
            } else {
                mataPole.setPlaceholder("Wybierz");
                mataIlosc.setPlaceholder("Podaj ilość");
            }
        });
        zelkotDodaj.addClickListener(e -> {
            if ((zelkotPole.getValue() != null) || (zelkotIlosc.getValue() != null)) {
                materialyTemp.put(zelkotPole.getValue().toString(), Double.parseDouble(zelkotIlosc.getValue()));
                zelkotPole.clear();
                zelkotPole.setPlaceholder("Dodano!");
                zelkotIlosc.clear();
            } else {
                zelkotPole.setPlaceholder("Wybierz");
                zelkotIlosc.setPlaceholder("Podaj ilość");
            }
        });
        topkotDodaj.addClickListener(e -> {
            if ((topkotPole.getValue() != null) || (topkotIlosc.getValue() != null)) {
                materialyTemp.put(topkotPole.getValue().toString(), Double.parseDouble(topkotIlosc.getValue()));
                topkotPole.clear();
                topkotPole.setPlaceholder("Dodano!");
                topkotIlosc.clear();
            } else {
                topkotPole.setPlaceholder("Wybierz");
                topkotIlosc.setPlaceholder("Podaj ilość");
            }
        });
        piankaDodaj.addClickListener(e -> {
            if ((piankaPole.getValue() != null) || (piankaIlosc.getValue() != null)) {
                materialyTemp.put(piankaPole.getValue().toString(), Double.parseDouble(piankaIlosc.getValue()));
                piankaPole.clear();
                piankaPole.setPlaceholder("Dodano!");
                piankaIlosc.clear();
            } else {
                piankaPole.setPlaceholder("Wybierz");
                piankaIlosc.setPlaceholder("Podaj ilość");
            }
        });
        rbhDodaj.addClickListener(e -> {
            if ((rbhPole.getValue() != null) || (rbhIlosc.getValue() != null)) {
                materialyTemp.put(rbhPole.getValue().toString(), Double.parseDouble(rbhIlosc.getValue()));
                rbhPole.clear();
                rbhPole.setPlaceholder("Dodano!");
                rbhIlosc.clear();
            } else {
                rbhPole.setPlaceholder("Wybierz");
                rbhIlosc.setPlaceholder("Podaj ilość");
            }
        });
        podstawoweDodaj.addClickListener(e -> {
            if ((podstawowePole.getValue() != null) || (podstawoweIlosc.getValue() != null)) {
                materialyTemp.put(podstawowePole.getValue().toString(), Double.parseDouble(podstawoweIlosc.getValue()));
                podstawowePole.clear();
                podstawowePole.setPlaceholder("Dodano!");
                podstawoweIlosc.clear();
            } else {
                podstawowePole.setPlaceholder("Wybierz");
                podstawoweIlosc.setPlaceholder("Podaj ilość");
            }
        });
        pomocniczeDodaj.addClickListener(e -> {
            if ((pomocniczePole.getValue() != null) || (pomocniczeIlosc.getValue() != null)) {
                materialyTemp.put(pomocniczePole.getValue().toString(), Double.parseDouble(pomocniczeIlosc.getValue()));
                pomocniczePole.clear();
                pomocniczePole.setPlaceholder("Dodano!");
                pomocniczeIlosc.clear();
            } else {
                pomocniczePole.setPlaceholder("Wybierz");
                pomocniczeIlosc.setPlaceholder("Podaj ilość");
            }
        });

        zywicaPole = new ComboBox<>("Żywica", listaZywic);
        mataPole = new ComboBox<>("Mata", listaMat);
        zelkotPole = new ComboBox<>("Żelkot", listaZelkotow);
        topkotPole = new ComboBox<>("Topkot", listaTopkotow);
        piankaPole = new ComboBox<>("Pianka", listaPianek);
        rbhPole = new ComboBox<>("RBH", listaRBH);
        podstawowePole = new ComboBox<>("Podstawowe", listaPodstawowych);
        pomocniczePole = new ComboBox<>("Pomocnicze", listaPomocniczych);

        zywica.add(zywicaPole, zywicaIlosc, zywicaDodaj);
        mata.add(mataPole, mataIlosc, mataDodaj);
        zelkot.add(zelkotPole, zelkotIlosc, zelkotDodaj);
        topkot.add(topkotPole, topkotIlosc, topkotDodaj);
        pianka.add(piankaPole, piankaIlosc, piankaDodaj);
        rbh.add(rbhPole, rbhIlosc, rbhDodaj);
        podstawowe.add(podstawowePole, podstawoweIlosc, podstawoweDodaj);
        pomocnicze.add(pomocniczePole, pomocniczeIlosc, pomocniczeDodaj);

        dlugosc.setVisible(false);
        szerokosc.setVisible(false);
        srednica.setVisible(false);

        pola1.add(zywica, mata, zelkot, topkot);
        pola2.add(pianka, rbh, podstawowe, pomocnicze);

        pola1.setVisible(true);
        pola2.setVisible(false);

        sLewo.addClickListener(e -> {
            pola1.setVisible(!pola1.isVisible());
            pola2.setVisible(!pola2.isVisible());
        });

        sPrawo.addClickListener(e -> {
            pola1.setVisible(!pola1.isVisible());
            pola2.setVisible(!pola2.isVisible());
        });

        cenaKoncowa.setReadOnly(true);

        add(daneInwestycji, typWymiary, laminatSztuki, sandwichSztuki);

        add(strzalki, pola1, pola2);

        add(marzaCena);

        add(save, cancel);

        binderWycena.bind(typPrzekrycia, Wycena::getTypPrzekrycia, Wycena::setTypPrzekrycia);
        binderWycena.forField(dlugosc)
                .withConverter(new StringToIntegerConverter("Potrzebna liczba!"))
                .bind(Wycena::getDlugosc, Wycena::setDlugosc);
        binderWycena.forField(szerokosc)
                .withConverter(new StringToIntegerConverter("Potrzebna liczba!"))
                .bind(Wycena::getSzerokosc, Wycena::setSzerokosc);
        binderWycena.forField(srednica)
                .withConverter(new StringToIntegerConverter("Potrzebna liczba!"))
                .bind(Wycena::getSrednica, Wycena::setSrednica);
        binderWycena.forField(laminatSztuki)
                .withConverter(new StringToIntegerConverter("Potrzebna liczba!"))
                .bind(Wycena::getIloscLaminat, Wycena::setIloscLaminat);
        binderWycena.forField(sandwichSztuki)
                .withConverter(new StringToIntegerConverter("Potrzebna liczba!"))
                .bind(Wycena::getIloscSandwich, Wycena::setIloscSandwich);
        binderWycena.forField(marza)
                .withConverter(new StringToDoubleConverter("Potrzebna liczba!"))
                .bind(Wycena::getMarza, Wycena::setMarza);
        binderWycena.forField(cenaKoncowa)
                .withConverter(new StringToDoubleConverter("Potrzebna liczba!"))
                .bind(Wycena::getCenaKoncowa, Wycena::setCenaKoncowa);

        save.addClickListener(e
                -> save());
        cancel.addClickListener(e
                -> cancel());

        typPrzekrycia.addValueChangeListener(e
                -> {
            if (e.getValue() == null) {
                dlugosc.setVisible(false);
                szerokosc.setVisible(false);
                srednica.setVisible(false);
            } else {
                switch (e.getSource().getValue().toString()) {
                    case ("Korytkowo_Zbieżne"):
                        dlugosc.setVisible(false);
                        szerokosc.setVisible(false);
                        srednica.setVisible(true);
                        break;
                    case ("Kopuła_samonośna"):
                        dlugosc.setVisible(false);
                        szerokosc.setVisible(false);
                        srednica.setVisible(true);
                        break;
                    case ("Korytkowo_proste"):
                        dlugosc.setVisible(true);
                        szerokosc.setVisible(true);
                        srednica.setVisible(false);
                        break;
                    case ("Sandwich"):
                        dlugosc.setVisible(true);
                        szerokosc.setVisible(true);
                        srednica.setVisible(false);
                        break;
                    case ("Inne"):
                        dlugosc.setVisible(false);
                        szerokosc.setVisible(false);
                        srednica.setVisible(true);
                        break;
                    default:
                        dlugosc.setVisible(false);
                        szerokosc.setVisible(false);
                        srednica.setVisible(false);
                }
            }
        }
        );

        //Czy to potrzebne?
        //https://vaadin.com/forum/thread/15385912/15640053
        //binderWycena.bindInstanceFields(this);
    }

    void save() {
        wycenaRepository.save(wycena);
        //       inwestycjeGrid.select(null);
        changeHandler.onChange();
    }

    void delete() {
        //       inwestycjeGrid.select(null);
        wycenaRepository.delete(wycena);
        changeHandler.onChange();
    }

    void cancel() {
        //      inwestycjeGrid.select(null);
        this.materialyTemp.clear();
        changeHandler.onChange();

    }

    public interface ChangeHandler {

        void onChange();
    }

//    public final void editWycena(Wycena w, Inwestycja inwestycja) {
//        if (w == null) {
//            setVisible(false);
//            return;
//        }
//        this.inwestycja = inwestycja;
//        //nazwaFirmy.setValue(this.firma.toString().replace("[", "").replace("]", ""));
//        final boolean wycenaIstnieje = w.getId() != null;
//        if (wycenaIstnieje) {
//            wycena = wycenaRepository.findById(w.getId()).get();
//            delete.setEnabled(true);
//        } else {
//            wycena = w;
//        }
//
//        binderWycena.setBean(wycena);
//        setVisible(true);
//
//    }

    /*
    TYMCZASOWY KONSTRUKTOR WYCENY NIEPOWIĄZANEJ Z INWESTYCJĄ
     */
    public final void editWycena(Wycena w, Inwestycja inwestycja) {
        if (w == null) {
            setVisible(false);
            return;
        }
        //nazwaFirmy.setValue(this.firma.toString().replace("[", "").replace("]", ""));
        this.inwestycja = inwestycja;
        miastoInwestycji.setValue(this.inwestycja.getInwestycjaMiasto());
        nazwaInwestycji.setValue(this.inwestycja.getInwestycjaNazwa());
        miastoInwestycji.setReadOnly(true);
        nazwaInwestycji.setReadOnly(true);

        final boolean wycenaIstnieje = w.getId() != null;
        if (wycenaIstnieje) {
            wycena = wycenaRepository.findById(w.getId()).get();
            delete.setEnabled(true);
        } else {
            wycena = w;
        }

        binderWycena.setBean(wycena);
        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
