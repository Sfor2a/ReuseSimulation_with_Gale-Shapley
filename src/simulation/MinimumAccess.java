package simulation;

import mapdata.Rangefinder;

public class MinimumAccess {
	int Length = 0; //����
	
	public MinimumAccess ( int[][] RouteArray, int N, int Start, int Goal, Main CP ) { //�ŒZ�����߂�
		
		new Rangefinder (CP, RouteArray); //�ŒZ�o�H��T�����邽�߂̍s��쐬
		
		boolean Visited[] = new boolean[N]; //�ŒZ�������m�肵���n�_
		int Dist[] = new int[N]; //�ŏ����炻���܂ł̋���
		int prev[] = new int[N]; //�܂��ɂ����n�_
		int nodes[] = new int[N]; //�X�ԍ�
		for ( int i = 0; i < N; i++ ) {
			Visited[i] = false; //�S�Ă̊X�������ĂȂ���Ԃɂ���
			Dist[i] = Integer.MAX_VALUE; //�S�Ă̊X�܂ł̋����𖳌��ɂ���(2147483647
			nodes[i] = i; //�X�ԍ�����			
		}
		Dist[Start] = 0; //�ŏ��̒n�_�Ȃ̂œ��R������0�ɂȂ�
		prev[Start] = Start; // �ŏ��ɑI���������̑O�̒��͖����̂ŁA�Ƃ肠���������̒������Ă���
		int pos = Start; //���ݒn��ݒ�
		MinimumRouteSearch ( Visited, RouteArray, Dist, prev, pos, N, nodes ); //�ŏ��o�H�T��
		
		//�C�J�R���X�g���N�^�I���܂ŕ\���̃R�}���h�A�Ԃ����Ⴏ�ꕔ�̂����Ă������@�܂�o�H�̕\���@�������̂�Dist�Ɋi�[����Ă�̂Ŗ��Ȃ�
		@SuppressWarnings("unused")
		String str = Start + "����" + Goal + "�܂ł̍ŒZ���[�g��";
		int node = prev[Goal];
		String track = Integer.toString ( Goal );
		for ( int i = 0; i < N; i++ ) {
			track += node;
			if ( node == Start ) break;
			node = prev[node];
		}
		for ( int i = track.length () - 1; i >= 0; i--) str += " => " + track.charAt ( i );
		//if ( node == Start ) System.out.println ( str );
		//else System.out.println ( "���[�g�Ȃ�" );
		//System.out.println ( "����"+ Dist[Goal] );
		Length = Dist[Goal]; //�����Q�Ɨp
	}
	
	public int getLength () {
		return Length;		
	}
	
	private void MinimumRouteSearch ( boolean[] Visited, int[][] RouteArray, int[] Dist, int[] prev, int pos, int N, int[] nodes ) {
		while (true) {
			Visited[pos] = true; //���̏����������Visited�t���O�𗧂Ă�
			for ( int x = 0; x < N; x++ ) {
				if ( Visited[x] ) continue; //���łɂ������X�Ȃ炱�̏����̓X���[
				if ( RouteArray[pos][x] > 0) { //�o�H������ꍇ���̏��������
					int d = Dist[pos] + RouteArray[pos][x]; //���ݒn���玟�̒n�_�܂ł̋���
					if ( d < Dist[x] ) { //���̒n�_ + ���̒n�_�����X�����Ă���������菬������΍X�V
						Dist[x] = d;
						prev[x] = pos;
					}	
				}
			}
			//�܂��K��Ă��Ȃ����̒��ŁA�X�^�[�g�n�_����̋������ŏ��ɂȂ钬��I��
			int next = -1; //���̊X�T���t���O
			int nextDist = Integer.MAX_VALUE;
			for ( int node: nodes ) { 
				if ( Visited[node] ) continue;
				if ( Dist[node] < nextDist ) {
					next = node;
					nextDist = Dist[node];
				}
			}
			if ( next == -1 ) break;
			else pos = next;			
		}
	}
}
