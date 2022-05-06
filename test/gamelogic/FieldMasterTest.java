package gamelogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FieldMasterTest {
	
	/**
	 * Teszteli, hogy t�rt�nik-e MineException dob�s.
	 * 
	 * @throws Exception
	 */
	@Test (expected=MineException.class)
	public void testMineException() throws Exception {
		FieldMaster fm = new FieldMaster(8, 8, 65, null);
	}
	
	/**
	 * Teszteli, hogy a megfelel� mennyis�g� akna kioszt�sra ker�l-e a mez�k k�z�tt.
	 */
	@Test
	public void testMethod_buildUpFields() {
		int mines_expected = 10;
		try {
			FieldMaster fm = new FieldMaster(8, 8, 10, null);
			
			Field[][] f = fm.getFields();
			
			int mines_actual = 0;
			for(int i = 0; i < fm.getRows(); i++) {
				for(int j = 0; j < fm.getColumns(); j++) {
					if (f[i][j].isMined()) mines_actual++;
				}
			}
			Assert.assertEquals(mines_expected, mines_actual);
		} catch (Exception e) {System.out.println("There was an error in the test.");}
	}
}
