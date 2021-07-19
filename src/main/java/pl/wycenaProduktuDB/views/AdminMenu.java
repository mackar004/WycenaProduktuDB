package pl.wycenaProduktuDB.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 *
 * @author m
 */
@Route("admin/menu")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("Admin menu")
public class AdminMenu extends VerticalLayout {

    private final Button menu = new Button("Powrót do Menu");
    private final Button dodajUzytkownika = new Button("Dodaj uzytkownika");
    private final Button wyloguj = new Button("Wyloguj");

    public AdminMenu() {
        setAlignItems(Alignment.CENTER);

        add(menu);
        menu.setWidth("250px");
        menu.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("main"));
        });

        add(dodajUzytkownika);
        dodajUzytkownika.setWidth("250px");
        dodajUzytkownika.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("admin/uzytkownicy"));
        });

        add(wyloguj);
        wyloguj.setWidth("250px");
        wyloguj.getElement().setAttribute("theme", "error tertiary");
        wyloguj.addClickListener(event -> {
            getUI().get().getPage().executeJavaScript("window.location.href='logout'");
            getUI().get().getSession().close();
        });
    }
}
