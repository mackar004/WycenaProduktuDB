package pl.pracaInzynierska.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import pl.pracaInzynierska.model.Uzytkownik;
import pl.pracaInzynierska.repository.UzytkownikRepository;

/**
 *
 * @author m
 */
@Route("admin/uzytkownicy")
@PageTitle("Admin - Uzytkownicy")
public class AdminUzytkownicy extends VerticalLayout {

    private final UzytkownikRepository repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Uzytkownik uzytkownik;

    final Grid grid;
    final TextField filtrUzytkownik;
    final TextField poleUsername;
    final TextField poleHaslo;
    final TextField poleRola;

    private final Button adminMenu;

    private final Button nowyUzytkownik;
    private final Button kasujUzytkownik;
    private final Button zapiszUzytkownik;

    Binder<Uzytkownik> binder = new Binder<>(Uzytkownik.class);

    public AdminUzytkownicy(UzytkownikRepository repo) {
        this.repo = repo;
        this.grid = new Grid<>(Uzytkownik.class);

        this.filtrUzytkownik = new TextField("Szukaj");
        this.poleUsername = new TextField("Username");
        this.poleRola = new TextField("Rola");
        this.poleHaslo = new TextField("Haslo 1");
        poleUsername.setEnabled(true);
        poleHaslo.setEnabled(true);
        poleRola.setEnabled(true);

        this.adminMenu = new Button("Wstecz");

        this.nowyUzytkownik = new Button("Nowy pracownik", VaadinIcon.PLUS.create());
        this.nowyUzytkownik.setEnabled(true);
        this.kasujUzytkownik = new Button("Kasuj", VaadinIcon.PLUS.create());
        this.kasujUzytkownik.setEnabled(false);
        this.zapiszUzytkownik = new Button("Zapisz", VaadinIcon.PLUS.create());
        this.zapiszUzytkownik.setEnabled(true);

        HorizontalLayout filterBar = new HorizontalLayout(filtrUzytkownik, nowyUzytkownik, kasujUzytkownik, zapiszUzytkownik);
        HorizontalLayout dataBar = new HorizontalLayout(poleUsername, poleHaslo, poleRola);

        filtrUzytkownik.setPlaceholder("Szukaj w bazie");
        filtrUzytkownik.setValueChangeMode(ValueChangeMode.EAGER);
        filtrUzytkownik.addValueChangeListener(e -> listUzytkownicy(e.getValue()));

        grid.setColumns("id", "username", "role");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0).setSortProperty("id");

        binder.bind(poleUsername, Uzytkownik::getUsername, Uzytkownik::setUsername);
        binder.bind(poleHaslo, Uzytkownik::getPassword, Uzytkownik::setPassword);
        binder.bind(poleRola, Uzytkownik::getRole, Uzytkownik::setRole);

        grid.asSingleSelect().addValueChangeListener(e -> {
            this.uzytkownik = ((Uzytkownik) e.getValue());
        });

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (grid.getSelectedItems().isEmpty()) {
                this.nowyUzytkownik.setEnabled(true);
                this.kasujUzytkownik.setEnabled(false);
                poleUsername.setValue("");
                poleHaslo.setValue("");
                poleRola.setValue("");
            } else {
                this.uzytkownik = (Uzytkownik) e.getValue();
                this.nowyUzytkownik.setEnabled(false);
                this.kasujUzytkownik.setEnabled(true);
                poleUsername.setValue(this.uzytkownik.getUsername());
                poleRola.setValue(this.uzytkownik.getRole());
            }
        });

        adminMenu.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("admin/menu"));
        });
        
        nowyUzytkownik.addClickListener(e -> {
            this.uzytkownik = null;
            poleUsername.setValue("");
            poleHaslo.setValue("");
            poleRola.setValue("");
            this.kasujUzytkownik.setEnabled(false);
            this.zapiszUzytkownik.setEnabled(true);
        });

        kasujUzytkownik.addClickListener(e -> {
            this.nowyUzytkownik.setEnabled(true);
            this.kasujUzytkownik.setEnabled(false);
            repo.delete(this.uzytkownik);
            listUzytkownicy(filtrUzytkownik.getValue());
        });

        zapiszUzytkownik.addClickListener(e -> {
            if ((!poleUsername.equals(null)) && (!poleHaslo.equals(null)) && (!poleRola.equals(null))) {
                this.uzytkownik = new Uzytkownik(poleUsername.getValue(), true, poleRola.getValue(), poleHaslo.getValue());
                repo.save(this.uzytkownik);
                listUzytkownicy(filtrUzytkownik.getValue());
            } else {
                poleUsername.setValue("Uzupełnij dane");
                poleHaslo.setValue("");
                poleRola.setValue("");
            }
            poleUsername.setValue("");
            poleHaslo.setValue("");
            poleRola.setValue("");
            this.uzytkownik = null;
            this.nowyUzytkownik.setEnabled(true);
            this.kasujUzytkownik.setEnabled(false);
            this.zapiszUzytkownik.setEnabled(false);
        });

        binder.setBean(uzytkownik);
        add(adminMenu, filterBar, dataBar, grid);
        listUzytkownicy(filtrUzytkownik.getValue());
    }

    private void listUzytkownicy(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByUsernameContainsIgnoreCase(filterText));
        }
    }

}
