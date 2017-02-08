package br.cefetrj.ppcic.alc.kmmhs.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.la4j.Matrices;
import org.la4j.Matrix;
import org.la4j.matrix.DenseMatrix;
import org.la4j.matrix.sparse.CRSMatrix;

/**
 * 
 * @author rtavares
 *
 */
public class Loader {
	
	public static CRSMatrix loadMatrixFromCSV(String file) throws Exception {
		
		BufferedReader br = new BufferedReader( new FileReader(Loader.class.getResource("/" + file).getPath()));
		
		return fromCSVStr(br).to(Matrices.CRS);
		
	}
	
	public static CRSMatrix loadMatrixFromMM(String file) throws Exception {
		
		BufferedReader br = new BufferedReader( new FileReader(Loader.class.getResource("/" + file).getPath()));
		
		String line = "";
		String mm = "";
        
        while((line = br.readLine()) != null)
        	mm += line;
		
        br.close();
        
		return Matrix.fromMatrixMarket(mm).to(Matrices.CRS);
		
	}
	
	private static Matrix fromCSVStr(BufferedReader br) {
	        
			try{
			
				Matrix result = DenseMatrix.zero(10, 10);
		        int rows = 0, columns = 0;
				
				String strLine = "";
		        
		        while((strLine = br.readLine()) != null){
		        	
		        	if (result.rows() == rows) {
		                result = result.copyOfRows((rows * 3) / 2 + 1);
		            }
		
		            StringTokenizer elements = new StringTokenizer(strLine, ", ");
		            int j = 0;
		            while (elements.hasMoreElements()) {
		                if (j == result.columns()) {
		                    result = result.copyOfColumns((j * 3) / 2 + 1);
		                }
		
		                double x = Double.valueOf(elements.nextToken());
		                result.set(rows, j++, x);
		            }
		
		            rows++;
		            columns = j > columns ? j : columns;
		               
		        }
		        
		        return result.copyOfShape(rows, columns);
		        
			}catch(Exception e){
				
				e.printStackTrace();
				return null;
				
			}
	        
	    }
	
}
