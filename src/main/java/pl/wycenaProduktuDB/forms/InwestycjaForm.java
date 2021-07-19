package pl.wycenaProduktuDB.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.wycenaProduktuDB.model.Inwestycja;
import pl.wycenaProduktuDB.repository.InwestycjaRepository;
import com.vaadin.flow.data.binder.Binder;
import pl.wycenaProduktuDB.model.Firma;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class InwestycjaForm extends VerticalLayout implements KeyNotifier {

    private final InwestycjaRepository inwestycjaRepo;

    private Firma firma;
    private Inwestycja inwestycja;

    private final TextField inwestycjaNazwa = new TextField("Nazwa");
    private final TextField inwestycjaMiasto = new TextField("Miasto");
    private final TextField nazwaFirmy = new TextField("Firma");

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);
    private final HorizontalLayout formularz = new HorizontalLayout(inwestycjaNazwa, inwestycjaMiasto, nazwaFirmy);

    Binder<Inwestycja> binderInwestycja = new Binder<>(Inwestycja.class);

    private InwestycjaForm.ChangeHandler changeHandler;

    @Autowired
    public InwestycjaForm(InwestycjaRepository inwestycjaRepo) {

        this.inwestycjaRepo = inwestycjaRepo;

        delete.setEnabled(false);
        nazwaFirmy.setEnabled(false);

        add(buttonBar, formularz); //, inwestycjeGrid);

        binderInwestycja.bind(inwestycjaNazwa, Inwestycja::getInwestycjaNazwa, Inwestycja::setInwestycjaNazwa);
        binderInwestycja.bind(inwestycjaMiasto, Inwestycja::getInwestycjaMiasto, Inwestycja::setInwestycjaMiasto);

        setSpacing(false);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void save() {
        inwestycjaRepo.save(inwestycja);
        changeHandler.onChange();
    }

    void delete() {
        inwestycja.setFirma(null);
        inwestycjaRepo.save(inwestycja);
        inwestycjaRepo.delete(inwestycja);
        inwestycjaRepo.flush();
        changeHandler.onChange();
    }

    void cancel() {
        changeHandler.onChange();
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editInwestycja(Inwestycja i, Firma firma) {
        if (i == null) {
            setVisible(false);
            return;
        }
        this.firma = firma;
        if (firma != null) {
            nazwaFirmy.setValue(this.firma.getNazwaFirmy());
        }
        final boolean inwestycjaIstnieje = i.getInwestycja_id() != null;
        if (inwestycjaIstnieje) {
            inwestycja = inwestycjaRepo.findById(i.getInwestycja_id()).get();
            delete.setEnabled(true);
        } else {
            inwestycja = i;
            inwestycjaNazwa.focus();
        }
        binderInwestycja.setBean(inwestycja);
        setVisible(true);
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
