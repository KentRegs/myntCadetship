package javaLabs2;

import java.sql.Connection;
import java.util.ArrayList;

public interface ManagePromo {
	public ArrayList<String> retrievePromos(String stringValue, Connection con);
}
