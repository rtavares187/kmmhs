package br.cefetrj.ppcic.alc.kmmhs.util;

import org.la4j.matrix.sparse.CRSMatrix;

/**
 * 
 * @author rtavares
 *
 */
public class Util {
	
	private static Util instance;
	
	public static Util getInstance(){
		
		if(instance == null)
			instance = new Util();
		
		return instance;
		
	}
	
	public CRSMatrix getA(CRSMatrix m){
		
		int r = m.rows();
		int c = m.columns() - 1;
		
		CRSMatrix ret = new CRSMatrix(r, c);
		
		for(int i = 0; i < r; i++){
			
			for(int j = 0; j < c; j++){
				
				ret.set(i, j, m.get(i, j));
				
			}
			
		}
		
		return ret;
		
	}
	
	public CRSMatrix getB(CRSMatrix m){
		
		int r = m.rows();
		int c = m.columns() - 1;
		
		CRSMatrix ret = new CRSMatrix(r, 1);
		
		for(int i = 0; i < r; i++){
			
			ret.set(i, 0, m.get(i, c));
			
		}
		
		return ret;
		
	}
	
}
