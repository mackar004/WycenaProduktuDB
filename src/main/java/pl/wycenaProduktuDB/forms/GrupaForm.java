package pl.wycenaProduktuDB.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.wycenaProduktuDB.repository.GrupaRepository;
import pl.wycenaProduktuDB.repository.PracownikRepository;
import pl.wycenaProduktuDB.model.Grupa;
import pl.wycenaProduktuDB.model.Pracownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class GrupaForm extends VerticalLayout implements KeyNotifier {

    private final GrupaRepository grupaRepo;
    private final PracownikRepository pracownikRepo;

    final Grid gridPracownik;

    private Pracownik pracownik;
    private Grupa grupa;

    private final TextField nazwaGrupy = new TextField("Nazwa grupy");
    private final TextField pracownikFiltr = new TextField("Szukaj pracownika");

    private final Button save = new Button("Save", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Cancel");
    private final Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private Button dodajPracownika = new Button("Dodaj pracownika");
    HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);

    Binder<Grupa> binder = new Binder<>(Grupa.class);
    Binder<Pracownik> binderP = new Binder<>(Pracownik.class);

    private ChangeHandler changeHandlerG;

    @Autowired
    public GrupaForm(GrupaRepository grupaRepo, PracownikRepository pracownikRepo) {

        this.grupaRepo = grupaRepo;
        this.pracownikRepo = pracownikRepo;

        this.gridPracownik = new Grid<>(Pracownik.class);

        gridPracownik.setSelectionMode(SelectionMode.SINGLE);
        gridPracownik.setColumns("nazwisko", "imie", "obecneStanowisko");
        gridPracownik.setWidth("400px");
        gridPracownik.asSingleSelect().addValueChangeListener(event -> {
            event.getValue();
            getPracownik((Pracownik) event.getValue());
            Notification not = new Notification("wybrano pracownika" + pracownik, 0, Notification.Position.MIDDLE);
        });

        gridPracownik.setVisible(false);

        pracownikFiltr.setPlaceholder("Szukaj pracownika");
        pracownikFiltr.setValueChangeMode(ValueChangeMode.EAGER);
        pracownikFiltr.addValueChangeListener(e -> {
            listPracownik(e.getValue());
        });
        pracownikFiltr.setVisible(false);

        binder.bind(nazwaGrupy, Grupa::getNazwa, Grupa::setNazwa);

        save.addClickListener(e -> {
            save();
        });
        delete.addClickListener(e -> delete());

        cancel.addClickListener(e -> cancel());
        dodajPracownika.addClickListener(e -> {
            dodajPrac();
            //Poniższy if obsługuje wielofunkcyjny przycisk
            if (dodajPracownika.getText().equalsIgnoreCase("Przypisz pracownika")) {
                pracownik.setGrupa(grupa);
                pracownikRepo.save(pracownik);
                Notification show = Notification.show("Przypisano pracowników!");
                show.setPosition(Notification.Position.MIDDLE);
            } else {
                dodajPracownika.setText("Przypisz pracownika");
            }
        });

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        add(buttonBar, nazwaGrupy, dodajPracownika, pracownikFiltr, gridPracownik);

    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editGrupa(Grupa g) {
        if (g == null) {
            return;
        }
        final boolean grupaIstnieje = g.getId() != null;
        if (grupaIstnieje) {
            grupa = grupaRepo.findById(g.getId()).get();
            listPracownik(pracownikFiltr.getValue());
        } else {
            grupa = g;
            nazwaGrupy.focus();
        }
        dodajPracownika.setVisible(grupaIstnieje);
        binder.setBean(grupa);
        setVisible(true);
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandlerG = h;
    }

    void save() {
        grupaRepo.save(grupa);
        pracownikFiltr.setVisible(false);
        gridPracownik.setVisible(false);
        gridPracownik.select(null);
        dodajPracownika.setText("Dodaj pracownika");
        changeHandlerG.onChange();
    }

    void cancel() {
        pracownikFiltr.setVisible(false);
        gridPracownik.setVisible(false);
        gridPracownik.select(null);
        dodajPracownika.setText("Dodaj pracownika");
        changeHandlerG.onChange();
    }

    void delete() {
        grupa.getPracownik().forEach(prac -> {
            prac.setGrupa(null);
            pracownikRepo.save(prac);
        });
        grupaRepo.delete(grupa);
        pracownikFiltr.setVisible(false);
        gridPracownik.setVisible(false);
        gridPracownik.select(null);
        dodajPracownika.setText("Dodaj pracownika");
        changeHandlerG.onChange();
    }

    void dodajPrac() {
        pracownikFiltr.setVisible(true);
        gridPracownik.setVisible(true);
    }

    public final void getPracownik(Pracownik p) {
        if (p == null) {
            return;
        }
        final boolean pracIstnieje = p.getId() != null;
        if (pracIstnieje) {
            pracownik = pracownikRepo.findById(p.getId()).get();
        } else {
            pracownik = p;
        }
    }

    private void listPracownik(String pracownikFiltr) {
        if (StringUtils.isEmpty(pracownikFiltr)) {
            gridPracownik.setItems(pracownikRepo.findAll());
        } else {
            gridPracownik.setItems(pracownikRepo.findByNazwiskoContainsOrImieContains(pracownikFiltr, pracownikFiltr));
        }
    }
}
