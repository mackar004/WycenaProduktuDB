package pl.pracaInzynierska.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author maciek
 */
public class NavigationBar extends HorizontalLayout {

    NavigationBar() {
        Button btnMain = new Button("MENU", new Icon(VaadinIcon.MENU));
        btnMain.setHeight("70px");
        btnMain.addClickListener(e -> {
            btnMain.getUI().ifPresent(ui -> ui.navigate("main"));
        });

        Button btnPracownicy = new Button("Pracownicy", new Icon(VaadinIcon.MALE));
        btnPracownicy.setHeight("70px");
        btnPracownicy.addClickListener(e -> {
            btnPracownicy.getUI().ifPresent(ui -> ui.navigate("user/pracownicy"));
        });

        Button btnGrupy = new Button("Grupy", new Icon(VaadinIcon.USERS));
        btnGrupy.setHeight("70px");
        btnGrupy.addClickListener(e -> {
            btnGrupy.getUI().ifPresent(ui -> ui.navigate("user/grupy"));
        });

        add(btnMain, btnPracownicy, btnGrupy);
    }
}
