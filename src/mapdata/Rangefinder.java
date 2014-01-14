package mapdata;

import simulation.Main;

public class Rangefinder {
	public Rangefinder ( Main CP, int [][] RouteArray ) {
		
		int PointNum = CP.getPitList ().size (); //�n�_�̐�
		
		for ( int i = 0; i < PointNum; i++ ) { //�n�_1���炭�肩����
			int RouteOutNum = CP.getPitList ().get ( i ).getRteOutList ().size ();
			for ( int j = 0; j < RouteOutNum; j++ ) {
				double x1 = CP.getPitList ().get ( i ).getRoutedataOut ( j ).getTo ().getX ();
				double y1 = CP.getPitList ().get ( i ).getRoutedataOut ( j ).getTo ().getY ();
				double x2 = CP.getPitList ().get ( i ).getX ();
				double y2 = CP.getPitList ().get ( i ).getY ();
				double Length = Math.sqrt ( ( x2 - x1 ) * ( x2 - x1 ) + ( y2 - y1 ) * ( y2 - y1 ) ); //���΂�Ă���2�_�Ԃ̋������v�Z
				
				String AiteName = CP.getPitList ().get ( i ).getRoutedataOut ( j ).getTo ().getName ();
				int l = 0;
				
				for ( int k = 0; k < PointNum; k++ ) { //�Ȃ����̃|�C���g���������āA���X�g�̔ԍ�����ɓ����
					if ( AiteName.equals( CP.getPitList ().get ( k ).getName () ) ){
						l = k; 
						break;
					}
				}
				
				int Costs = getRouteCosts ( CP, 
						CP.getPitList ().get ( i ).getRoutedataOut ( j ).getTo ().getName (), CP.getPitList ().get ( i ).getName() ); //���H�̃R�X�g�擾
				
				RouteArray[i][l] = (int) Length * Costs; //���������i������
				RouteArray[l][i] = (int) Length * Costs ; //���������i����
				//System.out.println((int)Length + ","+Costs);
			}
			
		}
		//System.out.println("�����܂�");
		
	}
	
	private int getRouteCosts ( Main CP, String nam1, String nam2 ) { //���̃R�X�g�𔭌�
		int ret = 0;
		for ( int i=0; i < CP.getRteList().size(); i++ ) {
			if ( CP.getRteList().get(i).getTo().getName().equals ( nam1 ) && CP.getRteList().get(i).getFrom().getName().equals ( nam2 ) ) {
				ret = CP.getRteList().get(i).getMoveCosts();
			}
		}
		return ret;
	}
}
