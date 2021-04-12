/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.views;

import com.vaadin.flow.component.UI;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
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

    /*
	private static final long serialVersionUID = 3461787310452366610L;
	private Grid<Student> sGrid = new Grid<>(Student.class);
	private TextField filter = new TextField();
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newStudent = new Button("Add");
	private InfoPanel infoPanel = new InfoPanel();
	@Autowired
	StudentRepository repo;
     */
    private final Button uzytkownicy = new Button("Użytkownicy");
//    private final Button zarzadzanieBaza = new Button("Zarządzanie bazą");
    private final Button firmy = new Button("Firmy");
    private final Button inwestycje = new Button("Inwestycje");
    private final Button materialy = new Button("Materiały");
    private final Button szukaj = new Button("Szukaj");
    private final Button wyloguj = new Button("Wyloguj");

    public MainView() {
        setAlignItems(Alignment.CENTER);

        add(uzytkownicy);
        uzytkownicy.setWidth("250px");
        uzytkownicy.addClickListener(e -> Notification.show("No jest ich dwóch chwilowo, a tak w ogóle, to są zdeaktywowani"));

//        add(zarzadzanieBaza);
//        zarzadzanieBaza.setWidth("250px");
//        zarzadzanieBaza.addClickListener(event -> {
//            getUI().ifPresent(ui -> ui.navigate("admin/zarzadzanie"));
//        });
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

        add(szukaj);
        szukaj.setWidth("250px");

        ////////////
        // Działające wylogowanie
        ////////////
//          Ustawianie stylu przycisku
//          yourButton.getElement().setAttribute("theme", " string1 string2");
//          string1 can be : error(red), success(green), contrast(black) or nothing (blue)
//          string2 can be : primary, secondary or tertiary
        add(wyloguj);
        wyloguj.setWidth("250px");
        wyloguj.getElement().setAttribute("theme", "error tertiary");
        wyloguj.addClickListener(event -> {
            getUI().get().getPage().executeJavaScript("window.location.href='logout'");
            getUI().get().getSession().close();
        });

    }

}
