/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.forms;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.flow.component.ClickEvent;
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
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.model.Materialy;
import pl.torun.roma.RoMa3.model.MaterialyUzyte;
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
public final class WycenaForm extends VerticalLayout implements KeyNotifier {

    private final WycenaRepository wycenaRepository;
    private final InwestycjaRepository inwestycjaRepository;
    private final MaterialyRepository materialyRepository;
    private final MaterialyUzyteRepository materialyUzyteRepository;

    private Inwestycja inwestycja;
    private Wycena wycena;
    private MaterialyUzyte materialyUzyte;
    private MaterialyUzyte mU;

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button wylicz = new Button("Oblicz cenę");
    private final Button edit = new Button("Edytuj");
    private final Button kasuj = new Button("Usuń pozycję");
    private final Button zapisz = new Button("Zapisz zmiany");

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

    private final Button dodajMaterialy = new Button("Dodaj materiały");
    private final Button sLewo = new Button(VaadinIcon.CHEVRON_CIRCLE_LEFT.create());
    private final Button sPrawo = new Button(VaadinIcon.CHEVRON_CIRCLE_RIGHT.create());

//    private final List<HashMap<String, String>> materialyTemp = new ArrayList<>();
//    private final Map<String, String> materialUzyty = new HashMap<>();
//    private final String Nazwa = "Nazwa";
//    private final String Ilosc = "Ilość";
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
    ComboBox<Materialy> edytujMaterial;

    private final TextField zywicaIlosc = new TextField("Ilość");
    private final TextField mataIlosc = new TextField("Ilość");
    private final TextField zelkotIlosc = new TextField("Ilość");
    private final TextField topkotIlosc = new TextField("Ilość");
    private final TextField piankaIlosc = new TextField("Ilość");
    private final TextField rbhIlosc = new TextField("Ilość");
    private final TextField podstawoweIlosc = new TextField("Ilość");
    private final TextField pomocniczeIlosc = new TextField("Ilość");
    private final TextField edytujIlosc = new TextField("Ilość");

    private final HorizontalLayout daneInwestycji = new HorizontalLayout(miastoInwestycji, nazwaInwestycji);
    private final HorizontalLayout typWymiary = new HorizontalLayout(typPrzekrycia, srednica, dlugosc, szerokosc);
    private final HorizontalLayout laminatIlosc = new HorizontalLayout(laminat, laminatSztuki);
    private final HorizontalLayout sandwichIlosc = new HorizontalLayout(sandwich, sandwichSztuki);
    private final HorizontalLayout marzaCena = new HorizontalLayout(marza, cenaKoncowa, wylicz);

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
    //private final HorizontalLayout editBar = new HorizontalLayout();
    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);

    //private final Grid<HashMap<String, String>> materialyDodane;
    private final Grid materialyDodane;

    Boolean nowaWycLoc;

    Binder<Wycena> binderWycena = new Binder<>(Wycena.class);
    Binder<MaterialyUzyte> binderMaterialy = new Binder<>(MaterialyUzyte.class);

    private WycenaForm.ChangeHandler changeHandler;

    private double wspKsztaltu;

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

        //this.materialyTemp = new ArrayList<>();
        materialyDodane = new Grid<>(MaterialyUzyte.class);
        materialyDodane.setColumns("materialy", "iloscMaterialu");
        materialyDodane.addColumn("materialy.cena");

        materialyDodane.setWidth("500px");
        //materialyDodane.getColumnByKey("materialy").setWidth("70px").setFlexGrow(0);

        //Kasowanie wszystkich istniejących materialów użytych z tabeli bez przypisanej wyceny (tempy nie zapisane)
        //System.out.println("findById(null) PRE " + materialyUzyteRepository.findByWycena(null));
        materialyUzyteRepository.deleteAll(materialyUzyteRepository.findByWycena(null));
        materialyUzyteRepository.flush();
        //System.out.println("findById(null) POST " + materialyUzyteRepository.findByWycena(null));

        materialyDodane.asSingleSelect().addValueChangeListener(e -> {
            if (materialyDodane.getSelectedItems().isEmpty()) {
                kasuj.setEnabled(false);
                this.mU = null;
            } else {
                kasuj.setEnabled(true);
                this.mU = (MaterialyUzyte) e.getValue();
            }
        });

        dodajMaterialy.addClickListener(e -> {
            strzalki.setVisible(true);
            pola1.setVisible(true);
            pola2.setVisible(false);
            dodajMaterialy.setVisible(false);
            wycenaRepository.save(wycena);
            //System.out.println("Zapisano wycenę " + wycena.getId());
        });

        zywicaDodaj.addClickListener(e -> {
            if ((zywicaPole.getValue() != null) || ((zywicaIlosc.getValue() != null))) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(zywicaPole.getValue().toString()), Double.parseDouble(zywicaIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                zywicaPole.clear();
                zywicaPole.setPlaceholder("Dodano!");
                zywicaIlosc.clear();
                listMaterialy();
            } else {
                zywicaPole.setPlaceholder("Wybierz");
                zywicaIlosc.setPlaceholder("Podaj ilość");
            }
        });
        mataDodaj.addClickListener(e -> {
            if ((mataPole.getValue() != null) || (mataIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(mataPole.getValue().toString()), Double.parseDouble(mataIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                mataPole.clear();
                mataPole.setPlaceholder("Dodano!");
                mataIlosc.clear();
                listMaterialy();
            } else {
                mataPole.setPlaceholder("Wybierz");
                mataIlosc.setPlaceholder("Podaj ilość");
            }
        });
        zelkotDodaj.addClickListener(e -> {
            if ((zelkotPole.getValue() != null) || (zelkotIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(zelkotPole.getValue().toString()), Double.parseDouble(zelkotIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                zelkotPole.clear();
                zelkotPole.setPlaceholder("Dodano!");
                zelkotIlosc.clear();
                listMaterialy();
            } else {
                zelkotPole.setPlaceholder("Wybierz");
                zelkotIlosc.setPlaceholder("Podaj ilość");
            }
        });
        topkotDodaj.addClickListener(e -> {
            if ((topkotPole.getValue() != null) || (topkotIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(topkotPole.getValue().toString()), Double.parseDouble(topkotIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                topkotPole.clear();
                topkotPole.setPlaceholder("Dodano!");
                topkotIlosc.clear();
                listMaterialy();
            } else {
                topkotPole.setPlaceholder("Wybierz");
                topkotIlosc.setPlaceholder("Podaj ilość");
            }
        });
        piankaDodaj.addClickListener(e -> {
            if ((piankaPole.getValue() != null) || (piankaIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(piankaPole.getValue().toString()), Double.parseDouble(piankaIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                piankaPole.clear();
                piankaPole.setPlaceholder("Dodano!");
                piankaIlosc.clear();
                listMaterialy();
            } else {
                piankaPole.setPlaceholder("Wybierz");
                piankaIlosc.setPlaceholder("Podaj ilość");
            }
        });
        rbhDodaj.addClickListener(e -> {
            if ((rbhPole.getValue() != null) || (rbhIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(rbhPole.getValue().toString()), Double.parseDouble(rbhIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                rbhPole.clear();
                rbhPole.setPlaceholder("Dodano!");
                rbhIlosc.clear();
                listMaterialy();
            } else {
                rbhPole.setPlaceholder("Wybierz");
                rbhIlosc.setPlaceholder("Podaj ilość");
            }
        });
        podstawoweDodaj.addClickListener(e -> {
            if ((podstawowePole.getValue() != null) || (podstawoweIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(podstawowePole.getValue().toString()), Double.parseDouble(podstawoweIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                podstawowePole.clear();
                podstawowePole.setPlaceholder("Dodano!");
                podstawoweIlosc.clear();
                listMaterialy();
            } else {
                podstawowePole.setPlaceholder("Wybierz");
                podstawoweIlosc.setPlaceholder("Podaj ilość");
            }
        });
        pomocniczeDodaj.addClickListener(e -> {
            if ((pomocniczePole.getValue() != null) || (pomocniczeIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(materialyRepository.findByNazwa(pomocniczePole.getValue().toString()), Double.parseDouble(pomocniczeIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                pomocniczePole.clear();
                pomocniczePole.setPlaceholder("Dodano!");
                pomocniczeIlosc.clear();
                listMaterialy();
            } else {
                pomocniczePole.setPlaceholder("Wybierz");
                pomocniczeIlosc.setPlaceholder("Podaj ilość");
            }
        });
        wylicz.addClickListener((ClickEvent<Button> e) -> {
            double sumaTabela = 0;
            //wyliczanie ceny końcowej
            cenaKoncowa.setReadOnly(false);
            //ustawienie wartości pola

            List<MaterialyUzyte> materialyDoWyliczenia = materialyUzyteRepository.findByWycena(this.wycena);
            for (int i = 0; i < materialyDoWyliczenia.size(); i++) {
                sumaTabela += materialyDoWyliczenia.get(i).getIloscMaterialu()
                        * Double.parseDouble(materialyRepository.findByNazwa(materialyDoWyliczenia.get(i).getMaterialy().getNazwa()).getCena());
            }

            //sumaTabela += sumaTabela * (Double.parseDouble("0"+marza.getValue().replace(",", "").replace(".","")));
            //System.out.println(Double.parseDouble((marza.getValue())));
//            sumaTabela *= 100;
//            sumaTabela = Math.round(sumaTabela);
//            sumaTabela /= 100;
            cenaKoncowa.setValue(String.valueOf(sumaTabela).replace(".", ","));
            cenaKoncowa.setReadOnly(true);
        });

        zywicaPole = new ComboBox<>("Żywica", listaZywic);
        mataPole = new ComboBox<>("Mata", listaMat);
        zelkotPole = new ComboBox<>("Żelkot", listaZelkotow);
        topkotPole = new ComboBox<>("Topkot", listaTopkotow);
        piankaPole = new ComboBox<>("Pianka", listaPianek);
        rbhPole = new ComboBox<>("RBH", listaRBH);
        podstawowePole = new ComboBox<>("Podstawowe", listaPodstawowych);
        pomocniczePole = new ComboBox<>("Pomocnicze", listaPomocniczych);

        edytujMaterial = new ComboBox<>("Materiał", this.materialyRepository.findAll());

        kasuj.setEnabled(false);
        kasuj.setVisible(false);

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

        add(dodajMaterialy, strzalki, pola1, pola2);

        add(marzaCena);

        add(materialyDodane, kasuj);

        add(buttonBar);

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

//        binderMaterialy.forField(zywicaPole)
//                .bind(MaterialyUzyte::getMaterialy, MaterialyUzyte::setMaterialy);
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
        delete.addClickListener(e -> delete());
        kasuj.addClickListener(e -> kasuj());

        typPrzekrycia.addValueChangeListener(e -> {
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
        listMaterialy();
    }

    public final void listMaterialy() {
        //if (!materialyDodane.isVisible()) {
        materialyDodane.setVisible(true);
        kasuj.setVisible(true);
        //}
        materialyDodane.setItems(materialyUzyteRepository.findByWycenaOrWycena(null, this.wycena));
    }

    void kasuj() {
        materialyUzyteRepository.delete(this.mU);
        materialyUzyteRepository.flush();
        kasuj.setEnabled(false);
        listMaterialy();
    }

    void save() {
        marza.setValue(marza.getValue().replace(",", "."));
        materialyUzyteRepository.findByWycenaAndIsNew(null, true).forEach(e -> {
            e.setIsNew(false);
            e.setWycena(this.wycena);
            materialyUzyteRepository.save(e);
        });
        wycenaRepository.save(wycena);
        materialyDodane.select(null);
        clearFields();
        changeHandler.onChange();
    }

    void delete() {
        wycena.setInwestycja(null);
        wycenaRepository.save(wycena);
        wycenaRepository.delete(wycena);
        wycenaRepository.flush();
        //clearFields();
        changeHandler.onChange();
    }

    void cancel() {
        //System.out.println(wycenaRepository.findAll());
        materialyUzyteRepository.deleteAll(materialyUzyteRepository.findByWycenaAndIsNew(null, true));
        //materialyUzyteRepository.deleteAll(materialyUzyteRepository.findByWycena(null));
        materialyUzyteRepository.flush();
        materialyDodane.select(null);
        clearFields();
        if (nowaWycLoc) {
            //System.out.println("Cancel nowaWyc:" + nowaWycLoc);
            delete();
//            System.out.println("Kasowanie nowej wyceny (id: " + wycena.getId() + ") przez Anulowanie ");
//            wycena.setInwestycja(null);
//            wycenaRepository.save(wycena);
//            wycenaRepository.delete(wycena);
//            wycenaRepository.flush();
        } else {
//            System.out.println("Cancel nowaWyc:" + nowaWycLoc);
//            System.out.println(wycenaRepository.findAll());
            changeHandler.onChange();
        }
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void clearFields() {
        zywicaPole.setPlaceholder("");
        mataPole.setPlaceholder("");
        zelkotPole.setPlaceholder("");
        topkotPole.setPlaceholder("");
        piankaPole.setPlaceholder("");
        rbhPole.setPlaceholder("");
        podstawowePole.setPlaceholder("");
        pomocniczePole.setPlaceholder("");
        zywicaIlosc.setValue("");
        mataIlosc.setValue("");
        zelkotIlosc.setValue("");
        topkotIlosc.setValue("");
        piankaIlosc.setValue("");
        rbhIlosc.setValue("");
        podstawoweIlosc.setValue("");
        pomocniczeIlosc.setValue("");
        materialyDodane.setItems();
        materialyDodane.setVisible(false);
        kasuj.setVisible(false);
    }

    public final void editWycena(Wycena w, Inwestycja i, Boolean nowaWyc) {
        if (w == null) {
            setVisible(false);
            return;
        }
        this.inwestycja = i;
        //this.nowaWyc = nowaWyc;
        //System.out.println("editWycena. Nowa wycena: " + nowaWyc);
        /*
        IF tymczasowy, na potrzeby kasowania wycen bez firmy!
        Wnętrze ifa zostaje, warunek (L 468 + 471)do wykasowania
         */
        if (inwestycja != null) {
            miastoInwestycji.setValue(this.inwestycja.getInwestycjaMiasto());
            nazwaInwestycji.setValue(this.inwestycja.getInwestycjaNazwa());
        }
        miastoInwestycji.setReadOnly(true);
        nazwaInwestycji.setReadOnly(true);

        final boolean wycenaIstnieje = w.getId() != null;
        if (wycenaIstnieje) {
            wycena = wycenaRepository.findById(w.getId()).get();
//            materialyUzyte = (MaterialyUzyte) materialyUzyteRepository.findByWycena(wycena);
            delete.setEnabled(true);
        } else {
            wycena = w;
        }

        if (nowaWyc) {
            dodajMaterialy.setVisible(true);
            strzalki.setVisible(false);
            pola1.setVisible(false);
            pola2.setVisible(false);
            materialyDodane.setVisible(false);
            kasuj.setVisible(false);
            nowaWycLoc = true;
        } else {
            dodajMaterialy.setVisible(false);
            strzalki.setVisible(true);
            pola1.setVisible(true);
            pola2.setVisible(false);
            materialyDodane.setVisible(true);
            kasuj.setVisible(true);
            nowaWycLoc = false;
        }

        binderWycena.setBean(wycena);
        setVisible(true);
    }

    /*
    //
    //  Tutaj dodać kasowanie materiałów użytych do wyceny
    //
    // grid.addColumn(new NativeButtonRenderer<>("Remove item", clickedItem -> {
          // remove the item
        }));
    //
            materialyDodane.asSingleSelect().addValueChangeListener(e -> {
            //sprawdzenie czy w tabeli jest wybrany jakiś klucz i odpowiednie ustawienie dostępności przycisków
            if (materialyDodane.getSelectedItems().isEmpty()) {
                nowaWycena.setEnabled(true);
                wyswietlWycene.setEnabled(false);
                this.wycena = null;
            } else {
                nowaWycena.setEnabled(false);
                wyswietlWycene.setEnabled(true);
                this.wycena = (Wycena) e.getValue();
                this.wycenaForm.editWycena((Wycena) e.getValue(), this.inwestycja);
            }
        });
     */
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}


/*
Zamykanie dialog'a
https://vaadin.com/forum/thread/17054450/best-way-to-close-custom-dialog
 */
