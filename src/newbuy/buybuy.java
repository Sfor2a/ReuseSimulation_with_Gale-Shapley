package newbuy;

import housedata.HPAdata;
import housedata.ReadFile;

public class buybuy {
	public buybuy ( ReadFile RF, int i, int j ){
		ReadFileFreezer FRF = new ReadFileFreezer();
		FRF.createNewFreezerList(".\\recycle\\Newdata.txt");
		Findfreezer FR = new Findfreezer();
		FR.findfreezerspec( Integer.parseInt( RF.getHouseList ().get ( i ).getFurnitureList ().get ( j ).getSpec() ), FRF );
		FR.findfreezercost( RF.getHouseList ().get ( i ).getFurnitureList ().get ( j ).getMaxValue() );
		freezer NF = FR.FindFreezerLast();
		new HPAdata( "—â‘ ŒÉ", Integer.toString( NF.getSpec() ), NF.getCost(), RF.getHouseList ().get ( i ).getFurnitureList ().get ( j ).getMinusDur(), RF.getHouseList ().get ( i ));
	}
}
