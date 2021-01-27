package javaLabs2;

import java.sql.Connection;
import java.util.ArrayList;

public interface ManagePromo {
	public ArrayList<Promo> retrievePromos(String stringValue, Connection con);
}
