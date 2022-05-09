package org.java.world.dante.paradox;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.googlecode.paradox.ParadoxConnection;
import com.googlecode.paradox.data.TableData;
import com.googlecode.paradox.data.table.value.FieldValue;
import com.googlecode.paradox.metadata.ParadoxTable;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ParadoxConnection conn = null;
		try {
			Class.forName("com.googlecode.paradox.Driver");
		    conn = (ParadoxConnection) DriverManager.getConnection("jdbc:paradox:target/classes/db/");
		    final ParadoxTable table = TableData.listTables(conn, "20170224163011西南局.DB").get(0);
			final ArrayList<ArrayList<FieldValue>> data = TableData.loadData(conn, table, table.getFields());
			System.out.println();
			for (ArrayList<FieldValue> list : data) {
				for (FieldValue field : list) {
					System.out.print (field.getValue() + " || ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
