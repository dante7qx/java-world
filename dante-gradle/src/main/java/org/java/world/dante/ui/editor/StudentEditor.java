package org.java.world.dante.ui.editor;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

import org.java.world.dante.dao.StudentDao;
import org.java.world.dante.po.StudentPO;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class StudentEditor extends VerticalLayout implements KeyNotifier {

	private static final long serialVersionUID = 1L;
	private final StudentDao studentDao;
	
	private StudentPO student;
	
	TextField name = new TextField("姓名");
	
	Button save = new Button("保存", VaadinIcon.CHECK.create());
	Button cancel = new Button("取消");
	Button delete = new Button("删除", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<StudentPO> binder = new Binder<>(StudentPO.class);
	private ChangeHandler changeHandler;
	
	public StudentEditor(StudentDao studentDao) {
		this.studentDao = studentDao;
		
		add(name, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editStudent(student));
		setVisible(false);
	}
	
	void delete() {
		studentDao.delete(student);
		changeHandler.onChange();
	}

	void save() {
		student.setAge(new Random().nextInt(39 - 21 + 1) + 21);
		student.setUpdateDate(Date.from(Instant.now()));
		studentDao.save(student);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editStudent(StudentPO stu) {
		if (stu == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = stu.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			student = studentDao.findById(stu.getId()).get();
		}
		else {
			student = stu;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(student);

		setVisible(true);

		// Focus first name initially
		name.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}
	
	
}
