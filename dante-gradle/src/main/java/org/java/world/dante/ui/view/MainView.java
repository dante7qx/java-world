package org.java.world.dante.ui.view;

import org.java.world.dante.dao.StudentDao;
import org.java.world.dante.po.StudentPO;
import org.java.world.dante.ui.editor.StudentEditor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("/ui")
public class MainView extends VerticalLayout {
	
	private final StudentDao studentDao;
	
	final StudentEditor editor;
	final Grid<StudentPO> grid;
	final TextField filter;
	final Button addNewBtn;

	private static final long serialVersionUID = 1L;

	public MainView(StudentDao studentDao, StudentEditor editor) {
		this.studentDao = studentDao;
		this.editor = editor;
		this.grid = new Grid<>(StudentPO.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("添加学生", VaadinIcon.PLUS.create());
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "name", "age", "updateDate");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("按姓名过滤");
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listStudents(e.getValue()));
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editStudent(e.getValue());
		});
		
		// Instantiate and edit new @Data the new button is clicked
		addNewBtn.addClickListener(e -> editor.editStudent(new StudentPO()));
		
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listStudents(filter.getValue());
		});
		
		add(grid);
		listStudents("");
	}
	
	private void listStudents(String filterText) {
		if(StringUtils.hasText(filterText)) {
			grid.setItems(studentDao.findByNameLikeOrderByAgeDesc("%" + filterText + "%"));
		} else {
			grid.setItems(studentDao.findAll(Sort.by(Direction.DESC, "age")));
		}
	}
	
}
