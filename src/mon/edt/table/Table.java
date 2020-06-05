package mon.edt.table;

import java.sql.Connection;

public abstract class Table<T> {

	protected Connection connect = null;

	public Table(Connection connect) {
		this.connect = connect;
	}

	public abstract T find(int id);
}
