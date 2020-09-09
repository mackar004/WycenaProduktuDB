/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.views;

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
@Route("user/firmy/inwestycje/wyceny")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Wyceny")
public class WycenyView extends VerticalLayout {

    private WycenyView() {

        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje"));
        }));
    }
}
