package pl.pracaInzynierska.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.util.StringUtils;
import pl.pracaInzynierska.forms.MaterialForm;
import pl.pracaInzynierska.model.Materialy;
import pl.pracaInzynierska.repository.MaterialyRepository;

/**
 *
 * @author m
 */
@Route("user/materialy")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Materiały")
public class MaterialyView extends VerticalLayout {

    private final MaterialyRepository materialyRepository;

    private Materialy material;
    
    final private MaterialForm materialForm;

    private TextField nazwaMaterialu = new TextField("Nazwa");
    private TextField cenaMaterialu = new TextField("Cena");
    final Grid materialyGrid;

    private final Button nowyMaterial;
    final TextField filtrMaterial;
    final Dialog dialog;

    private MaterialyView(MaterialyRepository materialyRepository, MaterialForm materialForm) {

        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("main"));
        }));

        this.materialyRepository = materialyRepository;
        this.materialForm = materialForm;
        materialyGrid = new Grid<>(Materialy.class);

        this.filtrMaterial = new TextField();
        this.dialog = new Dialog();

        this.nowyMaterial = new Button("Nowy", VaadinIcon.PLUS.create());

        HorizontalLayout filterBar = new HorizontalLayout(filtrMaterial, nowyMaterial);

        filtrMaterial.setPlaceholder("Szukaj w bazie");
        filtrMaterial.setValueChangeMode(ValueChangeMode.EAGER);
        filtrMaterial.addValueChangeListener(e -> listMaterialy(e.getValue()));

        dialog.add(this.materialForm);
        dialog.setWidth("630px");
        dialog.setHeight("400px");
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        materialyGrid.setColumns("nazwa", "cena", "dataWprowadzenia");
        materialyGrid.getColumnByKey("nazwa").setWidth("120px").setFlexGrow(0).setSortProperty("nazwa");

        materialyGrid.asSingleSelect().addValueChangeListener(e -> {
            nowyMaterial.setEnabled(false);
            dialog.open();
            this.materialForm.editMaterial((Materialy) e.getValue());
        });

        nowyMaterial.addClickListener(e -> {
            nazwaMaterialu.setEnabled(true);
            cenaMaterialu.setEnabled(true);
            nowyMaterial.setEnabled(false);
            nazwaMaterialu.focus();
        });

        // Stworzenie i edytowanie nowego pracownika po kliknięciu przycisku Nowy
        nowyMaterial.addClickListener(e -> {
            dialog.open();
            this.materialForm.editMaterial(new Materialy("", ""));
        });

        // Zamykanie okna po kliknięciu na przycisk i odświeżenie danych
        this.materialForm.setChangeHandler(() -> {
            this.materialForm.setVisible(false);
            listMaterialy(filtrMaterial.getValue());
            nowyMaterial.setEnabled(true);
            dialog.close();
        });

        add(filterBar, materialyGrid);

        listMaterialy(filtrMaterial.getValue());

    }

    private void listMaterialy(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            materialyGrid.setItems(materialyRepository.findAll());
        } else {
            materialyGrid.setItems(materialyRepository.findByNazwaContainsIgnoreCase(filterText));

        }
    }

}
