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
@Route("main")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Strona główna")
public class MainView extends VerticalLayout {

    private final Button pracownicy = new Button("Pracownicy");
    private final Button firmy = new Button("Firmy");
    private final Button inwestycje = new Button("Inwestycje");
    private final Button materialy = new Button("Materiały");
    private final Button wyloguj = new Button("Wyloguj");
    private final Button adminMenu = new Button("Admin");
    
    public MainView() {
        setAlignItems(Alignment.CENTER);

        add(pracownicy);
        pracownicy.setWidth("250px");
        pracownicy.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("user/pracownicy"));
                });

        add(firmy);
        firmy.setWidth("250px");
        firmy.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy"));
        });

        add(inwestycje);
        inwestycje.setWidth("250px");
        inwestycje.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje"));
        });

        add(materialy);
        materialy.setWidth("250px");
        materialy.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("user/materialy"));
        });
      
       add(adminMenu);
        adminMenu.setWidth("250px");
        adminMenu.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("admin/menu"));
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
