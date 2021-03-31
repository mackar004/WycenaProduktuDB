package pl.pracaInzynierska.views;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pl.torun.roma.RoMa3.views;
//
//import pl.torun.roma.RoMa3.model.Firma;
//import pl.torun.roma.RoMa3.repository.FirmaRepository;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import com.vaadin.flow.spring.annotation.UIScope;
//import com.vaadin.flow.theme.Theme;
//import com.vaadin.flow.theme.lumo.Lumo;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// *
// * @author m
// */
//@Route("user/firmy")
//@Theme(value = Lumo.class, variant = Lumo.DARK)
//@PageTitle("RoMa - Firmy")
//@SpringComponent
//@UIScope
//public class FirmyView extends VerticalLayout {
//
//    private final FirmaRepository firmaRepository;
//
//    private Firma firma;
//
//    private TextField nazwaFirmy = new TextField("Nazwa");
//    private TextField miasto = new TextField("Miasto");
//    private TextField kraj = new TextField("Kraj");
//    final Grid firmaGrid;
//
//    private final Button nowaFirmaBtn = new Button("Nowa firma");
//    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
//    private final Button cancel = new Button("Anuluj");
//    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
//    private final Button edit = new Button("Edytuj");
//    private final HorizontalLayout buttonBar = new HorizontalLayout(nowaFirmaBtn, save, cancel, edit, delete);
//    private final HorizontalLayout formularz = new HorizontalLayout(nazwaFirmy, miasto, kraj);
//
//    Binder<Firma> binderFirma = new Binder<>(Firma.class);
//
//    @Autowired
//    private FirmyView(FirmaRepository repo) {
//
//        add(new Button("Powrót", event -> {
//            listFirmy();
//            getUI().ifPresent(ui -> ui.navigate("main"));
//        }));
//
//        this.firmaRepository = repo;
//        firmaGrid = new Grid<>(Firma.class);
//
//        binderFirma.bindInstanceFields(this);
//
//        nazwaFirmy.setEnabled(false);
//        miasto.setEnabled(false);
//        kraj.setEnabled(false);
//        save.setEnabled(false);
//        cancel.setEnabled(false);
//        delete.setEnabled(false);
//        edit.setEnabled(false);
//
//        //nowafirmabtn
//        nowaFirmaBtn.addClickListener(e -> {
//            nazwaFirmy.setEnabled(true);
//            miasto.setEnabled(true);
//            kraj.setEnabled(true);
//            nowaFirmaBtn.setEnabled(false);
//            save.setEnabled(true);
//            cancel.setEnabled(true);
//            nazwaFirmy.focus();
//        });
//
//        //grid
//        firmaGrid.setWidth("500px");
//        firmaGrid.setColumns("nazwaFirmy", "miasto", "kraj");
//        firmaGrid.getColumnByKey("nazwaFirmy").setHeader("Nazwa");
//        firmaGrid.setHeightByRows(true);
//        firmaGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
//        firmaGrid.asSingleSelect().addValueChangeListener(e -> {
//            if (e.getValue() == null) {
//                nowaFirmaBtn.setEnabled(true);
//                delete.setEnabled(false);
//                edit.setEnabled(false);
//            } else {
//                this.firma = (Firma) e.getValue();
//                nowaFirmaBtn.setEnabled(false);
//                delete.setEnabled(true);
//                edit.setEnabled(true);
//            }
//        });
//        add(buttonBar, formularz, firmaGrid);
//
////        https://vaadin.com/components/vaadin-form-layout/java-examples
////        https://vaadin.com/components/vaadin-crud/java-examples
//        binderFirma.bind(nazwaFirmy, Firma::getNazwaFirmy, Firma::setNazwaFirmy);
//        binderFirma.bind(miasto, Firma::getMiasto, Firma::setMiasto);
//        binderFirma.bind(kraj, Firma::getKraj, Firma::setKraj);
//
//        delete.getElement().getThemeList().add("error");
//        delete.addClickListener(e -> delete());
//        edit.addClickListener(e -> editFirma(firma));
//        save.getElement().getThemeList().add("primary");
//        save.addClickListener(e -> save());
//        cancel.addClickListener(e -> cancel());
//
//        listFirmy();
//
//    }
//
//    public final void getFirma(Firma f) {
//        if (f == null) {
//            return;
//        }
//        final boolean nIstnieje = f.getId() != null;
//        if (nIstnieje) {
//            this.firma = firmaRepository.findById(f.getId()).get();
//        } else {
//            this.firma = f;
//        }
//    }
//
//    private void delete() {
//        if (firma == null) {
//            return;
//        }
//        firmaGrid.select(null);
//        firmaRepository.delete(firma);
//        this.firma = null;
//        nazwaFirmy.setValue("");
//        miasto.setValue("");
//        kraj.setValue("");
//        delete.setEnabled(false);
//        listFirmy();
//    }
//
//    private void save() {
//        ///////////
//        //zrobić firma = firmaRepo.findByName(nazwaFirmy.getValue)
//        ///////////
//        getFirma(firma);
//
//        if (firma != null) {
//            firma.setNazwaFirmy(nazwaFirmy.getValue());
//            firma.setMiasto(miasto.getValue());
//            firma.setKraj(kraj.getValue());
//            firmaRepository.save(firma);
//        } else {
//            firmaRepository.save(new Firma(nazwaFirmy.getValue(), miasto.getValue(), kraj.getValue()));
//        }
//        cancel();
//        listFirmy();
//
//    }
//
//    private void cancel() {
//        this.firma = null;
//        firmaGrid.select(null);
//        firmaGrid.setEnabled(true);
//        nazwaFirmy.setEnabled(false);
//        miasto.setEnabled(false);
//        kraj.setEnabled(false);
//        nowaFirmaBtn.setEnabled(true);
//        save.setEnabled(false);
//        cancel.setEnabled(false);
//        nazwaFirmy.setValue("");
//        miasto.setValue("");
//        kraj.setValue("");
//    }
//
//    public final void editFirma(Firma f) {
//        firmaGrid.setEnabled(false);
//        nazwaFirmy.setEnabled(true);
//        miasto.setEnabled(true);
//        kraj.setEnabled(true);
//        nowaFirmaBtn.setEnabled(false);
//        edit.setEnabled(false);
//        save.setEnabled(true);
//        cancel.setEnabled(true);
//        delete.setEnabled(false);
//
//        getFirma(f);
//
//        binderFirma.setBean(firma);
//        setVisible(true);
//    }
//
//    private void listFirmy() {
//        firmaGrid.setItems(firmaRepository.findAll());
//    }
//
//}
