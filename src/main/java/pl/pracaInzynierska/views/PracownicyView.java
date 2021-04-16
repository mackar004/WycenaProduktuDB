package pl.pracaInzynierska.views;

import pl.pracaInzynierska.forms.PracownikForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import pl.pracaInzynierska.repository.NotatkaRepository;
import pl.pracaInzynierska.repository.PracownikRepository;
import pl.pracaInzynierska.model.Pracownik;
import org.springframework.util.StringUtils;

@Route(value = "user/pracownicy")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class PracownicyView extends VerticalLayout {

    private final PracownikRepository repo;
    private final NotatkaRepository notatkaRepo;
    
    final PracownikForm pracownikForm;
    
    final Grid grid;
    final TextField filtrPracownik;
    final NavigationBar menu;
    final Dialog dialog;
    private final Button nowyPracownik;

    public PracownicyView(PracownikRepository repo, PracownikForm pracownikForm, NotatkaRepository notatkaRepo) {
        this.repo = repo;
        this.notatkaRepo = notatkaRepo;

        this.grid = new Grid<>(Pracownik.class);
        
        this.filtrPracownik = new TextField();
        this.menu = new NavigationBar();
        this.pracownikForm = new PracownikForm(repo, notatkaRepo);
        this.dialog = new Dialog();

        this.nowyPracownik = new Button("Nowy pracownik", VaadinIcon.PLUS.create());

        HorizontalLayout filterBar = new HorizontalLayout(filtrPracownik, nowyPracownik);

        filtrPracownik.setPlaceholder("Szukaj w bazie");
        filtrPracownik.setValueChangeMode(ValueChangeMode.EAGER);
        filtrPracownik.addValueChangeListener(e -> listPracownik(e.getValue()));

        dialog.add(this.pracownikForm);
        dialog.setWidth("600px");
        dialog.setHeight("400px");
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        
        grid.setColumns("id", "nazwisko", "imie");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0).setSortProperty("id");

        // Edycja istniejącego pracownika po wybraniu go z tabeli
        grid.asSingleSelect().addValueChangeListener(e -> {
            dialog.open();
            this.pracownikForm.editPracownik((Pracownik) e.getValue());
        });

        // Stworzenie i edytowanie nowego pracownika po kliknięciu przycisku Nowy
        nowyPracownik.addClickListener(e -> {
            dialog.open();
            this.pracownikForm.editPracownik(new Pracownik("", ""));
        });

        // Zamykanie okna po kliknięciu na przycisk i odświeżenie danych
        this.pracownikForm.setChangeHandler(() -> {
            this.pracownikForm.setVisible(false);
            listPracownik(filtrPracownik.getValue());
            dialog.close();
        });

        add(menu, filterBar, grid);

        listPracownik(filtrPracownik.getValue());
    }

    private void listPracownik(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByNazwiskoContainsOrImieContains(filterText, filterText));

        }
    }

}
