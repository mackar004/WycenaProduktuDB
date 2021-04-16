package pl.pracaInzynierska.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import pl.pracaInzynierska.repository.GrupaRepository;
import pl.pracaInzynierska.repository.PracownikRepository;
import pl.pracaInzynierska.model.Grupa;
import org.springframework.util.StringUtils;

/**
 *
 * @author m
 */
@Route(value = "user/grupy")
public class GrupaView extends VerticalLayout {

    final NavigationBar menu;

    private final GrupaRepository grupaRepo;
    final Grid grupaGrid;
    final TextField grupaFiltr;
    final Button nowaGrupa;
    final HorizontalLayout panel;
    final GrupaForm grupaForm;

    public GrupaView(GrupaRepository grupaRepo, PracownikRepository pracownikRepo) {

        this.grupaRepo = grupaRepo;

        Dialog formularz = new Dialog();

        menu = new NavigationBar();
        grupaFiltr = new TextField();
        grupaFiltr.setPlaceholder("Szukaj...");
        grupaFiltr.setValueChangeMode(ValueChangeMode.EAGER);
        grupaFiltr.addValueChangeListener(e -> listGrupa(e.getValue()));

        nowaGrupa = new Button("Dodaj grupę", VaadinIcon.PLUS.create());

        this.grupaForm = new GrupaForm(grupaRepo, pracownikRepo);
        formularz.setWidth("450px");
        formularz.add(this.grupaForm);
        formularz.setCloseOnEsc(false);
        formularz.setCloseOnOutsideClick(false);

        nowaGrupa.addClickListener(e -> {
            formularz.open();
            this.grupaForm.editGrupa(new Grupa(""));
        });

        this.grupaForm.setChangeHandler(() -> {
            this.grupaForm.setVisible(false);
            listGrupa(grupaFiltr.getValue());
            formularz.close();
        });

        panel = new HorizontalLayout(grupaFiltr, nowaGrupa);

        this.grupaGrid = new Grid<>(Grupa.class);
        grupaGrid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grupaGrid.asSingleSelect().addValueChangeListener(e -> {
            formularz.open();
            this.grupaForm.editGrupa((Grupa) e.getValue());
        });

        add(menu, panel, grupaGrid);

        listGrupa(grupaFiltr.getValue());

    }

    private void listGrupa(String filtr) {
        if (StringUtils.isEmpty(filtr)) {
            grupaGrid.setItems(grupaRepo.findAll());
        } else {
            grupaGrid.setItems(grupaRepo.findByNazwaContains(filtr));
        }

    }

}
