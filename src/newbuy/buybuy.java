package newbuy;

import simulation.Main;
import housedata.HAdata;

public class buybuy {
	public buybuy ( Main RF, int i, int j ){
		ReadFileFreezer FRF = new ReadFileFreezer();
		FRF.createNewFreezerList(".\\recycle\\Newdata.txt");
		Findfreezer FR = new Findfreezer();
		FR.findfreezerspec( Integer.parseInt( RF.getHouseList ().get ( i ).getHAList ().get ( j ).getSpec() ), FRF );
		FR.findfreezercost( RF.getHouseList ().get ( i ).getHAList ().get ( j ).getMaxValue() );
		freezer NF = FR.FindFreezerLast();
		new HAdata( "�①��", Integer.toString( NF.getSpec() ), NF.getCost(), RF.getHouseList ().get ( i ).getHAList ().get ( j ).getMinusDur(), RF.getHouseList ().get ( i ));
	}
}
