package pl.pracaInzynierska.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.pracaInzynierska.repository.NotatkaRepository;
import pl.pracaInzynierska.repository.PracownikRepository;
import pl.pracaInzynierska.model.Notatka;
import pl.pracaInzynierska.model.Pracownik;
import pl.pracaInzynierska.dane.Stanowisko;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author maciek
 */
@SpringComponent
@UIScope
public class PracownikForm extends VerticalLayout implements KeyNotifier {

    private final PracownikRepository pracownikRepo;
    private final NotatkaRepository notatkaRepo;

    private Pracownik pracownik;
    private Notatka notatka;
    final Grid gridNotatka;

    private final TextField nazwisko = new TextField("Nazwisko");
    private final TextField imie = new TextField("Imię");
    private final TextField pesel = new TextField("PESEL");
    private final TextArea poleNotatki = new TextArea("Nowa notatka");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button dodajN = new Button("Zapisz");
    Button usunN = new Button("Usuń");
    HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);

    ComboBox<Stanowisko> stanowiskoBox = new ComboBox<>("Stanowisko", Stanowisko.values());

    Binder<Pracownik> binder = new Binder<>(Pracownik.class);
    Binder<Notatka> binderN = new Binder<>(Notatka.class);
    private ChangeHandler changeHandler;

    @Autowired
    public PracownikForm(PracownikRepository repo, NotatkaRepository nRepo) {

        this.pracownikRepo = repo;
        this.notatkaRepo = nRepo;

        this.gridNotatka = new Grid<>(Notatka.class);

        usunN.setEnabled(false);

        gridNotatka.setWidth("500px");
        gridNotatka.setColumns("tresc");
        gridNotatka.getColumnByKey("tresc").setHeader("Notatki");
        gridNotatka.setHeightByRows(true);
        gridNotatka.setSelectionMode(SelectionMode.SINGLE);
        gridNotatka.asSingleSelect().addValueChangeListener(event -> {
            usunN.setEnabled(true);
            getNotatka((Notatka) event.getValue());
        });

        add(buttonBar, nazwisko, imie, pesel, stanowiskoBox, poleNotatki, dodajN, usunN, gridNotatka);

        binder.bind(nazwisko, Pracownik::getNazwisko, Pracownik::setNazwisko);
        binder.bind(imie, Pracownik::getImie, Pracownik::setImie);
        binder.bind(pesel, Pracownik::getPesel, Pracownik::setPesel);
        binder.bind(stanowiskoBox, Pracownik::getObecneStanowisko, Pracownik::setObecneStanowisko);
        
        setSpacing(false);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        //Zapisywanie na klawisz Enter
        //addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        dodajN.addClickListener(e -> saveNotatka());
        usunN.addClickListener(e -> deleteNotatka());

        setVisible(false);
    }

    void delete() {
        gridNotatka.select(null);
        pracownikRepo.delete(pracownik);
        changeHandler.onChange();
    }

    void save() {
        pracownikRepo.save(pracownik);
        gridNotatka.select(null);
        changeHandler.onChange();
    }

    void cancel() {
        gridNotatka.select(null);
        changeHandler.onChange();
    }

    void saveNotatka() {
        notatkaRepo.save(new Notatka(poleNotatki.getValue(), pracownik));
        poleNotatki.setValue("");
        listNotatka(pracownik);
    }

    void deleteNotatka() {
        notatkaRepo.delete(notatka);
        gridNotatka.select(null);
        listNotatka(pracownik);
        usunN.setEnabled(false);
    }

    public final void getNotatka(Notatka n) {
        if (n == null) {
            return;
        }
        final boolean notIstnieje = n.getId() != null;
        if (notIstnieje) {
            notatka = notatkaRepo.findById(n.getId()).get();
        } else {
            notatka = n;
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editPracownik(Pracownik p) {
        if (p == null) {
            setVisible(false);
            return;
        }
        final boolean pracIstnieje = p.getId() != null;
        if (pracIstnieje) {
            pracownik = pracownikRepo.findById(p.getId()).get();
            listNotatka(pracownik);
        } else {
            pracownik = p;
            nazwisko.setValue(p.getNazwisko());
            imie.setValue(p.getImie());
            //pesel.setValue(p.getPesel());
            nazwisko.focus();
        }
        //elementy widoczne tylko przy edycji pracownika
        dodajN.setVisible(pracIstnieje);
        usunN.setVisible(pracIstnieje);
        poleNotatki.setVisible(pracIstnieje);
        gridNotatka.setVisible(pracIstnieje);

        binder.setBean(pracownik);
        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    private void listNotatka(Pracownik pracownik) {
        gridNotatka.setItems(notatkaRepo.findByPracownik(pracownik));
    }
}
