/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;
import pl.torun.roma.RoMa3.repository.WycenaRepository;

/**
 *
 * @author m
 */
@Route("user/firmy/inwestycje/wyceny")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Wyceny")
public class WycenyView extends VerticalLayout implements HasUrlParameter<String> {

    private final WycenaRepository wycenaRepository;
    private final InwestycjaRepository inwestycjaRepository;

    private WycenyView(WycenaRepository wycenaRepository, InwestycjaRepository inwestycjaRepository) {

        this.wycenaRepository = wycenaRepository;
        this.inwestycjaRepository = inwestycjaRepository;
        
        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje"));
        }));
    }
    
    
        @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter == null) {
           // this.inwestycja = null;
            //anulujFirme.setEnabled(false);
        } else {
           // this.inwestycja = (Inwestycja) inwestycjaRepository.findByNazwa(parameter);
//            anulujFirme.setEnabled(true);
//            aktualnaFirma.setReadOnly(false);
//            aktualnaFirma.setValue(this.firma.toString());
//            aktualnaFirma.setReadOnly(true);
        }
    }
}
