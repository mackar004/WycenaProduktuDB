package pl.pracaInzynierska.forms;

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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pl.pracaInzynierska.SendEmail;
import pl.pracaInzynierska.model.Inwestycja;
import pl.pracaInzynierska.model.Materialy;
import pl.pracaInzynierska.model.MaterialyUzyte;
import pl.pracaInzynierska.model.Wycena;
import static pl.pracaInzynierska.dane.TypMaterialu.*;
import pl.pracaInzynierska.dane.TypPrzekrycia;
import pl.pracaInzynierska.repository.InwestycjaRepository;
import pl.pracaInzynierska.repository.MaterialyRepository;
import pl.pracaInzynierska.repository.MaterialyUzyteRepository;
import pl.pracaInzynierska.repository.WycenaRepository;

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

    private SendEmail sendEmail;

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button wylicz = new Button("Oblicz cenę");
    private final Button kasuj = new Button("Usuń pozycję");

    private final Button zywicaDodaj = new Button("Dodaj");
    private final Button mataDodaj = new Button("Dodaj");
    private final Button zelkotDodaj = new Button("Dodaj");
    private final Button topkotDodaj = new Button("Dodaj");
    private final Button piankaDodaj = new Button("Dodaj");
    private final Button rbhDodaj = new Button("Dodaj");
    private final Button podstawoweDodaj = new Button("Dodaj");
    private final Button pomocniczeDodaj = new Button("Dodaj");

    ComboBox<TypPrzekrycia> typPrzekrycia = new ComboBox<>("Typ przekrycia", TypPrzekrycia.values());

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
    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);

    private final Grid materialyDodane;

    Boolean nowaWycLoc;

    Binder<Wycena> binderWycena = new Binder<>(Wycena.class);
    Binder<MaterialyUzyte> binderMaterialy = new Binder<>(MaterialyUzyte.class);

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

        sendEmail = new SendEmail();

        materialyDodane = new Grid<>(MaterialyUzyte.class);
        materialyDodane.setColumns("materialy", "iloscMaterialu");
        materialyDodane.addColumn("materialy.cena");

        materialyDodane.setWidth("500px");

        //Kasowanie wszystkich istniejących materialów użytych z tabeli bez przypisanej wyceny (tempy nie zapisane)
        materialyUzyteRepository.deleteAll(materialyUzyteRepository.findByWycena(null));
        materialyUzyteRepository.flush();

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
        });

        zywicaDodaj.addClickListener(e -> {
            if (checkField(zywicaPole,zywicaIlosc)) {
//                try {
                if (!(zywicaPole.getValue().equals(null)) || !(zywicaIlosc.getValue().equals(null))) {
                    this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(zywicaPole.getValue().toString()), Double.parseDouble(zywicaIlosc.getValue().replace(",", ".")), true);
                    materialyUzyteRepository.save(this.materialyUzyte);
                    zywicaPole.clear();
                    zywicaPole.setPlaceholder("Dodano!");
                    zywicaIlosc.clear();
                    listMaterialy();
                }
//                } catch (NullPointerException exep) {
//                    zywicaPole.setPlaceholder("Wybierz");
//                    zywicaIlosc.setPlaceholder("Podaj ilość");
//                }
            }
        });
        mataDodaj.addClickListener(e -> {
            if (checkField(mataPole,mataIlosc)) {
                try {
                    if (!(mataPole.getValue().equals(null)) || !(mataIlosc.getValue().equals(null))) {
                        System.out.println("wewnatrz ifa");
                        this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(mataPole.getValue().toString()), Double.parseDouble(mataIlosc.getValue().replace(",", ".")), true);
                        materialyUzyteRepository.save(this.materialyUzyte);
                        mataPole.clear();
                        mataPole.setPlaceholder("Dodano!");
                        mataIlosc.clear();
                        listMaterialy();
                    }
                } catch (NullPointerException exep) {
                    mataPole.setPlaceholder("Wybierz");
                    mataIlosc.setPlaceholder("Podaj ilość");
                }
            }
        });
        zelkotDodaj.addClickListener(e -> {
            if ((zelkotPole.getValue() != null) || (zelkotIlosc.getValue() != null)) {
                this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(zelkotPole.getValue().toString()), Double.parseDouble(zelkotIlosc.getValue().replace(",", ".")), true);
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
                this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(topkotPole.getValue().toString()), Double.parseDouble(topkotIlosc.getValue().replace(",", ".")), true);
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
                this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(piankaPole.getValue().toString()), Double.parseDouble(piankaIlosc.getValue().replace(",", ".")), true);
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
                this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(rbhPole.getValue().toString()), Double.parseDouble(rbhIlosc.getValue().replace(",", ".")), true);
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
                this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(podstawowePole.getValue().toString()), Double.parseDouble(podstawoweIlosc.getValue().replace(",", ".")), true);
                materialyUzyteRepository.save(this.materialyUzyte);
                System.out.println(this.materialyUzyte);
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
                this.materialyUzyte = new MaterialyUzyte(this.wycena, materialyRepository.findByNazwa(pomocniczePole.getValue().toString()), Double.parseDouble(pomocniczeIlosc.getValue().replace(",", ".")), true);
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
            double suma;
            cenaKoncowa.setReadOnly(false);

            //sumowanie tabeli
            List<MaterialyUzyte> materialyDoWyliczenia = materialyUzyteRepository.findByWycena(this.wycena);
            for (int i = 0; i < materialyDoWyliczenia.size(); i++) {
                sumaTabela += materialyDoWyliczenia.get(i).getIloscMaterialu()
                        * Double.parseDouble(materialyRepository.findByNazwa(materialyDoWyliczenia.get(i).getMaterialy().getNazwa()).getCena().replace(",", "."));
            }

            //wyliczanie kwoty z uwzględnieniem marży
            suma = sumaTabela * (100 + Double.parseDouble(marza.getValue().replace(",", "."))) / 100;
            cenaKoncowa.setValue(String.valueOf(suma).replace(".", ","));
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
//        binderWycena.forField(zywicaIlosc)
//                .withConverter(new StringToDoubleConverter("Potrzebna liczba!"))
//                .bind(MaterialyUzyte::getIloscMaterialu, MaterialyUzyte::setIloscMaterialu);

        save.addClickListener(e -> {
            save();
            sendEmail.SendEmail("Wycena " + this.inwestycja,
                    "Nowa wycena dla " + this.inwestycja + " o wartości " + String.valueOf(cenaKoncowa.getValue()),
                    "inzmailserv@gmail.com");
        });
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

        listMaterialy();
    }

    public final void listMaterialy() {
        materialyDodane.setVisible(true);
        kasuj.setVisible(true);
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
        changeHandler.onChange();
    }

    void cancel() {
        materialyUzyteRepository.deleteByWycenaAndIsNew(this.wycena, true);
        materialyUzyteRepository.flush();
        materialyDodane.select(null);
        clearFields();
        if (nowaWycLoc) {
            delete();
        } else {
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
        materialyUzyteRepository.deleteByWycenaAndIsNew(null, true);
        if (w == null) {
            setVisible(false);
            return;
        }
        this.inwestycja = i;
        if (inwestycja != null) {
            miastoInwestycji.setValue(this.inwestycja.getInwestycjaMiasto());
            nazwaInwestycji.setValue(this.inwestycja.getInwestycjaNazwa());
        }
        miastoInwestycji.setReadOnly(true);
        nazwaInwestycji.setReadOnly(true);

        final boolean wycenaIstnieje = w.getId() != null;
        if (wycenaIstnieje) {
            wycena = wycenaRepository.findById(w.getId()).get();
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

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    private boolean checkField(ComboBox field1, TextField field2) {
        String fieldValue = field2.getValue().replace(",", ".");
        if (fieldValue.matches("^(0|([1-9][0-9]*))(\\.[0-9]+)?$")) {
            if (!(field1.equals(null))) {
                return true;
            } else {
                field1.setValue("Wybierz");
                return false;
            }
        }
        field2.setValue("Błąd danych!");
        return false;
    }
}
