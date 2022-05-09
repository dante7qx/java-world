package com.googlecode.paradox.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;

import com.googlecode.paradox.ParadoxConnection;
import com.googlecode.paradox.metadata.ParadoxField;
import com.googlecode.paradox.metadata.ParadoxTable;
import com.googlecode.paradox.metadata.ParadoxView;
import com.googlecode.paradox.utils.filefilters.ViewFilter;

public class ViewData {
	// FIXME Use charset from last table
	// TODO Use lexical based parser

	private static Charset charset = Charset.forName("Cp1250");

	public static ArrayList<ParadoxView> listViews(final ParadoxConnection conn, final String tableName) throws SQLException {
		final ArrayList<ParadoxView> views = new ArrayList<ParadoxView>();
		final File[] fileList = conn.getDir().listFiles(new ViewFilter(tableName));
		for (final File file : fileList) {
			final ParadoxView view;
			try {
				view = ViewData.loadView(conn, file);
			} catch (final IOException ex) {
				throw new SQLException("Error loading Paradox tables.", ex);
			}
			if (view.isValid()) {
				views.add(view);
			}
		}
		return views;
	}

	public static ArrayList<ParadoxView> listViews(final ParadoxConnection conn) throws SQLException {
		final ArrayList<ParadoxView> views = new ArrayList<ParadoxView>();
		final File[] fileList = conn.getDir().listFiles(new ViewFilter());
		for (final File file : fileList) {
			final ParadoxView view;
			try {
				view = ViewData.loadView(conn, file);
			} catch (final IOException ex) {
				throw new SQLException("Error loading Paradox views.", ex);
			}
			if (view.isValid()) {
				views.add(view);
			}
		}
		return views;
	}

	private static ParadoxView loadView(final ParadoxConnection conn, final File file) throws IOException, SQLException {
		final ByteBuffer buffer = ByteBuffer.allocate(8192);

		buffer.order(ByteOrder.LITTLE_ENDIAN);
		FileChannel channel = null;
		final FileInputStream fs = new FileInputStream(file);
		final ParadoxView view = new ParadoxView(file, file.getName());

		try {
			channel = fs.getChannel();
			channel.read(buffer);
			buffer.flip();

			final BufferedReader reader = new BufferedReader(new StringReader(ViewData.charset.decode(buffer).toString()));
			if ("Query".equals(reader.readLine())) {
				// ANSWER
				String line = reader.readLine();
				// Extra Line
				line = reader.readLine();

				line = reader.readLine().trim();

				// FIELDORDER
				if (line.startsWith("FIELDORDER: ")) {
					line = line.substring("FIELDORDER: ".length());

					do {
						line += reader.readLine().trim();
					} while (line.endsWith(","));

					ParadoxTable lastTable = null;
					final ArrayList<ParadoxField> fields = new ArrayList<ParadoxField>();
					final String[] cols = line.split("\\,");
					for (final String col : cols) {
						final String[] i = col.split("->");
						final ParadoxField field = new ParadoxField();

						if (i.length < 2) {
							if (lastTable == null) {
								throw new SQLException("Invalid table.");
							}
							// TODO review. Paradox looks the number after columns (always calc fileds)
							// field.setName(getFieldByIndex(lastTable, i));
							continue;
						} else {
							lastTable = ViewData.getTable(conn, i[0]);
							field.setName(i[1].substring(1, i[1].length() - 1));
						}
						final ParadoxField originalField = ViewData.getFieldByName(lastTable, field.getName());

						field.setType(originalField.getType());
						field.setSize(originalField.getSize());
						fields.add(field);
					}
					view.setFieldsOrder(fields);

					// Extra line
					line = reader.readLine();
					// New Line
					line = reader.readLine().trim();
				}

				// SORT
				if (line.startsWith("SORT: ")) {
					line = line.substring("SORT: ".length());

					do {
						line += reader.readLine().trim();
					} while (line.endsWith(","));

					ParadoxTable lastTable = null;
					final ArrayList<ParadoxField> fields = new ArrayList<ParadoxField>();
					final String[] cols = line.split("\\,");
					for (final String col : cols) {
						final String[] i = col.split("->");
						final ParadoxField field = new ParadoxField();

						if (i.length < 2) {
							if (lastTable == null) {
								throw new SQLException("Invalid table.");
							}
							// TODO review. Paradox looks the number after columns (always calc fileds)
							// field.setName(getFieldByIndex(lastTable, i));
							continue;
						} else {
							lastTable = ViewData.getTable(conn, i[0]);
							field.setName(i[1].substring(1, i[1].length() - 1));
						}
						final ParadoxField originalField = ViewData.getFieldByName(lastTable, field.getName());

						field.setType(originalField.getType());
						field.setSize(originalField.getSize());
						fields.add(field);
					}
					view.setFieldsSort(fields);

					// Extra Line
					line = reader.readLine();
					// New Line
					line = reader.readLine().trim();
				}

				final ArrayList<ParadoxField> fields = new ArrayList<ParadoxField>();
				do {
					// Fields
					final String[] flds = line.split("\\|");
					final String table = flds[0].trim();

					for (int loop = 1; loop < flds.length; loop++) {
						final String name = flds[loop].trim();
						final ParadoxField field = new ParadoxField();
						final ParadoxField original = ViewData.getFieldByName(ViewData.getTable(conn, table), name);

						field.setTableName(table);
						field.setName(name);
						field.setType(original.getType());
						field.setSize(original.getSize());
						fields.add(field);
					}
					line = reader.readLine();
					final String[] types = line.split("\\|");
					for (int loop = 1; loop < types.length; loop++) {
						if (types[loop].trim().length() > 0) {
							final ParadoxField field = fields.get(loop - 1);
							ViewData.parseExpression(field, types[loop]);
						}
					}
					// Extra Line
					line = reader.readLine();

					// New Line
					line = reader.readLine().trim();
				} while (line != null && !"EndQuery".equals(line));

				view.setPrivateFields(fields);
				view.setValid(true);
			}
		} finally {
			if (channel != null) {
				channel.close();
			}
			fs.close();
		}
		return view;
	}

	private static ParadoxField getFieldByName(final ParadoxTable table, final String name) {
		ParadoxField originalField = null;
		for (final ParadoxField f : table.getFields()) {
			if (f.getName().equals(name)) {
				originalField = f;
				break;
			}
		}
		if (originalField == null) {
			// FIXME Fix charset
			originalField = new ParadoxField();
			originalField.setType((byte) 1);
		}
		return originalField;
	}

	private static ParadoxTable getTable(final ParadoxConnection conn, final String tableName) throws SQLException {
		final ArrayList<ParadoxTable> tables = TableData.listTables(conn, tableName.trim());
		if (tables.size() > 0) {
			return tables.get(0);
		}
		throw new SQLException("Table " + tableName + " not found");
	}

	public static void parseExpression(final ParadoxField field, final String expression) {
		final StringBuilder builder = new StringBuilder(expression.trim());

		if (builder.indexOf("Check") == 0) {
			builder.delete(0, "Check".length() + 1);
			field.setChecked(true);
		}
		if (builder.length() > 0) {
			if (builder.charAt(0) == '_') {
				final StringBuilder temp = new StringBuilder(builder.length());

				forloop: for (final char c : builder.toString().toCharArray()) {
					switch (c) {
					case ' ':
					case ',':
						break forloop;
					default:
						temp.append(c);
					}
				}
				final String name = temp.toString();
				builder.delete(0, name.length());
				field.setJoinName(name);
			}
			final String typeTest = builder.toString().trim();
			if (typeTest.toUpperCase().startsWith("AS")) {
				field.setAlias(typeTest.substring(3).trim());
			} else {
				if (typeTest.startsWith(",")) {
					builder.delete(0, 1);
				}
				final int index = builder.toString().toUpperCase().lastIndexOf("AS");
				if (index != -1) {
					field.setExpression(builder.substring(0, index).trim());
					field.setAlias(builder.substring(index + 3).trim());
				} else {
					field.setExpression(builder.toString().trim());
				}
				if (field.getExpression().toUpperCase().startsWith("CALC")) {
					field.setChecked(true);
				}
			}
		}
	}

	private ViewData() {
	}
}
